package tmdbproject_mvvm.fvaldiviadev.tmdbproject_mvvm.UI;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tmdbproject_mvvm.fvaldiviadev.tmdbproject_mvvm.PopularMovies.UI.PopularMoviesFragment;
import tmdbproject_mvvm.fvaldiviadev.tmdbproject_mvvm.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            PopularMoviesFragment fragment = new PopularMoviesFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(android.R.id.content, fragment, PopularMoviesFragment.TAG).commit();
        }
    }
}
