package tmdbproject_mvvm.fvaldiviadev.tmdbproject_mvvm.PopularMovies.ViewModel;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Interactors.PopularMoviesInteractor;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.PopularMoviesContract;

import java.util.List;

public class PopularMoviesViewModel implements PopularMoviesContract.Presenter,PopularMoviesContract.Interactor.ResponseRequestPopularMovieInteractor {

    private PopularMoviesContract.View view;
    private PopularMoviesContract.Interactor interactor;

    public PopularMoviesViewModel(PopularMoviesContract.View view){
        this.view=view;

        interactor=new PopularMoviesInteractor(this);

    }

    @Override
    public void loadPopularMovieList() {

        interactor.requestPopularMovieList();

    }

    @Override
    public void loadMoreMovies() {

        interactor.requestLoadMoreMovies();
    }

    @Override
    public void startSearch() {

        view.navigateToSearchActivity();
    }

    @Override
    public void onSuccessInteractor(List<PopularMovie> newPopularMovieList) {

        view.addList(newPopularMovieList);
    }

    @Override
    public void onFailureInteractor(String error) {

        view.showError(" - Error: " + error);
    }

    @Override
    public void hideList(boolean hide) {

        view.hideList(hide);
    }
}
