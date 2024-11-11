package property;

import java.util.List;
import java.util.stream.Collectors;

import property.search.Filter;
import site.Site;
import user.Owner;

public class PropertiesManager {
	private Site site; // ES NECESARIO TENER EL SITE ACA?
	private List<Property> properties;
	// private List<Filter> searchFilter; ???? o es por param no mas

	// ESTE CONSTRUCTOR ESTA DE MAS?
	public PropertiesManager(Site site) {
		this.setSite(site);
	}
	
	public PropertiesManager(Site site, List<Property> properties) {
		this.setSite(site);
		this.setProperties(properties);
	}
	
	// Getters
	public Site getSite() {
		return site;
	}
	
	public List<Property> getProperties() {
		return properties;
	}
	
	// Setters
	public void setSite(Site site) {
		this.site = site;
	}
	
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
	// ------------------------------------------------------

	public void post(Property property, Owner owner) {
		// o.postProperty(p);
		this.getProperties().add(property); // si se usa el primer constructor esto falla, no se inicializa properties
	}

	public List<Property> search(List<Filter> filters) {
		return this.getProperties().stream()
				.filter(p -> this.passFilter(p, filters))
				.collect(Collectors.toList());
	}

	private boolean passFilter(Property property, List<Filter> filters) {
		return filters.stream()
				.allMatch(filter -> filter.matches(property));
	}
}
