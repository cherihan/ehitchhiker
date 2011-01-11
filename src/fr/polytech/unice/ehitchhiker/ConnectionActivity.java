/**
 * ConnectionActivity.java, Created 11 janv. 2011 at 13:49:26
 *
 * Project : ehitchhikerAndroid
 * Package : fr.polytech.unice.ehitchhiker
 * 
 * Author : Christophe Dupont
 * Mail : mail@christophedupont.com
 * 
 */
package fr.polytech.unice.ehitchhiker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author kinder
 *
 */
public class ConnectionActivity extends Activity implements OnClickListener {
	
	
	
	private Button connect;
	private Button suscribe;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection);
        connect = (Button) this.findViewById(R.id.connection_connect);
        suscribe = (Button) this.findViewById(R.id.connection_suscribe);
        connect.setOnClickListener(this);
        suscribe.setOnClickListener(this);
        Log.w("Connection", "Ca passe ici");
    }

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		Log.w("Connection", "Ca passe ici");
		
		
		if(v.getId()==R.id.connection_connect){
			Toast.makeText(this.getApplicationContext(), "Connect", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this.getApplicationContext(), "Suscribe", Toast.LENGTH_SHORT).show();
		}

	}

}
