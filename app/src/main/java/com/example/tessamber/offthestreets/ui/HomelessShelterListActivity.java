package com.example.tessamber.offthestreets.ui;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * An activity representing a list of Homeless Shelters .
 */
public class HomelessShelterListActivity extends AppCompatActivity {

    // DECLARE RECYCLERVIEW ADAPTER
    SimpleItemRecyclerViewAdapter myAdapter;

    // DECLARE SHELTER COLLECTION
    final ShelterCollection model = ShelterCollection.INSTANCE;

    // DECLARE VIEWS
    private Spinner spGender;
    private Spinner spAgeRage;
    private EditText etShelterName;

    // DECLARE BUTTONS
    Button bSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeless_shelter_list);

        /**
         * Method to add shelter array list to firebase homeless_shelters database
         */
//        ShelterCollection.addShelterCollectionToFirebase(model.getShelters());

        // INITIALIZE: VIEWS

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        final View recyclerView = findViewById(R.id.shelter_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        //SEARCH PART

        // SPINNERS

        spGender = findViewById(R.id.spGender);
        String[] genders = {"", "Men", "Women", "Both"};
        ArrayAdapter<String> gendersAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, genders);
        spGender.setAdapter(gendersAdapter);

        spAgeRage = findViewById(R.id.spAgeRange);
        String[] ageRanges = {"", "Families with newborns", "Children", "Young Adults", "Anyone"};
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, ageRanges);
        spAgeRage.setAdapter(ageAdapter);

        // EDIT TEXT
        etShelterName = findViewById(R.id.etShelterName);

        // BUTTONS
        bSearch = findViewById(R.id.bSearch);
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

        setCapacityFromFirebase();
    }

    /**
     * Set up the recycler view for the list to show the shelters
     * @param recyclerView to display list of homeless shelters to user
     */

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        myAdapter = new SimpleItemRecyclerViewAdapter(model.getShelters());
        recyclerView.setAdapter(myAdapter);
    }

    /**
     * Inner class SimpleItemRecyclerViewAdapter
     */
    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private ArrayList<HomelessShelter> mValues;


        /**
         * Constructor for simple item recycler view adapter that takes in arraylist of items
         * and stores it.
         * @param items array list of shelters
         */
        public SimpleItemRecyclerViewAdapter(ArrayList<HomelessShelter> items) {
            mValues = items;
        }

        /**
         * method that resets values of arraylist in simple item recycler view adapter
         * @param newlist arraylist of shelters
         */
        public void setmValues(ArrayList<HomelessShelter> newlist) { mValues = newlist; }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

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
            final View mView;
            final TextView mIdView;
            final TextView mContentView;
            public HomelessShelter mItem;

            ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = view.findViewById(R.id.id);
                mContentView = view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

    public void setCapacityFromFirebase() {
        // FIREBASE
        // DECLARE & INITIALIZE FIREBASE DATABASE REFERENCES FOR SHELTERS
        FirebaseDatabase hFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference hDatabaseReference = hFirebaseDatabase.getReference("OffTheStreetsDatabase");
        DatabaseReference sheltersRef = hDatabaseReference.child("homeless_shelters");
        // Attach a listener to read the data at our posts reference
        sheltersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // ...

                for (HomelessShelter shelter : model.getShelters()) {
                    int id = shelter.getId();
                    String name = shelter.getShelterName();
                    String concatenate = (id + name);
                    Long lon = (Long) dataSnapshot.child(concatenate).child("capacity").getValue();
                    if (lon != null ) {
                        int cap = lon.intValue();
                        shelter.setCapacity(cap);}
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}