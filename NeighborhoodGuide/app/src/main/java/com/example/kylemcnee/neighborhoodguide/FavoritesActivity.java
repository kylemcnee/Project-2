package com.example.kylemcnee.neighborhoodguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    ListView favoritesList;
    ArrayList<String> favoritesArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        favoritesList = (ListView)findViewById(R.id.favoritesList);

        //Grabs the intent of the favorited item, and adds it to a favorites array
        Intent intent = getIntent();
        String newFavorite = intent.getStringExtra("favorite");
        favoritesArray.add(newFavorite);

        //Adapts the favorites List Array into a list view.
        ArrayAdapter<String> favoritesAdapter = new ArrayAdapter<String>(FavoritesActivity.this, android.R.layout.simple_list_item_1, favoritesArray);
        favoritesList.setAdapter(favoritesAdapter);
        favoritesAdapter.notifyDataSetChanged();

    }
}
