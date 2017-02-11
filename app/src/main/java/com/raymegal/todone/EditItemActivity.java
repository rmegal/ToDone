package com.raymegal.todone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

import static com.raymegal.todone.R.menu.menu_edit;

public class EditItemActivity extends AppCompatActivity {
    private EditText mltEdit;
    private EditText tDueDate;
    private Spinner spPriority;
    private CheckBox cbDone;
    private EditText mltNotes;
    private ToDoneExtra curItem;
    private EditAction editAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        mltEdit = (EditText) findViewById(R.id.mltEdit);
        tDueDate = (EditText) findViewById(R.id.tDueDate);
        spPriority = (Spinner) findViewById(R.id.spPriority);
        cbDone = (CheckBox) findViewById(R.id.cbDone);
        mltNotes = (EditText) findViewById(R.id.mltNotes);

        // Get name from intent and set multi-line edit
        curItem = (ToDoneExtra) getIntent().getSerializableExtra("task");
        editAction = (EditAction) getIntent().getSerializableExtra("requestcode");

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

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
        tDueDate.setText(String.format("%1$tm/%1$td/%1$tY", curItem.task.dueDate));
        spPriority.setSelection(curItem.task.priority);
        cbDone.setChecked(curItem.task.done);
        mltNotes.append(curItem.task.notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menu_edit, menu);

        if (editAction == EditAction.ADD_ACTION ) {
            menu.findItem(R.id.miDelete).setVisible(false);
        }

        return true;
    }

    public void onMenuSave(MenuItem item) {
        // Prepare data intent and pass back name
        curItem.task.name = mltEdit.getText().toString();
        curItem.task.dueDate = new Date(String.valueOf(tDueDate.getText()));
        curItem.task.priority = spPriority.getSelectedItemPosition();
        curItem.task.done = cbDone.isChecked();
        curItem.task.notes = mltNotes.getText().toString();

        Intent data = new Intent();
        data.putExtra("task", curItem);
        setResult(RESULT_OK, data);
        finish();
    }

    public void onMenuDelete(MenuItem item) {
        Intent data = new Intent();
        data.putExtra("task", curItem);
        data.putExtra("requestcode", EditAction.DELETE_ACTION);
        setResult(RESULT_OK, data);
        finish();
    }

    public void onMenuCancel(MenuItem item) {
        finish();
    }
}
