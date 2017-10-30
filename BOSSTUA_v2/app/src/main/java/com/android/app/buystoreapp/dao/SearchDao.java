package com.android.app.buystoreapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 尚帅波 on 2016/10/17.
 */
public class SearchDao {
    Context context;

    public SearchDao(Context context) {
        super();
        this.context = context;
        SearchDBHelper dbHelper = null;
        SQLiteDatabase database = null;
        try {
            if (dbHelper == null) {
                dbHelper = new SearchDBHelper(context);
                database = dbHelper.getReadableDatabase();
                String sql = "create table search(_id integer primary key,content text)";
                database.execSQL(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
            if (database != null) {
                database.close();
            }
        }
    }


    public void insert(String content) {
        SearchDBHelper dbHelper = null;
        SQLiteDatabase database = null;
        try {
            if (dbHelper == null) {
                dbHelper = new SearchDBHelper(context);
                database = dbHelper.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("content", content);
                database.insert("search", null, values);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
            if (database != null) {
                database.close();
            }
        }
    }


    public List<String> query() {
        List<String> list = new ArrayList<String>();
        SearchDBHelper dbHelper = null;
        SQLiteDatabase database = null;
        String content = null;
        try {
            if (dbHelper == null) {
                dbHelper = new SearchDBHelper(context);
                database = dbHelper.getReadableDatabase();
                String[] columns = {"content"};
                Cursor c = database.query("search", columns, null, null, null, null, "_id desc");
                while (c.moveToNext()) {
                    content = c.getString(c.getColumnIndex("content"));
                    list.add(content);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
            if (database != null) {
                database.close();
            }
        }
        return list;
    }


    public void delete() {
        SearchDBHelper dbHelper = null;
        SQLiteDatabase database = null;
        try {
            if (dbHelper == null) {
                dbHelper = new SearchDBHelper(context);
                database = dbHelper.getReadableDatabase();
                database.delete("search", null, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
            if (database != null) {
                database.close();
            }
        }
    }
}
