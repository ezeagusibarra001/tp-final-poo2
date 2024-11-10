package view;

import java.time.LocalDate;

public class Comment {
	private String author;
	private String content;
	private LocalDate date;

	public Comment(String author, String content) {
		this.setAuthor(author);
		this.setContent(content);
		this.setDate(LocalDate.now());
	}
	
	// Getters
	private String getAuthor() {
		return this.author;
	}
	
	private String getContent() {
		return this.content;
	}
	
	private LocalDate getDate() {
		return this.date;
	}
	
	// Setters
	private void setAuthor(String author) {
		this.author = author;
	}
	
	private void setContent(String content) {
		this.content = content;
	}
	
	private void setDate(LocalDate date) {
		this.date = date;
	}
	
	public String showComment() {
		return "Autor: " + this.getAuthor() +
				"\nFecha: " + this.getDate() +
			   "\n" + this.getContent();
	}
}
