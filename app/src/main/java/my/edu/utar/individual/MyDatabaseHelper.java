package my.edu.utar.individual;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDatabaseHelper {

    public static final String MYDATABASE_NAME = "MY_DATABASE";
    public static final String MYDATABASE_TABLE = "MY_TABLE1";
    public static final int MYDATABASE_VERSION = 1;
    public static final String KEY_CONTENT = "Content";
    public static final String KEY_CONTENT2 = "Content2";


    private static final String SCRIPT_CREATE_DATABASE = "create table "
            + MYDATABASE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_CONTENT + " text not null,"
            +KEY_CONTENT2 + " text);";

    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public MyDatabaseHelper(Context c) {// the contractor SQLiteAdapter
        context = c;
    }

    //read the database
    public MyDatabaseHelper openToRead() throws //read data
            android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
                MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    //write the database
    public MyDatabaseHelper openToWrite() throws
            android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
                MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        return this;
    }
    public void close() {
        sqLiteHelper.close();
    }

    //insert data
    public long insert(String content, int content2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CONTENT, content);
        contentValues.put(KEY_CONTENT2,content2);

        return sqLiteDatabase.insert(MYDATABASE_TABLE, null,
                contentValues);
    }

    //delete all the record:
    public int deleteAll() {
        return sqLiteDatabase.delete(MYDATABASE_TABLE, null, null);
    }

    //    select content from MY_Table
    public String queueAll() {
        String[] columns = new String[] { KEY_CONTENT,KEY_CONTENT2};
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_TABLE, columns,
                null, null, null, null, null);
        String resultname ="Your name is: ";
        String resultscore = "Your score is: ";
        String result = "";

        int index_CONTENT = cursor.getColumnIndex(KEY_CONTENT);
        int index_CONTENT2 = cursor.getColumnIndex(KEY_CONTENT2);

        for (cursor.moveToFirst(); !(cursor.isAfterLast());
             cursor.moveToNext()) {
            result = result +resultname+ cursor.getString(index_CONTENT) + "\n"
                    + resultscore +cursor.getString(index_CONTENT2) + "\n";
        }
        return result;
    }

    //OpenHelper
    public class SQLiteHelper extends SQLiteOpenHelper {
        public SQLiteHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SCRIPT_CREATE_DATABASE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int
                newVersion) {
            db.execSQL(SCRIPT_CREATE_DATABASE);
        }

    }
}