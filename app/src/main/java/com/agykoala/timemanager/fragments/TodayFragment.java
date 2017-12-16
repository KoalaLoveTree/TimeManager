package com.agykoala.timemanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.agykoala.timemanager.DB.EventDTO;
import com.agykoala.timemanager.R;
import com.agykoala.timemanager.interfaces.OnDataPass;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class TodayFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    String date;
    private OnDataPass tDataPasser;
    Realm readData;
    RealmResults<EventDTO> eventsDTO;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TodayFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tDataPasser = (OnDataPass) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readData = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        try {
            Bundle bundle = getArguments();
            date = bundle.getString("date");
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Today", Toast.LENGTH_SHORT);
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            date = String.valueOf(gregorianCalendar.get(Calendar.DATE))
                    + "-" + String.valueOf(gregorianCalendar.get(Calendar.MONTH) + 1)
                    + "-" + String.valueOf(gregorianCalendar.get(Calendar.YEAR));
        }
        tDataPasser.onDataPass(date);
        eventsDTO = readData.where(EventDTO.class).equalTo("date",date)
                .findAllSorted("timeStart", Sort.ASCENDING);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyEventRecyclerViewAdapter(getActivity().getApplicationContext(), eventsDTO));
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        readData.close();
    }
}
