package com.android.app.buystore.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.app.buystoreapp.R;

public class CustomLinearlayout extends LinearLayout {
    private Context context;

    private LinearLayout mContainer;
    private ImageView mImageView;
    private TextView mTextView;

    private String txtContent;
    private float txtSize;
    private int imgSrc;
    private int txtColor;

    public String getTxtContent() {
        return txtContent;
    }

    public void setTxtContent(String txtContent) {
        this.txtContent = txtContent;
    }

    public float getTxtSize() {
        return txtSize;
    }

    public void setTxtSize(float txtSize) {
        this.txtSize = txtSize;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getTxtColor() {
        return txtColor;
    }

    public void setTxtColor(int txtColor) {
        this.txtColor = txtColor;
    }

    public void setImgAndText(int imgSrc, int txtColor) {
        this.imgSrc = imgSrc;
        this.txtColor = txtColor;
        invalidate();
    }

    public CustomLinearlayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public CustomLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();

        TypedArray styleArray = context.obtainStyledAttributes(attrs,
                R.styleable.CustomLinearLayout);
        this.setTxtContent(styleArray
                .getString(R.styleable.CustomLinearLayout_txtContent));
        this.setTxtSize(styleArray.getInt(
                R.styleable.CustomLinearLayout_txtSize,
                R.dimen.custom_layout_text_default_size));
        this.setTxtColor(styleArray.getColor(
                R.styleable.CustomLinearLayout_txtColor, Color.BLACK));
        this.setImgSrc(styleArray.getResourceId(
                R.styleable.CustomLinearLayout_imageSrc, R.drawable.ic_default));

        int c_h = (int) styleArray.getDimension(
                R.styleable.CustomLinearLayout_containerHight,
                R.dimen.custom_layout_container_default_height);
        int c_w = (int) styleArray.getDimension(
                R.styleable.CustomLinearLayout_containerWidth,
                R.dimen.custom_layout_container_default_width);
        mContainer.getLayoutParams().width = c_w;
        mContainer.getLayoutParams().height = c_h;
        mContainer.invalidate();

        int i_h = (int) styleArray.getDimension(
                R.styleable.CustomLinearLayout_imageHight,
                R.dimen.custom_layout_image_default_height);
        int i_w = (int) styleArray.getDimension(
                R.styleable.CustomLinearLayout_imageWidth,
                R.dimen.custom_layout_image_default_width);

        mImageView.getLayoutParams().width = i_w;
        mImageView.getLayoutParams().height = i_h;
        mImageView.setImageResource(this.getImgSrc());
        mImageView.invalidate();

        mTextView.setText(this.getTxtContent());
        mTextView.setTextSize(this.getTxtSize());
        mTextView.setTextColor(this.getTxtColor());
        mTextView.invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mTextView.setTextColor(this.getTxtColor());
        mImageView.setImageResource(this.getImgSrc());
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_linear_layout, this);
        mContainer = (LinearLayout) view
                .findViewById(R.id.id_custom_layout_container);
        mImageView = (ImageView) view.findViewById(R.id.id_custom_layout_image);
        mTextView = (TextView) view.findViewById(R.id.id_custom_layout_text);

    }
}
