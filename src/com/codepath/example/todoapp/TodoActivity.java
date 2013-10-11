package com.codepath.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		
		etNewItem =  (EditText)findViewById(R.id.etNewItem);
		lvItems = (ListView)findViewById(R.id.lvItems);
		readItems();
		
		// time 15.19 mins in the video
		// below, we can use 'this' as the context which typically means Activity here. We can also pass getBaseContext() which will also return activity
		// for the second argument, we can create a custom item ourselves and design it display the data that we have in the data source i.e. todoItems
		todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
		lvItems.setAdapter(todoAdapter);
		setupListViewListener();
		
	}

	
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int pos, long id) {
				todoItems.remove(pos);
				todoAdapter.notifyDataSetChanged();
				writeItems();
				return false;
			}
		
		});
		
	}

	public void onAddedItem(View v){
		String itemText = etNewItem.getText() .toString();
		todoAdapter.add(itemText);
		etNewItem.setText("");
		writeItems();
		
	}
	
	// mins 35.34
	private void readItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try{
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e){
			todoItems = new ArrayList<String>();
		}
	}
	
	private void writeItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, todoItems);
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

}
