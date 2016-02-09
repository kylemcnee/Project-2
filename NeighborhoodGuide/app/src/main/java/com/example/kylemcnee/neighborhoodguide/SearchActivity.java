package com.example.kylemcnee.neighborhoodguide;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Cursor cursor = GloryholeOpenHelper.getInstance(SearchActivity.this).getGloryholeList();


    }
}
