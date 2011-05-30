package com.chrishunters.pmba;




import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.view.MenuItem;
import android.view.Menu;
/*
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.app.ListActivity;
import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import org.apache.http.message.BasicNameValuePair;
import com.insready.drupalcloud.JSONServerClient;
import com.chrishunters.pmba.*;
*/
import com.chrishunters.pmba.R;


public class Dashboard extends Activity {
	Button btnConditions;
	Button btnAddCondition;
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.dashboard);
	    
	    btnConditions = (Button) findViewById(R.id.btn_conditions);
	    btnAddCondition = (Button) findViewById(R.id.btn_add_condition);
	    
	    btnConditions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Conditions.class);
                startActivityForResult(myIntent, 0);
            }
        });
	    
	    btnAddCondition.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ConditionAdd.class);
                startActivityForResult(myIntent, 0);
            }
        });
	    
	}
	//Load preferences activity
	protected void showPreferences()
	{
	    Intent i = new Intent(this, Preferences.class);
	    startActivity(i);
	}
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 * Load dashboard menus from xml
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.dashboard, menu);
	    return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 * Dashboard menu handler
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.preferencesMenu:
	    	showPreferences();
	        return true;
	    case R.id.quit:
	    	this.finish();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	
	/*
	public class ButtonAdapter extends BaseAdapter {
	    private Context mContext;

	    public ImageAdapter(Context c) {
	        mContext = c;
	    }

	    public int getCount() {
	        return mThumbIds.length;
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	        Button buttonView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            buttonView = new Button(mContext);
	            buttonView.setLayoutParams(new GridView.LayoutParams(85, 85));
	            //buttonView.setScaleType(Button.);
	            buttonView.setPadding(8, 8, 8, 8);
	        } else {
	            buttonView = (Button)convertView;
	        }

	        buttonView.setBackgroundResource(resid) setImageResource();
	        return buttonView;
	    }

	    // references to our images
	    private Integer[] mThumbIds = {
	            R.drawable.sample_2, R.drawable.sample_3,
	            R.drawable.sample_4, R.drawable.sample_5,
	            R.drawable.sample_6, R.drawable.sample_7,
	            R.drawable.sample_0, R.drawable.sample_1,
	            R.drawable.sample_2, R.drawable.sample_3,
	            R.drawable.sample_4, R.drawable.sample_5,
	            R.drawable.sample_6, R.drawable.sample_7,
	            R.drawable.sample_0, R.drawable.sample_1,
	            R.drawable.sample_2, R.drawable.sample_3,
	            R.drawable.sample_4, R.drawable.sample_5,
	            R.drawable.sample_6, R.drawable.sample_7
	    };
	}
	*/
}