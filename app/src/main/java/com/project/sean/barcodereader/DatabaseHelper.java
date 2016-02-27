package com.project.sean.barcodereader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.sean.barcodereader.ItemContract.*;

/**
 * Created by Sean on 27/02/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Stock.db";
    public static final int DATABASE_VERSION = 1;
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Item.TABLE_NAME + " (" +
                Item.COL_ID + " TEXT PRIMARY KEY, " +
                Item.COL_NAME + " TEXT," +
                Item.COL_PRICE + " REAL)";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Item.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    /**
     * Insert item into the database.
     * @param itemId
     * @param itemName
     * @param price
     * @return
     */
    public boolean insertData(String itemId, String itemName, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Var1. Column name, Var2. Value
        contentValues.put(Item.COL_ID, itemId);
        contentValues.put(Item.COL_NAME, itemName);
        contentValues.put(Item.COL_PRICE, price);
        //Var1. Table name, Var2. , Var3.
        //Returns -1 if data is not inserted
        //Returns row ID of newly inserted row if successful
        long result = db.insert(Item.TABLE_NAME, null, contentValues);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Random read write access to the result using Cursor class.
     * @return result from the query
     */
    public Cursor getallData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + Item.TABLE_NAME, null);
        return result;
    }


}
