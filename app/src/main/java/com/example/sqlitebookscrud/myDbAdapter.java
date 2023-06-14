package com.example.sqlitebookscrud;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class myDbAdapter {
    myDbHelper myhelper;

    public myDbAdapter(Context context) {

        myhelper = new myDbHelper(context);
    }

    public long insertData(String title, String author, String pubDate) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase(); // Object of Database. The getWritableDatabase() function allows you to read and also write the values.
        ContentValues contentValues = new ContentValues(); //This object allows you to use column and keys to add data in database.
        contentValues.put(myDbHelper.TITLE, title);
        contentValues.put(myDbHelper.AUTHOR, author);
        contentValues.put(myDbHelper.PUBLICATION_DATE, pubDate);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public String getData() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.BID, myDbHelper.TITLE, myDbHelper.AUTHOR, myDbHelper.PUBLICATION_DATE};
        Cursor cursor = db.query(myDbHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int bid = cursor.getInt(cursor.getColumnIndex(myDbHelper.BID));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(myDbHelper.TITLE));
            @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(myDbHelper.AUTHOR));
            @SuppressLint("Range") String pubDate = cursor.getString(cursor.getColumnIndex(myDbHelper.PUBLICATION_DATE));
            buffer.append(bid + "   " + title + "   " + author + "  " + pubDate + " \n ");
        }
        return buffer.toString();
    }

    public int delete(String bname) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs = {bname};

        int count = db.delete(myDbHelper.TABLE_NAME, myDbHelper.TITLE + " = ?", whereArgs);
        return count;
    }

    public int updateName(String oldName, String newName) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.TITLE, newName);
        String[] whereArgs = {oldName};
        int count = db.update(myDbHelper.TABLE_NAME, contentValues, myDbHelper.TITLE + " = ?", whereArgs);
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper {
        public static final String TITLE = "Title";
        public static final String AUTHOR = "Author";
        public static final String BID = "_bid";
        public static final String PUBLICATION_DATE = "PublicationDate";
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + //Concatenation
                " (" + BID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " VARCHAR(255) ," + AUTHOR + " VARCHAR(225), " + PUBLICATION_DATE + " VARCHAR(255));"; //Query
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context = context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context, "" + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context, "OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (Exception e) {
                Message.message(context, "" + e);
            }
        }
    }
}