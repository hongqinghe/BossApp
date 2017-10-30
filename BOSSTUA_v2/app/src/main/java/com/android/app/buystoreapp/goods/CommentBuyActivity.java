package com.android.app.buystoreapp.goods;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.adapter.CommentListAdapter;
import com.android.app.buystoreapp.bean.GsonCommentBean;
import com.android.app.buystoreapp.wallet.ToastUtil;
import com.android.app.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.view.SwipeRefreshLayoutUpDown;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * $desc
 * Created by likaihang on 16/09/10.  alter by shangshuaibo on 16/10/06.
 */
public class CommentBuyActivity extends Activity implements View.OnClickListener,
        SwipeRefreshLayoutUpDown.OnRefreshListener, SwipeRefreshLayoutUpDown.OnLoadListener {
    private Context context;
    private ListView listView;
    private TextView tv_comment_title, tv_comment_all, tv_comment_best, tv_comment_middle,
            tv_comment_worst, tv_comment_pic;

    private List<GsonCommentBean.CommodityEvalList> list = new ArrayList<>();
    private CommentListAdapter adapter;
    private SwipeRefreshLayoutUpDown swipe;

    private String proId;   //商品ID
    private int indexId;  //评价类别
    private String proUserId; //发布商品人的ID

    private int totalPage;  //  总页数
    private int nowPage = 1; //  当前页
    private int badEvalNum; //   差评个数
    private int goodEvalNum; //  好评个数
    private int mediumEvalNum; //  中评个数
    private int imgIsNotNullNum; //  有图个数
    private int totalEvalNum; //总评价数

    private static final int LOAD_END = 100;  //加载结束
    private static final int LOAD_CANCLE = 101;  //加载取消
    private static final int REFRESH_END = 102;  //刷新结束
    private static final int REFRESH_CANCLE = 103;  //刷新取消

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.comment_activity_layout);

        context = this;

        proId = getIntent().getStringExtra("proId");
        indexId = getIntent().getIntExtra("indexId",0);
        proUserId = getIntent().getStringExtra("proUserId");

        initview();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOAD_END:
                    swipe.setLoading(false);
                    adapter.notifyDataSetChanged();
                    break;
                case LOAD_CANCLE:
                    ToastUtil.showMessageDefault(context, "数据获取失败");
                    swipe.setLoading(false);
                    break;
                case REFRESH_END:
                    tv_comment_all.setText("全部");
                    tv_comment_best.setText("好评(" + goodEvalNum + ")");
                    tv_comment_middle.setText("中评(" + mediumEvalNum + ")");
                    tv_comment_worst.setText("差评(" + badEvalNum + ")");
                    tv_comment_pic.setText("有图(" + imgIsNotNullNum + ")");


                    switch (Integer.valueOf(indexId)) {
                        case 0:
                            tv_comment_title.setText("全部(" + totalEvalNum + ")");
                            break;
                        case 1:
                            tv_comment_title.setText("好评(" + goodEvalNum + ")");
                            break;
                        case 2:
                            tv_comment_title.setText("中评(" + mediumEvalNum + ")");
                            break;
                        case 3:
                            tv_comment_title.setText("差评(" + badEvalNum + ")");
                            break;
                        case 4:
                            tv_comment_title.setText("有图(" + imgIsNotNullNum + ")");
                            break;
                    }

                    swipe.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                    break;
                case REFRESH_CANCLE:
                    ToastUtil.showMessageDefault(context, "数据获取失败");
                    swipe.setRefreshing(false);
                    break;

            }
        }
    };

    public void initview() {
        tv_comment_title = (TextView) findViewById(R.id.tv_comment_title);
        tv_comment_all = (TextView) findViewById(R.id.tv_comment_all);
        tv_comment_best = (TextView) findViewById(R.id.tv_comment_best);
        tv_comment_middle = (TextView) findViewById(R.id.tv_comment_middle);
        tv_comment_worst = (TextView) findViewById(R.id.tv_comment_worst);
        tv_comment_pic = (TextView) findViewById(R.id.tv_comment_pic);

        tv_comment_all.setOnClickListener(this);
        tv_comment_best.setOnClickListener(this);
        tv_comment_middle.setOnClickListener(this);
        tv_comment_worst.setOnClickListener(this);
        tv_comment_pic.setOnClickListener(this);

        findViewById(R.id.ib_back).setOnClickListener(this);
        listView = (ListView) findViewById(R.id.lv_comment_list);
        adapter = new CommentListAdapter(this, list);
        listView.setAdapter(adapter);

        swipe = (SwipeRefreshLayoutUpDown) findViewById(R.id.comment_list_container);
        swipe.setOnLoadListener(this);
        swipe.setOnRefreshListener(this);
        swipe.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light, R.color.holo_orange_light,
                R.color.holo_red_light);
        swipe.setLoadNoFull(false);
        refresh();
    }

    private void refresh() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd","queryProductEvaluate");
            obj.put("indexId",indexId);
            obj.put("proId",proId);
            obj.put("proUserId",proUserId);
            obj.put("nowPage",nowPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("评价提交数据---",obj.toString());
        HttpUtils.post(context, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("获取评价内容---",new String(bytes));
                Gson gson = new Gson();
                try {
                    GsonCommentBean gsonCommentBean = gson.fromJson(new String(bytes), new
                            TypeToken<GsonCommentBean>() {
                            }.getType());
                    String result = gsonCommentBean.getResult();
                    if (result.equals("0")) {
                        totalPage = gsonCommentBean.getTotaPage();
                        totalEvalNum = gsonCommentBean.getAllEvalNum();
                        badEvalNum = gsonCommentBean.getBadEvalNum();
                        goodEvalNum = gsonCommentBean.getGoodEvalNum();
                        mediumEvalNum = gsonCommentBean.getMediumEvalNum();
                        imgIsNotNullNum = gsonCommentBean.getImgIsNotNullNum();

                        list.clear();
                        list.addAll(gsonCommentBean.getCommodityEvalList());
                        if (list == null || list.size() == 0) {
                            ToastUtil.showMessageDefault(context, "没有评价内容");
                        }
                        handler.sendEmptyMessage(REFRESH_END);
                    } else {
                        handler.sendEmptyMessage(REFRESH_CANCLE);
                    }
                } catch (JsonSyntaxException e) {
                    handler.sendEmptyMessage(REFRESH_CANCLE);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                handler.sendEmptyMessage(REFRESH_CANCLE);
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {

            }
        });
    }


    private void load() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("cmd","queryProductEvaluate");
            obj.put("indexId",indexId);
            obj.put("proId",proId);
            obj.put("proUserId",proUserId);
            obj.put("nowPage",nowPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtils.post(context, obj, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Gson gson = new Gson();
                try {
                    GsonCommentBean gsonCommentBean = gson.fromJson(new String(bytes), new
                            TypeToken<GsonCommentBean>() {
                            }.getType());
                    String result = gsonCommentBean.getResult();
                    if (result.equals("0")) {
                        totalPage = gsonCommentBean.getTotaPage();
                        badEvalNum = gsonCommentBean.getBadEvalNum();
                        goodEvalNum = gsonCommentBean.getGoodEvalNum();
                        mediumEvalNum = gsonCommentBean.getMediumEvalNum();
                        imgIsNotNullNum = gsonCommentBean.getImgIsNotNullNum();

                        list.addAll(gsonCommentBean.getCommodityEvalList());
                        handler.sendEmptyMessage(LOAD_END);
                    } else {
                        handler.sendEmptyMessage(LOAD_CANCLE);
                    }
                } catch (JsonSyntaxException e) {
                    handler.sendEmptyMessage(LOAD_CANCLE);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                handler.sendEmptyMessage(REFRESH_CANCLE);
            }
        }, new HttpUtils.RequestNetworkError() {
            @Override
            public void networkError() {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_comment_all:
                listView.setSelection(0);
                nowPage = 1;
                indexId = 0;
                refresh();
                break;
            case R.id.tv_comment_best:
                listView.setSelection(0);
                nowPage = 1;
                indexId = 1;
                refresh();
                break;
            case R.id.tv_comment_middle:
                listView.setSelection(0);
                nowPage = 1;
                indexId = 2;
                refresh();
                break;
            case R.id.tv_comment_worst:
                listView.setSelection(0);
                nowPage = 1;
                indexId = 3;
                refresh();
                break;
            case R.id.tv_comment_pic:
                listView.setSelection(0);
                nowPage = 1;
                indexId = 4;
                refresh();
                break;
        }
    }

    @Override
    public void onRefresh() {
        nowPage = 1;
        refresh();
    }

    @Override
    public void onLoad() {
        if (nowPage < totalPage) {
            nowPage++;
            load();
        } else {
            ToastUtil.showMessageDefault(context, "没有更多数据了");
            handler.sendEmptyMessage(LOAD_END);
        }
    }
}
