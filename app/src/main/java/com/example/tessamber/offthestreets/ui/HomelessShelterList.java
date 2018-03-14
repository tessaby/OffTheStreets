package com.example.tessamber.offthestreets.ui;

//import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.tessamber.offthestreets.R;
import com.example.tessamber.offthestreets.model.HomelessShelter;
import com.example.tessamber.offthestreets.model.ShelterCollection;

import java.util.ArrayList;
import java.util.List;

public class HomelessShelterList extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ListView lv;
    private ShelterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeless_shelter_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle(getTitle());

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

//        if (findViewById(R.id.dataitem_detail_container) != null) {
//            // The detail container view will be present only in the
//            // large-screen layouts (res/values-w900dp).
//            // If this view is present, then the
//            // activity should be in two-pane mode.
//            mTwoPane = true;
        final Spinner gSpinner = findViewById(R.id.genderSpinner);
        java.util.ArrayList<String> genders = new java.util.ArrayList<>();
        genders.add("Both") ;
        genders.add("Men");
        genders.add("Women");
        ArrayAdapter<String> gendersAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, genders);
        gSpinner.setAdapter(gendersAdapter);

        final Spinner agSpinner = findViewById(R.id.ageRangeSpinner);
        String[] agRanges = {"Families with newborns", "Children", "Young Adults", "Anyone"};
        ArrayAdapter<String> agAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, agRanges);
        agSpinner.setAdapter(agAdapter);

        lv = findViewById(R.id.listV);
        ShelterCollection model = ShelterCollection.INSTANCE;
        adapter = new ShelterAdapter(this, model.getShelters());
        lv.setAdapter(adapter);
        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String gender = gSpinner.getSelectedItem().toString();
                String ageRange = agSpinner.getSelectedItem().toString();
                String name = ((EditText) findViewById(R.id.nameTextField)).getText().toString();
            adapter = new ShelterAdapter(getApplicationContext(),
                    ShelterCollection.INSTANCE.searchShelterList(gender, ageRange, name));
            lv.setAdapter(adapter);
            }
        });
    }/*
    public ArrayList<HomelessShelter> searchShelterList(List<HomelessShelter> shelterList,
                                                   String gender, String ageRange, String name) {
        ArrayList<HomelessShelter> displayList = new ArrayList<HomelessShelter>();
        for (int i = 0; i < shelterList.size(); i++) {
            HomelessShelter shelt = shelterList.get(i);
            // the replace all is so it can match famillies with newborns"
            if((ageRange.equalsIgnoreCase("all") || ageRange.equalsIgnoreCase("anyone") ||
                    shelt.getRestrictions().replaceAll("w/", "with").toLowerCase()
                            .indexOf(ageRange.toLowerCase()) != -1 )&& (name.equals("") ||
                    shelt.getShelterName().equalsIgnoreCase(name)) && (
                    gender.equalsIgnoreCase("both") ||
                            shelt.getGender().equalsIgnoreCase(gender))) {
                displayList.add(shelt);
            }
        }
        return displayList;
    }*/

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new HomelessShelterList.SimpleItemRecyclerViewAdapter
                (ShelterCollection.INSTANCE.getShelters()));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<HomelessShelterList.SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<HomelessShelter> mValues;

        public SimpleItemRecyclerViewAdapter(List<HomelessShelter> items) {
            mValues = items;
        }

        @Override
        public HomelessShelterList.SimpleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_homeless_shelter_list, parent, false);
            return new HomelessShelterList.SimpleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final HomelessShelterList.SimpleItemRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText("" + mValues.get(position).getId());
            holder.mContentView.setText(mValues.get(position).getShelterName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putInt(HomelessShelterDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
                        Fragment fragment = new Fragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.homeless_shelter_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, HomelessShelterDetail.class);
                        Log.d("MYAPP", "Switch to detailed view for item: " + holder.mItem.getId());
                        intent.putExtra(HomelessShelterDetailFragment.ARG_ITEM_ID, holder.mItem.getId());

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public HomelessShelter mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

}
