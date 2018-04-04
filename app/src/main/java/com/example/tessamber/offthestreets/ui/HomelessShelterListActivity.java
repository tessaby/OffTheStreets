package com.example.tessamber.offthestreets.ui;

//import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tessamber.offthestreets.R;
import com.example.tessamber.offthestreets.model.HomelessShelter;
import com.example.tessamber.offthestreets.model.ShelterCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Homeless Shelters .
 */
public class HomelessShelterListActivity extends AppCompatActivity {

    SimpleItemRecyclerViewAdapter myAdapter;
    final ShelterCollection model = ShelterCollection.INSTANCE;

    private Spinner spGender;
    private Spinner spAgeRage;
    private EditText etShelterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeless_shelter_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        spGender = findViewById(R.id.mGender);
        String[] genders = {"", "Men", "Women", "Both"};
        ArrayAdapter<String> gendersAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, genders);
        spGender.setAdapter(gendersAdapter);

        spAgeRage = findViewById(R.id.mAgeRange);
        String[] ageRanges = {"", "Families with newborns", "Children", "Young Adults", "Anyone"};
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, ageRanges);
        spAgeRage.setAdapter(ageAdapter);

        etShelterName = findViewById(R.id.etShelterName);

        final View recyclerView = findViewById(R.id.shelter_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        Button bSearch = findViewById(R.id.bSearch);
        bSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //figure out how to display new list of shelters according to search...
                String gender = spGender.getSelectedItem().toString();
                String ageRange = spAgeRage.getSelectedItem().toString();
                String shelterName = etShelterName.getText().toString();

                myAdapter.setmValues(model.searchShelterList(gender, ageRange, shelterName));
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Set up the list to show the shelters
     *
     * @param recyclerView
     */

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        myAdapter = new SimpleItemRecyclerViewAdapter(model.getShelters());
        recyclerView.setAdapter(myAdapter);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private ArrayList<HomelessShelter> mValues;

        public SimpleItemRecyclerViewAdapter(ArrayList<HomelessShelter> items) {
            mValues = items;
        }

        public void setmValues(ArrayList<HomelessShelter> newlist) { mValues = newlist; }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_homeless_shelter_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText("" + mValues.get(position).getId());
            holder.mContentView.setText(mValues.get(position).getShelterName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Detected Click");

                    Context context = v.getContext();
                    Intent intent3 = new Intent(context, HomelessShelterDetailActivity.class);
                    Log.d("OffTheStreets", "Switch to detailed view for item: " + holder.mItem.toString());
                    intent3.putExtra(HomelessShelterDetailFragment.ARG_ITEM_ID, holder.mItem.getId());

                    context.startActivity(intent3);
                }
            });
        }

        @Override
        public int getItemCount() { return mValues.size(); }

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

    public ArrayList<HomelessShelter> searchShelterList(List<HomelessShelter> shelterList,
                                                   String gender, String ageRange, String name) {
        ArrayList<HomelessShelter> displayList = new ArrayList<HomelessShelter>();
        for (int i = 0; i < shelterList.size(); i++) {
            HomelessShelter shelt = shelterList.get(i);
            // the replace all is so it can match famillies with newborns"
            if((ageRange.equalsIgnoreCase("all") || ageRange.equalsIgnoreCase("anyone") ||
                    shelt.getRestrictions().replaceAll("w/", "with").toLowerCase()
                            .contains(ageRange.toLowerCase()))&& (name.equals("") ||
                    shelt.getShelterName().equalsIgnoreCase(name)) && (
                    gender.equalsIgnoreCase("both") ||
                            shelt.getGender().equalsIgnoreCase(gender))) {
                displayList.add(shelt);
            }
        }
        return displayList;
    }
}