package com.example.kylemcnee.neighborhoodguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kyle McNee on 2/4/2016.
 */
public class GloryholeOpenHelper extends SQLiteOpenHelper {

    private static final String gloryholeConstructor = GloryholeOpenHelper.class.getCanonicalName();

    private static GloryholeOpenHelper mInstance;

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GLORYHOLE_DB";
    public static final String GLORYHOLE_LIST_TABLE_NAME = "GLORYHOLE_LIST";

    public static final String COL_ID = "_id";
    public static final String COL_NAME = "NAME";
    public static final String COL_ADDRESS = "ADDRESS";
    public static final String COL_DESCRIPTION = "DESCRIPTION";
    public static final String COL_FAVORITE = "FAVORITE" ;
    public static final String COL_IMAGE = "IMAGE";

    public static final String[] GLORYHOLE_COLUMNS = {COL_ID, COL_NAME, COL_ADDRESS, COL_DESCRIPTION};

    private static final String CREATE_GLORYHOLE_LIST_TABLE = "CREATE TABLE " +
            GLORYHOLE_LIST_TABLE_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NAME + " TEXT, " +
            COL_ADDRESS + " TEXT, " +
            COL_DESCRIPTION + " TEXT " +
            COL_FAVORITE + " TEXT " +
            COL_IMAGE + " TEXT )";


    private GloryholeOpenHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static GloryholeOpenHelper getInstance (Context context){
        if (mInstance == null){
            mInstance = new GloryholeOpenHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_GLORYHOLE_LIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + GLORYHOLE_LIST_TABLE_NAME);
        this.onCreate(db);
    }



    public long addItem(String name, String address, String description, String favorite, String image){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_ADDRESS, address);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_FAVORITE, favorite);
        values.put(COL_IMAGE, image);

        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(GLORYHOLE_LIST_TABLE_NAME, null, values);
        db.close();
        return returnId;
    }

    public Cursor getGloryholeList(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(GLORYHOLE_LIST_TABLE_NAME,
                GLORYHOLE_COLUMNS,
                null,  //selections
                null,  //selections args
                null,  //group by
                null,  //having
                null,  //order by
                null);  //limit
        return cursor;
    }

    public Cursor searchGloryholeList(String query){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(GLORYHOLE_LIST_TABLE_NAME,
                GLORYHOLE_COLUMNS,
                COL_NAME + " LIKE ?",
                new String[]{"%" + query + "%"},
                null,
                null,
                null,
                null);

        return cursor;
    }

}
