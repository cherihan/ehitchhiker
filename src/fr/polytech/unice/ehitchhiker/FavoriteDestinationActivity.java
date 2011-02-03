package fr.polytech.unice.ehitchhiker;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FavoriteDestinationActivity extends Activity {
	private ListView favDestListView;
	private String type;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.favorite_destination);
		
		type = getIntent().getExtras().getString("type");

		favDestListView = (ListView) this
				.findViewById(R.id.favoriteDestinationListView);

		HashMap<String, Object> map = UserParameters.getUserFavorites();

		ArrayList<Destination> dest = ((ArrayList<Destination>) map
				.get("favorites"));

		favDestListView.setAdapter(new FavoriteDestinationListAdapter(this,
				dest));
		// UserParameters.init(this);
		// UserParameters.setUserFavorites(map);

	}
	
	public void onResume() {
		super.onResume();
		updateFavorites();
	}
	
	public void onRestart() {
		super.onRestart();
		updateFavorites();
	}

	public void updateFavorites() {
		HashMap<String, Object> map = UserParameters.getUserFavorites();

		ArrayList<Destination> dest = ((ArrayList<Destination>) map
				.get("favorites"));

		favDestListView.setAdapter(new FavoriteDestinationListAdapter(this,
				dest));
	}
	
	public String getType() {
		return type;
	}

}

class FavoriteDestinationListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<Destination> favDestList;
	private String header;
	private Context context;

	public FavoriteDestinationListAdapter(Context context,
			ArrayList<Destination> favDestList) {
		this.context = context;
		this.favDestList = favDestList;
		inflater = LayoutInflater.from(context);
		header = (String) context.getResources().getString(
				R.string.favoriteDestinationHeader);
	}

	@Override
	public int getCount() {
		return favDestList.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		if (position == 0)
			return header;
		else
			return favDestList.get(position - 1);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public boolean areAllItemsEnabled() {
		return false;
	}

	public boolean isEnabled(int position) {
		return (position == 0) ? false : true;
	}

	public int getViewType(int position) {
		return (position == 0) ? 0 : 1;
	}

	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FavoriteDestinationListViewHolder holder;

		holder = new FavoriteDestinationListViewHolder();
		switch (getViewType(position)) {
		case 0:
			convertView = inflater.inflate(R.layout.list_destination_header,
					null);
			holder.txt1 = (TextView) convertView
					.findViewById(R.id.destinationListViewHeader);
			holder.txt1.setText(header);
			break;
		case 1:
			convertView = inflater.inflate(R.layout.favorite_destination_list,
					null);
			holder.txt1 = (TextView) convertView
					.findViewById(R.id.favoriteDestinationText);
			holder.txt1.setText(favDestList.get(position - 1).toString());
			holder.btn1 = (Button) convertView.findViewById(R.id.deleteButton);
			holder.btn2 = (Button) convertView.findViewById(R.id.launchButton);
			holder.btn1.setOnClickListener(new DeleteButtonListener(context,
					position - 1));
			holder.btn2.setOnClickListener(new LaunchButtonListener(context,
					position - 1));
			break;

		}

		return convertView;
	}

	public static class FavoriteDestinationListViewHolder {
		TextView txt1;
		Button btn1;
		Button btn2;
	}

}

class DeleteButtonListener implements OnClickListener {

	private Context context;
	private int position;

	public DeleteButtonListener(Context context, int position) {
		this.context = context;
		this.position = position;
	}

	@Override
	public void onClick(View v) {
		HashMap<String, Object> map = UserParameters.getUserFavorites();

		ArrayList<Destination> dest = ((ArrayList<Destination>) map
				.get("favorites"));

		dest.remove(position);
		map.remove("favorites");
		map.put("favorites", dest);

		UserParameters.setUserFavorites(map);
		
		//Toast.makeText(context, "lancer map", Toast.LENGTH_SHORT).show();
		((FavoriteDestinationActivity) context).updateFavorites();
	}

}

class LaunchButtonListener implements OnClickListener {

	private Context context;
	private int position;

	public LaunchButtonListener(Context context, int position) {
		this.context = context;
		this.position = position;
	}

	@Override
	public void onClick(View v) {
		HashMap<String, Object> map = UserParameters.getUserFavorites();

		ArrayList<Destination> dest = ((ArrayList<Destination>) map
				.get("favorites"));

		Intent intent = new Intent(context, RequestSavedActivity.class);
		intent.putExtra("type", ((FavoriteDestinationActivity) context).getType());
		intent.putExtra("destination", dest.get(position));
		((FavoriteDestinationActivity) context).startActivity(intent);
	}

}
