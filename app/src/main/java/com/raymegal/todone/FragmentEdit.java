package com.raymegal.todone;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ray on 2/15/2017.
 */

public class FragmentEdit extends Fragment {
    OnEditSaveListener mSaveCallback;

    public interface OnEditSaveListener {
        public void onEditSave(ToDoneTask task, int pos);
    }

    private static final String TASK_KEY = "task_key";
    private static final String ACTION_KEY = "action_key";
    private static final String POS_KEY = "pos_key";

    private MainActivity myContext;
    private ToDoneTask task;
    private EditAction action;
    private int pos;

    private EditText mltEdit;
    private TextView tvDueDate;
    private Spinner spPriority;
    private CheckBox cbDone;
    private EditText mltNotes;

    public FragmentEdit() {
    }

    public static FragmentEdit newInstance(ToDoneTask task, EditAction action, int pos) {
        FragmentEdit frag = new FragmentEdit();
        Bundle args = new Bundle();
        args.putSerializable(TASK_KEY, task);
        args.putSerializable(ACTION_KEY, action);
        args.putInt(POS_KEY, pos);
        frag.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        Bundle bundle = getArguments();
        task = (ToDoneTask) bundle.getSerializable(TASK_KEY);
        action = (EditAction) bundle.getSerializable(ACTION_KEY);
        pos = bundle.getInt(POS_KEY);

        mltEdit = (EditText) view.findViewById(R.id.mltEdit);
        tvDueDate = (TextView) view.findViewById(R.id.tvDueDate);
        tvDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar date = Calendar.getInstance();
                date.setTime(task.dueDate);
                DatePickerFragment fragment = DatePickerFragment.newInstance(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
                fragment.show(myContext.getSupportFragmentManager(), "datePicker");
            }
        });
        spPriority = (Spinner) view.findViewById(R.id.spPriority);
        cbDone = (CheckBox) view.findViewById(R.id.cbDone);
        mltNotes = (EditText) view.findViewById(R.id.mltNotes);

        mltEdit.append(task.name);
        tvDueDate.setText(String.format("%1$tm/%1$td/%1$tY", task.dueDate));
        spPriority.setSelection(task.priority);
        cbDone.setChecked(task.done);
        mltNotes.append(task.notes);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);

        if (action == EditAction.ADD_ACTION) {
            menu.findItem(R.id.miDelete).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = false;
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.miSave:
                task.name = mltEdit.getText().toString();
                task.dueDate = new Date(String.valueOf(tvDueDate.getText()));
                task.priority = spPriority.getSelectedItemPosition();
                task.done = cbDone.isChecked();
                task.notes = mltNotes.getText().toString();

                mSaveCallback.onEditSave(task, pos);

                handled = true;
                break;
            case R.id.miDelete:
                FragmentManager fm = myContext.getSupportFragmentManager();
                VerifyDialogFragment vfyDialog = VerifyDialogFragment.newInstance("Delete Task?", pos);
                vfyDialog.show(fm, "verify_alert");

                handled = true;
                break;
            default:
                handled = super.onOptionsItemSelected(item);
        }
        return handled;
    }

    @Override
    public void onAttach(Context context) {
        myContext = (MainActivity) context;
        super.onAttach(context);

        // This ensures the container activity has implemented the callback
        // interfaces, else throw an exception
        try {
            mSaveCallback = (OnEditSaveListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnEditSaveListener");
        }
    }

    public void setDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        task.dueDate = cal.getTime();
        tvDueDate.setText(String.format("%1$tm/%1$td/%1$tY", task.dueDate));
    }
}
