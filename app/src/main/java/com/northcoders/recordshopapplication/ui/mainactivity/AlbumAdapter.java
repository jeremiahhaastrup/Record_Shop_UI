package com.northcoders.recordshopapplication.ui.mainactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.AlbumViewBinding;
import com.northcoders.recordshopapplication.model.Album;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>{

    List<Album> albumList;
    Context context;

    public AlbumAdapter(List<Album> albumList, Context context) {
        this.albumList = albumList;
        this.context = context;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AlbumViewBinding albumViewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.album_view,
                parent,
                false);

        return new AlbumViewHolder(albumViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.albumViewBinding.setAlbum(album);
    }

    @Override
    public int getItemCount() {
        return albumList == null ? 0 : albumList.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        private AlbumViewBinding albumViewBinding;

        public AlbumViewHolder(AlbumViewBinding albumViewBinding) {
            super(albumViewBinding.getRoot());
            this.albumViewBinding = albumViewBinding;
        }
    }
}
