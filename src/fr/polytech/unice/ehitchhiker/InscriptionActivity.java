/**
 * InscriptionActivity.java, Created 1 févr. 2011 at 16:21:47
 *
 * Project : ehitchhikerAndroid
 * Package : fr.polytech.unice.ehitchhiker
 * 
 * Author : Christophe Dupont
 * Mail : mail@christophedupont.com
 * 
 */
package fr.polytech.unice.ehitchhiker;

import java.util.ArrayList;
import java.util.Calendar;

import fr.polytech.unice.ehitchhiker.APIAccess.Response;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author kinder
 * 
 */
public class InscriptionActivity extends Activity implements OnClickListener {

	private EditText pseudo;
	private Spinner genre;
	private Button dateNaissance;
	private Button datePermis;
	private ImageButton plusCar;
	private String dateNaissanceString;
	private String datePermisString;
	private Button valider;
	private ArrayList<View> voitures = new ArrayList<View>();

	static final int DIALOG_DATE_NAISSANCE = 101;
	static final int DIALOG_DATE_PERMIS = 102;
	static final int DIALOG_NON_REMPLI = 103;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inscription);
		pseudo = (EditText) this.findViewById(R.id.inscription_pseudo_input);
		genre = (Spinner) this.findViewById(R.id.inscription_genre_input);
		dateNaissance = (Button) this
				.findViewById(R.id.inscription_naissance_input);
		datePermis = (Button) this.findViewById(R.id.inscription_permis_input);
		plusCar = (ImageButton) this.findViewById(R.id.inscription_plus);

		ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
				R.array.genre, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		genre.setAdapter(adapter);

		dateNaissance.setOnClickListener(this);
		datePermis.setOnClickListener(this);
		plusCar.setOnClickListener(this);
		
		valider = (Button) this.findViewById(R.id.inscription_valider);
		valider.setOnClickListener(this);
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {

		case DIALOG_DATE_NAISSANCE:

			Calendar c = Calendar.getInstance();

			int cyear = c.get(Calendar.YEAR);

			int cmonth = c.get(Calendar.MONTH);

			int cday = c.get(Calendar.DAY_OF_MONTH);


			return new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker arg0, int arg1,
								int arg2, int arg3) {
							String jourNaissance = String.valueOf(arg3);
							if (arg3 < 10) {
								jourNaissance = "0" + jourNaissance;
							}
							String moisNaissance = String.valueOf(arg2 + 1);
							if (arg2 + 1 < 10) {
								moisNaissance = "0" + moisNaissance;
							}
							String anneeNaissance = String.valueOf(arg1);
							dateNaissanceString = jourNaissance + "/"
									+ moisNaissance + "/" + anneeNaissance;
							dateNaissance.setText(dateNaissanceString);

						}
					}, cyear, cmonth, cday);

		case DIALOG_DATE_PERMIS:

			Calendar c2 = Calendar.getInstance();

			int cyear2 = c2.get(Calendar.YEAR);

			int cmonth2 = c2.get(Calendar.MONTH);

			int cday2 = c2.get(Calendar.DAY_OF_MONTH);

			
			return new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker arg0, int arg1,
								int arg2, int arg3) {
							String jourNaissance = String.valueOf(arg3);
							if (arg3 < 10) {
								jourNaissance = "0" + jourNaissance;
							}
							String moisNaissance = String.valueOf(arg2 + 1);
							if (arg2 + 1 < 10) {
								moisNaissance = "0" + moisNaissance;
							}
							String anneeNaissance = String.valueOf(arg1);
							datePermisString = jourNaissance + "/"
									+ moisNaissance + "/" + anneeNaissance;
							datePermis.setText(datePermisString);

						}
					}, cyear2, cmonth2, cday2);
			
			
		case DIALOG_NON_REMPLI:
			AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
			builder3.setTitle("Il manque quelque chose...");
			builder3.setMessage("Vérifiez que vous avez bien donné toutes les informations !");
			
			builder3.setNeutralButton("OK", 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							
						}

					});
			return builder3.create();
		}

		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.inscription_naissance_input) {
			showDialog(DIALOG_DATE_NAISSANCE);
		}
		if (arg0.getId() == R.id.inscription_permis_input) {
			showDialog(DIALOG_DATE_PERMIS);
		}
		if(arg0.getId()== R.id.inscription_plus){
			Log.v("Inscription", "Une voiture en plus!");
			
			
			View v = View.inflate(this, R.layout.inscription_voiture, null);
			TextView number = (TextView) v.findViewById(R.id.inscription_voiture_number);
			number.setText(voitures.size()+1+".");
			voitures.add(v);
			LinearLayout lin = (LinearLayout) this.findViewById(R.id.inscription_linear_layout);
			lin.addView(v);
			
		}
		
		if(arg0.getId()==R.id.inscription_valider){
			Log.v("Inscription", "Appui sur s'inscrire");
			ArrayList<Car> l = new ArrayList<Car>();
			for(int i=0; i<voitures.size(); i++){
				View v = voitures.get(i);
				EditText marque = (EditText) v.findViewById(R.id.inscription_marque_input);
				EditText modele = (EditText) v.findViewById(R.id.inscription_modele_input);
				EditText couleur = (EditText) v.findViewById(R.id.inscription_couleur_input);
				EditText conso = (EditText) v.findViewById(R.id.inscription_conso_input);
				String sMarque = marque.getText().toString();
				String sModele = modele.getText().toString();
				String sCouleur = couleur.getText().toString();
				String sConso = conso.getText().toString();
				if(sMarque.length()>0 && sModele.length()>0 && sCouleur.length() >0 && sConso.length() > 0){
					Car c = new Car();
					c.marque=sMarque;
					c.modele=sModele;
					c.couleur=sCouleur;
					c.consommation = Double.parseDouble(sConso);
					l.add(c);				
				}				
			}
			
			
			Log.v("Inscription", "Voitures : "+l.toString());
			String utilisateur = UserParameters.getUserAccount();
			String sPseudo = pseudo.getText().toString();
			int g = genre.getSelectedItemPosition();
			String sGenre;
			if(g==0){
				sGenre = "M";
			}else{	
				sGenre = "F";
			}
			
			
			if(sPseudo.length()>0 && dateNaissanceString.length() >0 && datePermisString.length() >0){
				Toast.makeText(this.getApplicationContext(), "Inscription en cours...", Toast.LENGTH_LONG).show();
				
	 			APIAccess.Response r = APIAccess.get().sendInscriptionRequest(utilisateur, sPseudo, sGenre, dateNaissanceString, datePermisString, l);
	 			
	 			switch(r) {
	 			case INSCRIPTION_GENRE_INCORRECT:
	 				Log.v("ERREUR CONNEXION", "GENRE INCORRECT");
	 				break;
	 			case INSCRIPTION_NAISSANCE_INCORRECTE:
	 				Log.v("ERREUR CONNEXION", "NAISSANCE INCORRECT");
	 				break;
	 				
	 			case INSCRIPTION_PERMIS_INCORRECT:
	 				Log.v("ERREUR CONNEXION", "PERMIS INCORRECT");
	 				break;
	 				
	 			case INSCRIPTION_OK:
	 				Log.v("ERREUR CONNEXION", "INSCRIPTION OK");
	 				break;
	 				
	 			case INSCRIPTION_PSEUDO_DEJA_PRIS:
	 				Log.v("ERREUR CONNEXION", "PSEUDO DEJA PRIS");
	 				break;
	 				
	 			case INSCRIPTION_DEJA_INSCRIT:
	 				Log.v("ERREUR CONNEXION", "DEJA INSCRIT");
	 				break;
	 				
	 			default:
	 				Log.v("ERREUR CONNEXION", "WHAT THE FUCK?");
	 				
	 			}
	 			
				if(r.equals(APIAccess.Response.INSCRIPTION_OK)){
					Toast.makeText(this.getApplicationContext(), "Inscription terminée !", Toast.LENGTH_SHORT).show();
					this.finish();
				}else{
	
					Toast.makeText(this.getApplicationContext(), "Erreur !", Toast.LENGTH_SHORT).show();
				}
			}
			else{
				showDialog(DIALOG_NON_REMPLI);
			}
			
		}

	}

}
