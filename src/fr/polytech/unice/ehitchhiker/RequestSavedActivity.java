package fr.polytech.unice.ehitchhiker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class RequestSavedActivity extends Activity implements OnClickListener {

	private Button cancel;
	private Button map;
	private String type;
	private Destination dest;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestsaved);
        
        type = getIntent().getExtras().getString("type");
        dest = (Destination)getIntent().getExtras().get("destination");
		UserParameters.init(getApplicationContext());
		cancel = (Button) this.findViewById(R.id.RSCancelButton);
		map = (Button) this.findViewById(R.id.RSMapButton);
		cancel.setOnClickListener(this);
		map.setOnClickListener(this);
		

    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			if(v.getId()==R.id.RSCancelButton){
				// requete serveur annulation trajet
				Toast.makeText(this.getApplicationContext(), "annuler", Toast.LENGTH_SHORT).show();
			}
			if(v.getId()==R.id.RSMapButton){
				// requete serveur annulation trajet
				if (type.equals("pieton")) {
					Intent intent = new Intent(this, SearchMapActivity.class);
					intent.putExtra("type", type);
					intent.putExtra("destination", dest);
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(), " Driver - can't display map !", Toast.LENGTH_SHORT).show();
				}
			}
	}

}
