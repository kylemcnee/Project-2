package com.example.kylemcnee.neighborhoodguide;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        GloryholeOpenHelper helper = GloryholeOpenHelper.getInstance(SearchActivity.this);


        ListView listView = (ListView)findViewById(R.id.resultList);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            //TODO Something has to be done with this query, but i don't...know...what...
            String query = intent.getStringExtra(SearchManager.QUERY);

            Cursor nameCursor = helper.searchGloryholeListByName(query);
            Cursor addressCursor = helper.searchGloryholeListByAddress(query);

            if (nameCursor != null && nameCursor.getCount() > 0){
                    cursorAdapter.swapCursor(nameCursor);
            }else if (addressCursor != null && addressCursor.getCount() > 0){
                    cursorAdapter.swapCursor(addressCursor);
            }


        }

        mCursor = GloryholeOpenHelper.getInstance(SearchActivity.this).getGloryholeList();

        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(listener);

    }

    CursorAdapter cursorAdapter = new CursorAdapter(SearchActivity.this, mCursor,0) {
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

                TextView textView = (TextView)view.findViewById(android.R.id.text1);
                String resultString = mCursor.getString(mCursor.getColumnIndex(GloryholeOpenHelper.COL_NAME))+ " " + mCursor.getString(mCursor.getColumnIndex(GloryholeOpenHelper.COL_ADDRESS));
                textView.setText(resultString);
        }
    };

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //TODO send the row to the detail activity

            Intent i = new Intent(SearchActivity.this, FavoritesActivity.class);
            startActivity(i);
        }
    };



    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager)SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);
        searchManager.getSearchableInfo(getComponentName());

        return super.onCreateOptionsMenu(menu);
    }


}