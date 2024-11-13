package ranking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import site.Category;

class RankingTest {

    RankingManager rankingManager;
    Ranking ranking;
    SimpleRankingStrategy strategy;

    Score score1;
    Score score2;
    Score score3;
    Score score4;

    @BeforeEach
    void setUp() {
        // Initialize strategy and ranking manager
        strategy = new SimpleRankingStrategy();
        rankingManager = new RankingManager(strategy);

        // Mock Ranking and Scores
        ranking = mock(Ranking.class);
        Category category1 = mock(Category.class);
        Category category2 = mock(Category.class);

        // Scores for different categories
        score1 = new Score(category1, 4);
        score2 = new Score(category1, 5);
        score3 = new Score(category2, 3);
        score4 = new Score(category2, 2);

        when(ranking.getScores()).thenReturn(Arrays.asList(score1, score2, score3, score4));
    }

    @Test
    void testCalculateTotalAvg() {
        double totalAvg = rankingManager.calculateTotalAvg(ranking);
        assertEquals(3.5, totalAvg, 0.01, "The total average should be 3.5");
    }

    @Test
    void testCalculateAvgPerCategory() {
        Map<Category, Double> avgPerCategory = rankingManager.calculateAvgPerCategory(ranking);
        assertEquals(4.5, avgPerCategory.get(score1.getCategory()), 0.01, "Average for category1 should be 4.5");
        assertEquals(2.5, avgPerCategory.get(score3.getCategory()), 0.01, "Average for category2 should be 2.5");
    }

    @Test
    void testCalculateTotalAvgWithNoScores() {
        // Empty list of scores
        when(ranking.getScores()).thenReturn(Collections.emptyList());

        double totalAvg = rankingManager.calculateTotalAvg(ranking);
        assertEquals(0, totalAvg, 0.01, "The total average should be 0 when there are no scores");
    }

    @Test
    void testCalculateAvgPerCategoryWithSingleCategory() {
        // All scores belong to a single category
        Category singleCategory = mock(Category.class);
        score1 = new Score(singleCategory, 3);
        score2 = new Score(singleCategory, 4);
        score3 = new Score(singleCategory, 5);
        score4 = new Score(singleCategory, 2);

        when(ranking.getScores()).thenReturn(Arrays.asList(score1, score2, score3, score4));
        Map<Category, Double> avgPerCategory = rankingManager.calculateAvgPerCategory(ranking);

        assertEquals(3.5, avgPerCategory.get(singleCategory), 0.01, "The average for the single category should be 3.5");
        assertEquals(1, avgPerCategory.size(), "There should be only one category in the result");
    }

    @Test
    void testCalculateAvgPerCategoryWithNoScores() {
        when(ranking.getScores()).thenReturn(Collections.emptyList());

        Map<Category, Double> avgPerCategory = rankingManager.calculateAvgPerCategory(ranking);
        assertTrue(avgPerCategory.isEmpty(), "The average per category map should be empty when there are no scores");
    }

    @Test
    void testCalculateTotalAvgWithAllSameScores() {
        // All scores have the same value
        score1 = new Score(mock(Category.class), 5);
        score2 = new Score(mock(Category.class), 5);
        score3 = new Score(mock(Category.class), 5);
        score4 = new Score(mock(Category.class), 5);

        when(ranking.getScores()).thenReturn(Arrays.asList(score1, score2, score3, score4));
        double totalAvg = rankingManager.calculateTotalAvg(ranking);

        assertEquals(5, totalAvg, 0.01, "The total average should be 5 when all scores are the same");
    }

    @Test
    void testCalculateAvgPerCategoryWithMixedCategoriesAndScores() {
        Category category1 = mock(Category.class);
        Category category2 = mock(Category.class);
        Category category3 = mock(Category.class);

        // Mix of categories and scores
        score1 = new Score(category1, 3);
        score2 = new Score(category2, 4);
        score3 = new Score(category1, 2);
        score4 = new Score(category3, 5);

        when(ranking.getScores()).thenReturn(Arrays.asList(score1, score2, score3, score4));
        Map<Category, Double> avgPerCategory = rankingManager.calculateAvgPerCategory(ranking);

        assertEquals(2.5, avgPerCategory.get(category1), 0.01, "Average for category1 should be 2.5");
        assertEquals(4.0, avgPerCategory.get(category2), 0.01, "Average for category2 should be 4.0");
        assertEquals(5.0, avgPerCategory.get(category3), 0.01, "Average for category3 should be 5.0");
    }
}
