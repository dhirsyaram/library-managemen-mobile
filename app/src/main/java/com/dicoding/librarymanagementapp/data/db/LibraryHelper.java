package com.dicoding.librarymanagementapp.data.db;

import static android.provider.BaseColumns._ID;
import static com.dicoding.librarymanagementapp.data.db.DBContract.LibraryColumns.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LibraryHelper {

    private static final String DB_TABLE = TABLE_NAME;
    private static DatabaseHelper dbHelper;
    private static volatile LibraryHelper INSTANCE;
    private static SQLiteDatabase sqLiteDatabase;

    private LibraryHelper(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static LibraryHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LibraryHelper(context);
                }
            }
        }
        return INSTANCE;
    }
    public void open() throws SQLException {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        if (sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }

    public Cursor queryAll() {
        Cursor cursor = sqLiteDatabase.query(DB_TABLE, null, null, null, null, null, _ID + " ASC");
        if (cursor != null && cursor.getCount() > 0) {
            Log.d("LibraryHelper", "Query succeeded. Rows returned: " + cursor.getCount());
        } else {
            Log.d("LibraryHelper", "Query returned no data or cursor is null.");
        }
        return cursor;
    }

    public Cursor queryById(String id) {
        return sqLiteDatabase.query(DB_TABLE, null, _ID+" = ?", new String[]{id}, null, null, null, null);
    }

    public long insert(ContentValues values) {
        return sqLiteDatabase.insert(DB_TABLE, null, values);
    }

    public int update(String id, ContentValues values) {
        return sqLiteDatabase.update(DB_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteById(String id) {
        return sqLiteDatabase.delete(DB_TABLE, _ID + " = ?", new String[]{id});
    }
}
