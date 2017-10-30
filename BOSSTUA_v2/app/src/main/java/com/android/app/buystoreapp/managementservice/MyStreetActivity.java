package com.android.app.buystoreapp.managementservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;

import java.util.List;



/**
 * 街道列表
 *  weilin
 */
public class MyStreetActivity extends Activity {
private  MyChoiceListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_street);
        ((TextView)findViewById(R.id.tv_title)).setText(getResources().getString(R.string.street_list));
        ListView lv_street = (ListView) findViewById(R.id.lv_street);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
        final List<String> lists = getIntent().getStringArrayListExtra("Streets");
Log.e("lin","lists==="+lists);
        adapter=new MyChoiceListAdapter(this,lists);
        lv_street.setAdapter(adapter);
        lv_street.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("streetName",lists.get(position));
                setResult(-1,intent);
                finish();
            }
        });
    }

}
