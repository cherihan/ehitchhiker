package fr.polytech.unice.ehitchhiker;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.kinder.utils.LocationHelper;



public class SearchMapActivity extends MapActivity implements LocationHelper.LocationHelperListener{
	private MapView searchMap;
	private MapController searchMapController;
	private ArrayList<Driver> drivers;
	private CurrentPositionOverlay curr;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_search);
		
		
		searchMap = (MapView)findViewById(R.id.searchMap);
		searchMap.setBuiltInZoomControls(true);
		searchMap.setSatellite(true);
		
		LocationHelper.init((LocationManager)getSystemService(Context.LOCATION_SERVICE), this);
		LocationHelper.get().start();
		curr = new CurrentPositionOverlay(this.getResources().getDrawable(R.drawable.marker_blue));
		//getIntent().getExtras().getString("type").toUpperCase()
		drivers = APIAccess.get().sendResearchRequest("richter.romain@gmail.com", "PIETON", "0674921998", 43.6171113, 7.0722257, "Marseille", 43.6171113, 7.0722257);
		//drivers = new ArrayList<Driver>();
		//drivers.add(new Driver("Edouard Marquez", "0684726678", 43.6171113, 7.0722257, 43.6171113, 7.0722257));
		
		DriverOverlay overlay = new DriverOverlay(this.getResources().getDrawable(R.drawable.marker_orange), drivers);
		
		searchMap.getOverlays().add(overlay);
		searchMap.getOverlays().add(curr);
		
		searchMapController = searchMap.getController();
		searchMapController.setZoom(15);

	}
	
	private int microdegrees(double value) {
		return (int)(value * 1000000);
	}
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void onDestroy() {
		super.onDestroy();
		LocationHelper.get().stop();
	}

	@Override
	public void onBetterLocationReceived(Location newLocation) {
		curr.updatePosition(newLocation.getLatitude(), newLocation.getLongitude());
		GeoPoint point = new GeoPoint(microdegrees(newLocation.getLatitude()), microdegrees(newLocation.getLongitude()));
		searchMapController.setCenter(point);
		
	}
	
	public class DriverOverlay extends ItemizedOverlay<OverlayItem> {
		
		private List<OverlayItem> items;
		

		public DriverOverlay(Drawable defaultMarker, List<Driver> drivers) {
			super(boundCenterBottom(defaultMarker));
			
			items = new ArrayList<OverlayItem>();
			
			for (Driver driver : drivers) {
				items.add(new OverlayItem(driver.getPositionPoint(), driver.getName(), driver.getNumber()));
			}
			populate();
			
			// TODO Auto-generated constructor stub
		}

		@Override
		protected OverlayItem createItem(int i) {
			return items.get(i);
		}

		@Override
		public int size() {
			return drivers.size();
		}
		
		public void addDriver(Driver driver) {
			items.add(new OverlayItem(driver.getPositionPoint(), driver.getName(), driver.getNumber()));
			populate();
		}
		
		@Override
		protected boolean onTap(int i) {
			//Toast.makeText(SearchMapActivity.this, items.get(i).getSnippet(), Toast.LENGTH_SHORT).show();
			startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + items.get(i).getSnippet())));
			return true;
		}
		
	}
	
	public class CurrentPositionOverlay extends ItemizedOverlay<OverlayItem> {
		
		private OverlayItem position;
		public CurrentPositionOverlay(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
			
			position = new OverlayItem(new GeoPoint(SearchMapActivity.this.microdegrees(0.0),SearchMapActivity.this.microdegrees(0.0)), "Me", "Current Location");
			
			populate();
			
			// TODO Auto-generated constructor stub
		}

		@Override
		protected OverlayItem createItem(int i) {
			return position;
		}

		@Override
		public int size() {
			return 1;
		}
		
		public void updatePosition(double latitude, double longitude) {
			position = new OverlayItem(new GeoPoint(SearchMapActivity.this.microdegrees(latitude),SearchMapActivity.this.microdegrees(longitude)), "Me", "Current Location");
			populate();
		}
		
		@Override
		protected boolean onTap(int i) {
			Toast.makeText(SearchMapActivity.this, "My current location", Toast.LENGTH_SHORT).show();
			return true;
		}
		
	}

}


