package com.agykoala.timemanager.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.agykoala.timemanager.MainMenuActivity;
import com.agykoala.timemanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {


    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        CalendarView calendarView = (CalendarView) v.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = String.valueOf(dayOfMonth) + "-" + String.valueOf(month + 1)
                        + "-" + String.valueOf(year);
                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                TodayFragment fragment = new TodayFragment();
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment, date);
                ft.addToBackStack(null);
                MainMenuActivity mainMenuActivity = (MainMenuActivity) getActivity();
                mainMenuActivity.showFab();
                ft.commit();
            }
        });
        return v;
    }

}
