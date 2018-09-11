package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.SearchResults;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMoviesFeed;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface TheMovieDB_MovieService {

    void init();

    @GET("/3/movie/popular")
    Call<PopularMoviesFeed> getPopularMovies(
            @QueryMap Map<String, String> parameters
    );

    @GET("/3/search/movie")
    Call<SearchResults> getSearchResults(
            @QueryMap Map<String, String> parameters
    );

}
