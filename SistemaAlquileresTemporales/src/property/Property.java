package property;

import java.util.Date;
import java.util.List;
import user.Owner;

import property.enums.PaymentMethod;
import property.enums.PropertyType;
import property.enums.Service;
import ranking.Rankeable;
import ranking.Ranked;
import site.Category;

public class Property implements Rankeable {
	/* ATTRIBUTES */
	private PropertyType propertyType;
	private int area;
	private List<Ranked> rankings;
	private Date time_check_in;
	private Date time_check_out;
	private double price;
	private List<PaymentMethod> paymentMethods;
	private int guests;
	private List<Service> services;
	private List<Photo> photos;
	private Location location;
	private List<SpecialPrice> specialPrices;
	private Owner owner; // falta asignarse
	private boolean available;

	/* CONSTRUCTOR */
	public Property(PropertyType propertyType, int area, List<Ranked> rankings, Date time_check_in, Date time_check_out,
			double price, List<PaymentMethod> paymentMethods, int guests, List<Service> services, List<Photo> photos,
			Location location, List<SpecialPrice> specialPrices) {
		this.setPropertyType(propertyType);
		this.setArea(area);
		this.setRankings(rankings);
		this.setTime_check_in(time_check_in);
		this.setTime_check_out(time_check_out);
		this.setPrice(price);
		this.setPaymentMethods(paymentMethods);
		this.setGuests(guests);
		this.setServices(services);
		this.setPhotos(photos);
		this.setLocation(location);
		this.setSpecialPrices(specialPrices);
		this.setAvailable(true);
	}

	/* METHODS */
	@Override
	public void addRanking(Category c, int n) {
		Ranked r = new Ranked();
		this.getRankings().add(r);
	}

	@Override
	public double getAvgRanking(Category c) {
		return this.getRankings().stream().mapToInt(Ranked::getValue) // Convierte cada ranking a un int
				.average() // Calcula el promedio
				.orElse(0); // Devuelve 0 si la lista está vacía
	}

	public String getCity() {
		return this.getLocation().getCity();
	}

	public boolean isAvailableBetween(Date startDate, Date endDate) {
		// Tendra que chequear las bookings del site??
		return true;
	}

	public double getHighestPriceBetween(Date startDate, Date endDate) {
		return this.getSpecialPrices().stream()
				.filter(sp -> !sp.getEndDate().before(startDate) && !sp.getStartDate().after(endDate))
				.mapToDouble(SpecialPrice::getPrice).max().orElse(this.getPrice());
	}

	public double getLowerPriceBetween(Date startDate, Date endDate) {
		return this.getSpecialPrices().stream()
				.filter(sp -> !sp.getEndDate().before(startDate) && !sp.getStartDate().after(endDate))
				.mapToDouble(SpecialPrice::getPrice).min().orElse(this.getPrice());
	}

	public void addSpecialPrice(SpecialPrice sp) {
		this.getSpecialPrices().add(sp);
	}

	/* GETTERS & SETTERS */
	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	private List<Ranked> getRankings() {
		return rankings;
	}

	private void setRankings(List<Ranked> rankings) {
		this.rankings = rankings;
	}

	public Date getTime_check_in() {
		return time_check_in;
	}

	public void setTime_check_in(Date time_check_in) {
		this.time_check_in = time_check_in;
	}

	public Date getTime_check_out() {
		return time_check_out;
	}

	public void setTime_check_out(Date time_check_out) {
		this.time_check_out = time_check_out;
	}

	public List<PaymentMethod> getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getGuests() {
		return guests;
	}

	public void setGuests(int guests) {
		this.guests = guests;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<SpecialPrice> getSpecialPrices() {
		return specialPrices;
	}

	private void setSpecialPrices(List<SpecialPrice> specialPrices) {
		this.specialPrices = specialPrices;
	}
	
	public Owner getOwner() {
		return this.owner;
	}
	
	public boolean isAvailable() {
		// falta implementar puede reemplazar al metodo isAvailableBetween
		return this.available;
	}
	
	public void setAvailable(boolean b) {
		this.available = b;
	}
}
