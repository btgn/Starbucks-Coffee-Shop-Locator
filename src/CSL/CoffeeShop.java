package CSL;

public class CoffeeShop {
	private String loc;
	private String city;
	private String address;
	private String zipcode;
	private double srad;
	private double lat;
	private double lng;
	private float dist;
	
	public CoffeeShop(String loc, String city, String address, String zipcode, double lat, double srad, double lng, float dist) {
		this.srad = srad;
		this.lat = lat;
		this.lng = lng;
		this.dist = dist;
		this.loc = loc;
		this.city = city;
		this.address = address;
		this.zipcode = zipcode;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public double getSrad() {
		return srad;
	}
	public void setSrad(double srad) {
		this.srad = srad;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public float getDist() {
		return dist;
	}
	public void setDist(float dist) {
		this.dist = dist;
	}
	
}