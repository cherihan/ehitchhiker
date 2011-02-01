package fr.polytech.unice.ehitchhiker;

import java.io.Serializable;

public class Destination implements Serializable {
	private String name;
	private double latitude;
	private double longitude;
	private boolean favorite;
	
	public Destination(String name, double latitude, double longitude)  {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		favorite = false;
	}
	
	public String getName() {
		return name;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	public boolean isFavorite(){
		return favorite;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Destination) {
			Destination dest = (Destination)o;
			return this.name ==  dest.name;
		} else {
			return false;
		}
	}

}
