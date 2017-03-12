package com.harsh.ecare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 19-02-2017.
 */

public class DocAppointmentsAdapter extends RecyclerView.Adapter<DocAppointmentsAdapter.ViewHolder> {

    @Override
    public DocAppointmentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_doc_appointment, parent, false);

        // Return a new holder instance
        DocAppointmentsAdapter.ViewHolder viewHolder = new DocAppointmentsAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DocAppointmentsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Appointment contact = mContacts.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(Html.fromHtml(contact.getPatient()));
        TextView button = holder.messageButton;
        button.setText(Html.fromHtml(contact.getTime()));
        TextView date = holder.date;
        date.setText(Html.fromHtml(contact.getDate()));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView messageButton;
        public TextView date;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.patientName);
            messageButton = (TextView) itemView.findViewById(R.id.time);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }

    // Store a member variable for the contacts
    private List<Appointment> mContacts = new ArrayList<>();
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public DocAppointmentsAdapter(Context context, List<Appointment> contacts) {
        mContacts = contacts;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }
}
