package user;

import property.Property;

public class Owner extends User {
	public Owner(String fullName, String email, int phone) {
		super(fullName, email, phone);
	}
	
	// ------------------------------------------------------

	public int getRentalCount(Property property) {
		if (this.getProperties().contains(property)) {
			return property.getStats().getTotalRentals();
		} else {
			throw new IllegalArgumentException("La propiedad no le pertenece al dueño");
		}
	}
	
	public int getTotalRentals() {
		return this.getProperties().stream()
				.mapToInt(property -> property.getStats().getTotalRentals())
				.sum();
	}
	
	public void showDetails() {
		this.getStats().showDetails();
//		this.getRegisterDate();
		System.out.println("Cantidad total de inmuebles alquilados: " + this.getTotalRentals());
	}
}