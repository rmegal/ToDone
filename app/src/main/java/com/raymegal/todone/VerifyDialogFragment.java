package com.raymegal.todone;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by ray on 2/11/2017.
 */

public class VerifyDialogFragment extends DialogFragment {
    OnOkSelectedListener mCallback;

    public interface OnOkSelectedListener {
        public void onOkSelected(int pos);
    }
    public VerifyDialogFragment() {
    }

    public static VerifyDialogFragment newInstance(String title, int pos) {
        VerifyDialogFragment frag = new VerifyDialogFragment();
        Bundle args = new Bundle();
        args.putString("title" , title);
        args.putInt("position" , pos);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Are you sure?");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                int pos = getArguments().getInt("position");
                mCallback.onOkSelected(pos);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return alertDialogBuilder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This ensures the container activity has implemented the callback
        // interface. If not, it throws an exception
        try {
            mCallback = (OnOkSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnOkSelectedListener");
        }
    }
}
