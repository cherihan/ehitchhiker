/**
 * UserParameters.java, Created 13 janv. 2011 at 12:34:00
 *
 * Project : ehitchhikerAndroid
 * Package : fr.polytech.unice.ehitchhiker
 * 
 * Author : Christophe Dupont
 * Mail : mail@christophedupont.com
 * 
 */
package fr.polytech.unice.ehitchhiker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author kinder
 *
 */
public class UserParameters {
	private final static String PREF = "eHitchhikerPrefs";
	private final static String userAccount_KEY="userAccount";
	private static Context context;
	
	public static void init(Context C) {
		context = C;
	}
	public static String getUserAccount(){
		SharedPreferences prefs = context.getSharedPreferences(PREF, 0);
		String userAccount = prefs.getString(userAccount_KEY, "null");
		return userAccount;
	}
	
	public static void setUserAccount(String s){
		SharedPreferences prefs = context.getSharedPreferences(PREF,0);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString(userAccount_KEY, s);
		edit.commit();
		
	}
}
