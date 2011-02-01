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

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
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

	}

}
