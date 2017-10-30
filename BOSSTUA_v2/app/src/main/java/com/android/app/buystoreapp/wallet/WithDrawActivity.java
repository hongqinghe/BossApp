package com.android.app.buystoreapp.wallet;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.app.buystore.utils.SharedPreferenceUtils;
import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.buystoreapp.bean.BalanceWithdrawalsBean;
import com.android.app.buystoreapp.bean.MarksBean;
import com.android.app.buystoreapp.bean.YhkListBean;
import com.android.app.buystoreapp.bean.ZfbListBean;
import com.android.app.buystoreapp.managementservice.ExplainWebViewActivity;
import com.android.app.buystoreapp.managementservice.SubscribeActivity;
import com.android.app.buystoreapp.other.RealCertifiActivity;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WithDrawActivity extends BaseAct implements View.OnClickListener {

    private ImageView iv_with_draw_select_alipay, iv_with_draw_select_unionpay;
    private TextView tv_with_draw_replace_unionpay;
    private RelativeLayout rl_with_draw_add_alipay, rl_with_draw_add_unionpay;
    private EditText et_hint;
    private TextView tv_with_draw_money;
    private boolean isSelectedzfb = true;
    private boolean isSelectedyhk = false;
    private String userId;

    private TextView tv_explain;

    private Button btn_with_draw;

    private TextView zfb_name;
    private TextView yhk_name;

    private List<YhkListBean> yhkList = new ArrayList<>();
    private List<ZfbListBean> zfbList = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SubscribeActivity.HANDLE_LOADMORE:
                    et_hint.setHint("提现额度" + txMoney);
                    if (zfbList.size() != 0) {
                        zfb_name.setText(zfbList.get(0).getPayTreasureNum() + "");
                    }
                    if (yhkList.size() != 0) {
                        yhk_name.setText(yhkList.get(0).getVisaName() + "(" + yhkList.get(0).getVisanoFour() + ")");
                    } else {
                        yhk_name.setText(getResources().getString(R.string.add_yhk));
                    }
                    break;
            }
        }
    };
    private String flag = "1";
    private double txMoney;
    private int bindingMark1;
    private Dialog showCertifiedDialog;

    //    银联卡
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);

        initView();
        initErrorPage();
        addIncludeLoading(true);
    }



    @Override
    protected void onResume() {
        super.onResume();
        userId = SharedPreferenceUtils.getCurrentUserInfo(this).getUserId();
        saveLevelMark();
        bindingMark1 = SharedPreferenceUtils.getMarkInfo(this).getBindingMark1();

        startWhiteLoadingAnim();
        load();
    }

    @Override
    protected void load() {
        super.load();
        selBalanceWithdrawals();
    }

    /**
     * 查询认证状态
     */
    private void saveLevelMark() {
        startWhiteLoadingAnim();
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "getMarks");
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                MarksBean marksBean = gson.fromJson(new String(bytes), new
                        TypeToken<MarksBean>() {
                        }.getType());
                String result = marksBean.getResult();
                MarksBean.BeanBean bean = marksBean.getBean();
                if ("0".equals(result)) {
                    SharedPreferenceUtils.saveMarkInfo(bean, mContext);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable
                    throwable) {
                stopLoadingAnim();
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                stopLoadingAnim();
            }
        });
    }

    /**
     * 查看余额接口
     */
    private void selBalanceWithdrawals() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "selBalanceWithdrawals");
            obj.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("查看余额", "查看余额提交数据  obj==" + obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.e("查看余额", "查看余额返回数据 bytes==" + new String(bytes));
                stopLoadingAnim();
                hideErrorPageState();
                Gson gson = new Gson();
                BalanceWithdrawalsBean balanceWithdrawalsBean = gson.fromJson(new String(bytes), new TypeToken<BalanceWithdrawalsBean>() {
                }.getType());
                txMoney = balanceWithdrawalsBean.getBean().getTxMoney();
                String result = balanceWithdrawalsBean.getResult();
                if ("0".equals(result)) {
                    yhkList.clear();
                    zfbList.clear();
                    yhkList.addAll(balanceWithdrawalsBean.getBean().getYhkList());
                    zfbList.addAll(balanceWithdrawalsBean.getBean().getZfbList());
                    tv_with_draw_money.setText("￥" + balanceWithdrawalsBean.getBean().getTotalMoney() + "");


                    mHandler.obtainMessage(SubscribeActivity.HANDLE_LOADMORE).sendToTarget();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(WithDrawActivity.this, getResources().getString(R.string.service_error_hint));
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }

    private void initView() {
        ((TextView) findViewById(R.id.tv_title)).setText(getResources().getString(R.string.with_draw));
        findViewById(R.id.iv_back).setOnClickListener(this);

        zfb_name = (TextView) findViewById(R.id.zfb_name);
        yhk_name = (TextView) findViewById(R.id.yhk_name);
        iv_with_draw_select_alipay = (ImageView) findViewById(R.id.iv_with_draw_select_alipay);
        iv_with_draw_select_unionpay = (ImageView) findViewById(R.id.iv_with_draw_select_unionpay);
        tv_with_draw_replace_unionpay = (TextView) findViewById(R.id.tv_with_draw_replace_unionpay);
        rl_with_draw_add_alipay = (RelativeLayout) findViewById(R.id.rl_with_draw_add_alipay);
        rl_with_draw_add_unionpay = (RelativeLayout) findViewById(R.id.rl_with_draw_add_unionpay);

        tv_explain = (TextView) findViewById(R.id.tv_explain);
        tv_with_draw_money = (TextView) findViewById(R.id.tv_with_draw_money);
        btn_with_draw = (Button) findViewById(R.id.btn_with_draw);
        et_hint = (EditText) findViewById(R.id.et_hint);
        tv_explain.setOnClickListener(this);
        btn_with_draw.setOnClickListener(this);
        iv_with_draw_select_alipay.setOnClickListener(this);
        iv_with_draw_select_unionpay.setOnClickListener(this);
        tv_with_draw_replace_unionpay.setOnClickListener(this);
        rl_with_draw_add_alipay.setOnClickListener(this);
        rl_with_draw_add_unionpay.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_with_draw_select_alipay:
                isSelectedzfb = true;
                isSelectedyhk = false;
                iv_with_draw_select_alipay.setImageResource(R.drawable.with_draw_selected);
                iv_with_draw_select_unionpay.setImageResource(R.drawable.with_draw_unselected);
                flag = "1";
                break;
            case R.id.iv_with_draw_select_unionpay:
                isSelectedzfb = false;
                isSelectedyhk = true;
                iv_with_draw_select_alipay.setImageResource(R.drawable.with_draw_unselected);
                iv_with_draw_select_unionpay.setImageResource(R.drawable.with_draw_selected);
                flag = "2";
                break;
            case R.id.tv_with_draw_replace_unionpay:
                Intent intent = new Intent(this, SelectUnionpayActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_with_draw_add_alipay:
                if (zfbList.size() >= 1) {
                    ToastUtil.showMessageDefault(this, getResources().getString(R.string.bind_one_zfb));
                } else {
                    Intent intent1 = new Intent(this, AddAlipayActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.rl_with_draw_add_unionpay:
                if (yhkList.size() >= 1) {
                    ToastUtil.showMessageDefault(this, getResources().getString(R.string.bind_one_yhk));
                } else {
                    Intent intent2 = new Intent(this, AddUnionpayActivity.class);
                    startActivity(intent2);
                }
                break;

            case R.id.btn_with_draw:
                if (bindingMark1 == 1) {
                    if (!TextUtils.isEmpty(et_hint.getText().toString().trim())) {
                        if (Integer.valueOf(et_hint.getText().toString().trim()) > 10000) {
                            ToastUtil.showMessageDefault(this, getResources().getString(R.string.max_money));
                        } else if (Integer.valueOf(et_hint.getText().toString().trim()) < 100) {
                            ToastUtil.showMessageDefault(this, getResources().getString(R.string.min_money));
                        } else {
                            if (Double.valueOf(et_hint.getText().toString().trim()) > txMoney) {
                                Log.e("查询提现上限", "" + txMoney);
                                ToastUtil.showMessageDefault(this, getResources().getString(R.string.tx_number_hint));
                            } else {
                                if (isSelectedzfb == true) {
                                    if ((getResources().getString(R.string.zfb)).equals(zfb_name.getText().toString().trim())) {
                                        ToastUtil.showMessageDefault(this, getResources().getString(R.string.choose_zfb));
                                        Intent zfb = new Intent(WithDrawActivity.this, AddAlipayActivity.class);
                                        startActivity(zfb);
                                    } else {
                                        startWhiteLoadingAnim();
                                        addApplyWithdrawalsRecord();
                                    }
                                } else if (isSelectedyhk == true) {
                                    if ((getResources().getString(R.string.add_yhk)).equals(yhk_name.getText().toString().trim())) {
                                        ToastUtil.showMessageDefault(this, getResources().getString(R.string.choose_yhk));
                                        Intent yhk = new Intent(WithDrawActivity.this, AddUnionpayActivity.class);
                                        startActivity(yhk);
                                    } else {
                                        startWhiteLoadingAnim();
                                        addApplyWithdrawalsRecord();
                                    }
                                }
                            }
                        }

                    } else {
                        ToastUtil.showMessageDefault(this, getResources().getString(R.string.tx_number));
                    }
                } else {
                    showCertifiedDialog();
                }
                break;
            case R.id.tv_explain:
                Intent i = new Intent(WithDrawActivity.this, ExplainWebViewActivity.class);
                i.putExtra("flag", 2000);
                startActivity(i);
                break;
            case R.id.iv_back:
                this.finish();
                break;
        }
    }

    /**
     * 提现
     */
    private void addApplyWithdrawalsRecord() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd", "addApplyWithdrawalsRecord");
            obj.put("userId", userId);
            obj.put("withdrawalStyle", flag);

            if (flag == "1") {
                if (zfbList != null && zfbList.size() > 0) {
                    obj.put("withdrawalId", zfbList.get(0).getBindPayTreasureId());
                }
            } else if (flag == "2") {
                if (yhkList != null && yhkList.size() > 0) {
                    obj.put("withdrawalId", yhkList.get(0).getId());
                }
            }
            obj.put("txMoney", et_hint.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("提现提交数据 obj==", obj.toString());
        HttpUtils.post(mContext, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stopLoadingAnim();
                Log.e("提现返回数据 bytes==", new String(bytes));
                try {
                    JSONObject object = new JSONObject(new String(bytes));
                    String result = object.getString("result");
                    String resultNote = object.getString("resultNote");
                    if ("0".equals(result)) {
                        et_hint.setHint("每日最多可取额度" + txMoney);
                        ToastUtil.showMessageDefault(WithDrawActivity.this, "提现申请成功");
                        et_hint.setText("");
                        load();
                    } else if ("1".equals(result)) {
                        ToastUtil.showMessageDefault(WithDrawActivity.this, resultNote);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                stopLoadingAnim();
                ToastUtil.showMessageDefault(WithDrawActivity.this, getResources().getString(R.string.service_error_hint));

            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {
                showErrorPageState(SERVEICE_ERR_FLAG);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    private void showCertifiedDialog()  {
        View layout = this.getLayoutInflater().inflate(R.layout
                .certified_dialog, null);
        Button btn_true = (Button) layout.findViewById(R.id.btn_true);
        ImageButton btn_false = (ImageButton) layout.findViewById(R.id.btn_false);
        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCertifiedDialog.dismiss();

            }
        });
        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WithDrawActivity.this,RealCertifiActivity.class);
                startActivity(intent);
                showCertifiedDialog.dismiss();
            }
        });
        showCertifiedDialog = new Dialog(this, R.style.Dialog1);
        showCertifiedDialog.setContentView(layout);
        showCertifiedDialog.show();
    }
}
