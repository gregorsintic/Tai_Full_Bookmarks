package com.example.rok.terroristinfo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BookmarksDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Bookmarks";
    private static final int DATABASE_VERSION =1;

    private static final String ID = "id";
    private static final String LOCATION = "location";
    private static final String DATE = "date";
    private static final String SUMMARY = "summary";
    private static final String ICON = "icon";

//    private SQLiteDatabase database;
//    private DataBaseOpenHelper dataBaseOpenHelper;

    private static final String TABLE_BOOKMARKS = "bookmarksTable";

    public BookmarksDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE "+ TABLE_BOOKMARKS + "("+ID+" integer primary key ," + LOCATION+ " TEXT,"+ DATE+" TEXT,"+SUMMARY+" TEXT,"+ ICON+" TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARKS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKMARKS);
        onCreate(db);
    }

    // Read operations

    void addBookmark(int id,String location, String date, String summary, String iconString) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("location", location);
        values.put("date", date);
        values.put("summary", summary);
        values.put("icon", iconString);
        // Inserting Row
        db.insert(TABLE_BOOKMARKS, null, values);
        db.close(); // Closing database connection

    }

    void deleteBookmark(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKMARKS, ID + " = ?",
                new String[] { String.valueOf(id) });
        db.execSQL("DELETE ");
        db.close();
    }

    public Cursor getAllBookmarks(){

        String select_query = "SELECT DISTINCT * FROM "+TABLE_BOOKMARKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query,null);

        return cursor;
    }




}

