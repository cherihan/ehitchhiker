package fr.polytech.unice.ehitchhiker;

import java.util.ArrayList;
import java.util.HashMap;

import fr.polytech.unice.ehitchhiker.FavoriteDestinationListAdapter.FavoriteDestinationListViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class DestinationChoiceActivity extends Activity implements TextWatcher,
		OnItemClickListener {
	private ArrayList<Destination> suggestionList;
	private ListView suggestionListView;
	private EditText searchDestinationEditText;
	private Handler reqHandler;
	private Runnable timer;
	private String type;

	private static final int UPDATE_TIME = 500;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.destination_choice);
		
		type = getIntent().getExtras().getString("type");
		reqHandler = new Handler();
		timer = new Runnable() {
			public void run() {
				String address = correctAddress(searchDestinationEditText
						.getText().toString());
				updateSuggestionResults(APIAccess.get()
						.getGoogleMapsSuggestionList(address));

			}
		};

		suggestionListView = (ListView) this
				.findViewById(R.id.destinationChoiceListView);
		searchDestinationEditText = (EditText) this
				.findViewById(R.id.searchDestinationEditText);
		searchDestinationEditText.addTextChangedListener(this);

		suggestionList = new ArrayList<Destination>();
		suggestionList.add(new Destination("Aucun résultat",0,0));
		suggestionListView.setAdapter(new DestinationChoiceListAdapter(this, suggestionList));
		suggestionListView.setOnItemClickListener(this);

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	public void updateSuggestionResults(ArrayList<Destination> suggestions) {
		suggestionList = suggestions;
		suggestionListView.setAdapter(new DestinationChoiceListAdapter(this, suggestionList));
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// String address =
		// correctAddress(searchDestinationEditText.getText().toString());
		// updateSuggestionResults(APIAccess.get().getGoogleMapsSuggestionList(address));
		reqHandler.removeCallbacks(timer);
		reqHandler.postDelayed(timer, UPDATE_TIME);
	}

	private String correctAddress(String add) {
		String[] tmp = add.split(" ");

		String address = "";
		for (String temp : tmp)
			address += temp + "%20";

		return address;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		HashMap<String, Object> map = UserParameters.getUserFavorites();
		Destination tmp;
		
		tmp = (Destination)map.get("last2");
		map.remove("last3");
		map.put("last3", tmp);
		
		tmp = (Destination)map.get("last1");
		map.remove("last2");
		map.put("last2", tmp);
		
		tmp = suggestionList.get(position);
		map.put("last1", tmp);
		
		UserParameters.setUserFavorites(map);
		Intent intent = new Intent(this, RequestSavedActivity.class);
		intent.putExtra("type", type);
		intent.putExtra("destination", suggestionList.get(position));
		startActivity(intent);
	}
}

class DestinationChoiceListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<Destination> sugg;

	public DestinationChoiceListAdapter(Context context,
			ArrayList<Destination> sugg) {
		this.sugg = sugg;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return sugg.size();
	}

	@Override
	public Object getItem(int position) {
		return sugg.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public boolean areAllItemsEnabled() {
		return true;
	}

	public boolean isEnabled(int position) {
		return true;
	}

	public int getViewType(int position) {
		return 0;
	}

	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView address;

		convertView = inflater.inflate(R.layout.destination_choice_list, null);
		address = (TextView) convertView
				.findViewById(R.id.suggestionRowText);
		address.setText(sugg.get(position).toString());

		return convertView;
	}

}
