package com.chrishunters.pmba;

import java.util.Date;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.insready.drupalcloud.JSONServerClient;
import com.insready.drupalcloud.ServiceNotAvailableException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
public class ConditionAdd extends Activity
{
	protected static EditText mBody;
    protected static EditText mTitle;
    protected static RadioGroup mCond;
	protected static RadioButton mCheckedCond;
	private JSONServerClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        client = new JSONServerClient(this,
				getString(R.string.sharedpreferences_name),
				getString(R.string.SERVER), getString(R.string.API_KEY),
				getString(R.string.DOMAIN), getString(R.string.ALGORITHM), Long
						.parseLong(getString(R.string.SESSION_LIFETIME)));
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String username = prefs.getString("usernamePref", "");
		String password = prefs.getString("passwordPref", "");
		
		try {
			client.setCredential(username,password);
		} catch (ServiceNotAvailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//If the user is not logged in
		// TODO: DO this globally
		if(client.user == null) {
			
			new AlertDialog.Builder(this)
	        .setTitle("Invalid Credentials")
	        .setMessage("Your credentials are invalid, please re-key them")
	        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                //Stop the activity
	                ConditionAdd.this.finish();
    				Intent i = new Intent(ConditionAdd.this, Preferences.class);
         	    	startActivity(i);
	    			
	            }

	        }).show();
  
			
		}
		
        setContentView(R.layout.condition_add);
        
        Button btnConditions = (Button) findViewById(R.id.btn_add_cond_submit);
        
		
	    btnConditions.setOnClickListener(new View.OnClickListener() {
	    	
            public void onClick(final View view) {
            	try {
            		new NodeSaveTask().execute();
            	} catch(Exception e) {
            		Log.e("POST_NEW_CONDITION", e.toString());
            	}
            }
            
        });
        
    }
    
    private void gotoPostActivity()
    {
    	Intent i = new Intent(this, Dashboard.class);
 	    startActivity(i);
    }
    

    private class NodeSaveTask extends AsyncTask<String, Void, Void> {
       private final ProgressDialog dialog = new ProgressDialog(ConditionAdd.this);
       private boolean successful=false;
       // can use UI thread here
       protected void onPreExecute() {
          this.dialog.setMessage("Saving trail condition...");
          this.dialog.show();
       }

       // automatically done on worker thread (separate from UI thread)
       protected Void doInBackground(final String... args) {
    	   if(this.postNewNodeFromJSON())
    		   successful=true;
          return null;
       }
       // can use UI thread here
       protected void onPostExecute(final Void unused) {
    	  if(successful)
    		  this.dialog.setMessage("Success!");
    	  
          if (this.dialog.isShowing()) {
             this.dialog.dismiss();
          }
         
          // reset the output view by retrieving the new data
          // (note, this is a naive example, in the real world it might make sense
          // to have a cache of the data and just append to what is already there, or such
          // in order to cut down on expensive database operations)
          gotoPostActivity();
       }

       /*
   	 * Make a call to Drupal via DrupalCloud JSON Library for all conditions via trail_conditions view
   	 */
   	private boolean postNewNodeFromJSON() {
   		
   		try {
   			
   			
   			//Assign values from form here (not sure this is a good place to do this)
   			mBody = (EditText)findViewById(R.id.txt_body);
       		mTitle= (EditText)findViewById(R.id.txt_title);
       		mCond= (RadioGroup)findViewById(R.id.rad_cond);
       		mCheckedCond = (RadioButton)findViewById(mCond.getCheckedRadioButtonId());
       		
   			//JSON Server post vars, passed to Drupal site
   			BasicNameValuePair[] bnvp = new BasicNameValuePair[1];
   			
   			JSONObject jso = new JSONObject();
   	    	Date now = new Date();
   	    		jso.put("uid", client.user.get_uid());
   	    		jso.put("name", client.user.get_name());
   	    		jso.put("type", "trail_condition_update");
   				jso.put("title", mTitle.getText());
   				jso.put("body", mBody.getText());
   				jso.put("field_condition_rating",  
   						new JSONArray().put(
   								new JSONObject().put("value", mCheckedCond.getText())));
   				jso.put("created", now.getTime() / 1000);
   				jso.put("status", 1);
   				bnvp[0] = new BasicNameValuePair("node", jso.toString());
   				
   				//get the json from the views.get Drupal services call
   				if(client.loggedIn)
   					client.call("node.save", bnvp);
   				else
   					return false;
   				
   		
   			
   			return true;
   			
   		} catch (ServiceNotAvailableException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   			return false;
   		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
   		
   	}
    }

   
    
}