package com.example.tessamber.offthestreets.ui;

import android.app.Activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tessamber.offthestreets.R;
import com.example.tessamber.offthestreets.model.HomelessShelter;
import com.example.tessamber.offthestreets.model.ShelterCollection;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;


/**
 * A fragment representing a single DataItem detail screen.
 * This fragment is contained in {@link HomelessShelterDetailActivity}
 * on handsets.
 */
public class HomelessShelterDetailFragment extends Fragment {

    private static final String TAG = "DetailFragment";

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The content this fragment is presenting (The Homeless Shelter).
     */
    private HomelessShelter mItem;

    // DECLARE FIREBASE REF AND USER
    private DatabaseReference mDatabase;
    private FirebaseUser currentUser;

    //DECLARE BOOKING BUTTON AND TEXT VIEW
    private EditText toBook;

    private TextView tvCap;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomelessShelterDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean found = true;

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            int item_id = getArguments().getInt(ARG_ITEM_ID);
            System.out.println("found this: " + item_id);
            Log.d("MYAPP", "Start details for: " + item_id);

            //THE SHELTER BEING VIEWED ON THE DETAILS PAGE
            mItem = ShelterCollection.INSTANCE.findItemById(item_id);

            if (mItem == null) found = false;

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                if (found) {
                    appBarLayout.setTitle(mItem.getShelterName());
                } else {
                    appBarLayout.setTitle("Shelter Details Not Found");
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //CURRENT USER AUTH FIREBASE (LOGGED IN)
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Show the dummy content as text in a TextView.
        View rootView = inflater.inflate(R.layout.homeless_shelter_detail, container, false);
        Log.d("MYAPP", "Getting ready to set data");
        if (mItem != null) {
            Log.d("MYAPP", "Getting ready to set id");
            ((TextView) rootView.findViewById(R.id.id2)).setText("Unique Key: " + Integer.toString(mItem.getId()));
            Log.d("MYAPP", "Getting ready to set name");
            ((TextView) rootView.findViewById(R.id.name)).setText(mItem.getShelterName());

            tvCap = rootView.findViewById(R.id.capacity);
            tvCap.setText("Capacity: " + Integer.toString(mItem.getCapacity()));

            ((TextView) rootView.findViewById(R.id.restrictions)).setText("Restrictions: " + mItem.getRestrictions());
            ((TextView) rootView.findViewById(R.id.address)).setText("Address: " + mItem.getAddress());
            ((TextView) rootView.findViewById(R.id.longitude)).setText("Longitude: " + Double.toString(mItem.getLongitude()));
            ((TextView) rootView.findViewById(R.id.latitude)).setText("Latitude: " + Double.toString(mItem.getLatitude()));
            ((TextView) rootView.findViewById(R.id.specialNotes)).setText("Note: " + mItem.getSpecialNotes());
            ((TextView) rootView.findViewById(R.id.phoneNumber)).setText("Phone Number: " + mItem.getPhoneNumber());
            if (currentUser == null) {
                Toast.makeText(getActivity(), "Please log in before booking", Toast.LENGTH_SHORT).show();
            }
        }

        // INITIALIZE TEXT AND BUTTON:

        toBook = rootView.findViewById(R.id.numberToBook); // ENTER NUMBER OF BEDS TO BOOK
        Button bookButton = rootView.findViewById(R.id.bookButton);

        // CLICK BUTTON TO BOOK BEDS
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser != null && !toBook.getText().toString().equals("")) {

                    // NUMBER
                    final int number = Integer.parseInt(toBook.getText().toString());
                    // POSITIVE, AND LESS THAN SHELTER CAPACITY
                    if (number > 0 && number <= mItem.getCapacity()) {
                        Toast.makeText(getActivity(), "Preparing to book " + number + " bed(s)", Toast.LENGTH_SHORT).show();

                        //make sure that user's booking is empty first.
                        //otherwise they can't book.
                        //put a toast message.

                        //GET CURRENT USER EMAIL
                        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        final String emailRef = email.replace(".", ",");

                        mDatabase = FirebaseDatabase.getInstance().getReference("OffTheStreetsDatabase");


                        mDatabase.child("users").child(emailRef).child("bedsBooked").addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                Integer num = snapshot.getValue(Integer.class);
                                System.out.println("READY!?!" + num);

                                if (num != 0) {
                                    Toast.makeText(getActivity(), "Booking unsuccessful. Must clear previous booking of " + num + "bed(s)" , Toast.LENGTH_SHORT).show();
                                } else {
                                    System.out.println(num);

                                    mDatabase.child("users").child(emailRef).child("shelterBookedAt").setValue(mItem.getId() + mItem.getShelterName());
                                    mDatabase.child("users").child(emailRef).child("bedsBooked").setValue(number);

                                    mDatabase.child("homeless_shelters").child(mItem.getId() + mItem.getShelterName()).child("capacity").setValue(mItem.getCapacity() - number);
                                    mItem.setCapacity(mItem.getCapacity() - number);
                                    tvCap.setText("Capacity: " + (mItem.getCapacity()));

                                    Toast.makeText(getActivity(), "Successfully booked!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w(TAG, "error message");
                                // ...
                            }
                        });

                    } else {
                        Toast.makeText(getActivity(), "Invalid number", Toast.LENGTH_SHORT).show();
                    }

                }
                if(toBook.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "must specify number of beds",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Must log in first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }
}
