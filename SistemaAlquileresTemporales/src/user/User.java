package user;

public abstract class User {
	// pueden cambiar a protected
	private String fullName;
	private String email;
	private int phone;
	
	public User(String fullName, String email, int phone) {
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
	}
	
	// getters y setters...?
}
