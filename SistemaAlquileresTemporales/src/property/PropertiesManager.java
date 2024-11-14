package property;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import property.search.Filter;
import user.Owner;

public class PropertiesManager {
	private List<Property> properties;

	public PropertiesManager() {
		this.setProperties(new ArrayList<Property>());
	}
	
	public PropertiesManager(List<Property> properties) {
		this.properties = new ArrayList<>(properties);
	}
	
	// Getters
	public List<Property> getProperties() {
		return properties;
	}
	
	// Setters
	public void setProperties(List<Property> properties) { 
	    this.properties = new ArrayList<>(properties);
	}
	
	// ------------------------------------------------------

	public void post(Property property, Owner owner) {
		owner.addProperty(property);
		this.getProperties().add(property);
	}

	public List<Property> search(List<Filter> filters) {
		return this.getProperties().stream()
				.filter(p -> this.passFilter(p, filters))
				.collect(Collectors.toList());
	}

	public boolean passFilter(Property property, List<Filter> filters) {
		return filters.stream()
				.allMatch(filter -> filter.matches(property));
	}

	public List<Property> getAllAvailableProperties() {
		return this.getProperties().stream()
					.filter(p -> p.isAvailable())
					.collect(Collectors.toList());
	}

	public double getOccupancyRate() {
		
		return (double) this.getOccupiedProperties().size() / this.getProperties().size() ;
	}

	private List<Property> getOccupiedProperties() {
		return this.getProperties().stream()
				.filter(p -> !p.isAvailable())
				.collect(Collectors.toList());
	}
	
 
}
