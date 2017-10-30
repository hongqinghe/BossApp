package com.android.app.buystoreapp.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystore.utils.CameraUtils;
import com.android.app.buystore.utils.ImageUtils;
import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.CommodityBean;
import com.android.app.buystoreapp.bean.GsonBackOnlyResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.ResType;
import com.lidroid.xutils.view.annotation.ResInject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import java.io.ByteArrayOutputStream;

public class MyOrderAssessActivity extends Activity {
    private static final int MAX_LENGTH = 140;
    private static final int CODE_GALLERY_REQUEST = 0xa1;
    private static final int CODE_CAMERA_REQUEST = 0xa2;
    private static final int CODE_RESIZE_REQUEST = 0xa3;
    private static final int ICON_WIDTH_AND_HEIGHT = 200;

    private String mCurrentUserName;
    private CommodityBean mCommodityBean;

    private String mRatingScore = "0";
    private String mTalkContent = "";

    @ResInject(id = R.string.web_url, type = ResType.String)
    private String url;
    @ViewInject(R.id.id_custom_title_text)
    private TextView mTitleText;

    @ViewInject(R.id.id_order_assess_rating_bar_description)
    private RatingBar mRatingBarDescription;
    @ViewInject(R.id.id_order_assess_content)
    private EditText mAssessContentEdit;

    @ViewInject(R.id.id_order_assess_pickOne_image)
    private ImageView mAssessImagePickOne;
    @ViewInject(R.id.id_order_assess_pickOne_container)
    private View mAssessImagePickOneContainer;
    private Bitmap mtalkImage;

    @ViewInject(R.id.id_order_assess_submit)
    private Button mSubmitButton;

    @ViewInject(R.id.id_order_assess_commodity_img)
    private ImageView mAssessCommodityImage;

    @ViewInject(R.id.id_order_assess_content_rest)
    private TextView mAssessContentRest;
    private String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.order_assess_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                R.layout.custom_action_bar);

        ViewUtils.inject(this);
        mTitleText.setText("评价商品");
        orderId = getIntent().getStringExtra("orderId");
        mCurrentUserName = SharedPreferenceUtils.getCurrentUserInfo(this)
                .getUserName();
        mCommodityBean = (CommodityBean) getIntent().getSerializableExtra(
                "commodity");

        LogUtils.d("assess commodity name=" + mCommodityBean.getProName());
        String commodityImg = mCommodityBean.getUserHeadIcon();
        if (!TextUtils.isEmpty(commodityImg)) {
            Picasso.with(this).load(commodityImg).into(mAssessCommodityImage);
        }

        mAssessContentEdit.addTextChangedListener(new EditWatcher());
        mAssessContentEdit
                .setFilters(new InputFilter[] { new InputFilter.LengthFilter(
                        MAX_LENGTH) });
        mRatingBarDescription
                .setOnRatingBarChangeListener(descriptionBarChangeListener);
        mSubmitButton.setOnClickListener(submitClickListener);
        mSubmitButton.setBackgroundResource(R.drawable.app_btn_disabled_shape);
        mSubmitButton.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        switch (requestCode) {
        case CODE_CAMERA_REQUEST:
            mtalkImage = (Bitmap) data.getExtras().get("data");
            mAssessImagePickOne.setImageBitmap(mtalkImage);
            mAssessImagePickOneContainer.setVisibility(View.VISIBLE);
            break;
        case CODE_GALLERY_REQUEST:
            String path = CameraUtils.getPhotoPathByLocalUri(this, data);
            if (path != null
                    && (path.endsWith(".jpg") || path.endsWith(".png")
                            || path.endsWith(".PNG") || path.endsWith(".JPG"))) {
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, option);
                option.inSampleSize = ImageUtils.calculateInSampleSize(option,
                        ICON_WIDTH_AND_HEIGHT, ICON_WIDTH_AND_HEIGHT);
                option.inJustDecodeBounds = false;
                mtalkImage = BitmapFactory.decodeFile(path, option);
                mAssessImagePickOne.setImageBitmap(mtalkImage);
                mAssessImagePickOneContainer.setVisibility(View.VISIBLE);
            }
            break;
        case CODE_RESIZE_REQUEST:

            break;
        default:
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CODE_RESIZE_REQUEST);
    }

    @OnClick(R.id.id_custom_back_image)
    public void onCustomBarBackClicked(View v) {
        switch (v.getId()) {
        case R.id.id_custom_back_image:
            this.finish();
            break;
        default:
            break;
        }
    }

    @OnClick(R.id.id_order_assess_image)
    public void onPickImageClicked(View v) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.modify_icon_dialog_title)
                .setItems(R.array.modify_icon_dialog_choices,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int which) {
                                if (which == 0) {
                                    CameraUtils.openCamera(
                                            MyOrderAssessActivity.this,
                                            CODE_CAMERA_REQUEST);
                                } else if (which == 1) {
                                    CameraUtils.openPhotos(
                                            MyOrderAssessActivity.this,
                                            CODE_GALLERY_REQUEST);
                                }
                            }
                        }).show();
    }

    @OnClick(R.id.id_order_assess_pickOne_del)
    public void onPickOneDelete(View v) {
        mAssessImagePickOneContainer.setVisibility(View.GONE);
    }

    private OnRatingBarChangeListener descriptionBarChangeListener = new OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar arg0, float rating, boolean arg2) {
            mRatingBarDescription.setRating(rating);
            mRatingScore = String.valueOf(rating);
           int index = mRatingScore.indexOf(".");
           mRatingScore = mRatingScore.substring(0, index);
            LogUtils.d("mRatingScore=" + mRatingScore);
        }
    };

    private String bitmaptoString(Bitmap bitmap) {
        if (bitmap == null)
            return "";
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 30, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    private OnClickListener submitClickListener = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams requestParams = new RequestParams();
            String param = "{\"cmd\":\"talkCommodity\",\"userName\":\"aaa\",\"commodityID\":\"bbb\",\"talkContent\":\"ccc\",\"orderID\":\""+orderId+"\",\"rateScore\":\""+mRatingScore+"\",\"talkImage\":\"ddd\"}";
            param = param.replace("aaa", mCurrentUserName);
            param = param.replace("bbb", mCommodityBean.getProId());
            param = param.replace("ccc", mTalkContent);
            param = param.replace("ddd", bitmaptoString(mtalkImage));
           // param = param.replace("eee", mRatingScore);
            requestParams.put("json", param);
            LogUtils.d("talkCommodity,param= " + param);

            client.post(url, requestParams, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                    try {
                    	Log.d("lulu","商品评价"+new String(arg2));
                        Gson gson = new Gson();
                        GsonBackOnlyResult gsonBackOnlyResult = gson.fromJson(
                                new String(arg2),
                                new TypeToken<GsonBackOnlyResult>() {
                                }.getType());
                        String result = gsonBackOnlyResult.getResult();
                        if ("0".equals(result)) {
                            Toast.makeText(MyOrderAssessActivity.this, "评价成功",
                                    Toast.LENGTH_SHORT).show();
                            MyOrderAssessActivity.this.finish();
                        } else {
                            Toast.makeText(MyOrderAssessActivity.this,
                                    "提交失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        LogUtils.e("talkCommodity error:", e);
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                        Throwable arg3) {

                }
            });
        }
    };

    private class EditWatcher implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            if (s.length() == 0) {
                mAssessContentRest.setText(MAX_LENGTH + "/" + MAX_LENGTH);
                mTalkContent = "";
                mSubmitButton
                        .setBackgroundResource(R.drawable.app_btn_disabled_shape);
                mSubmitButton.setEnabled(false);
            } else {
                int length = MAX_LENGTH - s.length();
                mAssessContentRest.setText(length + "/" + MAX_LENGTH);
                mTalkContent = s.toString();
                mSubmitButton
                        .setBackgroundResource(R.drawable.app_btn_enabled_shape);
                mSubmitButton.setEnabled(true);
            }
            LogUtils.d("mTalkContent=" + mTalkContent);
        }
    }
}
