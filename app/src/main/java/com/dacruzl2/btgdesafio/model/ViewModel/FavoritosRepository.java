package com.dacruzl2.btgdesafio.model.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dacruzl2.btgdesafio.model.impl.RetrofitManager;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritosRepository implements IMoviesPresenter {

    private RetrofitManager retrofitManager;
    private MutableLiveData<List<Movie>> searchResults =
            new MutableLiveData<>();

    private LiveData<List<Movie>> allMovies;

    private FavoritosDao favoritosDao;


    public FavoritosRepository(Application application) {
        FavoritosDatabase db;
        db = FavoritosDatabase.getInstance(application);
        favoritosDao = db.getFavoritosDao();
        allMovies = favoritosDao.loadAllMovies();

    }

    LiveData<List<Movie>> getallMovies() {
        return allMovies;
    }

    //LiveData<List<Movieold>> getlastLocation() {
      //  return oneLocation;
 //   }

   // public void loadMovieTitle (String title){
   //     new QueryAsyncTask(favoritosDao).execute(title);
   // }

    public void delete(Movie movie) {
        new DeleteAsyncTask(favoritosDao).execute(movie);
    }
    public void insert(Movie movie) {
        new insertAsyncTask(favoritosDao).execute(movie);
    }

    public void update(Movie movie) {

        new UpdateAsyncTask(favoritosDao).execute(movie);
    }

    @Override
    public LiveData<Movie> getMovie(int movieId, final IMovieDetailsCallback IMovieDetailsCallback) {

        final MutableLiveData<Movie> movieDetailLiveData = new MutableLiveData<>();

        retrofitManager.getMovie(RetrofitManager.API_KEY, movieId, "pt-br")
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                        if (response.isSuccessful()) {
                            Movie movieDetailResponse = response.body();
                            if (movieDetailResponse != null) {
                                IMovieDetailsCallback.onSuccess(movieDetailResponse);
                                movieDetailLiveData.setValue(movieDetailResponse);
                                Log.d("MOVIE RESPONSE", movieDetailLiveData.toString());
                            } else {
                                IMovieDetailsCallback.onError();
                                Log.d("MOVIE ERRROR RESPONSE", "NULL");
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {

                    }
                });
        return movieDetailLiveData;
    }

    private static class QueryAsyncTask extends AsyncTask<String, Void, Void> {

        private FavoritosDao mAsyncTaskDao;

        public QueryAsyncTask(FavoritosDao dao) {

            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(String... params) {

            mAsyncTaskDao.loadMovie(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Movie, Void, Void> {

        private FavoritosDao mAsyncTaskDao;

        public DeleteAsyncTask(FavoritosDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Movie... params) {

            mAsyncTaskDao.deleteAll();

            return null;
        }
    }


    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private FavoritosDao mAsyncTaskDao;

        insertAsyncTask(FavoritosDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.saveMovieToFavorites(params[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Movie, Void, Void> {

        private FavoritosDao mAsyncTaskDao;

        UpdateAsyncTask(FavoritosDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Movie... params) {
            mAsyncTaskDao.updateMovie(params[0]);

            return null;
        }
    }


}
