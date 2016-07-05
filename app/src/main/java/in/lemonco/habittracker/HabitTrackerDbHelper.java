package in.lemonco.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


//subclass of SQLiteOpenhelper. Has functions : create, insert, update, select and delete
public class HabitTrackerDbHelper extends SQLiteOpenHelper {
    //To keep track of any update in database schema
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HabitTracker.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HabitTrackerContract.HabitEntry.TABLE_NAME + " (" +
                    HabitTrackerContract.HabitEntry.COLUMN_NAME_ENTRYID + " INTEGER PRIMARY KEY," +
                    HabitTrackerContract.HabitEntry.COLUMN_NAME_HABITNAME + TEXT_TYPE + COMMA_SEP +
                    HabitTrackerContract.HabitEntry.COLUMN_NAME_REPEAT + TEXT_TYPE + COMMA_SEP +
                    HabitTrackerContract.HabitEntry.COLUMN_NAME_REMINDER + TEXT_TYPE + COMMA_SEP +
                    HabitTrackerContract.HabitEntry.COLUMN_NAME_STARTDATE + TEXT_TYPE + COMMA_SEP +
                    HabitTrackerContract.HabitEntry.COLUMN_NAME_PRIORITY + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitTrackerContract.HabitEntry.TABLE_NAME;

    public HabitTrackerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public void deleteDatabase(){
        this.getWritableDatabase().execSQL(SQL_DELETE_ENTRIES);
    }


    //insert method
    public boolean insertHabit(int entryid, String habitName, String repeat, String reminder, String startDate, String priority) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_ENTRYID, entryid);
        contentValues.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_HABITNAME, habitName);
        contentValues.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_REPEAT, repeat);
        contentValues.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_REMINDER, reminder);
        contentValues.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_STARTDATE, startDate);
        contentValues.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_PRIORITY, priority);
        db.insert(HabitTrackerContract.HabitEntry.TABLE_NAME, null, contentValues);
        return true;
    }

    //update method
    public boolean updateHabit(Integer id, String habitName, String repeat, String reminder) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_HABITNAME, habitName);
        contentValues.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_REPEAT, repeat);
        contentValues.put(HabitTrackerContract.HabitEntry.COLUMN_NAME_REMINDER, reminder);

        String selection = HabitTrackerContract.HabitEntry.COLUMN_NAME_ENTRYID + " = ?";
        String[] selectionArgs = new String[]{Integer.toString(id)};
        db.update(HabitTrackerContract.HabitEntry.TABLE_NAME, contentValues, selection, selectionArgs);
        return true;
    }

    //select method
    public Cursor getAllHabits() {
        //projection defines which columns of the database we will use in query
        String[] projection = {
                HabitTrackerContract.HabitEntry.COLUMN_NAME_ENTRYID,
                HabitTrackerContract.HabitEntry.COLUMN_NAME_HABITNAME,
                HabitTrackerContract.HabitEntry.COLUMN_NAME_REPEAT,
                HabitTrackerContract.HabitEntry.COLUMN_NAME_REMINDER,
                HabitTrackerContract.HabitEntry.COLUMN_NAME_STARTDATE,
                HabitTrackerContract.HabitEntry.COLUMN_NAME_PRIORITY};

        Cursor c = getReadableDatabase().query(HabitTrackerContract.HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
        return c;
    }

    //delete all entries
    public void deleteAllEntries() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(HabitTrackerContract.HabitEntry.TABLE_NAME, null, null);
    }
}
