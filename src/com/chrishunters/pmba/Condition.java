package com.chrishunters.pmba;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * The Condition class is an object based on the trail_conditions view object
 * Fields can be added to the view, and they will be ignored here unless those properties are added
 */
public class Condition {

	private int nid = 0;
	private String node_data_field_condition_rating_field_condition_rating_value = "";
	private long history_user_timestamp=0;
	private long node_created=0;
	private long node_changed=0;
	private String term_data_name ="";
	private String node_revisions_body ="";
	private String node_title ="";
	private long node_comment_statistics_last_updated =0;
	private int node_comment_statistics_comment_count=0;
	private long node_time_ago=0;
	private String users_name="";
	
	/*
	 * Constructor - Default
	 */
	public Condition()
	{
		
	}
	
	/*
	 * Constructor takes Serialized Drupal View Row object as JSON string
	 * @String _json
	 */
	public Condition(JSONObject j)
	{
		
		try {
			this.set_nid(j);
			this.set_node_data_field_condition_rating_field_condition_rating_value(j);
			this.set_history_user_timestamp(j);
	    	this.set_node_created(j);
	    	this.set_node_changed(j);
	    	this.set_term_data_name(j);
	    	this.set_node_revisions_body(j);
	    	this.set_node_title(j);	
	    	this.set_node_comment_statistics_last_updated(j);
	    	this.set_users_name(j);
	    	this.set_node_time_ago();
	    	this.set_node_comment_statistics_comment_count(j);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * set_node_time_ago
	 * This does some logic on available info, either use the last change if available
	 * or use the node created date, if both aren't available use the current date.
	 */
	private void set_node_time_ago()
	{
    	Date now = new Date();
    	if(this.node_comment_statistics_last_updated != 0)
    		this.node_time_ago = (now.getTime() /1000) - this.node_comment_statistics_last_updated;
    	else if((this.node_comment_statistics_last_updated == 0) && (this.node_created !=0))
    		this.node_time_ago= (now.getTime() /1000) - this.node_created;
    	else
    		this.node_time_ago= (now.getTime()/1000);
	}
	
	public long get_node_time_ago() {
		return this.node_time_ago;
	}
	
	/*
	 * This takes the nodes time ago timespan and formats it with some logic
	 */
	public String get_formatted_node_time_ago() {
		long t = get_node_time_ago();
		StringBuilder sb = new StringBuilder();
		long sec_per_day =86400;
		long sec_per_hour = 3600; 
		long days_ago = t / sec_per_day;
		long weeks_ago = days_ago/7;
		long hours_ago = 0;
		long minutes_ago =0;
		
		if(days_ago >= 1) { 
			if(weeks_ago>0)
			{
				sb.append(weeks_ago + " weeks, " + (days_ago - (weeks_ago * 7))+" days ago");
				
			}
			else
			{
				sb.append(days_ago + " days " + ((t - (days_ago * sec_per_day)) / sec_per_hour) + " hours ago");
			}
		} else {
			hours_ago = t / sec_per_hour;
			if(hours_ago <= 1) {
				minutes_ago = t / 60;
				if(minutes_ago ==0) {
					if(t<20)
						sb.append("Just now");
					else
						sb.append(t+" seconds ago");
				} else {
					sb.append(minutes_ago + " minutes ago");
				}
			} else {
				sb.append(hours_ago + " hours ago");
			}
		}
		return sb.toString();
	}
	
	private void set_users_name(JSONObject _json) throws JSONException
	{
		if(_json.has("users_name"))
			this.users_name=_json.getString("users_name");
	}
	public String get_users_name()
	{
		return this.users_name;
	}
	/*
	 * Set the object nid which is the node id for the Drupal Node
	 */
	private void set_nid(JSONObject _json) throws JSONException {
		if(_json.has("nid"))
			this.nid=_json.getInt("nid");
	}
	public int get_nid(){
		return this.nid;
	}
	
	/*
	 * Set the Node's last updated time aka node_changed
	 */
	private void set_history_user_timestamp(JSONObject _json) throws JSONException {
		if(_json.has("history_user_timestamp"))
			this.history_user_timestamp=Date.parse(_json.getString("history_user_timestamp"));
	}
	public long get_history_user_timestamp() {
		return this.history_user_timestamp;
	}
	
	/*
	 * Set the Node's last updated time aka node_changed
	 */
	private void set_node_comment_statistics_comment_count(JSONObject _json) throws JSONException {
		if(_json.has("node_comment_statistics_comment_count"))
			this.node_comment_statistics_comment_count=_json.getInt("node_comment_statistics_comment_count");
	}
	public int get_node_comment_statistics_comment_count() {
		return this.node_comment_statistics_comment_count;
	}
	
	/*
	 * Get the nodes comment count
	 */
	public String get_formatted_comment_statistics() {
		if(this.node_comment_statistics_comment_count > 0)
			return "("+this.node_comment_statistics_comment_count+")" + " Comments";
		else
			return "No comments yet";
	}
	
	/*
	 * Set the Node's last updated time aka node_changed
	 */
	private void set_node_changed(JSONObject _json) throws JSONException {
		if(_json.has("node_changed"))
			this.node_changed=_json.getLong("node_changed");
	}
	public long get_node_changed() {
		return this.node_changed;
	}
	
	/*
	 * Set the node created time
	 */
	private void set_node_created(JSONObject _json) throws JSONException {
		if(_json.has("node_created"))
			this.node_created=_json.getLong("node_created");
	}
	public long get_node_created() {
		return this.node_created;
	}
	
	/*
	 * Set the nodes term_data_name aka this is the name of the park for this condition.
	 */
	private void set_term_data_name(JSONObject _json) throws JSONException {
		if(_json.has("term_data_name"))
			this.term_data_name=_json.getString("term_data_name");
	}
	public String get_term_data_name(){
		return this.term_data_name;
	}
	
	/*
	 * Set the nodes title
	 */
	private void set_node_title(JSONObject _json) throws JSONException {
		if(_json.has("node_title"))
			this.node_title=_json.getString("node_title");
	}
	public String get_node_title(){
		return this.node_title;
	}
	
	/*
	 * Set the nodes body aka the node_revision_body
	 */
	private void set_node_revisions_body(JSONObject _json) throws JSONException {
		if(_json.has("node_revisions_body"))
			this.node_revisions_body=_json.getString("node_revisions_body");
	}
	public String get_node_revisions_body(){
		return this.node_revisions_body;
	}
	
	/*
	 * Set the nodes body aka the node_comment_statistics_last_updated
	 */
	private void set_node_comment_statistics_last_updated(JSONObject _json) throws JSONException {
		if(_json.has("node_comment_statistics_last_updated"))
			this.node_comment_statistics_last_updated=_json.getLong("node_comment_statistics_last_updated");
	}
	public long get_node_comment_statistics_last_updated(){
		return this.node_comment_statistics_last_updated;
	}
	
	/*
	 * This is specific to the node type "trail_condition"
	 * This is the field that contains the value of the trail condition
	 */
	private void set_node_data_field_condition_rating_field_condition_rating_value(JSONObject _json) throws JSONException {
		if(_json.has("node_data_field_condition_rating_field_condition_rating_value"))
			this.node_data_field_condition_rating_field_condition_rating_value=_json.getString("node_data_field_condition_rating_field_condition_rating_value");
	}
	public String get_node_data_field_condition_rating_field_condition_rating_value(){
		return this.node_data_field_condition_rating_field_condition_rating_value;
	}
}

