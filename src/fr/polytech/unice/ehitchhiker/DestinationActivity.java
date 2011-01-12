package fr.polytech.unice.ehitchhiker;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DestinationActivity extends Activity {
	private String[] destinationList;
	private ListView list;
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.destination);
        
        list = (ListView)this.findViewById(R.id.destinationList);
        destinationList = (String[])this.getResources().getStringArray(R.array.destination_list);
        list.setAdapter(new DestinationAdapter(this, destinationList));
    }
	
}

class DestinationAdapter extends BaseAdapter {

	private String[] destList;
	private LayoutInflater inflater;
	
	public DestinationAdapter(Context context, String[] destList) {
		this.destList = destList;
		inflater = LayoutInflater.from(context);
	}
	public int getCount() {
		return destList.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return destList[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DestinationViewHolder holder;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listdestination, null);
			holder = new DestinationViewHolder();
			holder.txt1 = (TextView)convertView.findViewById(R.id.destinationText);
			convertView.setTag(holder);
		} else {
			holder = (DestinationViewHolder)convertView.getTag();
		}
		
		holder.txt1.setText(destList[position]);
		
		return convertView;
	}
	
	public static class DestinationViewHolder {
		TextView txt1;
	}
	
	
}
