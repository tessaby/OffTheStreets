package com.example.tessamber.offthestreets.ui;

/**
 * Created by tess.amber on 3/13/18.
 */

import android.app.Activity;

import android.content.Context;
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
import com.example.tessamber.offthestreets.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A fragment representing a single DataItem detail screen.
 * This fragment is contained in {@link HomelessShelterDetailActivity}
 * on handsets.
 */
public class HomelessShelterDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private HomelessShelter mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomelessShelterDetailFragment() {
    }

    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShelterCollection sc = ShelterCollection.INSTANCE;
        boolean found = true;

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            int item_id = getArguments().getInt(ARG_ITEM_ID);
            System.out.println("found this: " + item_id);
            Log.d("MYAPP", "Start details for: " + item_id);
            mItem = ShelterCollection.INSTANCE.findItemById(item_id);

            //SEARCH FOR USER IN FIREBASE
            //SET LAST VIEWED SHELTER FOR CURRENT USER.
            //////////////User.lastViewedShelter = mItem name and id concatenated;
            if (mItem != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String ref = EncodeString(email);
                mDatabase = FirebaseDatabase.getInstance().getReference("OffTheStreetsDatabase");
                mDatabase.child("users").child(ref).child("lastViewedShelter").setValue(mItem.getId() + mItem.getShelterName());
            } else {
                //cannot book. no user signed in.
            }

            if (mItem == null) found = false;

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                if (found) {
                    appBarLayout.setTitle(mItem.getShelterName());
                    //appBarLayout.setTitle(mItem.getShelterName());
                } else {
                    appBarLayout.setTitle("Shelter Details Not Found");
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //final Context myC = getActivity();
        View rootView = inflater.inflate(R.layout.homeless_shelter_detail, container, false);
        final EditText toBook = null;
        // Show the dummy content as text in a TextView.
        Log.d("MYAPP", "Getting ready to set data");
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            Log.d("MYAPP", "Getting ready to set id");
            ((TextView) rootView.findViewById(R.id.id2)).setText("Unique Key: " + Integer.toString(mItem.getId()));
            Log.d("MYAPP", "Getting ready to set name");
            ((TextView) rootView.findViewById(R.id.name)).setText(mItem.getShelterName());
            ((TextView) rootView.findViewById(R.id.capacity)).setText("Capacity: " + Integer.toString(mItem.getCapacity()));
            ((TextView) rootView.findViewById(R.id.restrictions)).setText("Restrictions: " + mItem.getRestrictions());
            ((TextView) rootView.findViewById(R.id.address)).setText("Address: " + mItem.getAddress());
            ((TextView) rootView.findViewById(R.id.longitude)).setText("Longitude: " + Double.toString(mItem.getLongitude()));
            ((TextView) rootView.findViewById(R.id.latitude)).setText("Latitude: " + Double.toString(mItem.getLatitude()));
            ((TextView) rootView.findViewById(R.id.specialNotes)).setText("Note: " + mItem.getSpecialNotes());
            ((TextView) rootView.findViewById(R.id.phoneNumber)).setText("Phone Number: " + mItem.getPhoneNumber());
            toBook = (EditText) rootView.findViewById(R.id.numberToBook);
        }
        Button bookButton = rootView.findViewById(R.id.bookButton);
        bookButton.setOnClickListener( bookButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                int number = Integer.parseInt(toBook.getText().toString());
                if (number < 0 || number > mItem.getCapacity()) {
                    toBook.clearComposingText();
                    Toast.makeText(myC,
                            "Number must be between 0 and " + mItem.getCapacity(), Toast.LENGTH_LONG)
                            .show();
                }
                if (currentUser == null) {
                    Toast.makeText(myC,
                            "Please log in before booking", Toast.LENGTH_LONG)
                            .show();
                } else {
                    ShelterCollection model = ShelterCollection.INSTANCE;
                    model.updateCapacity(mItem.getId(),
                            mItem.getCapacity() - number);
                    //need to set booking
                    String email = DecodeString(currentUser.getEmail());
                    User user = null;
                    List<User> userList = User.MyArr1;
                    boolean found = false;
                    for (User u: userList) {
                        if (u.getEmail().equalsIgnoreCase(email)) {
                            found = true;
                            user = u;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.print("Error: couldn't find user");
                    } else {
                        user.setBooking(mItem.getId(), number);
                    }
                }
            }
        }));
        return rootView;
    }

    public String EncodeString(String string) {
        return string.replace(".", ",");
    }
    public String DecodeString(String string) {
        return string.replace(",", ".");
    }
}
