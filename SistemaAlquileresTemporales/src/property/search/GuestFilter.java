package property.search;

import property.Property;

public class GuestFilter implements Filter {
	private int guests;

	public GuestFilter(int guests) {
		this.setGuests(guests);
	}
	
	private int getGuests() {
		return guests;
	}

	private void setGuests(int guests) {
		this.guests = guests;
	}

	// ------------------------------------------------------
	
	@Override
	public boolean matches(Property property) {
		return property.getGuests() >= this.getGuests();
	}
}
