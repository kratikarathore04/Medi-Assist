package com.example.medi_assist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private static ClickListener clickListener;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvName;
        public Button btnDelete;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
            btnDelete.setText("DELETED");
            btnDelete.setBackgroundColor(Color.parseColor("#8e75af"));
        }

        public TextView getTvName() {
            return tvName;
        }

        public Button getBtnDelete() {
            return btnDelete;
        }
    }

    private List<Reminder> reminders;

    public ItemAdapter(List<Reminder> reminders){this.reminders=reminders;}



    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_alarm, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Reminder reminder = reminders.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.tvName;
        textView.setText(reminder.getName()+" "+ reminder.getTime());
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ItemAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
