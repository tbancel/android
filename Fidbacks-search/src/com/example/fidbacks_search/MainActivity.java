package com.example.fidbacks_search;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.string;
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
                String url = "https://www.fidbacks.com/api/v1/users/" + query;
	            htmlretriever retrieve = new htmlretriever();
	            try
	            {
	                String text = retrieve.grabSource(url);
	                try {
	                	JSONObject nested = new JSONObject(text);
	                	nested.getJSONArray("accounts");
	                }
	                catch (Exception e){
	                	// If no user is found
	                	TextView view = (TextView) findViewById(R.id.no_result);
	                	view.setVisibility(View.VISIBLE);
	                }
	                JSONArray accountsArray = new JSONArray();
	                JSONObject nested = new JSONObject(text);
                	nested.getJSONArray("accounts");
	                // Getting the accounts name
	                accountsArray = nested.getJSONArray("accounts");
					ArrayList<String> sites_name = new ArrayList<String>();
					for(int i = 0; i < accountsArray.length(); i++){
		                JSONObject accountObject = (JSONObject) accountsArray.get(i);
		                sites_name.add(((JSONObject) accountObject.get("account")).getString("site"));
	                }
					// Getting the accounts logo
					ArrayList<String> sites_logo = new ArrayList<String>();
					for(int i = 0; i < accountsArray.length(); i++){
						sites_logo.add("https://www.fidbacks.com/assets/logos/" + sites_name.get(i).toString() + "_aside.png");
	                }
	                // Repacking
	                JSONArray repackArray = new JSONArray();
                    //get values you need
                    String fidbacks_number = nested.getString("fidbacks_number");
                    String pseudo = nested.getString("pseudo");
                    String name = nested.getString("name");

                    //add values to new object
                    JSONObject repack = new JSONObject();
                    repack.put("fidbacks_number", fidbacks_number);
                    repack.put("pseudo", pseudo);
                    repack.put("name", name);
                    repack.put("sites_logo", sites_logo.toString());
                    repack.put("sites_name", sites_name).toString();

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
