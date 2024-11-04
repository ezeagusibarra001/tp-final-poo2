package booking;

//import ranking.Rankable;
import user.Owner;
import user.Tenant;
import property.Property;

public class Booking {
	private Owner owner; // pueden cambiar a Rankable owner;
	private Tenant tenant;
	private Property property; 
	private String status; // crear estructura, confirmado, libre, etc

	public Booking(Tenant t, Owner o, Property p) {
		this.tenant = t;
		this.owner = o;
		this.property = p;
		// asisgnar status
	}
	
	public void confirm() {
		if (property.isAvailable()) { // puede cambiar por el otro metodo de property
			this.status = "confirmado"; 
			property.setAvailable(false);
			// notificaciones
		}
	}
}
