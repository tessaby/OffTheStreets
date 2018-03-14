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

/**
 * A fragment representing a single DataItem detail screen.
 * This fragment is either contained in a {@link HomelessShelterDetail}
 * in two-pane mode (on tablets) or a {@link HomelessShelterDetail}
 * on handsets.
 */
public class HomelessShelterDetailFragment extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomelessShelterDetailFragment() {
    }

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private HomelessShelter mItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            int item_id = getArguments().getInt(ARG_ITEM_ID);
            Log.d("MYAPP", "Start details for: " + item_id);
            mItem = ShelterCollection.INSTANCE.findItemById(item_id);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getShelterName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homeless_shelter_detail, container, false);
        Log.d("MYAPP", "Getting ready to set data");
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            Log.d("MYAPP", "Getting ready to set id");
            ((TextView) rootView.findViewById(R.id.id2)).setText("" + mItem.getId());
            Log.d("MYAPP", "Getting ready to set name");
            ((TextView) rootView.findViewById(R.id.name)).setText(mItem.getShelterName());
            ((TextView) rootView.findViewById(R.id.capacity)).setText(mItem.getCapacity());
            ((TextView) rootView.findViewById(R.id.restrictions)).setText(mItem.getRestrictions());
            ((TextView) rootView.findViewById(R.id.longitude)).setText(Double.toString(mItem.getLongitude()));
            ((TextView) rootView.findViewById(R.id.address)).setText(Double.toString(mItem.getLatitude()));
            ((TextView) rootView.findViewById(R.id.specialNotes)).setText(mItem.getSpecialNotes());
            ((TextView) rootView.findViewById(R.id.phoneNumber)).setText(mItem.getPhoneNumber());
        }

        return rootView;
    }
}
