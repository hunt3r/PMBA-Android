package com.chrishunters.pmba;
//import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/*"user": {
        "uid": "3",
        "name": "chunter",
        "pass": "54d68418d3b99f1bdbdf12c8983804e2",
        "mail": "hunter.christopher@gmail.com",
        "mode": "0",
        "sort": "0",
        "threshold": "0",
        "theme": "",
        "signature": "\x3cp\x3eCome on Rick I'm not a rope, now pull your socks up.\x26nbsp; Come on Rick, I'm not a child, I'm not special or one of a kind.\x26nbsp; \x3cbr\x3eCome on Rick I'm not a drunk, I know my own worth...\x3cbr\x3e I'm an adult!\x26nbsp; I'm an adult!\x3c\/p\x3e",
        "signature_format": "1",
        "created": "1161018173",
        "access": "1279829934",
        "login": 1279830060,
        "status": "1",
        "timezone": "-14400",
        "language": "",
        "picture": "sites\/default\/files\/pictures\/picture-3.jpg",
        "init": "",
        "data": "a:7:{s:14:\"picture_delete\";i:0;s:14:\"picture_upload\";s:0:\"\";s:13:\"form_build_id\";s:37:\"form-9926fe9f3d26f4e369ef562ba71e7568\";s:17:\"messaging_default\";s:4:\"mail\";s:27:\"notifications_send_interval\";s:1:\"0\";s:18:\"notifications_auto\";i:0;s:7:\"contact\";i:1;}",
        "timezone_name": "America\/New_York",
        "picture_delete": 0,
        "picture_upload": "",
        "form_build_id": "form-9926fe9f3d26f4e369ef562ba71e7568",
        "messaging_default": "mail",
        "notifications_send_interval": "0",
        "notifications_auto": 0,
        "contact": 1,
        "roles": {
            "2": "authenticated user",
            "4": "paying member",
            "7": "sudo admin",
            "8": "board of directors"
        },
        "profile_realname": "Chris Hunter",
        "profile_first_name": "Chris",
        "profile_last_name": "Hunter",
        "profile_address_line_1": "830 S. Howard St",
        "profile_address_line_2": "",
        "profile_address_city": "Philadelphia",
        "profile_address_state": "PA",
        "profile_address_zip": "19147",
        "profile_imba_member": "1",
        "profile_liability_waiver": "ch",
        "profile_bio": "I'm first a rider, then a web developer, then an Economist. I enjoy mountain biking, motocross, playing guitar and generally geeking out on my computer.\r\nOne of my current goals is to make the PMBA one of the most legit non-profits in Philadelphia.\r\nI like to go on mountain biking trips all over the country. This year Team Locust went to Whistler B.C., George Washington State Park (twice), as well as several regional destinations.\r\nI'm also graduating Drexel Universities LeBowe College of Business for Economics in the Spring of 2008.\r\nLately I've been doing a lot of riding in the Wissahickon only since I am very busy with school and my website \/ design freelance business.\r\nYou can see my work by going to: http:\/\/www.chrishunters.com\r\nI also play really weird music, so if you're into that, here's a fairly recent one.\r\nhttp:\/\/www.chrishunters.com\/music\/3-4-08.mp3\u00a0",
        "profile_riding_places": "Wissahickon, Fruita, Rays, Raystown Lake, Whiteclay, Kingdom Trails, Kokopelli, Michaux, Belmont Plateau, Whistler, George Washington State Forrest, Lehigh",
        "profile_shirt_size": "L",
        "profile_ability": "Expert",
        "profile_birth_day": {
            "month": "4",
            "day": "11",
            "year": "1981"
        },
        "profile_gender": "Male",
        "profile_phone": "9085812733",
        "user_aim": "chrishunter",
        "user_website": "http:\/\/www.chrishunters.com",
        "og_groups": {
            "1965": {
                "title": "Bike to Beer",
                "type": "group",
                "status": "1",
                "nid": "1965",
                "og_role": "0",
                "is_active": "1",
                "is_admin": "0",
                "uid": "3",
                "created": "1273763548",
                "changed": "1273763548"
            },
            "1976": {
                "title": "Blitzkrieg Gravity Soldiers",
                "type": "group",
                "status": "1",
                "nid": "1976",
                "og_role": "0",
                "is_active": "1",
                "is_admin": "0",
                "uid": "3",
                "created": "1273974034",
                "changed": "1273974034"
            },
            "273": {
                "title": "Board of Directors",
                "type": "group",
                "status": "1",
                "nid": "273",
                "og_role": "0",
                "is_active": "1",
                "is_admin": "0",
                "uid": "3",
                "created": "1272921644",
                "changed": "1272921644"
            },
            "2119": {
                "title": "Trail Committee",
                "type": "group",
                "status": "1",
                "nid": "2119",
                "og_role": "0",
                "is_active": "1",
                "is_admin": "1",
                "uid": "3",
                "created": "1278508121",
                "changed": "1278508121"
            },
            "144": {
                "title": "Web Committee",
                "type": "group",
                "status": "1",
                "nid": "144",
                "og_role": "0",
                "is_active": "1",
                "is_admin": "0",
                "uid": "3",
                "created": "1273759852",
                "changed": "1273759852"
            }
            */

/*
 * The Condition class is an object based on the trail_conditions view object
 * Fields can be added to the view, and they will be ignored here unless those properties are added
 */
public class User {

	private int uid = 0;
	private String name = "Lurker";
	//private String username
	
	/*
	 * Constructor - Default
	 */
	public User()
	{
		
	}
	
	/*
	 * Constructor takes Serialized Drupal View Row object as JSON string
	 * @String _json
	 */
	public User(JSONObject j)
	{
		
		try {
			//create a JSONObject from a String containing JSON data
			set_name(j);
			set_uid(j);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	private void set_name(JSONObject j) throws JSONException
	{
		if(j.has("name"));
			this.name = j.getString("name");
	}
	public String get_name()
	{
		return this.name;
	}
	
	private void set_uid(JSONObject j) throws JSONException
	{
		if(j.has("uid"));
			this.uid = j.getInt("uid");
	}
	public int get_uid()
	{
		return this.uid;
	}
}

