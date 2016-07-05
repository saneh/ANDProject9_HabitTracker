package in.lemonco.habittracker;

import android.provider.BaseColumns;

/**
 * Defines the database schema of habit tracker app
 */
public final class HabitTrackerContract {
    //to prevent someone from accidently instantiating this class, we have an empty construcor
    public HabitTrackerContract() {
    }

    /* Inner class for table schema*/
    public static abstract class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";
        public static final String COLUMN_NAME_ENTRYID = "entryid";
        public static final String COLUMN_NAME_HABITNAME = "HabitName";
        public static final String COLUMN_NAME_REPEAT = "repeat";
        public static final String COLUMN_NAME_REMINDER = "reminder";
        public static final String COLUMN_NAME_STARTDATE = "StartDate";
        public static final String COLUMN_NAME_PRIORITY = "priority";
    }
}
