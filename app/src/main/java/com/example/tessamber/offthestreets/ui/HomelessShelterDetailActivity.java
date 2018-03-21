package com.example.tessamber.offthestreets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.tessamber.offthestreets.R;
import com.example.tessamber.offthestreets.model.HomelessShelter;
import com.example.tessamber.offthestreets.model.ShelterCollection;

import java.util.ArrayList;
import java.util.List;

public class HomelessShelterDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeless_shelter_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
//            System.out.println("HELLODETAIL!");
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();

//            String name = getIntent().getStringExtra(HomelessShelterDetailFragment.ARG_ITEM_ID);
//            Log.d("MY APP", "Detail got name: " + name);

//            arguments.putString(HomelessShelterDetailFragment.ARG_ITEM_ID,
//                    getIntent().getStringExtra(HomelessShelterDetailFragment.ARG_ITEM_ID));
            arguments.putInt(HomelessShelterDetailFragment.ARG_ITEM_ID,
                    getIntent().getIntExtra(HomelessShelterDetailFragment.ARG_ITEM_ID, 1000));
            HomelessShelterDetailFragment fragment = new HomelessShelterDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.homeless_shelter_detail_container, fragment)
                    .commit();

//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.homeless_shelter_detail_container, fragment);
                   
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, HomelessShelterListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
