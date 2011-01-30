/**
 * APIAccess.java, Created 25 janv. 2011 at 11:40:44
 *
 * Project : ehitchhikerAndroid
 * Package : fr.polytech.unice.ehitchhiker
 * 
 * Author : Christophe Dupont
 * Mail : mail@christophedupont.com
 * 
 */
package fr.polytech.unice.ehitchhiker;


import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kinder.utils.WebClient;


/**
 * @author kinder
 *
 */
public class APIAccess {
	public enum Response {
	    CONNEXION_ERROR, CONNEXION_NEED_INSCRIPTION, CONNEXION_OK
	}
	private static APIAccess mInstance;
	
	
	
	private APIAccess(){
		
	}
	
	public static APIAccess get(){
		if(null==mInstance){
			mInstance = new APIAccess();
		}
		
		return mInstance;
	}
	
	
	public Response sendConnectionRequestWithGoogleAccount(String google){
		JSONObject obj = new JSONObject();
		try {
			obj.put("CONNEXION", google);
			
			
			try {
				JSONObject jsonRes = WebClient.getJSONObjectFromPostJSONToUrl(obj, Constants.URL_CONNEXION_DECONNEXION);
				
				if(jsonRes.getString("CONNEXION").equals("REUSSIE")) return Response.CONNEXION_OK;
				if(jsonRes.getString("CONNEXION").equals("UTILISATEUR_INCONNU")) return Response.CONNEXION_NEED_INSCRIPTION;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.CONNEXION_ERROR ;
		
	}
	
	public ArrayList<String> getGoogleMapsSuggestionList(String address) {
		ArrayList<String> suggestionList = new ArrayList<String>();
		 JSONObject jsonRes = null;
		try {
			jsonRes  = WebClient.getJSONFromUrl(Constants.URL_GOOGLE_MAPS + address + "&sensor=true&language=fr");
			if (!jsonRes.getString("status").equals("OK")) {
				suggestionList.add("Aucun résultat");
				return suggestionList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		JSONArray results = null;
		try {
			results  = jsonRes.getJSONArray("results");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i=0; i<results.length(); i++){
			try {
				suggestionList.add(results.getJSONObject(i).getString("formatted_address"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return suggestionList;
	}
}
