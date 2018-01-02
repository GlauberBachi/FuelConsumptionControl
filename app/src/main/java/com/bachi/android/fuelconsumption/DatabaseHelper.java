package com.bachi.android.fuelconsumption;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

/**
 * Created by Windows on 10/09/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Databasec.db";
    //TABLE VEHICLE
    public static final String TABLE_NAME = "VEHICLE_TABLE";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    //TABLE FILLUP
    public static final String TABLE_FILLUP = "FILLUP";
    public static final String FILLUP_ID = "ID";
    public static final String FILLUP_VEHICLE_ID = "VEHICLE_ID";
    public static final String FILLUP_DATE = "DATE";
    public static final String FILLUP_ODOMETER = "ODOMETER";
    public static final String FILLUP_KM = "KM";
    public static final String FILLUP_LITER = "LITERS";
    public static final String FILLUP_CONSUMPTION = "CONSUMPTION";
    public static final String FILLUP_LABEL = "LABEL";
    public static final String FILLUP_SEQUENCE = "SEQUENCE";




    private static final String TAG = "DatabaseHelper";

    //constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    db.execSQL("CREATE TABLE " + TABLE_NAME + " ("+ COL_1 +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                  + COL_2 + " TEXT )");

        db.execSQL("CREATE TABLE " + TABLE_FILLUP + " (" + FILLUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                                         + FILLUP_VEHICLE_ID + " INT,"
                                                         + FILLUP_DATE + " TEXT,"
                                                         + FILLUP_ODOMETER + " REAL,"
                                                         + FILLUP_KM + " REAL,"
                                                         + FILLUP_LITER + " REAL,"
                                                         + FILLUP_CONSUMPTION + " REAL,"
                                                         + FILLUP_LABEL + " TEXT,"
                                                         + FILLUP_SEQUENCE + " REAL)");




    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILLUP);
        onCreate(db);
    }

    public boolean insertData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new  ContentValues();
        contentValues.put(COL_2, name);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("Select * from " + TABLE_NAME, null);
        return result;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + COL_1 + " from " + TABLE_NAME +
                " where " + COL_2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor checkName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + COL_1 + " from " + TABLE_NAME +
                " where " + COL_2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }




    //updateName
    public void updateName(String newName, int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Update " + TABLE_NAME + " set " + COL_2 +
                " = '" + newName + "' where " + COL_1 + " = " + id;
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
     }

    //delete from VEHICLE_TABLE
    public void deleteName(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Delete from " + TABLE_NAME + " where "
                + COL_1 + " = " + id;
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name);
        db.execSQL(query);


    }

    /*public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Drop table " + TABLE_NAME);
    }*/


    //------------------------------------------------------------------------------------------
    //table FILLUP

    public boolean insertDataFillup(int vehicleID, String date, float odo, float kmDriven, float liters, float consumption,
                                    String label, int sequence){
        SQLiteDatabase db = this.getWritableDatabase();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ContentValues contentValues = new  ContentValues();
        contentValues.put(FILLUP_VEHICLE_ID, vehicleID);
        contentValues.put(FILLUP_DATE, date);
        contentValues.put(FILLUP_ODOMETER, odo);
        contentValues.put(FILLUP_KM, kmDriven);
        contentValues.put(FILLUP_LITER, liters);
        contentValues.put(FILLUP_CONSUMPTION, consumption);
        contentValues.put(FILLUP_LABEL, label);
        contentValues.put(FILLUP_SEQUENCE, sequence);

        long result = db.insert(TABLE_FILLUP, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }



    public Cursor getAllDataFillup(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("Select * from " + TABLE_FILLUP + " where " + FILLUP_VEHICLE_ID + " = " + id, null);
        return result;
    }

    public Cursor getAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("Select * from " + TABLE_FILLUP , null);
        return result;
    }

    public Cursor getDataTableFillup(long fillupID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_FILLUP +
                " where " + FILLUP_ID + " = " + fillupID ;
        Cursor data = db.rawQuery(query, null);
        return data;
    }



    public Cursor getFillupItemID(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + FILLUP_ID + " from " + TABLE_FILLUP +
                " where " + FILLUP_ID + " = " + id;
        Cursor data = db.rawQuery(query, null);

        return data;
    }





    //delete from TABLE_FILLUP
    public void deleteFillup(int fillupID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Delete from " + TABLE_FILLUP + " where "
                + FILLUP_ID + " = " + fillupID;
        Log.d(TAG, "deleteFillup: query: " + query);
        db.execSQL(query);
    }


    public void deleteFillupByVehicle(int vehicleID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Delete from " + TABLE_FILLUP + " where "
                + FILLUP_VEHICLE_ID + " = " + vehicleID;
        Log.d(TAG, "deleteFillupByVehicle: query: " + query);
        db.execSQL(query);
    }



    public void deleteAllFillup(int selectedID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FILLUP);
    }


    public void updateFillup(String newName, int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Update " + TABLE_NAME + " set " + COL_2 +
                " = '" + newName + "' where " + COL_1 + " = " + id;
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public Cursor getLastFillupID(int vehicleID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + FILLUP_ID + " from " + TABLE_FILLUP+ " where "
                + FILLUP_VEHICLE_ID + " = " + vehicleID;
        Cursor data = db.rawQuery(query, null);

        return data;
    }



    public Cursor getLastOdometer(int lastFillupID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + FILLUP_ODOMETER +"," + FILLUP_SEQUENCE + " from " + TABLE_FILLUP +
                " where " + FILLUP_ID + " = " + lastFillupID;
        Cursor data = db.rawQuery(query, null);

        return data;

    }



}
