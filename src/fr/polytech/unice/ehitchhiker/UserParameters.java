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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author kinder
 * 
 */
public class UserParameters {
	private final static String PREF = "eHitchhikerPrefs";
	private final static String FILENAME = "favorites_file";
	private final static String userAccount_KEY = "userAccount";
	private final static String userFavorites_KEY = "userFavorites";

	private static Context context;

	public static void init(Context C) {
		context = C;
	}

	public static String getUserAccount() {
		SharedPreferences prefs = context.getSharedPreferences(PREF, 0);
		String userAccount = prefs.getString(userAccount_KEY, "null");
		return userAccount;
	}

	public static void setUserAccount(String s) {
		SharedPreferences prefs = context.getSharedPreferences(PREF, 0);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString(userAccount_KEY, s);
		edit.commit();

	}

	public static void setUserFavorites(HashMap map) {
		try {
			FileOutputStream fos = context.openFileOutput(FILENAME,
					context.MODE_PRIVATE);
			try {
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(map);
				oos.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static HashMap getUserFavorites() {
		HashMap map = null;
		try {
			FileInputStream fis = context.openFileInput(FILENAME);
			try {
				ObjectInputStream ois = new ObjectInputStream(fis);
				try {
					map = (HashMap)ois.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ois.close();
				fis.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
}
