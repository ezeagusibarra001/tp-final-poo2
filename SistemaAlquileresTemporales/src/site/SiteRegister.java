package site;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.time.LocalDate;
import user.User;

public class SiteRegister {
	private Set<Category> categoriesTenant;
	private Set<Category> categoriesOwner;
	private Set<Category> categoriesProperty;
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
	 
	void registerUser(User user, LocalDate date) {
		users.put(user, date);
		user.setRegisterDate(date);
	}

	public boolean isRegistered(User user) {
		return this.getUsers().containsKey(user);
	}
}
