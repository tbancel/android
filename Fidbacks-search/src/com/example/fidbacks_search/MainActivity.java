package com.example.fidbacks_search;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.searchfield);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // collapse the view ?
                htmlretriever myRetrieve = new htmlretriever();
                String url = "http://192.168.0.105:3000/users.json?search=" + query;
	            htmlretriever retrieve = new htmlretriever();
	            try
	            {
	                String text = retrieve.grabSource(url);
	                if (text.replaceAll("(\\r|\\n)", "").equals("\"No result\"")) {
		                // If no user is found
	                	TextView view = (TextView) findViewById(R.id.no_result);
	                	view.setVisibility(View.VISIBLE);
	                }
	                else {
		                // If at least a user is found
		                JSONObject nested = new JSONObject(text);
		                
		                JSONArray repackArray = new JSONArray();
	
	                    //get values you need
	                    String fidbacks_number = nested.getString("fidbacks_number");
	                    String pseudo = nested.getString("pseudo");
	                    String name = nested.getString("name");
	                    String sites_logo = nested.getString("sites_logo");
	                    String sites_name = nested.getString("sites_name");
	
	                    //add values to new object
	                    JSONObject repack = new JSONObject();
	                    repack.put("fidbacks_number", fidbacks_number);
	                    repack.put("pseudo", pseudo);
	                    repack.put("name", name);
	                    repack.put("sites_logo", sites_logo);
	                    repack.put("sites_name", sites_name);
	
	                    //add to new array 
	                    repackArray.put(repack);
	                    
	                    Intent intent = new Intent(MainActivity.this,ShowUser.class);
	                    intent.putExtra("fidbacks_number", fidbacks_number);
	                    intent.putExtra("pseudo", pseudo);
	                    intent.putExtra("name", name);
	                    intent.putExtra("sites_logo", sites_logo);
	                    intent.putExtra("sites_name", sites_name);
	                    startActivity(intent);

	                }

	            }
	            catch (Exception e)
	            {
	                e.printStackTrace();
	                return false;
	            }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // search goes here !!
                // listAdapter.getFilter().filter(query);
                return false;
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
