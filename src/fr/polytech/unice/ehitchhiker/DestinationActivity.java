package fr.polytech.unice.ehitchhiker;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DestinationActivity extends Activity implements OnItemClickListener{
    /** Called when the activity is first created. */
	private String[] strListView;
	private ListView destinationListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.destination);
        
        destinationListView = (ListView)this.findViewById(R.id.destinationListView);
        strListView = (String[])this.getResources().getStringArray(R.array.destListViewStr);
        destinationListView.setAdapter(new DestinationListAdapter(this, strListView));
        destinationListView.setOnItemClickListener(this);
       
    }
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (position == 0) {
			Intent intent = new Intent(this, DestinationChoiceActivity.class);
			startActivity(intent);
		}
		
	}

}

class DestinationListAdapter extends BaseAdapter{
	
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
	
	public boolean isEnabled(int position){
		return (position == 3)? false : true; 
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
	
	public int getViewTypeCount(){
		return 3;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DestinationListViewHolder holder;
		
		if (convertView == null) {
			holder = new DestinationListViewHolder();
			switch (getViewType(position)) {
			case 0:
				convertView = inflater.inflate(R.layout.list_destination_1, null);
				holder.txt1 = (TextView)convertView.findViewById(R.id.destinationChoiceText);
				break;
			case 1:
				convertView = inflater.inflate(R.layout.list_destination_header, null);
				holder.txt1 = (TextView)convertView.findViewById(R.id.destinationListViewHeader);
				break;
			case 2:
				convertView = inflater.inflate(R.layout.list_destination_2, null);
				holder.txt1 = (TextView)convertView.findViewById(R.id.destinationFavText);
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (DestinationListViewHolder)convertView.getTag();
		}
		
		holder.txt1.setText(strListView[position]);
		
		return convertView;
	}
	
	public static class DestinationListViewHolder {
		TextView txt1;
		ImageView img1;
	}
}