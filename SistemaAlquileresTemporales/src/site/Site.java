package site;

import java.util.ArrayList;
import java.util.List;

import property.Property;
import ranking.Rankeable;
import user.Owner;
import user.User;

public class Site {
	/* ATTRIBUTES */
	private String name;

	/* CONSTRUCTOR */
	public Site(String name) {
		super();
		this.setName(name);
	}

	/* METHODS */
	public void registerUser(User u) {

	}

	public void postProperty(Property p, Owner o) {
		
	}

	public List<Property> searchProperties(List<Filter> fs) {
		return new ArrayList<Property>();
	}
	
	public void rank(Rankeable r, Category c, int n) {

	}

	/* GETTERS & SETTERS */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
