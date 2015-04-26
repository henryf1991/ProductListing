package com.example.mcafee;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsActivity extends ActionBarActivity {

	TextView title, description, type, price, users, last_update;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		String data = getIntent().getStringExtra("data");
		setUpUI(data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			Intent i = new Intent(this, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	private void setUpUI(String data) {

		title = (TextView) findViewById(R.id.disp_title);
		description = (TextView) findViewById(R.id.disp_description);
		type = (TextView) findViewById(R.id.disp_type);
		price = (TextView) findViewById(R.id.disp_price);
		users = (TextView) findViewById(R.id.disp_users);
		last_update = (TextView) findViewById(R.id.disp_last_update);

		try {
			JSONObject obj = new JSONObject(data);
			title.setText(obj.getString("name"));
			description.setText(obj.getString("description"));
			type.setText(obj.getString("type"));
			price.setText(obj.getString("price"));
			users.setText(obj.getString("users"));
			last_update.setText(obj.getString("last_update"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
