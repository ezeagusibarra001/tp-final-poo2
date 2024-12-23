package property;

import java.util.Date;
import java.util.List;

import cancellation.CancellationPolicy;
import user.Owner;
import property.enums.PaymentMethod;
import property.enums.PropertyType;
import property.enums.Service;
import site.*;
import ranking.Ranking;
import stats.Stats;

public class Property{
	private PropertyType propertyType;
	private int area;
	private Date time_check_in;
	private Date time_check_out;
	private double price;
	private List<PaymentMethod> paymentMethods;
	private int guests;
	private List<Service> services;
	private List<Photo> photos;
	private Location location;
	private List<SpecialPrice> specialPrices;
	private Owner owner;
	private boolean available;
	private Ranking ranking = new Ranking();
	private CancellationPolicy cancellationPolicy;
	private Stats stats = new Stats();

	public Property(Owner owner, PropertyType propertyType, int area, Date time_check_in, Date time_check_out,
			double price, List<PaymentMethod> paymentMethods, int guests, List<Service> services, List<Photo> photos,
			Location location, List<SpecialPrice> specialPrices, CancellationPolicy cancellationPolicy) {
		this.setPropertyType(propertyType);
		this.setArea(area);
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
		this.setCancellationPolicy(cancellationPolicy); 
		this.setOwner(owner);
	}
	
	// Revisar usos y visualizacion
	
	// Getters
	public PropertyType getPropertyType() {
		return propertyType;
	}

	public int getArea() {
		return area;
	}
	
	public Ranking getRanking() {
		return this.ranking;
	}
	
	public Date getTime_check_in() {
		return time_check_in;
	}
	
	public Date getTime_check_out() {
		return time_check_out;
	}
	
	public List<PaymentMethod> getPaymentMethods() {
		return paymentMethods;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getGuests() {
		return guests;
	}
	
	public List<Service> getServices() {
		return services;
	}
	
	public List<Photo> getPhotos() {
		return photos;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public List<SpecialPrice> getSpecialPrices() {
		return specialPrices;
	}
	
	public Owner getOwner() {
		return this.owner;
	}
	
	public Stats getStats() {
		return this.stats;
	}
	
	public boolean isAvailable() {
		return this.available;
	}
	
	// Setters
	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public void setTime_check_in(Date time_check_in) {
		this.time_check_in = time_check_in;
	}

	public void setTime_check_out(Date time_check_out) {
		this.time_check_out = time_check_out;
	}

	public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setGuests(int guests) {
		this.guests = guests;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	private void setSpecialPrices(List<SpecialPrice> specialPrices) {
		this.specialPrices = specialPrices;
	}
	
	public void setAvailable(boolean bool) {
		this.available = bool;
	}
	
	public void setCancellationPolicy(CancellationPolicy policy) {
		this.cancellationPolicy = policy;
	}
	
	public CancellationPolicy getCancellationPolicy() {
		return this.cancellationPolicy;
	}
	
	private void setOwner(Owner owner) {
		this.owner = owner;
	}

	// ------------------------------------------------------
	
	public boolean isAvailableBetween(Date startDate, Date endDate) {
		// Tendra que chequear las bookings del site??
		return true;
	}

	public double getHighestPriceBetween(Date startDate, Date endDate) {
		return this.getSpecialPrices().stream()
				.filter(sp -> !sp.getEndDate().before(startDate) && !sp.getStartDate().after(endDate))
				.mapToDouble(SpecialPrice::getPrice)
				.max().orElse(this.getPrice());
	}

	public double getLowerPriceBetween(Date startDate, Date endDate) {
		return this.getSpecialPrices().stream()
				.filter(sp -> !sp.getEndDate().before(startDate) && !sp.getStartDate().after(endDate))
				.mapToDouble(SpecialPrice::getPrice)
				.min().orElse(this.getPrice());
	}

	public void addSpecialPrice(SpecialPrice specialPrice) {
		this.getSpecialPrices().add(specialPrice);
	}
	
	public String showDetails() {
	    String output = "Detalles de propiedad:\n" +
	                    "Tipo: " + getPropertyType().getDescription() + "\n" +
	                    "Superficie: " + getArea() + " m²\n" +
	                    "Precio: $" + getPrice() + "\n" +
	                    "Ubicación:\n" +
	                    "  - País: " + getLocation().getCountry() + "\n" +
	                    "  - Ciudad: " + getLocation().getCity() + "\n" +
	                    "  - Dirección: " + getLocation().getAddress() + "\n" +
	                    "Capacidad: " + getGuests() + " personas\n";

	    if (!getServices().isEmpty()) {
	        output += "Servicios:\n";
	        for (Service service : getServices()) {
	            output += "  - " + service.getDescription() + "\n";
	        }
	    }

	    output += "Horario:\n" +
	              "  - Check-in: " + getTime_check_in() + "\n" +
	              "  - Check-out: " + getTime_check_out() + "\n";

	    if (!getPaymentMethods().isEmpty()) {
	        output += "Métodos de pago:\n";
	        for (PaymentMethod method : getPaymentMethods()) {
	            output += "  - " + method.getDescription() + "\n";
	        }
	    }

	    if (!getPhotos().isEmpty()) {
	        output += "Fotos:\n";
	        for (Photo photo : getPhotos()) {
	            output += "  - " + photo.getUrl() + "\n";
	        }
	    }

	    output += getStats().showDetails();

	    return output;
	}
}
