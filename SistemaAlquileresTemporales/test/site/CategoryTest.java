package site;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category("Sample Category");
    }

    @Test
    void testCategoryInitialization() {
        assertEquals("Sample Category", category.name, "The name of the category should match the initialized value");
    }

    @Test
    void testSetName() {
        category.setName("New Category Name");
        assertEquals("New Category Name", category.name, "The name should be updated to the new value");
    }
}
