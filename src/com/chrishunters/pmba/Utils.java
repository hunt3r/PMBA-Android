package com.chrishunters.pmba;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {

	public String e(String entity) {
		StringBuilder sb = new StringBuilder();
		sb.append("\""+entity+"\"");
		return sb.toString();
	}
	/*
	 * Utility class to check a json string for a value, and return a default if it doens't exist
	 */
	public String hasValueOrDefault(JSONObject _json, String key, String defaultStr)
	{
		if(_json.has(key))
		{
			try {
				return _json.getString(key);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				return "Invalid JSON";
			}
		}
		else
		{
			return defaultStr;
		}
	}
	
	
}
