package fr.polytech.unice.ehitchhiker;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * FirstMenuActivity.java, Created 12 janv. 2011 at 16:34:51
 *
 * Project : ehitchhikerAndroid
 * Package : 
 * 
 * Author : Christophe Dupont
 * Mail : mail@christophedupont.com
 * 
 */

/**
 * @author kinder
 * 
 */
public class FirstMenuActivity extends Activity implements OnClickListener,
		OnTouchListener {

	private final static int DIALOG_ACCOUNTS = 100;
	private final static int DIALOG_CONFIRM = 101;
	private final static int REQUEST_AUTHENTICATE = 1;
	private final static String PREF = "eHitchhiker";
	private ImageButton conducteur;
	private ImageButton passager;
	private String authToken;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstmenu);
		UserParameters.init(getApplicationContext());
		conducteur = (ImageButton) this
				.findViewById(R.id.firstMenuConductorButton);
		passager = (ImageButton) this
				.findViewById(R.id.firstMenuPassagerButton);
		conducteur.setOnClickListener(this);
		passager.setOnClickListener(this);
		passager.setOnTouchListener(this);

	}

	public void onResume() {
		super.onResume();
		passager.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.entranceanimfromleft));
		conducteur.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.entranceanimfromright));

		if (UserParameters.getUserAccount() == "null") {
			this.showDialog(DIALOG_ACCOUNTS);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ACCOUNTS:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("SŽlectionnez un compte google");
			final AccountManager manager = AccountManager.get(this);
			final Account[] accounts = manager.getAccountsByType("com.google");
			final int size = accounts.length;

			String[] names = new String[size];
			for (int i = 0; i < size; i++) {
				names[i] = accounts[i].name;
			}

			builder.setItems(names, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					gotAccount(accounts[which].name);
				}

			});
			return builder.create();

		case DIALOG_CONFIRM:
			AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
			builder2.setTitle("Etes vous sžr ?");
			builder2.setMessage("Etes vous sur de vouloir utiliser le compte '"
					+ authToken + "' pour vous connecter ?");
			builder2.setPositiveButton("Oui",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							UserParameters.setUserAccount(authToken);
						}

					});
			builder2.setNegativeButton("Non",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							showDialog(DIALOG_ACCOUNTS);
						}

					});
			return builder2.create();
		}

		return null;
	}

	/**
	 * @param name
	 */
	protected void gotAccount(String name) {
		authToken = name;
		showDialog(DIALOG_CONFIRM);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {

		arg0.setBackgroundColor(Color.GRAY);
		return false;
	}

}
