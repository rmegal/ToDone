package com.raymegal.todone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private final int EDIT_ITEM = 77;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListeners();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /*
     * Editing items from list
     * 1. Create method for setting up the listener (this method)
     * 2. Invoke listener (i.e. this method) from onCreate
     * 3. Attach a ClickListener to each ToDoneItem2 for List View that:
     *   a. Edits the item
     *
     * Removing items from list
     * 1. Create method for setting up the listener (this method)
     * 2. Invoke listener (i.e. this method) from onCreate
     * 3. Attach a LongClickListener to each ToDoneItem2 for List View that:
     *   a. Removes that item
     *   b. Refreshes the adapter
     */
    private void setupListViewListeners() {
        // Set up item click listener to launch EditItemActivity
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                               View item, int pos, long id) {
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        ToDoneItem curItem = new ToDoneItem(pos, itemsAdapter.getItem(pos).toString());
                        i.putExtra("item", curItem);
                        startActivityForResult(i, EDIT_ITEM);
                    }
                }
        );

        // Set up item *long* click listener to remove current item
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_ITEM) {
            ToDoneItem changedItem = (ToDoneItem) data.getExtras().getSerializable("item");
            items.set(changedItem.pos, changedItem.text);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
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
        List<ToDoneItem2> dbItems = SQLite.select()
                .from(ToDoneItem2.class)
                .queryList();

        items = new ArrayList<>();
        for (ToDoneItem2 dbItem : dbItems ) {
            items.add(dbItem.text);
        }
    }

    private void writeItems() {
        // empty table
        Delete.table(ToDoneItem2.class);

        for (String item : items) {
            createItem(item);
        }
    }

    private void createItem(String text) {
        ToDoneItem2 item = new ToDoneItem2(text);
        item.save();
    }
}
