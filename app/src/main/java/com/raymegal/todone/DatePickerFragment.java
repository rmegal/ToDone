package com.raymegal.todone;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

/**
 * Created by ray on 2/17/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    OnDateSetListener mCallback;

    public interface OnDateSetListener {
        public void onDateSet(int year, int month, int day);
    }

    public DatePickerFragment() {
    }

    public static DatePickerFragment newInstance(int year, int month, int day) {
        DatePickerFragment frag = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt("year" , year);
        args.putInt("month" , month);
        args.putInt("day" , day);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the incoming arguments to set initial date
        int year = getArguments().getInt("year");
        int month = getArguments().getInt("month");
        int day = getArguments().getInt("day");

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCallback.onDateSet(year, month, dayOfMonth);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This ensures the container activity has implemented the callback
        // interface. If not, it throws an exception
        try {
            mCallback = (OnDateSetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnDateSetListener");
        }
    }
}
