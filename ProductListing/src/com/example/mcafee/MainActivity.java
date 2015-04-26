package com.example.mcafee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.mcafee.adapter.ProductListAdapter;
import com.example.mcafee.model.Product;

public class MainActivity extends ActionBarActivity {

	static final String TAG = "McAfeeTest";
	RequestQueue volleyQueue;
	JsonArrayRequest jsonArrayRequest;
	NetworkImageView prodImage;
	List<Product> product_list;
	ListView prodlistview;
	ProductListAdapter prodlistAdapter;
	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpActionBar();

		volleyQueue = Volley.newRequestQueue(this);
		product_list = new ArrayList<Product>();
		prodlistview = (ListView) findViewById(R.id.productList);
		prodlistAdapter = new ProductListAdapter(MainActivity.this,
				product_list, volleyQueue);
		prodlistview.setAdapter(prodlistAdapter);
		makeHttpRequest();
	}

	private void setUpActionBar() {
		actionBar = getSupportActionBar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		MenuItem item = menu.findItem(R.id.menu_sort);
		setupSpinner(item);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return true;
	}

	private void setupSpinner(MenuItem item) {
	    //  item.setVisible(getSupportActionBar().getNavigationMode() == ActionBar.NAVIGATION_MODE_LIST);
	    //    item.setVisible(actionBar.getNavigationMode() == ActionBar.NAVIGATION_MODE_LIST);

	        View view = MenuItemCompat.getActionView(item);
	        Context context = actionBar.getThemedContext(); //to get the declared theme
	        if (view instanceof Spinner) {
	            Spinner spinner = (Spinner) view;
	            spinner.setOnItemSelectedListener(sortListener);
	            ArrayAdapter<CharSequence> listAdapter =ArrayAdapter.createFromResource(context,
	                    R.array.sort_list,
	                    R.layout.support_simple_spinner_dropdown_item);
	            listAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
	            spinner.setAdapter(listAdapter);
	        }


	        }
	
	

	private void makeHttpRequest() {

		String url = "http://mcafee.0x10.info/api/app?type=json";
		jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
				new JSONArray(), new Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						prodlistAdapter.setProductList(parseResponse(response));
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d(TAG, error.toString());

					}
				});
		jsonArrayRequest.setTag(TAG);
		volleyQueue.add(jsonArrayRequest);
	}

	private ArrayList<Product> parseResponse(JSONArray response) {
		ArrayList<Product> result = null;
		try {
			result = new ArrayList<Product>();
			for (int i = 0; i < response.length(); i++) {
				JSONObject obj = response.getJSONObject(i);
				Product p = new Product(obj.getString("name"),
						obj.getString("imagee"), obj.getString("type"),
						obj.getString("price"), obj.getString("rating"),
						obj.getString("users"), obj.getString("last_update"),
						obj.getString("description"), obj.getString("url"));
				result.add(p);
			}

		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}
		return result;
	}

	OnItemSelectedListener sortListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			product_list = prodlistAdapter.getProductList();
			if(pos == 1) {
				Collections.shuffle(product_list, new Random());
			} else if(pos == 1) {
				Collections.sort(product_list, new Comparator<Product>() {

					@Override
					public int compare(Product lhs, Product rhs) {
						if(Integer.parseInt(lhs.getPrice()) > Integer.parseInt(rhs.getPrice())) return -1;
						else if (Integer.parseInt(lhs.getPrice()) < Integer.parseInt(rhs.getPrice())) return 1;
						return 0;
					}
				});
			} else if(pos == 2) {
				Collections.sort(product_list, new Comparator<Product>() {

					@Override
					public int compare(Product lhs, Product rhs) {
						if(Float.parseFloat(lhs.getRating()) > Float.parseFloat(rhs.getRating())) return -1;
						else if (Float.parseFloat(lhs.getRating()) < Float.parseFloat(rhs.getRating())) return 1;
						return 0;
					}
				});
			}
			prodlistAdapter.setProductList((ArrayList<Product>)product_list);
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
}
