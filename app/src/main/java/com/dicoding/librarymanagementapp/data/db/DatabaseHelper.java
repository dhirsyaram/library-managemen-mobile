package com.dicoding.librarymanagementapp.data.db;

import static com.dicoding.librarymanagementapp.data.db.DBContract.LibraryColumns.TABLE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dicoding.librarymanagementapp.data.db.DBContract.LibraryColumns;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dblibraryapp";

    private static final int DB_VERSION = 1;

    private static final String SQL_CREATE_TABLE_LIBRARY =
            String.format("CREATE TABLE %s"
                            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL)",
                    TABLE_NAME,
                    LibraryColumns._ID,
                    LibraryColumns.NAME,
                    LibraryColumns.AUTHOR,
                    LibraryColumns.PUBLISHER,
                    LibraryColumns.CATEGORY);

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_LIBRARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
