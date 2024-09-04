package com.dicoding.librarymanagementapp.data.helper;

import android.database.Cursor;
import android.util.Log;

import com.dicoding.librarymanagementapp.data.db.DBContract;
import com.dicoding.librarymanagementapp.data.entity.Book;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Book> mapCursorToArrayList(Cursor libraryCursor) {
        ArrayList<Book> libraryList = new ArrayList<>();

        if (libraryCursor != null) {
            while (libraryCursor.moveToNext()) {
                int id = libraryCursor.getInt(libraryCursor.getColumnIndexOrThrow(DBContract.LibraryColumns._ID));
                String name = libraryCursor.getString(libraryCursor.getColumnIndexOrThrow(DBContract.LibraryColumns.NAME));
                String author = libraryCursor.getString(libraryCursor.getColumnIndexOrThrow(DBContract.LibraryColumns.AUTHOR));
                String publisher = libraryCursor.getString(libraryCursor.getColumnIndexOrThrow(DBContract.LibraryColumns.PUBLISHER));
                String category = libraryCursor.getString(libraryCursor.getColumnIndexOrThrow(DBContract.LibraryColumns.CATEGORY));

                Log.d("MappingHelper", "Book Found: ID=" + id + ", Name=" + name + ", Author=" + author +
                        ", Publisher=" + publisher + ", Category=" + category);

                libraryList.add(new Book(id, name, author, publisher, category));
            }
        } else {
            Log.d("MappingHelper", "Cursor is null.");
        }

        Log.d("MappingHelper", "Total books mapped: " + libraryList.size());
        return libraryList;
    }
}
