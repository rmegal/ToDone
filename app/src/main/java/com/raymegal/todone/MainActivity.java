package com.raymegal.todone;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.raymegal.todone.EditAction.ADD_ACTION;

public class MainActivity extends AppCompatActivity
        implements FragmentMain.OnMainItemClickListener, FragmentMain.OnMainItemLongClickListener, FragmentEdit.OnEditSaveListener, VerifyDialogFragment.OnOkSelectedListener {
    android.app.FragmentManager manager;
    private ArrayList<ToDoneTask> tasks;
    private ToDoneTasksAdapter adapter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // set up fragment manager
        manager = getFragmentManager();

        /*
         * 1. Create an ArrayList
         * 2. Create an ArrayAdapter
         * 3. Get a handle to ListView
         * 4. Attach adapter to ListView
         *
         * An adapter allows us to easily display the contents of an
         * ArrayList within a ListView.
         *
         * See: https://docs.google.com/presentation/d/15JnmfmFa0hJOEkBhG_TeymChLzDzpOTJvBlOj29A9fY/edit#slide=id.gf45d6347_3_119
         *
         */
        readItems();
        // adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        adapter = new ToDoneTasksAdapter(this, tasks);

        // set up main fragment
        FragmentMain fragment = FragmentMain.newInstance();
        fragment.setAdapter(adapter);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content, fragment, "fragMain");
        transaction.commit();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds tasks to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        String tag = "";
        boolean handled = false;
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.miAdd:
                fragment = FragmentEdit.newInstance(new ToDoneTask("", Calendar.getInstance().getTime(), 1), ADD_ACTION, -1);
                tag = "fragEdit";
                handled = true;
                break;
            case R.id.miCancel:
                manager.popBackStack();
                handled = true;
                break;
            default:
                handled = super.onOptionsItemSelected(item);
        }

        if (fragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content, fragment, tag);
            transaction.addToBackStack(tag);
            transaction.commit();
        }
        return handled;
    }

    private void deleteTask(int pos) {
        tasks.remove(pos);
        adapter.notifyDataSetChanged();
        writeItems();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private void readItems() {
        List<ToDoneTask> dbItems = SQLite.select()
                .from(ToDoneTask.class)
                .queryList();

        tasks = new ArrayList<>();
        for (ToDoneTask dbItem : dbItems) {
            tasks.add(dbItem);
        }
    }

    private void writeItems() {
        // empty table
        Delete.table(ToDoneTask.class);

        for (ToDoneTask item : tasks) {
            item.save();
        }
    }

    @Override
    public void onOkSelected(int pos) {
        deleteTask(pos);
        manager.popBackStack();
    }

    @Override
    public void onBackPressed() {
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMainItemClick(int pos) {
        ToDoneTask task = adapter.getItem(pos);
        FragmentEdit fragment = FragmentEdit.newInstance(task, EditAction.EDIT_ACTION, pos);
        String tag = "fragEdit";
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    @Override
    public void onMainItemLongClick(int pos) {
        FragmentManager fm = getSupportFragmentManager();
        VerifyDialogFragment vfyDialog = VerifyDialogFragment.newInstance("Delete Task?", pos);
        vfyDialog.show(fm, "verify_alert");
    }

    @Override
    public void onEditSave(ToDoneTask task, int pos) {
        if (pos > -1) {
            tasks.set(pos, task);
        } else {
            tasks.add(task);
        }

        adapter.notifyDataSetChanged();
        writeItems();
        manager.popBackStack();
    }
}
