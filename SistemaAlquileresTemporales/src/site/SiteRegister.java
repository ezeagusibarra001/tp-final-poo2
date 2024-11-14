package site;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import property.enums.PropertyType;
import property.enums.Service;

import java.time.LocalDate;

import user.Tenant;
import user.User;

public class SiteRegister {
	private Set<Category> categoriesTenant;
	private Set<Category> categoriesOwner;
	private Set<Category> categoriesProperty;
	private Set<PropertyType> propertyTypes;
    private Set<Service> services;
	private Map<User, LocalDate> users;

	public SiteRegister(Set<Category> categoriesTenant, Set<Category> categoriesOwner, Set<Category> categoriesProperty) {
		this.SetCategoriesTenant(categoriesTenant);
		this.SetCategoriesOwner(categoriesOwner);
		this.SetCategoriesProperty(categoriesProperty);
		this.users = new HashMap<User, LocalDate>();
	}
	
	// Getters
	private Map<User, LocalDate> getUsers() {
		return this.users;
	}
	
	public Set<Category> getCategoriesTenant() {
		return this.categoriesTenant;
	}

	public Set<Category> getCategoriesOwner() {
		return this.categoriesOwner;
	}
	
	public Set<Category> getCategoriesProperty() {
		return this.categoriesProperty;
	}
	
	public Set<PropertyType> getPropertyTypes() {
	     return this.propertyTypes;
	 }
	 
	 public Set<Service> getServices() {
	     return this.services;
	 }
	
	// Setters
	private void SetCategoriesTenant(Set<Category> categories) {
		this.categoriesTenant = categories;
	}
	
	private void SetCategoriesOwner(Set<Category> categories) {
		this.categoriesOwner = categories;
	}
	
	private void SetCategoriesProperty(Set<Category> categories) {
		this.categoriesProperty = categories;
	}


   

	
	// ------------------------------------------------------
	 
    public void addPropertyType(PropertyType propertyType) {
        this.getPropertyTypes().add(propertyType);
    }

    // Métodos para dar de alta servicios de inmuebles
    public void addService(Service service) {
        this.getServices().add(service);
    }
	
	void registerUser(User user, LocalDate date) {
		users.put(user, date);
		user.setRegisterDate(date);
	}

	public boolean isRegistered(User user) {
		return this.getUsers().containsKey(user);
	}

	public List<User> getTenants() {
		return this.getUsers().keySet().stream()
				.filter(u -> u instanceof Tenant)
				.collect(Collectors.toList());
	}
}
