package com.gicoza.tabunganqurban;

/**
 * Created by Gicoza on 10/20/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();


    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "kurban";

    private static final String TABLE_TRANSAKSI = "transaksi";
    private static final String KEY_ID = "id";
    private static final String KEY_JUMLAH = "jumlah";
    private static final String KEY_TGL = "tanggal";

    private static final String TABLE_SALDO = "saldo";
    private static final String KEY_TOTAL_SALDO = "totalsaldo";
    private static final String KEY_TARGET = "target";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_TRANSAKSI = "CREATE TABLE " + TABLE_TRANSAKSI + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TGL + " TEXT,"
                + KEY_JUMLAH + " TEXT)";

        String CREATE_TABLE_SALDO = "CREATE TABLE " + TABLE_SALDO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TARGET + " TEXT,"
                + KEY_TOTAL_SALDO + " TEXT)";


        db.execSQL(CREATE_TABLE_TRANSAKSI);

        ContentValues saldo = new ContentValues();
        db.execSQL(CREATE_TABLE_SALDO);
        saldo.put(KEY_TARGET, "0");
        saldo.put(KEY_TOTAL_SALDO, "0");
        db.insert(TABLE_SALDO, null, saldo);
        Log.e(TAG, "tables SALDO created");

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSAKSI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALDO);
        onCreate(db);
    }

    /**
     * Storing user details in database
     */
    public void addTransaksi(String tgl, String jumlah, int saldosekarang) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues trans = new ContentValues();
        trans.put(KEY_TGL, tgl); // Name
        trans.put(KEY_JUMLAH, jumlah); // jumlah
        // Inserting Row
        long id = db.insert(TABLE_TRANSAKSI, null, trans);

        ContentValues saldo = new ContentValues();
        saldo.put(KEY_TOTAL_SALDO,saldosekarang);
        db.update(TABLE_SALDO, saldo, KEY_ID + "= ?", new String[]{"1"});

        db.close(); // Closing database connection
        Log.d(TAG, "berhasil menambah transaksi" + id);
    }

//    public HashMap<String, String> showHarga(String jenis) {
//        HashMap<String, String> user = new HashMap<>();
//        String selectQuery = "SELECT * FROM " + TABLE_PULSA + " WHERE " + KEY_JENIS + "='" + jenis + "'";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // Move to first row
//        cursor.moveToFirst();
//        if (cursor.getCount() > 0) {
//            user.put("kode", cursor.getString(1));
//            user.put("harga", cursor.getString(3));
//            user.put("hargajual", cursor.getString(4));
//        }
//        cursor.close();
//        db.close();
//        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
//
//        return user;
//    }
//
//    public ArrayList<ArrayList<Object>> showPulsa() {
//
//        ArrayList<ArrayList<Object>> dataArray = new
//                ArrayList<ArrayList<Object>>();
//
//        try {
//            String selectQuery = "SELECT  * FROM " + TABLE_PULSA;
//
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery(selectQuery, null);
//            // Move to first row
//            cursor.moveToFirst();
//            if (!cursor.isAfterLast()) {
//                do {
//                    ArrayList<Object> dataList = new ArrayList<Object>();
//                    dataList.add(cursor.getString(0));
//                    dataList.add(cursor.getString(1));
//                    dataList.add(cursor.getString(2));
//                    dataList.add(cursor.getString(3));
//                    dataList.add(cursor.getString(4));
//                    dataArray.add(dataList);
//
//                } while (cursor.moveToNext());
//                db.close();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.e("fail","gagal ambil data");
//        }
//        // return user
//        Log.d(TAG, "Fetching user from Sqlite: " + dataArray.toString());
//
//        return dataArray;
//    }
//
//
    public ArrayList<ArrayList<Object>> showTransaksi() {

        ArrayList<ArrayList<Object>> dataArray = new
                ArrayList<ArrayList<Object>>();

        try {
            String selectQuery = "SELECT  * FROM " + TABLE_TRANSAKSI;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            // Move to first row
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cursor.getString(0));
                    dataList.add(cursor.getString(1));
                    dataList.add(cursor.getString(2));
                    dataArray.add(dataList);
                } while (cursor.moveToNext());
                db.close();
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("fail","gagal ambil data");
        }
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + dataArray.toString());

        return dataArray;
    }

    public HashMap<String, String> showSaldo() {
        HashMap<String, String> user = new HashMap<>();
        String selectQuery = "SELECT  * FROM " + TABLE_SALDO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("totalsaldo", cursor.getString(2));
            user.put("target", cursor.getString(1));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }
//
//    public void updatePulsa(String kode,String harga, String hargajual) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_HARGA, harga);
//        values.put(KEY_HARGA_JUAL, hargajual);
//        db.update(TABLE_PULSA, values, KEY_KODE + "= ?", new String[]{String.valueOf(kode)});
//
//        db.close(); // Closing database connection
//    }

    public void updateTarget(String target) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TARGET, target);
        // Inserting Row
        long id = db.update(TABLE_SALDO, values, KEY_ID + "= ?", new String[]{String.valueOf(1)});

        db.close(); // Closing database connection
        Log.d(TAG, "Update into sqlite: " + id);
    }

    public void clearData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Delete All Rows
        db.delete(TABLE_TRANSAKSI, null, null);
        values.put(KEY_TARGET, "0");
        values.put(KEY_TOTAL_SALDO, "0");
        db.update(TABLE_SALDO, values, KEY_ID + "= ?", new String[]{String.valueOf(1)});
        db.close();

//        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
