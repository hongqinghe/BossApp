package com.android.app.buystoreapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * 街道列表
 * Created by 尚帅波 on 2016/10/3.
 */
public class StreetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_street);
        ListView lv_street = (ListView) findViewById(R.id.lv_street);

        final List<String> lists = getIntent().getStringArrayListExtra("Streets");

        lv_street.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lists));
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
