package comment;

import java.time.LocalDate;
import property.Property;

public class Comment {
	private String author;
	private String content;
	private LocalDate date;
	private Property property;

	public Comment(String author, String content, Property property) {
		this.setAuthor(author);
		this.setContent(content);
		this.setDate(LocalDate.now());
		this.setProperty(property);
	}
	
	// Getters
	public String getAuthor() {
		return this.author;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public Property getProperty() {
		return this.property;
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
	
	private void setProperty(Property property) {
		this.property = property;
	}
	
	// ------------------------------------------------------
	
	public void showComment() {
		System.out.println("Autor: " 	 + this.getAuthor());   
		System.out.println("Propiedad: " + this.getProperty()); 
		System.out.println("Fecha: "     + this.getDate());     
		System.out.println(this.getContent());
	}
}
