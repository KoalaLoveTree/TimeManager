package com.agykoala.timemanager.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.agykoala.timemanager.DB.EventDTO;
import com.agykoala.timemanager.R;
import com.agykoala.timemanager.interfaces.OnDataPass;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogAddNewEvent extends DialogFragment implements OnDataPass {

    EditText name, description;
    Spinner start, end, startMin, endMin;
    Button add, cancel;
    String startTime, endTime, date;
    Realm eventsDB;


    public DialogAddNewEvent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventsDB = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_add_new_event, container, false);
        // Inflate the layout for this fragment
        getDialog().setTitle("New Event");
        name = (EditText) v.findViewById(R.id.eventNameField);
        description = (EditText) v.findViewById(R.id.eventDescriptionField);
        start = (Spinner) v.findViewById(R.id.spinnerTimeStart);
        end = (Spinner) v.findViewById(R.id.spinnerTimeEnd);
        add = (Button) v.findViewById(R.id.AddEventButton);
        cancel = (Button) v.findViewById(R.id.CancelEventButton);
        startMin = (Spinner) v.findViewById(R.id.spinnerMinStart);
        endMin = (Spinner) v.findViewById(R.id.spinnerMinEnd);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            date = bundle.getString("date");
        }

        start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.time_spinner);
                startTime = choose[position] + ":";
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                startTime = "00:";
            }
        });

        startMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] chooseMin = getResources().getStringArray(R.array.min_spinner);
                startTime = startTime + chooseMin[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                startTime = startTime + "00";
            }
        });

        end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.time_spinner);
                endTime = choose[position] + ":";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                endTime = "00:";
            }
        });

        endMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] chooseMin = getResources().getStringArray(R.array.min_spinner);
                endTime = endTime + chooseMin[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                endTime = endTime + "00";
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventsDB.beginTransaction();
                EventDTO newEvent = eventsDB.createObject(EventDTO.class);
                newEvent.setName(name.getText().toString());
                newEvent.setDate(date);
                newEvent.setDescription(description.getText().toString());
                newEvent.setTimeStart(startTime);
                newEvent.setTimeEnd(endTime);
                eventsDB.commitTransaction();
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onDataPass(String data) {
        this.date = data;
    }
}
