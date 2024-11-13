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
		this.setProperties(properties);
	}
	
	// Getters
	
	public List<Property> getProperties() {
		return properties;
	}
	
	// Setters
	
	public void setProperties(List<Property> properties) { 
		this.properties = properties;
	}
	
	// ------------------------------------------------------

	public void post(Property property, Owner owner) {
		// o.postProperty(p);
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
}
