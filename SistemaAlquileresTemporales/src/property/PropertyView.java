package property;

import java.util.List;
import comment.*;

public class PropertyView {
	private Property property; // la visualizacion de los get/set property podria cambiar a default
	private CommentManager commentManager;
	
	public PropertyView(Property property, CommentManager commentManager) {
		this.setProperty(property);
		this.setCommentManager(commentManager);
	}
	
	// Getters
	private Property getProperty() {
		return this.property;
	}
	
	// Setters
	private void setProperty(Property property) {
		this.property = property;
	}
	
	private void setCommentManager(CommentManager commentManager) {
		this.commentManager = commentManager;
	}
	
	// ------------------------------------------------------
	
	public void showDetails() {
		List<Comment> comments = this.commentManager.filterComments(this.getProperty());
		
		this.getProperty().showDetails();
		this.commentManager.showComments(comments);
		this.getProperty().getOwner().showDetails();
		System.out.println("Cantidad de veces que alquilo el inmueble: " + 
			this.getProperty().getOwner().getRentalCount(this.getProperty())
		);
	}
}