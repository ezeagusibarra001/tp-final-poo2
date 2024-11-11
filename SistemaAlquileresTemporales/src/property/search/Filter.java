package property.search;

import property.Property;

public interface Filter {
	public boolean matches(Property property);
}
