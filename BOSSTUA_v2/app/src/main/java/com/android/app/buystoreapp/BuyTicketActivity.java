package com.android.app.buystoreapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.buystoreapp.base.BaseAct;
import com.android.app.bystoreapp.dialog.TicketCountDialog;
import com.android.app.bystoreapp.dialog.TicketCountDialog.OnOtherTicketChooseListener;

public class BuyTicketActivity extends BaseAct implements OnClickListener {

    private int ids[] = {R.id.count10, R.id.count20, R.id.count50, R.id.countother};
    private int counts[] = {10, 20, 50, 0};
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        BindClick();
        initView();
    }

    private void initView() {
        ((TextView)findViewById(R.id.tv_title)).setText(getResources().getString(R.string.get_boss));
        findViewById(R.id.iv_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                BuyTicketActivity.this.finish();
            }
        });
    }

    private void BindClick() {
        findViewById(R.id.BuyBtn).setOnClickListener(this);
        findViewById(R.id.countother).setOnClickListener(this);
        findViewById(R.id.count10).setOnClickListener(this);
        findViewById(R.id.count20).setOnClickListener(this);
        findViewById(R.id.count50).setOnClickListener(this);
        //findViewById(R.id.ImageBack).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.countother:
                OtherPrice();
                break;
            case R.id.count10:
                Choose(0);
                break;
            case R.id.count20:
                Choose(1);
                break;
            case R.id.count50:
                Choose(2);
                break;
    /*	case R.id.ImageBack:
            finish();
			break;*/
            case R.id.BuyBtn:
                if (count == 0) {
                    Toast.makeText(BuyTicketActivity.this, "请选择购买数量", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(BuyTicketActivity.this, BossTicketPay.class);
                intent.putExtra("count", count);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    private void OtherPrice() {
        findViewById(ids[3]).setBackgroundResource(R.drawable.buy_ticket_type_btn_yellow);
        ((TextView) findViewById(ids[3])).setTextColor(getResources().getColor(R.color
                .activity_bac));
        for (int i = 0; i < 3; i++) {
            findViewById(ids[i]).setBackgroundResource(R.drawable.buy_ticket_type_btn_green);
            ((TextView) findViewById(ids[i])).setTextColor(getResources().getColor(R.color
                    .bill_text_lv));
        }

        TicketCountDialog dialog = new TicketCountDialog(this);
        dialog.setOnOtherTicketChooseListener(new OnOtherTicketChooseListener() {

                                                  @Override
                                                  public void otherPrice(int price) {

                                                      counts[3] = price;
                                                      Choose(3);
                                                  }
                                              }

        );
        dialog.show();
    }

    private void Choose(int index) {
        if (index != 3) {
            counts[3] = 0;
        }
        for (int i = 0; i < 4; ++i) {
            if (index == i) {
                findViewById(ids[i]).setBackgroundResource(R.drawable.buy_ticket_type_btn_yellow);
                ((TextView) findViewById(R.id.Price)).setText("" + counts[i]);
                ((TextView) findViewById(ids[i])).setTextColor(getResources().getColor(R.color
                        .activity_bac));
                count = counts[i];
            } else {
                findViewById(ids[i]).setBackgroundResource(R.drawable.buy_ticket_type_btn_green);
                ((TextView) findViewById(ids[i])).setTextColor(getResources().getColor(R.color
                        .bill_text_lv));
            }
        }
    }

	
	/*
    private void Init(){
		ListView mList = (ListView)findViewById(R.id.ListView);
		mList.setAdapter(new MyAdapter());
	}*/

	/*
    class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = View.inflate(BuyTicketActivity.this, R.layout.item_boss_ticket, null);
			return convertView;
		}
		
	}*/
}
