package ranking;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import site.Category;

public class SimpleRankingStrategy implements RankingStrategy {
	
	public SimpleRankingStrategy() {}
	
	// ------------------------------------------------------
	
	@Override
	public double calculateTotalAvg(Ranking ranking) {
		return ranking.getScores().stream()
				.mapToInt(score -> score.getValue())
				.average()
				.orElse(0);
	}

	@Override
	public Map<Category, Double> calculateAvgPerCategory(Ranking ranking) {
        List<Score> scores = ranking.getScores();

        Map<Category, Double> averages = scores.stream()
            .collect(Collectors.groupingBy(
                Score::getCategory,
                Collectors.averagingInt(Score::getValue)
            ));

        return averages;
	}
}
