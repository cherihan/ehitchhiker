package fr.polytech.unice.ehitchhiker;

import java.util.ArrayList;
import java.util.HashMap;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DestinationActivity extends Activity implements
		OnItemClickListener, OnItemLongClickListener {
	/** Called when the activity is first created. */
	private String[] strListView;
	private ListView destinationListView;
	public static final int DIALOG_FAVORITE = 100;
	public static final int DIALOG_ERROR = 101;
	private int dest_chosen = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.destination);

		destinationListView = (ListView) this
				.findViewById(R.id.destinationListView);
		strListView = (String[]) this.getResources().getStringArray(
				R.array.destListViewStr);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("last1", new Destination("Aucun", 0, 0));
		map.put("last2", new Destination("Aucun", 0, 0));
		map.put("last3", new Destination("Aucun", 0, 0));
		map.put("favorites", new ArrayList<Destination>());

		strListView[4] = ((Destination) map.get("last1")).toString();
		strListView[5] = ((Destination) map.get("last2")).toString();
		strListView[6] = ((Destination) map.get("last3")).toString();

		UserParameters.init(this);
		UserParameters.setUserFavorites(map);

		destinationListView.setAdapter(new DestinationListAdapter(this,
				strListView));
		destinationListView.setOnItemClickListener(this);
		destinationListView.setOnItemLongClickListener(this);

	}

	public void onResume() {
		super.onResume();

		HashMap<String, Object> map = UserParameters.getUserFavorites();
		strListView[4] = ((Destination) map.get("last1")).toString();
		strListView[5] = ((Destination) map.get("last2")).toString();
		strListView[6] = ((Destination) map.get("last3")).toString();

		destinationListView.setAdapter(new DestinationListAdapter(this,
				strListView));
	}

	public void onRestart() {
		super.onRestart();

		HashMap<String, Object> map = UserParameters.getUserFavorites();
		strListView[4] = ((Destination) map.get("last1")).toString();
		strListView[5] = ((Destination) map.get("last2")).toString();
		strListView[6] = ((Destination) map.get("last3")).toString();

		destinationListView.setAdapter(new DestinationListAdapter(this,
				strListView));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position == 0) {
			Intent intent = new Intent(this, DestinationChoiceActivity.class);
			startActivity(intent);
		}
		
		if (position == 2) {
			Intent intent = new Intent(this, FavoriteDestinationActivity.class);
			startActivity(intent);
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
			int position, long id) {
		if (position > 3 && position < 7) {
			dest_chosen = position - 3;
			if (!strListView[position].equals("Aucun"))
				showDialog(DIALOG_FAVORITE);
			else
				showDialog(DIALOG_ERROR);
		}

		return true;
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_FAVORITE:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Destination favorite");
			builder.setMessage("Voulez-vous ajouter cette destination en favori?");
			builder.setPositiveButton("Oui",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							HashMap<String, Object> map = UserParameters
									.getUserFavorites();
							Destination dest = (Destination) map.get("last"
									+ dest_chosen);
							((ArrayList<Destination>) map.get("favorites"))
									.add(dest);
							UserParameters.setUserFavorites(map);
						}

					});
			builder.setNegativeButton("Non",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}

					});
			return builder.create();
			
		case DIALOG_ERROR:
			AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
			builder3.setTitle("Erreur");
			builder3.setMessage("Destination non valide");
			
			builder3.setNeutralButton("OK", 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							
						}

					});
			return builder3.create();
		}

		return null;
	}

}

class DestinationListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private String[] strListView;

	public DestinationListAdapter(Context context, String[] strListView) {
		this.strListView = strListView;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return strListView.length;
	}

	@Override
	public Object getItem(int position) {
		return strListView[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public boolean areAllItemsEnabled() {
		return false;
	}

	public boolean isEnabled(int position) {
		return (position == 3) ? false : true;
	}

	public int getViewType(int position) {
		if (position >= 0 && position < 3) {
			return 0;
		} else if (position > 3 && position < 7) {
			return 2;
		} else {
			return 1;
		}
	}

	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DestinationListViewHolder holder;

		if (convertView == null) {
			holder = new DestinationListViewHolder();
			switch (getViewType(position)) {
			case 0:
				convertView = inflater.inflate(R.layout.list_destination_1,
						null);
				holder.txt1 = (TextView) convertView
						.findViewById(R.id.destinationChoiceText);
				break;
			case 1:
				convertView = inflater.inflate(
						R.layout.list_destination_header, null);
				holder.txt1 = (TextView) convertView
						.findViewById(R.id.destinationListViewHeader);
				break;
			case 2:
				convertView = inflater.inflate(R.layout.list_destination_2,
						null);
				holder.txt1 = (TextView) convertView
						.findViewById(R.id.destinationFavText);
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (DestinationListViewHolder) convertView.getTag();
		}

		holder.txt1.setText(strListView[position]);

		return convertView;
	}

	public static class DestinationListViewHolder {
		TextView txt1;
		ImageView img1;
	}
}