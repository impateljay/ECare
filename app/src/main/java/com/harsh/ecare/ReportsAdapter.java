package com.harsh.ecare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jay on 10-03-2017.
 */

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_report, parent, false);

        // Return a new holder instance
        ReportsAdapter.ViewHolder viewHolder = new ReportsAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Report contact = mContacts.get(position);

        // Set item views based on your views and data model
        TextView docName = holder.fileName;
        docName.setText(contact.getFileName());
        TextView date = holder.uploadedBy;
        date.setText(contact.getUploadedBy());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView fileName;
        public TextView uploadedBy;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            fileName = (TextView) itemView.findViewById(R.id.textView5);
            uploadedBy = (TextView) itemView.findViewById(R.id.textView6);
        }
    }

    // Store a member variable for the contacts
    private List<Report> mContacts;
    // Store the context for easy access
    public Context mContext;

    // Pass in the contact array into the constructor
    public ReportsAdapter(Context context, List<Report> contacts) {
        mContacts = contacts;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }
}
