package user;

import ranking.Ranking;

public class Owner extends User {
	private Ranking ranking;

	public Owner(String fullName, String email, int phone) {
		super(fullName, email, phone);
		this.ranking = new Ranking();
	}
	
	// Getters y Setters
	public Ranking getRanking() { // lo necesito?
		return this.ranking;
	}

}
