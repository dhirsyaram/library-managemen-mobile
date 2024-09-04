package com.dicoding.librarymanagementapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.librarymanagementapp.data.entity.Book;
import com.dicoding.librarymanagementapp.databinding.ItemBookBinding;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final ArrayList<Book> listBook = new ArrayList<>();
    private final OnItemClickCallback onItemClickCallback;

    public BookAdapter(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public ArrayList<Book> getListBook() {
        return listBook;
    }

    public void setListBook(ArrayList<Book> listBook) {
        Log.d("BookAdapter", "Setting list with size: " + listBook.size());
        if (listBook.size() > 0) {
            this.listBook.clear();
        }
        this.listBook.addAll(listBook);
        notifyDataSetChanged();
    }


    public void addItem(Book book) {
        this.listBook.add(book);
        notifyItemInserted(listBook.size() - 1);
    }

    public void updateItem(int position, Book book) {
        this.listBook.set(position, book);
        notifyItemChanged(position, book);
    }

    public void removeItem(int position) {
        this.listBook.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listBook.size());
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding binding = ItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = listBook.get(position);
        holder.bind(book);

        Log.d("BookAdapter", "Binding book at position: " + position + ", Name: " + book.getName());

        holder.binding.containerDataBook.setOnClickListener(v -> onItemClickCallback.onItemClicked(book, position));
    }

    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(Book selectedBook, Integer position);
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private final ItemBookBinding binding;

        public BookViewHolder(ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Book book) {
            binding.tvNameBook.setText(book.getName());
            binding.tvAuthorBook.setText(book.getAuthor());
            binding.tvCategoryBook.setText(book.getCategory());
        }
    }
}
