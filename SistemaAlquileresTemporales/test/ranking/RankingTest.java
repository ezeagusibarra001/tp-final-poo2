package ranking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        strategy = new SimpleRankingStrategy();
        rankingManager = new RankingManager(strategy);

        ranking = new Ranking();
    }

    @Test
    void testAddScoreWithinValidRange() {
        Category category = mock(Category.class);
        ranking.addScore(category, 3);

        assertEquals(1, ranking.getScores().size(), "There should be one score added within valid range");
        assertEquals(3, ranking.getScores().get(0).getValue(), "Score value should be 3");
    }

    @Test
    void testAddScoreOutsideValidRange() {
        Category category = mock(Category.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ranking.addScore(category, 6));
        assertEquals("La puntuación debe estar entre 1 y 5", exception.getMessage(), "Exception message should match");
    }

    @Test
    void testAddScores() {
        Category category1 = mock(Category.class);
        Category category2 = mock(Category.class);

        Ranking otherRanking = new Ranking();
        otherRanking.addScore(category1, 4);
        otherRanking.addScore(category2, 5);

        ranking.addScores(otherRanking);

        assertEquals(2, ranking.getScores().size(), "There should be two scores added from other ranking");
        assertEquals(4, ranking.getScores().get(0).getValue(), "First score value should be 4");
        assertEquals(5, ranking.getScores().get(1).getValue(), "Second score value should be 5");
    }

    @Test
    void testCalculateTotalAvgWhenEmpty() {
        double totalAvg = rankingManager.calculateTotalAvg(ranking);
        assertEquals(0, totalAvg, 0.01, "The total average should be 0 when there are no scores");
    }

    @Test
    void testCalculateAvgPerCategoryWhenEmpty() {
        Map<Category, Double> avgPerCategory = rankingManager.calculateAvgPerCategory(ranking);
        assertTrue(avgPerCategory.isEmpty(), "The average per category map should be empty when there are no scores");
    }

    @Test
    void testSwitchRankingStrategy() {
        RankingStrategy newStrategy = mock(RankingStrategy.class);
        rankingManager.setRankingStrategy(newStrategy);

        rankingManager.calculateTotalAvg(ranking);
        verify(newStrategy).calculateTotalAvg(ranking);
    }

    @Test
    void testCalculateAvgPerCategoryWithMultipleStrategies() {
        RankingStrategy customStrategy = new SimpleRankingStrategy();
        rankingManager.setRankingStrategy(customStrategy);

        Category category1 = mock(Category.class);
        Category category2 = mock(Category.class);

        ranking.addScore(category1, 3);
        ranking.addScore(category1, 4);
        ranking.addScore(category2, 5);
        ranking.addScore(category2, 2);

        Map<Category, Double> avgPerCategory = rankingManager.calculateAvgPerCategory(ranking);

        assertEquals(3.5, avgPerCategory.get(category1), 0.01, "Average for category1 should be 3.5");
        assertEquals(3.5, avgPerCategory.get(category2), 0.01, "Average for category2 should be 3.5");
    }
}
