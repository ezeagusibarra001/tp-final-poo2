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
	
	public String showDetails() {
	    String output = "";

	    output += getProperty().showDetails() + "\n";
	    output += "Comentarios:\n";

	    List<Comment> comments = commentManager.filterComments(getProperty());

	    output += this.commentManager.showComments(comments) + "\n";
	    output += getProperty().getOwner().showDetails() + "\n";
	    output += "Cantidad de veces que alquiló el inmueble: " + getProperty().getOwner().getRentalCount(getProperty());

	    return output;
	}
}