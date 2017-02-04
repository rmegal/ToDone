package com.raymegal.todone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.raymegal.todone.R.id.tvPriority;

/**
 * Created by ray on 2/2/2017.
 */

public class ToDoneTasksAdapter extends ArrayAdapter<ToDoneTask> {
    // View Lookup cache
    private static class ViewHolder {
        TextView name;
        TextView priority;
    }

    public ToDoneTasksAdapter(Context context, ArrayList<ToDoneTask> items) {
        super(context, R.layout.item_todone, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data task for this position
        ToDoneTask task = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_todone, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.priority = (TextView) convertView.findViewById(tvPriority);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name.setText(task.name);
        viewHolder.priority.setText(task.priority);
        // Return the completed view to render on screen
        return convertView;
    }
}
