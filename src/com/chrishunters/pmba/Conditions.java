package com.chrishunters.pmba;


import java.util.ArrayList;
//import java.util.Date;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.chrishunters.pmba.R;
//import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//import android.view.KeyEvent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.*;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;

import com.insready.drupalcloud.JSONServerClient;
import com.insready.drupalcloud.ServiceNotAvailableException;


public class Conditions extends ListActivity {
	
	//Refactor
	private ProgressDialog m_progressDialog = null;
    private ArrayList<Condition> m_conditions = null;
    private ConditionAdapter m_adapter;
    private Runnable viewConditions;
    //End Refactor
   
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conditions);
		m_conditions = new ArrayList<Condition>();
		this.m_adapter = new ConditionAdapter(this, R.layout.conditions_row, m_conditions);
        setListAdapter(this.m_adapter);

		viewConditions = new Runnable(){
		    @Override
		    public void run() {
		    	String results = getConditionsViewJSON();
		    	getConditions(results);
		    }
		};
		Thread thread =  new Thread(null, viewConditions, "MagentoBackground");
		thread.start();
		
		m_progressDialog = new ProgressDialog(Conditions.this);
		m_progressDialog.setTitle("Getting Condition Updates");
		m_progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		m_progressDialog.isIndeterminate();
		m_progressDialog.show();
		
		
		
	}

	
	/*
	 * Make a call to Drupal via DrupalCloud JSON Library for all conditions via trail_conditions view
	 */
	private String getConditionsViewJSON() {
		
		try {
			//instantiate json reader
			JSONServerClient client = new JSONServerClient(this,
					getString(R.string.sharedpreferences_name),
					getString(R.string.SERVER), getString(R.string.API_KEY),
					getString(R.string.DOMAIN), getString(R.string.ALGORITHM), Long
							.parseLong(getString(R.string.SESSION_LIFETIME)));
			
			//JSON Server post vars, passed to Drupal site
			BasicNameValuePair[] bnvp = new BasicNameValuePair[2];
			bnvp[0] = new BasicNameValuePair("view_name", getString(R.string.d_view_trailConditions));
			bnvp[1] = new BasicNameValuePair("limit", getString(R.string.d_view_limit_trailConditions));
			
			//get the json from the views.get Drupal services call
			String viewData = client.call("views.get", bnvp);
			return viewData;
		} catch (ServiceNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return "[{\"#error\", \"true\"}]";
		
	}
	
	/*
	 * Convert JSON
	 */
	private void getConditions(String _JSON) {
		
        try
        {
        	//create new json object from String
        	JSONObject jso = new JSONObject(_JSON);
        	
        	//make sure Drupal didn't throw an error
        	if(!jso.getBoolean("#error")) 
        	{
	        	JSONArray jsoData = new JSONArray(jso.getString("#data"));
	        	m_conditions = new ArrayList<Condition>();
	        	int progressFactor=((100-m_progressDialog.getProgress())/jsoData.length());
	        	
	            //Loop through json root data, pass each nodes data to object
	            for(int i = 0; i < jsoData.length(); i++)
	        	{
	            	//Catch any conversion errors for a node
	            	try
	            	{
	            		
	            		m_progressDialog.setProgress(progressFactor*i);
	            		JSONObject j = (JSONObject)jsoData.get(i);
		            	Condition c = new Condition(j);
		            	m_conditions.add(c);
	            	}
	            	catch(Exception e)
	            	{
	            		Log.e("JSON_TO_CONDITION_ERROR", e.toString());
	            	}
	        	}
        	}
          } catch (Exception e) { 
            //Log.e("BACKGROUND_PROC", e.getMessage());
          }
          runOnUiThread(returnRes);
      }
	
	private Runnable returnRes = new Runnable() {

        @Override
        public void run() {
        	
        	//make sure we have some conditions to display
            if(m_conditions != null && m_conditions.size() > 0){
                m_adapter.notifyDataSetChanged();
                	
                for(int i=0;i<m_conditions.size();i++)
                {
                	
                	m_adapter.add(m_conditions.get(i));
                	
                }
            }
            m_progressDialog.dismiss();
            m_adapter.notifyDataSetChanged();
        }
      };	
    //Load preferences activity
  	protected void showConditionsAdd()
  	{
      Intent i = new Intent(this, ConditionAdd.class);
	    startActivity(i);
  	}
  	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.conditions, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.newTrailConditionsItem:
	    	showConditionsAdd();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
  
	private class ConditionAdapter extends ArrayAdapter<Condition> {

	    private ArrayList<Condition> items;

	    public ConditionAdapter(Context context, int textViewResourceId, ArrayList<Condition> items) {
	            super(context, textViewResourceId, items);
	            this.items = items;
	    }
	    
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	            View v = convertView;
	            if (v == null) { 
	                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	                v = vi.inflate(R.layout.conditions_row, null);
	            }
	            Condition c = items.get(position);
	            if (c != null) {
	            		//get layout objects
	                    TextView time_ago = (TextView)v.findViewById(R.id.time_ago);
	                    TextView node_title = (TextView)v.findViewById(R.id.node_title);
	                    TextView comments = (TextView)v.findViewById(R.id.comments);
	                    
	                    getImageFromValue(v, c);
	                    if(comments != null) {
	                    	comments.setText(c.get_node_data_field_condition_rating_field_condition_rating_value() + " - " + c.get_formatted_comment_statistics());
	                    }
	                    	
	                    if (time_ago != null) 
	                    	time_ago.setText(c.get_formatted_node_time_ago()+", by " + c.get_users_name());                            
	                    
	                    if(node_title != null)
	                    	node_title.setText(c.get_node_title());
	                    
	            }
	            return v;
	    }
	    
	    /*
	     * Decides which icon to display based on string values
	     */
	    
	    private ImageView getImageFromValue(View v, Condition c)
	    {
	    	 ImageView icon = (ImageView)v.findViewById(R.id.icon);
	    	 try
	    	 {
	    		 String val = c.get_node_data_field_condition_rating_field_condition_rating_value();
		    	 if(val.equals("Poor"))
		    		 icon.setImageResource(R.drawable.trailcond_red_icon_64);
		    	 else if(val.equals("Fair"))
		    		 icon.setImageResource(R.drawable.trailcond_orange_icon_64);
		    	 else if(val.equals("Good"))
		    		 icon.setImageResource(R.drawable.trailcond_green_icon_64);
		    	 else
		    		 icon.setImageResource(R.drawable.trailcond_green_icon_64);
		    	 
	            // icon.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions
	            // icon.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	    	 } catch(Exception e) {
	    		 e.fillInStackTrace();
	    	 }
             return icon;
	    	 
	    }
	}


}



