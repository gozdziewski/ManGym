package pl.edu.zut.wi.pgozdziewski.mangym;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import pl.edu.zut.wi.pgozdziewski.mangym.model.Exercise;
import pl.edu.zut.wi.pgozdziewski.mangym.model.Workout;
import pl.edu.zut.wi.pgozdziewski.mangym.model.WorkoutPlan;

public class GymDatabase {

    public final static String EX_TABLE = "Exercise";
    public final static String EX_ID = "_id";
    public final static String EX_NAME = "name";

    public final static String WO_TABLE = "Workout";
    public final static String WO_ID = "_id";
    public final static String WO_EXRC = "exercise";
    public final static String WO_SRS = "series";
    public final static String WO_RPTS = "repeats";
    public final static String WO_WGHT = "weight";
    public final static String WO_TIME = "time";
    public final static String WO_DONE = "done";

    public final static String WP_TABLE = "WorkoutPlan";
    public final static String WP_ID = "_id";
    public final static String WP_DAY = "day";
    public final static String WP_EXRC = "exercise";

    private GymDatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public GymDatabase(Context context) {
        dbHelper = new GymDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long exerciseCreate(String name) {
        ContentValues values = new ContentValues();
        values.put(EX_NAME, name);
        return database.insert(EX_TABLE, null, values);
    }

    public Cursor exerciseSelectAll() {
        String[] cols = new String[]{EX_ID, EX_NAME};
        Cursor mCursor = database.query(true, EX_TABLE, cols, null, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor exerciseSelectById(int id) {
        String[] cols = new String[]{EX_ID, EX_NAME};
        Cursor mCursor = database.query(true, EX_TABLE, cols, EX_ID + "=" + id, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor workoutSelectByExcerciseName(String name) {
        String[] cols = new String[]{WO_ID, WO_EXRC, WO_RPTS, WO_SRS, WO_WGHT, WO_TIME};
        String[] args = new String[]{name};
        Cursor mCursor = database.query(true, WO_TABLE, cols, WO_EXRC + "=?", args, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /*public Cursor exerciseRemove() {
        String[] cols = new String[]{EX_ID, EX_NAME};
        Cursor mCursor = database.query(true, EX_TABLE, cols, null, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }*/


    public long workoutPlanCreate(int day, String exrc) {
        ContentValues values = new ContentValues();
        values.put(WP_DAY, day);
        values.put(WP_EXRC, exrc);
        return database.insert(WP_TABLE, null, values);
    }

    public Cursor workoutPlanSelectAll() {
        String[] cols = new String[]{WP_ID, WP_DAY, WP_EXRC};
        Cursor mCursor = database.query(true, WP_TABLE, cols, null, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public long workoutPlanRemove(WorkoutPlan wp) {
        String[] where = new String[]{String.valueOf(wp.getId()), String.valueOf(wp.getDay()), wp.getExercise()};
        return database.delete(WP_TABLE, WP_ID + "=? AND " + WP_DAY + "=? AND " + WP_EXRC + "=?", where);
    }


    public long exerciseRemove(Exercise ex) {
        String[] where = new String[]{String.valueOf(ex.getId()), ex.getName()};
        return database.delete(EX_TABLE, EX_ID + "=? AND " + EX_NAME + "=?", where);
    }

    public Cursor workoutPlanSelectForDay(int day) {
        String[] cols = new String[]{WP_ID, WP_DAY, WP_EXRC};
        String[] args = new String[]{String.valueOf(day)};
        Cursor mCursor = database.query(true, WP_TABLE, cols, WP_DAY + "=?", args, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public long createWorkout(Workout workout) {
        ContentValues values = new ContentValues();
        values.put(WO_RPTS, workout.getRepeats());
        values.put(WO_EXRC, workout.getExercise());
        values.put(WO_SRS, workout.getSeries());
        values.put(WO_WGHT, workout.getWeight());
        values.put(WO_TIME, workout.getTime());
        Log.d("MOJE", "gymdata time=" + workout.getTime());
        values.put(WO_DONE, 1);
        return database.insert(WO_TABLE, null, values);
    }

    public Cursor selectAllWorkouts() {
        String[] cols = new String[]{WO_ID, WO_EXRC, WO_RPTS, WO_SRS, WO_WGHT, WO_TIME};
        Cursor mCursor = database.query(true, WO_TABLE, cols, null, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
