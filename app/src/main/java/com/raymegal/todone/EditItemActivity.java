package com.raymegal.todone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private EditText mltEdit;
    private ToDoneExtra curItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        mltEdit = (EditText) findViewById(R.id.mltEdit);

        // Get name from intent and set multi-line edit
        curItem = (ToDoneExtra) getIntent().getSerializableExtra("task");
        /*
         * Using append sets the name and leaves the cursor at the end
         *
         * Using setName/setSelection does the same thing.
         *
         * Regardless, I now have to hit the Back button twice to cancel the edit.
         * I thought that I only had to hit the Back button once when just using setName,
         * but I just tested that and I still had to hit the Back button twice.
         *
         * TODO: Why do I have to hit the Back button twice?
         */
        mltEdit.append(curItem.task.name);
        // mltEdit.setName(curItem.name);
        // mltEdit.setSelection(curItem.name.length());
    }

    public void onClick(View view) {
        // Prepare data intent and pass back name
        curItem.task.name = mltEdit.getText().toString();
        Intent data = new Intent();
        data.putExtra("task", curItem);
        setResult(RESULT_OK, data);
        finish();
    }
}
