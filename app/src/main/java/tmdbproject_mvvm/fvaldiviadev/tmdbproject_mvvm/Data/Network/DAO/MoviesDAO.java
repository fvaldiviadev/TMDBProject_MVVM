package tmdbproject_mvvm.fvaldiviadev.tmdbproject_mvvm.Data.Network.DAO;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMoviesFeed;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.SearchResults;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.TheMovieDB_MovieService;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Utils.Constants;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by francisco.valdivia on 17/08/2018.
 */

public class MoviesDAO {

    ResponseRequestPopularMoviesDAO listenerPopularMovie;
    ResponseRequestSearchDAO listenerSearch;

    Call<SearchResults> call;

    Retrofit retrofit;

    public MoviesDAO(ResponseRequestPopularMoviesDAO listener,ResponseRequestSearchDAO listenerSearch){
        this.listenerPopularMovie = listener;
        this.listenerSearch = listenerSearch;
    }

    public void initRetrofit(){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public void requestPopularMovieList(int page) {

        initRetrofit();

        TheMovieDB_MovieService theMovieDBMovieService = retrofit.create(TheMovieDB_MovieService.class);
        Map<String, String> data = new HashMap<>();
        data.put("api_key", Constants.API_KEY);
        data.put("language", Constants.LANGUAGE_GET_REQUEST);
        data.put("page", String.valueOf(page));
        Call<PopularMoviesFeed> call = theMovieDBMovieService.getPopularMovies(data);

        call.enqueue(new Callback<PopularMoviesFeed>() {
            @Override
            public void onResponse(Call<PopularMoviesFeed> call, Response<PopularMoviesFeed> response) {
                listenerPopularMovie.onResponsePopularMoviesDAO(response);
            }

            @Override
            public void onFailure(Call<PopularMoviesFeed> call, Throwable t) {
                listenerPopularMovie.onFailurePopularMoviesDAO(t.toString());
            }
        });
    }

    public void requestSearch(String searchText, int searchPage) {

        initRetrofit();

        if (call != null && call.isExecuted()) {
            call.cancel();
        }

        TheMovieDB_MovieService theMovieDBMovieService = retrofit.create(TheMovieDB_MovieService.class);
        Map<String, String> data = new HashMap<>();
        data.put("api_key", Constants.API_KEY);
        data.put("language", Constants.LANGUAGE_GET_REQUEST);
        data.put("query", searchText);
        data.put("page", String.valueOf(searchPage));
        call = theMovieDBMovieService.getSearchResults(data);

        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                listenerSearch.onResponseSearchDAO(response);
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                listenerSearch.onFailureSearchDAO(t.toString());
            }
        });
    }

    public interface ResponseRequestPopularMoviesDAO {
        void onResponsePopularMoviesDAO(Response<PopularMoviesFeed> response);
        void onFailurePopularMoviesDAO(String error);
    }

    public interface ResponseRequestSearchDAO{
        void onResponseSearchDAO(Response<SearchResults> response);
        void onFailureSearchDAO(String error);
    }
}
