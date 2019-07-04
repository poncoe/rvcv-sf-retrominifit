package com.poncoe.retrofit.recyclerviewsearchfilterexample;

import android.content.Context;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> implements Filterable {

    private List<Movie> movieList;
    private List<Movie> movieListFiltered;
    private Context context;

    public void setMovieList(Context context,final List<Movie> movieList){
        this.context = context;
        if(this.movieList == null){
            this.movieList = movieList;
            this.movieListFiltered = movieList;
            notifyItemChanged(0, movieListFiltered.size());
        } else {
            final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return MovieAdapter.this.movieList.size();
                }

                @Override
                public int getNewListSize() {
                    return movieList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return MovieAdapter.this.movieList.get(oldItemPosition).getTitle() == movieList.get(newItemPosition).getTitle();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

                    Movie newMovie = MovieAdapter.this.movieList.get(oldItemPosition);

                    Movie oldMovie = movieList.get(newItemPosition);

                    return newMovie.getTitle() == oldMovie.getTitle() ;
                }
            });
            this.movieList = movieList;
            this.movieListFiltered = movieList;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movielist_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MyViewHolder holder, int position) {
        holder.title.setText(movieListFiltered.get(position).getTitle());
        Glide.with(context).load(movieListFiltered.get(position).getImageUrl()).apply(RequestOptions.centerCropTransform()).into(holder.image);

        Movie movie = movieListFiltered.get(position);

        final String images = movie.getImageUrl();
        final String title = movie.getTitle();
        final String isi = movie.getIsi();

        //BIND
        holder.image.setImageURI(Uri.parse(images));
        holder.title.setText(title);
        holder.isi.setText(isi);

        //IMAGE
        PicassoClient.downloadImage(context, movieListFiltered.get(position).getImageUrl(), holder.image);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                openDetailActivity(images, title, isi);
            }
        });

    }

    @Override
    public int getItemCount() {

        if(movieList != null){
            return movieListFiltered.size();
        } else {
            return 0;
        }
    }

    ////open activity
    private void openDetailActivity(String... details) {
        Intent i = new Intent(context, DetailInfo.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("images", details[0]);
        i.putExtra("title", details[1]);
        i.putExtra("isi", details[2]);

        context.startActivity(i);

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    movieListFiltered = movieList;
                } else {
                    List<Movie> filteredList = new ArrayList<>();
                    for (Movie movie : movieList) {
               if (movie.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(movie);
                        }
                    }
                    movieListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = movieListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                movieListFiltered = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ItemClickListener {

        void onItemClick(int pos);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title,isi;
        ImageView image;
        ItemClickListener itemClickListener;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            image = (ImageView)view.findViewById(R.id.image);
            isi = (TextView)view.findViewById(R.id.isi);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
