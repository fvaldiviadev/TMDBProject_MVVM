package tmdbproject_mvvm.fvaldiviadev.tmdbproject_mvvm.Search.ViewModel;

import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Data.Network.Models.FoundMovie;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.Interactor.SearchInteractor;
import com.themoviedbproject_mvp.fvaldiviadev.tmdbproject_mvp.Search.SearchContract;

import java.util.List;

public class SearchViewModel implements SearchContract.Presenter,SearchContract.Interactor.ResponseRequestSearchInteractor {

    private SearchContract.View view;
    private SearchContract.Interactor interactor;



    public SearchViewModel(SearchContract.View view){

        this.view=view;

        interactor=new SearchInteractor(this);
    }


    @Override
    public void search(String searchText, int searchPage, final boolean firstSearch) {
        interactor.requestSearch(searchText,searchPage,firstSearch);
    }

    @Override
    public void loadMoreMovies(String searchText) {
        interactor.requestLoadMoreMovies(searchText);
    }

    @Override
    public void onKeySearch(String newSearch) {
        interactor.onKeySearch(newSearch);
    }

    @Override
    public void onSuccessInteractor(List<FoundMovie> foundMovieList) {

        view.addList(foundMovieList);
    }

    @Override
    public void onFailureInteractor(String error) {
        view.showError(" - Error: " + error);
    }

    @Override
    public void hideList(boolean hide) {
        view.hideList(hide);
    }

    @Override
    public void clearList() {
        view.clearList();
    }
}
