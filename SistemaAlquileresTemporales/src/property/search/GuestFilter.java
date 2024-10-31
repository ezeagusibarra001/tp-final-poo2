package property.search;

import property.Property;

public class GuestFilter implements Filter {
	private int guests;

	public GuestFilter(int guests) {
		super();
		this.setGuests(guests);
	}

	@Override
	public boolean matches(Property p) {
		return p.getGuests() >= this.getGuests();
	}

	public int getGuests() {
		return guests;
	}

	private void setGuests(int guests) {
		this.guests = guests;
	}

}
