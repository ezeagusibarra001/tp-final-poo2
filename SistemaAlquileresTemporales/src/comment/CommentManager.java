package comment;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import property.Property;

public class CommentManager {
	private List<Comment> comments;
	
	public CommentManager() {
		this.comments = new ArrayList<Comment>();
	}
	
	// ------------------------------------------------------
	
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
	
	public List<Comment> filterComments(Property property) {
		return comments.stream()
			      .filter(comment -> comment.getProperty().equals(property))
			      .collect(Collectors.toList());
	}
	
	public void showComments(List<Comment> comments) {
		comments.stream()
		.forEach(comment -> comment.showComment());
	}
}
