package com.dicoding.librarymanagementapp.data.db;

import android.provider.BaseColumns;

public class DBContract {

    public static final class LibraryColumns implements BaseColumns {
        public static final String TABLE_NAME = "library";

        public static final String NAME = "name";

        public static final String AUTHOR = "author";

        public static final String PUBLISHER = "publisher";

        public static final String CATEGORY = "category";

    }

}
