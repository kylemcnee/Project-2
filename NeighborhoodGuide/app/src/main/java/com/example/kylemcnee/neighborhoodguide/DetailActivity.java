package com.example.kylemcnee.neighborhoodguide;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    //TODO Find out how to set the text in the detail activity to the appropriate text from the database

    //Gets reference to the floating action button, creates a new cursor for this activity.
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    Cursor cursor = GloryholeOpenHelper.getInstance(DetailActivity.this).getGloryholeList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //References the image view, which is nested in the collapsible toolbar
        ImageView headerImage = (ImageView)findViewById(R.id.headerImage);

        //TODO Will this actually work?
        int resId = getResources().getIdentifier(
                cursor.getString(cursor.getColumnIndex("IMAGE")),    // file name w/o extension
                "raw",                          // file stored in res/raw/
                getPackageName()
        );
        if (resId != 0) { // getIdentifier returns 0 if resource not found
            Bitmap image = BitmapFactory.decodeResource(getResources(), resId);
            headerImage.setImageBitmap(image);
        }

       fab.setOnClickListener(favoriteListener);
    }

    View.OnClickListener favoriteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //When clicked, the image on the button changes and a toast shows alerting the user they've added it to their favorites.
            fab.setImageResource(R.drawable.hardcock);
            Toast.makeText(DetailActivity.this, "Gloryhole added to favorites", Toast.LENGTH_SHORT).show();

            Cursor favoriteCursor = GloryholeOpenHelper.getInstance(DetailActivity.this).getGloryholeList();


            String favorite = favoriteCursor.getString(favoriteCursor.getColumnIndex(GloryholeOpenHelper.COL_NAME));
            Intent favoriteIntent = new Intent(DetailActivity.this, FavoritesActivity.class);
            favoriteIntent.putExtra("favorite", favorite);
            startActivity(favoriteIntent);

        }

    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
