package com.example.tessamber.offthestreets.model;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tessamber.offthestreets.R;
import com.example.tessamber.offthestreets.model.HomelessShelter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sangwonyuh on 3/14/18.
 */

public class ShelterAdapter extends ArrayAdapter<HomelessShelter> {
    private Context mContext;
    private List<HomelessShelter> shelterList = new ArrayList<>();

    public ShelterAdapter(@NonNull Context context, @LayoutRes ArrayList<HomelessShelter> list) {
        super(context, 0 , list);
        mContext = context;
        shelterList = list;
    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View listItem = convertView;
//        if(listItem == null)
//            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
//
//        HomelessShelter currentShelter = shelterList.get(position);
//
//
//        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
//        name.setText(currentShelter.getShelterName());
//
//        TextView release = (TextView) listItem.findViewById(R.id.textView_release);
//        release.setText(currentShelter.getAddress());
//
//        return listItem;
//    }
}

