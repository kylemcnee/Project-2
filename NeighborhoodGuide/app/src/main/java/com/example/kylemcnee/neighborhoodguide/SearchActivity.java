package com.example.kylemcnee.neighborhoodguide;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ListView listView = (ListView)findViewById(R.id.resultList);


        Cursor cursor = GloryholeOpenHelper.getInstance(SearchActivity.this).getGloryholeList();
        CursorAdapter cursorAdapter = new CursorAdapter(SearchActivity.this, cursor,0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                /*TextView textView = (TextView)view.findViewById(android.R.id.text1);
                String resultString = cursor.getString(cursor.getColumnIndex(GloryholeOpenHelper.COL_NAME))+ " " + cursor.getString(cursor.getColumnIndex(GloryholeOpenHelper.COL_ADDRESS));
                textView.setText(resultString);*/


            }
        }; listView.setAdapter(cursorAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
