package fr.polytech.unice.ehitchhiker;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class DestinationChoiceActivity extends Activity implements TextWatcher{
	private ArrayList<String> suggestionList;
	private ListView suggestionListView;
	private EditText searchDestinationEditText;
	private Handler reqHandler;
	private Runnable timer;
	
	private static final int UPDATE_TIME = 500;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.destination_choice);
        
        reqHandler = new Handler();
        timer =  new Runnable () {
        	public void run() {
        		String address = correctAddress(searchDestinationEditText.getText().toString());
        		updateSuggestionResults(APIAccess.get().getGoogleMapsSuggestionList(address));
        		
        	}
        };
        
        suggestionListView = (ListView)this.findViewById(R.id.destinationChoiceListView);
        searchDestinationEditText = (EditText)this.findViewById(R.id.searchDestinationEditText);
        searchDestinationEditText.addTextChangedListener(this);
        
        suggestionList = new ArrayList<String>();
        suggestionList.add("Aucun résultat");
        suggestionListView.setAdapter(new ArrayAdapter<String>(this, R.layout.destination_choice_list, suggestionList));
        
    }

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {		
		
	}
	
	public void updateSuggestionResults(ArrayList<String> suggestions) {
		suggestionList = suggestions;
		suggestionListView.setAdapter(new ArrayAdapter<String>(this, R.layout.destination_choice_list, suggestionList));
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//String address = correctAddress(searchDestinationEditText.getText().toString());
		//updateSuggestionResults(APIAccess.get().getGoogleMapsSuggestionList(address));
		reqHandler.removeCallbacks(timer);
		reqHandler.postDelayed(timer, UPDATE_TIME);
	}
	
	private String correctAddress(String add){
		String[] tmp = add.split(" ");
		
		String address = "";
		for(String temp : tmp)
			address += temp + "%20";
		
		return address;
	}
}
