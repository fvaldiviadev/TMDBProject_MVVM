package tmdbproject_mvvm.fvaldiviadev.tmdbproject_mvvm.PopularMovies.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tmdbproject_mvvm.fvaldiviadev.tmdbproject_mvvm.PopularMovies.ViewModel.PopularMoviesViewModel;
import tmdbproject_mvvm.fvaldiviadev.tmdbproject_mvvm.R;


public class PopularMoviesFragment extends Fragment {


    public static final String TAG = "projectlistview";

    private TextView tvEmptyView;
    private RecyclerView rvPopularMovieList;
    private PopularMovieListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private PopularMovieListAdapter.OnLoadMorePopularMoviesListener onLoadMorePopularMoviesListener;

    private PopularMoviesContract.Presenter presenter;

    protected Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializateOnLoadMorePopularMoviesListener();
        adapter = new PopularMovieListAdapter(rvPopularMovieList,onLoadMorePopularMoviesListener);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_popularmovies, container, false);
        rvPopularMovieList = view.findViewById(R.id.rv_popularmovielist);

        tvEmptyView = view.findViewById(R.id.tv_nomovies);
        rvPopularMovieList = view.findViewById(R.id.rv_popularmovielist);
        handler = new Handler();

        rvPopularMovieList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvPopularMovieList.setLayoutManager(linearLayoutManager);

        setAdapter();

        return super.onCreateView(inflater, container, savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Add a empty item for show the progress bar
        addToList(null);
        presenter.loadPopularMovieList();

        final PopularMoviesViewModel viewModel = ViewModelProviders.of(this).get(PopularMoviesViewModel.class);
        observeViewModel(viewModel);
    }

    private void observeViewModel(PopularMoviesViewModel viewModel) {
        viewModel.getProjectListObservable().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                if (projects != null)
                    mAdapter.setProjectList(projects);
            }
        });
    }

    private void inicializateOnLoadMorePopularMoviesListener(){
        onLoadMorePopularMoviesListener =new PopularMovieListAdapter.OnLoadMorePopularMoviesListener() {
            @Override
            public void onLoadMoreMovies() {
                //Add a empty item for show the progress bar
                addToList(null);

                presenter.loadMoreMovies();
            }
        };
    }

    private void setAdapter() {
        adapter = new PopularMovieListAdapter(rvPopularMovieList,this);

        rvPopularMovieList.setAdapter(adapter);

        adapter.setLoading(true);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:

                presenter.startSearch();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void addToList(PopularMovie popularMovie) {
        adapter.addItem(popularMovie);
    }

    @Override
    public void addList(List<PopularMovie> newPopularMovieList) {

        //   remove progress item
        removeLastElement();

        adapter.addList(newPopularMovieList);

        setLoading(false);

    }



    @Override
    public void showError(String error) {
        tvEmptyView.append(error);
    }

    @Override
    public void navigateToSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void hideList(boolean hide) {
        if(hide){
            rvPopularMovieList.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);
        }else{
            rvPopularMovieList.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }
    }

    public void setLoading(boolean loading) {
        adapter.setLoading(loading);
    }

    public void removeLastElement() {
        adapter.removeLastElement();
    }

}
