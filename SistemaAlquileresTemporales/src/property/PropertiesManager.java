package property;

import java.util.List;

import site.Filter;
import site.Site;
import user.Owner;

public class PropertiesManager {
	/* ATTRIBUTES */
	private Site site;

	/* CONSTRUCTOR */
	public PropertiesManager(Site site) {
		super();
		this.setSite(site);
	}

	/* METHODS */
	public void post(Property p, Owner o) {
		// o.postProperty(p);
	}

	public List<Property> search(List<Filter> fs) {
		return null;

	}

	/* GETTERS & SETTERS */
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}
