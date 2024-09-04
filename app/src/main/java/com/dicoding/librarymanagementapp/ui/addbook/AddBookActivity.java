package com.dicoding.librarymanagementapp.ui.addbook;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.librarymanagementapp.R;
import com.dicoding.librarymanagementapp.data.db.LibraryHelper;
import com.dicoding.librarymanagementapp.data.entity.Book;
import com.dicoding.librarymanagementapp.databinding.ActivityAddBookBinding;

import java.util.Objects;

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddBookBinding binding;
    private boolean isEdit = false;
    private Book book;
    private int position;
    private LibraryHelper bookHelper;

    public static final String EXTRA_BOOK = "extra_book";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bookHelper = LibraryHelper.getInstance(getApplicationContext());
        bookHelper.open();

        book = getIntent().getParcelableExtra(EXTRA_BOOK);
        if (book != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        } else {
            book = new Book();
        }

        String actionBarTitle;
        String btnTitle;

        if (isEdit) {
            actionBarTitle = "Ubah";
            btnTitle = "Update";

            if (book != null) {
                binding.edtName.setText(book.getName());
                binding.edtAuthor.setText(book.getAuthor());
                binding.edtPublisher.setText(book.getPublisher());
                binding.edtCategory.setText(book.getCategory());
            }
        } else {
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        binding.btnAdd.setText(btnTitle);
        binding.btnAdd.setOnClickListener(this);
        binding.btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add) {
            performAddOrUpdateBook();
        } else {
            showAlertDialog(ALERT_DIALOG_CLOSE);
        }
    }

    private void performAddOrUpdateBook() {
        String name = Objects.requireNonNull(binding.edtName.getText()).toString().trim();
        String author = Objects.requireNonNull(binding.edtAuthor.getText()).toString().trim();
        String publisher = Objects.requireNonNull(binding.edtPublisher.getText()).toString().trim();
        String category = Objects.requireNonNull(binding.edtCategory.getText()).toString().trim();

        if (TextUtils.isEmpty(name)) {
            binding.edtName.setError("Field can not be blank");
            return;
        }

        book.setName(name);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategory(category);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_BOOK, book);
        intent.putExtra(EXTRA_POSITION, position);

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("author", author);
        values.put("publisher", publisher);
        values.put("category", category);

        if (isEdit) {
            long result = bookHelper.update(String.valueOf(book.getId()), values);
            if (result > 0) {
                setResult(RESULT_UPDATE, intent);
                finish();
            } else {
                Toast.makeText(AddBookActivity.this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show();
            }
        } else {
            long result = bookHelper.insert(values);
            if (result > 0) {
                book.setId((int) result);
                setResult(RESULT_ADD, intent);
                finish();
            } else {
                Toast.makeText(AddBookActivity.this, "Gagal menambah data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah Anda ingin membatalkan perubahan pada form?";
        } else {
            dialogTitle = "Hapus Buku";
            dialogMessage = "Apakah Anda yakin ingin menghapus buku ini?";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", (dialog, id) -> {
                    if (isDialogClose) {
                        finish();
                    } else {
                        long result = bookHelper.deleteById(String.valueOf(book.getId()));
                        if (result > 0) {
                            Intent intent = new Intent();
                            intent.putExtra(EXTRA_POSITION, position);
                            setResult(RESULT_DELETE, intent);
                            finish();
                        } else {
                            Toast.makeText(AddBookActivity.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Tidak", (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}