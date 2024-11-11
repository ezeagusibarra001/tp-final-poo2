package ranking;

import java.util.List;
import java.util.ArrayList;
import site.Category;

public class Ranking {
	private List<Score> scores;
	
	public Ranking() {
		this.scores = new ArrayList<Score>();
	}
	
	public List<Score> getScores() {
		return this.scores;
	}

	// ------------------------------------------------------

	public void addScore(Category category, int value) {
		if (value >= 1 && value <= 5) {
			Score s = new Score(category, value);
			scores.add(s);
		} else {
			throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5");
		}
	}
	
	public void addScores(Ranking ranking) {
		List<Score> newScores = ranking.getScores();
		this.scores.addAll(newScores);
	}
}
