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
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    CursorAdapter mCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        //Gets a reference to an instance of the helper, and the listview in which search results will be displayed.
        final ListView listView = (ListView)findViewById(R.id.resultList);

        final Cursor mCursor = GloryholeOpenHelper.getInstance(SearchActivity.this).getGloryholeList();


         mCursorAdapter= new CursorAdapter(SearchActivity.this, mCursor,0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                //Creates a simple list view to be inflated
                return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

                //Sets the search result textview to the name and address pulled from the database by the cursor
                TextView textView = (TextView)view.findViewById(android.R.id.text1);

                String resultString = cursor.getString(cursor.getColumnIndex(GloryholeOpenHelper.COL_NAME))+ " " + cursor.getString(cursor.getColumnIndex(GloryholeOpenHelper.COL_ADDRESS));
                textView.setText(resultString);

                mCursorAdapter.changeCursor(cursor);
                mCursorAdapter.notifyDataSetChanged();

            }
        };
        listView.setAdapter(mCursorAdapter);


        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*String bleh = mCursorAdapter.getCursor().getString(mCursorAdapter.getCursor().getColumnIndex("_id"));
                Toast.makeText(SearchActivity.this, bleh, Toast.LENGTH_SHORT).show();*/

                //TODO what is the issue here?
                //Stores the ID of the row that was clicked and sends it to the Detail activity
                int dbID = Integer.valueOf(mCursorAdapter.getCursor().getString(mCursorAdapter.getCursor().getColumnIndex("_id")));
                Intent i = new Intent(SearchActivity.this, DetailActivity.class);
                i.putExtra("id", dbID);
                startActivity(i);
            }
        };


        //Sets the cursor adapter and the item click listener
        listView.setOnItemClickListener(listener);

        handleIntent(getIntent());
    }



    public boolean onCreateOptionsMenu(Menu menu){

        //Inflates the Search bar menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        //Retrieves Android's baked in search service, sets up the search view
        SearchManager searchManager = (SearchManager)SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //The following lines are what conducts the actual search using the query provided by the user.
                Cursor cursor = GloryholeOpenHelper.getInstance(SearchActivity.this).searchGloryholeListByName(query);

                mCursorAdapter.changeCursor(cursor);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            //The following lines are what conducts the actual search using the query provided by the user.
            String query = intent.getStringExtra(SearchManager.QUERY);

            Cursor cursor = GloryholeOpenHelper.getInstance(SearchActivity.this).searchGloryholeListByName(query);

            mCursorAdapter.changeCursor(cursor);



        }
    }

}