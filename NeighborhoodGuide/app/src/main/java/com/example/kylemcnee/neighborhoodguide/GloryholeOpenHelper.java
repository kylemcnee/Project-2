package com.example.kylemcnee.neighborhoodguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kyle McNee on 2/4/2016.
 */
public class GloryholeOpenHelper extends SQLiteOpenHelper {

    private static final String gloryholeConstructor = GloryholeOpenHelper.class.getCanonicalName();

    private static GloryholeOpenHelper mInstance;

    private static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "GLORYHOLE_DB";
    public static final String GLORYHOLE_LIST_TABLE_NAME = "GLORYHOLE_LIST";

    public static final String COL_ID = "_id";
    public static final String COL_NAME = "NAME";
    public static final String COL_ADDRESS = "ADDRESS";
    public static final String COL_DESCRIPTION = "DESCRIPTION";
    public static final String COL_FAVORITE = "FAVORITE" ;
    public static final String COL_IMAGE = "IMAGE";

    public static final String[] GLORYHOLE_COLUMNS = {COL_ID, COL_NAME, COL_ADDRESS, COL_DESCRIPTION, COL_FAVORITE, COL_IMAGE};

    private static final String CREATE_GLORYHOLE_LIST_TABLE = "CREATE TABLE " +
            GLORYHOLE_LIST_TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY, " +
            COL_NAME + " TEXT, " +
            COL_ADDRESS + " TEXT, " +
            COL_DESCRIPTION + " TEXT, " +
            COL_FAVORITE + " TEXT, " +
            COL_IMAGE + " TEXT);";


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

        addItem(db, "1", "Playpen", "687 8th Ave, New York, NY 10036 Times Square",
                "One of the last remaining vestiges of the 'bad old days' of ye olde Times Square, Playpen stands three stories tall in what was once the " +
                        "active sex shop district of NYC.  Videos and toys downstairs, six booths upstairs.  'Friendly' porters and staff, but the 'sheer volume' of customers  " +
                        "(and what they leave behind) means it isn't the cleanest place to 'let loose'.  Prepare for an 'olfactory assault', and be sure to wear only 'rugged shoes' with 'grippy soles'.", "NO", "playpen");

        addItem(db, "2", "Port Authority", "625 8th Ave, New York, NY 10018 Times Square",
                "Not so much a XXX location as a SSS location (as in skeevy, sleazy, secretive), Port Authority is a 'hotspot' for the Jersey commuter crowd.  " +
                        "One benefits from not having to be spotted entering an adult video store, allowing one to 'blend in' with regular folk.  It is a blessing and " +
                        "a curse however, as misinterpreted non-verbal communication can lead to 'confrontation' with more 'closed minded types'. " +
                        " Most of the men's rooms partitions have been 'sealed up', however the bathrooms by the 400s gates are still 'active' as of this time.", "NO", "portauthority2");

      addItem(db, "3", "Show World Center", "671 8th Ave, New York, NY 10036 Times Square", "Another 'relic' of 'Ed Koch era' New York sordidness, Show World Center is a 'gritty' XXX video store and peep show located right above Port Authority.   " +
                "Although not as active and mostly catering to an 'older clientele', Show World Center boasts an 'absolutely ludicrous' selection of adult DVD's.  However, " +
                "beware of the 'drug pushers and hustlers' with whom the management seems 'unconcerned'.  Dealers hocking 'coke and smoke' will invite you to walk with them over to 9th avenue.  " +
                "You will be 'robbed blind' before you reach the next block.", "NO", "showworld");

        addItem(db, "4", "Video XCITEMENT", "746 3rd Ave, Brooklyn, NY 11232 Greenwood", "'Brooklyn's own' red light district is on 3rd ave west of Greenwood cemetery, and Video Xcitement is its 'cornerstone'.  " +
                "Unless you live in the neighborhood, you 'shouldn't run into anyone you know' unless they're also heading to Xcitement.   However, 'all types' seem to troll this stretch beneath the expressway, so 'avoid eye contact' if necessary.", "NO", "videoexcitement");

        addItem(db, "5", "Xpression Video", "230 E 53rd St, New York, NY 10022 Midtown East", "This East side adult emporium on 53rd street is known for its 'ease of access',  and its single room's 'homespun charm', " +
                "and a patronage consisting largely of 'lonely business types' from the area's many nearby hotels. Expect 'plenty of action' in the post-work time slot, but an 'eerily empty' atmosphere in the wee hours despite it's 'round the clock' hours of operation.", "NO", "xpression");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + GLORYHOLE_LIST_TABLE_NAME);
        this.onCreate(db);
    }



    public void addItem(SQLiteDatabase db, String id, String name, String address, String description, String favorite, String image){
        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_NAME, name);
        values.put(COL_ADDRESS, address);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_FAVORITE, favorite);
        values.put(COL_IMAGE, image);

        db.insert(GLORYHOLE_LIST_TABLE_NAME, null, values);
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

    public Cursor searchGloryholeListByID(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("GloryholeOpenHelper","Querying at id: "+id);
        Cursor cursor = db.query(GLORYHOLE_LIST_TABLE_NAME,
                GLORYHOLE_COLUMNS,
                COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        return cursor;
    }

    public Cursor searchGloryholeListByName(String query){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(GLORYHOLE_LIST_TABLE_NAME,
                GLORYHOLE_COLUMNS,
                COL_NAME + " LIKE ? OR " + COL_ADDRESS + " LIKE ?",
                new String[]{"%" + query + "%", "%" + query + "%"},
                null,
                null,
                null,
                null);

        return cursor;
    }


    public Cursor searchGloryholeListByAddress(String query){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(GLORYHOLE_LIST_TABLE_NAME,
                GLORYHOLE_COLUMNS,
                COL_ADDRESS + " LIKE ?",
                new String[]{"%" + query + "%"},
                null,
                null,
                null,
                null);

        return cursor;
    }
}
