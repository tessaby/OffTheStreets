package com.example.tessamber.offthestreets.ui;

/**
 * Created by tess.amber on 3/13/18.
 */

import android.app.Activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.tessamber.offthestreets.R;
import com.example.tessamber.offthestreets.model.HomelessShelter;
import com.example.tessamber.offthestreets.model.ShelterCollection;

import org.w3c.dom.Text;

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
            if (mItem == null) found = false;

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                if (found) {
                    appBarLayout.setTitle("Details for: " + mItem.getShelterName());
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
        View rootView = inflater.inflate(R.layout.homeless_shelter_detail, container, false);

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
        }
        return rootView;
    }
}
