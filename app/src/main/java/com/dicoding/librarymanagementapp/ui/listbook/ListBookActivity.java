package com.dicoding.librarymanagementapp.ui.listbook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dicoding.librarymanagementapp.adapter.BookAdapter;
import com.dicoding.librarymanagementapp.data.db.LibraryHelper;
import com.dicoding.librarymanagementapp.data.entity.Book;
import com.dicoding.librarymanagementapp.data.helper.MappingHelper;
import com.dicoding.librarymanagementapp.databinding.ActivityListBookBinding;
import com.dicoding.librarymanagementapp.ui.addbook.AddBookActivity;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListBookActivity extends AppCompatActivity implements LoadBooksCallback {

    private ActivityListBookBinding binding;
    private BookAdapter adapter;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    if (result.getResultCode() == AddBookActivity.RESULT_ADD) {
                        Book book = result.getData().getParcelableExtra(AddBookActivity.EXTRA_BOOK);

                        adapter.addItem(book);
                        binding.rvListBook.smoothScrollToPosition(adapter.getItemCount() - 1);

                        showSnackbarMessage("Satu item berhasil ditambahkan");
                    } else if (result.getResultCode() == AddBookActivity.RESULT_UPDATE) {
                        Book book = result.getData().getParcelableExtra(AddBookActivity.EXTRA_BOOK);
                        int position = result.getData().getIntExtra(AddBookActivity.EXTRA_POSITION, 0);

                        adapter.updateItem(position, book);
                        binding.rvListBook.smoothScrollToPosition(position);

                        showSnackbarMessage("Satu item berhasil diubah");
                    } else if (result.getResultCode() == AddBookActivity.RESULT_DELETE) {
                        int position = result.getData().getIntExtra(AddBookActivity.EXTRA_POSITION, 0);

                        adapter.removeItem(position);

                        showSnackbarMessage("Satu item berhasil dihapus");
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Library Management");

        binding.rvListBook.setLayoutManager(new LinearLayoutManager(this));
        binding.rvListBook.setHasFixedSize(true);

        adapter = new BookAdapter((selectedBook, position) -> {
            Intent intent = new Intent(ListBookActivity.this, AddBookActivity.class);
            intent.putExtra(AddBookActivity.EXTRA_BOOK, selectedBook);
            intent.putExtra(AddBookActivity.EXTRA_POSITION, position);
            resultLauncher.launch(intent);
        });
        binding.rvListBook.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadBooksAsync(this, this).execute();
        } else {
            ArrayList<Book> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListBook(list);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListBook());
    }

    @Override
    public void preExecute() {
        binding.pbListBook.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<Book> books) {
        binding.pbListBook.setVisibility(View.INVISIBLE);
        Log.d("ListBookActivity", "PostExecute called. Books received: " + (books != null ? books.size() : 0));
        if (!books.isEmpty()) {
            adapter.setListBook(books);
            Log.d("ListBookActivity", "Books are set to adapter.");
        } else {
            adapter.setListBook(new ArrayList<>());
            showSnackbarMessage("Tidak ada data saat ini");
            Log.d("ListBookActivity", "No books available.");
        }
    }

    private static class LoadBooksAsync {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadBooksCallback> weakCallback;

        private LoadBooksAsync(Context context, LoadBooksCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            weakCallback.get().preExecute();
            executor.execute(() -> {
                Context context = weakContext.get();

                LibraryHelper noteHelper = LibraryHelper.getInstance(context);
                noteHelper.open();
                Cursor dataCursor = noteHelper.queryAll();
                ArrayList<Book> notes = MappingHelper.mapCursorToArrayList(dataCursor);
                noteHelper.close();

                handler.post(() -> weakCallback.get().postExecute(notes));
            });
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(binding.rvListBook, message, Snackbar.LENGTH_SHORT).show();
    }
}

interface LoadBooksCallback {
    void preExecute();

    void postExecute(ArrayList<Book> books);
}