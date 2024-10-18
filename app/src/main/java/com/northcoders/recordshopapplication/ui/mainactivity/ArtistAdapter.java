package com.northcoders.recordshopapplication.ui.mainactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.recordshopapplication.R;
import com.northcoders.recordshopapplication.databinding.ArtistViewBinding;
import com.northcoders.recordshopapplication.model.Artist;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    List<Artist> artistList;
    Context context;

    public ArtistAdapter(List<Artist> artistList, Context context) {
        this.artistList = artistList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArtistViewBinding artistViewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.artist_view,
                parent,
                false);

        return new ArtistAdapter.ArtistViewHolder(artistViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Artist artist = artistList.get(position);
        holder.artistViewBinding.setArtist(artist);
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {
        private ArtistViewBinding artistViewBinding;

        public ArtistViewHolder(ArtistViewBinding artistViewBinding) {
            super(artistViewBinding.getRoot());
            this.artistViewBinding = artistViewBinding;
        }
    }
}
