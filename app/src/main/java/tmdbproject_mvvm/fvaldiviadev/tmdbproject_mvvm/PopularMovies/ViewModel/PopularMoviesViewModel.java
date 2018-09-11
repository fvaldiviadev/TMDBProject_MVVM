package tmdbproject_mvvm.fvaldiviadev.tmdbproject_mvvm.PopularMovies.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.PopularMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.Interactors.PopularMoviesInteractor;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.PopularMovies.PopularMoviesContract;

import java.util.List;

import tmdbproject_mvvm.fvaldiviadev.tmdbproject_mvvm.Data.Network.Models.PopularMovie;

public class PopularMoviesViewModel extends AndroidViewModel {

    private final LiveData<List<PopularMovie>> popularMovieListObservable;

    public PopularMoviesViewModel(Application application){
        super(application);

        //TODO instanciate repository
        popularMovieListObservable=null;

    }

    @Override
    public void loadPopularMovieList() {

        repository.requestPopularMovieList(page);

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
