package fr.polytech.unice.ehitchhiker;

import com.google.android.maps.GeoPoint;

public class Driver {
	private String name;
	private String number;
	private double lat_pos;
	private double lng_pos;
	private double lat_dst;
	private double lng_dst;
	public Driver(String name, String number, double lat_pos, double lng_pos,
			double lat_dst, double lng_dst) {

		this.name = name;
		this.number = number;
		this.lat_pos = lat_pos;
		this.lng_pos = lng_pos;
		this.lat_dst = lat_dst;
		this.lng_dst = lng_dst;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public double getLatPos() {
		return lat_pos;
	}
	public void setLatPos(double lat_pos) {
		this.lat_pos = lat_pos;
	}
	public double getLngPos() {
		return lng_pos;
	}
	public void setLngPos(double lng_pos) {
		this.lng_pos = lng_pos;
	}
	public double getLatDst() {
		return lat_dst;
	}
	public void setLatDst(double lat_dst) {
		this.lat_dst = lat_dst;
	}
	public double getLngDst() {
		return lng_dst;
	}
	public void setLngDst(double lng_dst) {
		this.lng_dst = lng_dst;
	}
	
	public GeoPoint getPositionPoint(){
		return new GeoPoint(microdegrees(lat_pos), microdegrees(lng_pos));
	}
	
	public GeoPoint getDestinationPoint() {
		return new GeoPoint(microdegrees(lat_dst), microdegrees(lng_dst));
	}
	
	private int microdegrees(double value) {
		return (int)(value * 1000000);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Driver other = (Driver) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}
	
	

}
