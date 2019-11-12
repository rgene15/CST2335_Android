package com.example.androidlabs;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "MessagesDB";
    public static final String DB_TABLE = "Messages_Table";
    //columns
    public static final String COL_MESSAGE = "Message";
    public static final String COL_MESSAGEID = "MessageID";

    //queries


    public MyDBHandler(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("MyDBHandler", "onCreate" );
        String CREATE_CHAT_TABLE = "CREATE TABLE " + DB_TABLE + " (" + COL_MESSAGEID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MESSAGE + " TEXT)";
        Log.i("ChatDatabaseHelper", "Testing: " + CREATE_CHAT_TABLE);
        db.execSQL(CREATE_CHAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
        Log.i("MyDBHandler", "onUpdate version " + oldVersion +" to new database version: " +  newVersion );
    }



//    public void printCursor( Cursor c) {
//
//        int column = c.getColumnCount();
//
//        String [] columnName = c.getColumnNames();
//        for (String printcolName: columnName){
//             Log.v("Column Names", printcolName);
//        }
//        int row = c.getCount();
//
//    }

    public void insertData(String message) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_MESSAGE, message);
        long insertResult = db.insert(DB_TABLE, null, contentValues);
        Log.i("ChatDatabaseHelper", "insert data result: " + insertResult );
    }

    //view data
    public Cursor viewData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_TABLE, null);
        return cursor;
    }
}