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
}
