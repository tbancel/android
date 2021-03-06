package com.example.fidbacks_search;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
		String sites_logo = thisIntent.getExtras().getString("sites_logo");
		String sites_name = thisIntent.getExtras().getString("sites_name");

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
			if (sites_logo.equals("[]")) {
			}
			else{
				for(String url : sites_logo.replaceAll("\\[|\\]", "").replace("\"", "").split(","))
				{
					URL newurl = null;
					try {
						newurl = new URL(url);

					} catch (MalformedURLException e) {
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
