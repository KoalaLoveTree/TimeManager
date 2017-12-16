package com.agykoala.timemanager.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agykoala.timemanager.DB.EventDTO;
import com.agykoala.timemanager.R;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class MyEventRecyclerViewAdapter extends RecyclerView.Adapter<MyEventRecyclerViewAdapter.ViewHolder> implements RealmChangeListener {

    private RealmResults<EventDTO> todayEvents;
    Context context;
    String date;

    public MyEventRecyclerViewAdapter(Context context, RealmResults<EventDTO> todayEvents) {
        this.context = context;
        this.todayEvents = todayEvents;
        todayEvents.addChangeListener(this);
    }

    @Override
    public MyEventRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        MyEventRecyclerViewAdapter.ViewHolder eventViewHolder = new MyEventRecyclerViewAdapter.ViewHolder(v);
        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(MyEventRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.start.setText(todayEvents.get(position).getTimeStart());
            holder.name.setText(todayEvents.get(position).getName());
            holder.end.setText(todayEvents.get(position).getTimeEnd());
    }

    @Override
    public int getItemCount() {
        return todayEvents.size();
    }

    @Override
    public void onChange(Object o) {
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView start;
        TextView name;
        TextView end;

        public ViewHolder(View itemView) {
            super(itemView);
            start = (TextView) itemView.findViewById(R.id.startTime);
            name = (TextView) itemView.findViewById(R.id.taskName);
            end = (TextView) itemView.findViewById(R.id.endTime);
        }
    }
}
