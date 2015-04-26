package com.example.mcafee.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.mcafee.model.Product;

public class Util {

	public static String objToJson(Product p) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("name", p.getName());
			obj.put("image", p.getImage());
			obj.put("type", p.getType());
			obj.put("price", p.getPrice());
			obj.put("rating", p.getRating());
			obj.put("users", p.getUsers());
			obj.put("last_update", p.getLast_update());
			obj.put("description", p.getDescription());
			obj.put("url", p.getUrl());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj.toString();
	}
}
