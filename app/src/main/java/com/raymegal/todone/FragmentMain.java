package com.raymegal.todone;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by ray on 2/15/2017.
 */

public class FragmentMain extends Fragment {
    OnMainItemClickListener mClickCallback;
    OnMainItemLongClickListener mLongClickCallback;

    public interface OnMainItemClickListener {
        public void onMainItemClick(int pos);
    }

    public interface OnMainItemLongClickListener {
        public void onMainItemLongClick(int pos);
    }

    private ListView lvTasks;

    private ToDoneTasksAdapter adapter;

    public FragmentMain() {
    }

    public static FragmentMain newInstance() {
        FragmentMain frag = new FragmentMain();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        lvTasks = (ListView) view.findViewById(R.id.lvTasks);
        lvTasks.setAdapter(adapter);

        setupListViewListeners();
        return view;
    }

    public void setAdapter(ToDoneTasksAdapter adapter) {
        this.adapter = adapter;
    }

    /*
     * Editing tasks from list
     * 1. Create method for setting up the listener (this method)
     * 2. Invoke listener (i.e. this method) from onCreate
     * 3. Attach a ClickListener to each ToDoneTask for List View that:
     *   a. Edits the task
     *
     * Removing tasks from list
     * 1. Create method for setting up the listener (this method)
     * 2. Invoke listener (i.e. this method) from onCreate
     * 3. Attach a LongClickListener to each ToDoneTask for List View that:
     *   a. Removes that task
     *   b. Refreshes the adapter
     */
    private void setupListViewListeners() {
        // Set up task click listener to launch FragmentEdit
        lvTasks.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                            View item, int pos, long id) {
                        mClickCallback.onMainItemClick(pos);
                    }
                }
        );

        // Set up task *long* click listener to remove current task
        lvTasks.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        mLongClickCallback.onMainItemLongClick(pos);
                        return true;
                    }
                }
        );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This ensures the container activity has implemented the callback
        // interfaces, else throw an exception
        try {
            mClickCallback = (OnMainItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMainItemClickListener");
        }
        try {
            mLongClickCallback = (OnMainItemLongClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMainItemClickListener");
        }
    }
}
