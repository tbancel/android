package com.example.fidbacks_search;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowUser extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_user);
		
		Intent thisIntent = getIntent();
		String fidbacks_number = thisIntent.getExtras().getString("fidbacks_number");
		String pseudo = thisIntent.getExtras().getString("pseudo");
		String name = thisIntent.getExtras().getString("name");
		ArrayList<String> sites_logo = thisIntent.getExtras().getStringArrayList("sites_logo");
		ArrayList<String> sites_name = thisIntent.getExtras().getStringArrayList("sites_name");

    	TextView fidbacks_number_view = (TextView) findViewById(R.id.fidbacks_number);
    	fidbacks_number_view.setVisibility(View.VISIBLE);
    	fidbacks_number_view.setText(fidbacks_number + " Fidbacks");

    	TextView pseudo_view = (TextView) findViewById(R.id.pseudo);
    	pseudo_view.setVisibility(View.VISIBLE);
    	pseudo_view.setText(pseudo);
		
    	TextView name_view = (TextView) findViewById(R.id.name);
    	name_view.setVisibility(View.VISIBLE);
    	name_view.setText(name);
    	
    	// Setting the images
    	LinearLayout inHorizontalScrollView = (LinearLayout)findViewById(R.id.inscrollview);
    	Bitmap mIcon_val = null;
		try {
			if (sites_logo.isEmpty()) {
			}
			else{
				for(String url : sites_logo)
				{
					URL newurl = null;
					try {
						newurl = new URL(url);
					    // SSL Handling
						HttpGet httpRequest = null;

					    httpRequest = new HttpGet(newurl.toURI());

					    HttpClient httpclient = new DefaultHttpClient();
					    HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);

					    HttpEntity entity = response.getEntity();
					    BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
					    InputStream input = b_entity.getContent();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
					mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
					ImageView myView = addImageView(inHorizontalScrollView);
			    	myView.setImageBitmap(mIcon_val);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_user, menu);
		return true;
	}
	
    private ImageView addImageView(LinearLayout layout){
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_launcher);
        layout.addView(imageView);
        return imageView;
       }

}
