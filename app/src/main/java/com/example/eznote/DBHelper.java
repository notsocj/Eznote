package com.example.eznote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
   private static final String DATABASE_NAME ="userDB";
   private static final int DATABASE_VERSION = 1;

   private static final String TABLE_NAME = "users" ;
   private static final String COLUMN_ID = "id";
   private static final String COLUMN_EMAIL = "email";
   private static final String COLUMN_PASSWORD = "password";
   private static final String COLUMN_NICKNAME = "nickname";

   public DBHelper(Context context){
       super(context, DATABASE_NAME, null, DATABASE_VERSION);
   }

   public void onCreate(SQLiteDatabase db) {
       String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
               + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
               + COLUMN_EMAIL + " TEXT, "
               + COLUMN_PASSWORD + " TEXT, "
               + COLUMN_NICKNAME + " TEXT)";
       db.execSQL(CREATE_TABLE);
   }

   @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
       onCreate(db);
   }

    public long addUser(String email, String password, String nickname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_NICKNAME, nickname);

        return db.insert(TABLE_NAME, null, values);
    }

    public boolean validateLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_NICKNAME},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{email, password}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
    public String getNickname(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NICKNAME},
                COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String nickname = cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME));
                cursor.close();
                return nickname;
            }
            cursor.close();
        }
        return null;
    }

}


