package com.poncoe.retrofit.recyclerviewsearchfilterexample;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);

        if(!isConnect()){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Tidak Ada Koneksi Internet")
                    .setMessage("Tidak Dapat Memuat Data, Silahkan Coba Lagi!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            MainActivity.this.finish();
                        }
                    }).show();
        }

        movieList = new ArrayList<>();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Movie>> call = apiService.getMovies();

        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                movieList = response.body();
                Log.d("TAG","Response = "+movieList);
                movieAdapter.setMovieList(getApplicationContext(),movieList);
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
                Snackbar.make(findViewById(android.R.id.content), "Tidak Dapat Memuat Data, Pastikan Terhubung Internet, Silahkan Coba Lagi!", Snackbar.LENGTH_LONG)
                        .setAction("OK", null)
                        .setActionTextColor(Color.RED)
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                movieAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    public boolean isConnect() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
