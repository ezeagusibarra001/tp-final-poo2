package property;

import java.util.List;
import java.util.stream.Collectors;

import property.search.Filter;
import site.Site;
import user.Owner;

public class PropertiesManager {
	/* ATTRIBUTES */
	private Site site;
	private List<Property> properties;
	// private List<Filter> searchFilter; ???? o es por param no mas

	/* CONSTRUCTOR */
	public PropertiesManager(Site site) {
		super();
		this.setSite(site);
	}

	public PropertiesManager(Site site, List<Property> properties) {
		super();
		this.setSite(site);
		this.setProperties(properties);
	}

	/* METHODS */
	public void post(Property p, Owner o) {
		// o.postProperty(p);
		this.getProperties().add(p);
	}

	public List<Property> search(List<Filter> fs) {
		return this.getProperties().stream().filter(p -> this.passFilter(p, fs)).collect(Collectors.toList());
	}

	private boolean passFilter(Property p, List<Filter> fs) {
		return fs.stream().allMatch(filter -> filter.matches(p));
	}

	/* GETTERS & SETTERS */
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

}
