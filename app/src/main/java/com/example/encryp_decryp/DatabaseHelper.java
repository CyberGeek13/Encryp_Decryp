package com.example.encryp_decryp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RegistrationDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "registration";
    private static final String ID_COL = "id";
    private static final String NAME_COl = "name";
    private static final String EMAIL_COL = "email";
    private static final String MOBILE_COL = "mobile";
    private static final String PASSWORD_COL = "password";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "" +
                "CREATE TABLE " + TABLE_NAME + "("+
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                NAME_COl + " TEXT, "+
                EMAIL_COL + " TEXT, " +
                MOBILE_COL + " TEXT, " +
                PASSWORD_COL + " TEXT);";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public long insertRegistrationDetails(String name, String email, String mobile, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("Inserting value","Database Insertion");
        values.put(NAME_COl, name);
        values.put(EMAIL_COL, email);
        values.put(MOBILE_COL, mobile);
        values.put(PASSWORD_COL, password);
        Log.d("Database Insert", "New row inserted with ID: ");
        return db.insert("registration", null, values);
    }
    public boolean isEmailRegistered(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {RegistrationContract.RegistrationEntry.COLUMN_EMAIL};
        String selection = RegistrationContract.RegistrationEntry.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(
                RegistrationContract.RegistrationEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean emailExists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return emailExists;
    }
    public boolean isValidLogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        String selection =
                RegistrationContract.RegistrationEntry.COLUMN_EMAIL + " = ? AND " +
                        RegistrationContract.RegistrationEntry.COLUMN_PASSWORD + " = ?";

        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(
                RegistrationContract.RegistrationEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isValid;
    }
}
