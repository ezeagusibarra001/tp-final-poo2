package booking;

import java.util.Date;
import java.util.List;

import cancellation.CancellationPolicy;
import user.Owner;
import user.Tenant;
import property.Property;
import ranking.Ranking;
import ranking.RankingType;

public class Booking {
	private Owner owner;
	private Tenant tenant;
	private Property property; 
	private Date checkInDate;
	private Date checkOutDate;
    private boolean isCompleted; // puede cambiar si hay diff estados

	public Booking(Tenant tenant, Owner owner, Property property, Date checkInDate, Date checkOutDate) {
		this.setTenant(tenant);
		this.setOwner(owner);
		this.setProperty(property);
        this.setCheckInDate(checkInDate);
        this.setCheckOutDate(checkOutDate);
		this.isCompleted = false; // falta setter
	}
	
	// Getters
	public Owner getOwner() {
		return this.owner;
	}
	
	public Tenant getTenant() {
		return this.tenant;
	}
	
	public Property getProperty() {
		return this.property;
	}
	
	public boolean isCompleted() {
		return this.isCompleted;
	}
	
	public Date getCheckInDate() {
		return this.checkInDate;
	}
	

	//Falta implementar estos dos metodos
	public double getTotalPrice() {
		return 0;
	}
	
	public double getDailyPrice() {
		return 0;
	}
	
	public CancellationPolicy getCancellationPolicy() {
		return this.property.getCancellationPolicy();
	}
	
	// Setters
	private void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	
	private void setOwner(Owner owner) {
		this.owner = owner;
	}
	
	private void setProperty(Property property) {
		this.property = property;
	}
	
	private void setCheckInDate(Date date) {
		this.checkInDate = date;
	}

	private void setCheckOutDate(Date date) {
		this.checkOutDate = date;
	}
	
	// ------------------------------------------------------

	public void makeCheckout(List<Ranking> rankings) {
        if (this.isCompleted()) {
            throw new IllegalStateException("Reserva completada");
        }
        
        this.isCompleted = true; // falta setter
        this.getProperty().setAvailable(true);
        this.enableRankings(rankings);
	}
	
	private void enableRankings(List<Ranking> rankings) { 
		Ranking tenantRanking = rankings.get(RankingType.TENANT.ordinal());
		Ranking ownerRanking = rankings.get(RankingType.OWNER.ordinal());
		Ranking propertyRanking = rankings.get(RankingType.PROPERTY.ordinal());
		  
		this.getTenant().rateAfterCheckout(owner, property, ownerRanking, propertyRanking); // Tenant rankea owner y property
		this.getOwner().rateAfterCheckout(owner, property, tenantRanking, propertyRanking); // owner rankea tenant
	}
	
	public void confirm() {
		if (property.isAvailable()) { // puede cambiar por el otro metodo de property
			this.isCompleted = true;
			property.setAvailable(false);
			// notificaciones
		}
	}
}
