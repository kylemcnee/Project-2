package com.example.kylemcnee.neighborhoodguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    ListView favoritesList = (ListView)findViewById(R.id.favoritesList);
    ArrayList<String> favoritesArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ArrayAdapter<String> favoritesAdapter = new ArrayAdapter<String>(FavoritesActivity.this, android.R.layout.simple_list_item_1, favoritesArray);

        favoritesList.setAdapter(favoritesAdapter);
        favoritesAdapter.notifyDataSetChanged();

    }
}
