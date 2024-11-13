package stats;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import site.Category;

class StatsTest {

    private Stats stats;
    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        stats = new Stats();
        category1 = new Category("Category 1");
        category2 = new Category("Category 2");
    }

    @Test
    void testInitialValues() {
        assertEquals(0, stats.getTotalRentals(), "Initial total rentals should be 0");
        assertEquals(0, stats.getTotalAvg(), "Initial total average rating should be 0.0");
        assertTrue(stats.getAvgPerCategory().isEmpty(), "Initial average ratings per category should be empty");
    }

    @Test
    void testIncrementRentalCount() {
        stats.incrementRentalCount();
        assertEquals(1, stats.getTotalRentals(), "Total rentals should be 1 after incrementing");
        
        stats.incrementRentalCount();
        assertEquals(2, stats.getTotalRentals(), "Total rentals should be 2 after incrementing again");
    }

    @Test
    void testUpdateCategoryRating() {
        Map<Category, Double> ratings = new HashMap<>();
        ratings.put(category1, 4.5);
        ratings.put(category2, 3.8);

        stats.updateCategoryRating(ratings);

        assertEquals(4.5, stats.getAvgPerCategory().get(category1), "Category 1 rating should be 4.5");
        assertEquals(3.8, stats.getAvgPerCategory().get(category2), "Category 2 rating should be 3.8");
    }

    @Test
    void testUpdateTotalAvgRating() {
        stats.updateTotalAvgRating(4.2);
        assertEquals(4.2, stats.getTotalAvg(), "Total average rating should be updated to 4.2");
        
        stats.updateTotalAvgRating(3.6);
        assertEquals(3.6, stats.getTotalAvg(), "Total average rating should be updated to 3.6");
    }

    @Test
    void testShowDetails() {
        Map<Category, Double> ratings = new HashMap<>();
        ratings.put(category1, 4.5);
        ratings.put(category2, 3.8);
        stats.updateCategoryRating(ratings);
        stats.updateTotalAvgRating(4.1);
        stats.incrementRentalCount();

        // Since showDetails() only prints to the console, we can invoke it to ensure it runs without errors
        // but we can't assert anything in this case without capturing the console output.
        stats.showDetails(); // We assume this works as it's a simple console output.
    }
}
