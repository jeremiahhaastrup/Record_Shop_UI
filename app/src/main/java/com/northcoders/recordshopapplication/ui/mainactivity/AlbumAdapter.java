package com.northcoders.recordshopapplication.ui.mainactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.AlbumViewBinding;
import com.northcoders.recordshopapplication.model.Album;
import com.northcoders.recordshopapplication.util.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>{

    private List<Album> albumList;
    private Context context;
    private RecyclerViewInterface recyclerViewInterface;

    public AlbumAdapter(List<Album> albumList, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.albumList = albumList;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void setAlbumFilteredList(List<Album> albumListFiltered) {
        this.albumList = new ArrayList<>(albumListFiltered);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AlbumViewBinding albumViewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.album_view,
                parent,
                false);

        return new AlbumViewHolder(albumViewBinding, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.albumViewBinding.setAlbum(album);

        Glide.with(context)
                .load(album.getImageUrl())
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(holder.albumViewBinding.albumCoverView);
    }

    @Override
    public int getItemCount() {
        return albumList == null ? 0 : albumList.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        private final AlbumViewBinding albumViewBinding;

        public AlbumViewHolder(AlbumViewBinding albumViewBinding, RecyclerViewInterface recyclerViewInterface) {
            super(albumViewBinding.getRoot());
            this.albumViewBinding = albumViewBinding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
