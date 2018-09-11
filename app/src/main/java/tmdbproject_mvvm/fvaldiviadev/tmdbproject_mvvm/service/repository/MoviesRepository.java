package com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Repositories;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.DAO.MoviesDAO;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.FoundMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMoviesFeed;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.SearchResults;

import java.util.List;

import retrofit2.Response;

/**
 * Created by francisco.valdivia on 16/08/2018.
 */

public class MoviesRepository implements MoviesDAO.ResponseRequestPopularMoviesDAO, MoviesDAO.ResponseRequestSearchDAO{

    private MoviesDAO moviesDAO;
    private ResponseRequestPopularMoviesRepository listener;
    private ResponseRequestSearchRepository listenerSearch;

    boolean firstSearch;

    private static MoviesRepository moviesRepositoryInstance;

    private MoviesRepository(){};

    public static MoviesRepository getInstance(){
        if(moviesRepositoryInstance==null){
            moviesRepositoryInstance=new MoviesRepository();
        }
        return moviesRepositoryInstance;
    }

    public void setResponseRequestPopularMoviesRepository(ResponseRequestPopularMoviesRepository listener){
        this.listener = listener;

        moviesDAO=new MoviesDAO(this,this);
    }

    public void setResponseRequestSearchRepository(ResponseRequestSearchRepository listenerSearch){

        this.listenerSearch=listenerSearch;

        moviesDAO=new MoviesDAO(this,this);
    }



    public void requestPopularMovieList(int page) {

        moviesDAO.requestPopularMovieList(page);

    }

    public void requestSearch(String searchText, int searchPage,  boolean firstSearch) {

        moviesDAO.requestSearch(searchText,searchPage);

        this.firstSearch=firstSearch;
    }


    @Override
    public void onResponsePopularMoviesDAO(Response<PopularMoviesFeed> response) {

        switch (response.code()) {
            case 200:
                PopularMoviesFeed data = response.body();

                List<PopularMovie> newPopularMovieList = data.getPopularMovies();
                int totalPages=data.getTotalPages();

                listener.onResponseOKPopularMoviesRepository(newPopularMovieList,totalPages);

                break;
            default:
                listener.onFailurePopularMoviesRepository(response.code(),response.message());
                break;
        }




    }

    @Override
    public void onFailurePopularMoviesDAO(String error) {
        listener.onFailurePopularMoviesRepository(0,error);
    }

    @Override
    public void onResponseSearchDAO(Response<SearchResults> response) {
        switch (response.code()) {
            case 200:

                SearchResults data = response.body();

                if (firstSearch) {
                    listenerSearch.clearList();
                }

                int totalPages = data.getTotalPages();

                List<FoundMovie> newFoundMovieList = data.getResults();

                listenerSearch.onResponseOKSearchRepository(newFoundMovieList,totalPages);

                break;
            default:
                listenerSearch.onFailureSearchRepository(response.code(),response.message());

                break;
        }
    }

    @Override
    public void onFailureSearchDAO(String error) {
        listenerSearch.onFailureSearchRepository(0,error);
    }



    public interface ResponseRequestSearchRepository {
        void onResponseOKSearchRepository(List<FoundMovie> newSearchList, int totalPages);
        void onFailureSearchRepository(int responseCode,String responseMessage);
        void clearList();
    }

    public interface ResponseRequestPopularMoviesRepository {
        void onResponseOKPopularMoviesRepository(List<PopularMovie> newPopularMovieList,int totalPages);
        void onFailurePopularMoviesRepository(int responseCode,String responseMessage);
    }

}
