package pl.edu.zut.wi.pgozdziewski.mangym;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GymDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Gym";

    private static final int DATABASE_VERSION = 6;

    // Database creation sql statement
    private static final String EX_CREATE
            = "create table Exercise(_id integer primary key, name text not null);";
    private static final String WP_CREATE
            = "create table WorkoutPlan(_id integer primary key, day integer not null, exercise text not null);";
    private static final String WO_CREATE
            = "create table Workout(_id integer primary key, exercise int not null, series integer not null, repeats integer not null, weight integer not null, time integer, done bit not null);";

    public GymDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(EX_CREATE);
        database.execSQL(WP_CREATE);
        database.execSQL(WO_CREATE);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(GymDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS Exercise");
        database.execSQL("DROP TABLE IF EXISTS WorkoutPlan");
        database.execSQL("DROP TABLE IF EXISTS Workout");
        onCreate(database);
    }
}


