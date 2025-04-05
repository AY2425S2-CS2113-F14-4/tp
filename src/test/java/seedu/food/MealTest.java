package seedu.food;

import org.junit.jupiter.api.Test;
import seedu.exceptions.EZMealPlanException;


import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MealTest {
    private static final Logger logger = Logger.getLogger(MealTest.class.getName());

    public MealTest() {
        String fileName = "MealTest.log";
        setupLogger(fileName);
    }

    private static void setupLogger(String fileName) {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        logger.addHandler(consoleHandler);
        createLogFile(fileName);
    }

    private static void createLogFile(String fileName) {
        try {
            FileHandler fileHandler = new FileHandler(fileName, true);
            fileHandler.setLevel(Level.FINE);
            logger.addHandler(fileHandler);
        } catch (IOException ioException) {
            logger.log(Level.SEVERE, "File logger is not working.", ioException);
        }
    }

    @Test
    void equals_sameNameSameIngredient_true() throws EZMealPlanException {
        logger.fine("Running equals_sameNameSameIngredient_true()");
        Ingredient chicken = new Ingredient("Chicken", "1.00");
        Meal meal1 = new Meal("Chicken Rice");
        meal1.addIngredient(chicken);
        Meal meal2 = new Meal("Chicken Rice");
        meal2.addIngredient(chicken);
        assertEquals(meal1, meal2);
        logger.info("Equality is true");
    }

    @Test
    void equals_sameNameDifferentIngredient_false() throws EZMealPlanException {
        logger.fine("Running equals_sameNameDifferentIngredient_false()");
        Ingredient steamedChicken = new Ingredient("Steamed Chicken", "1.00");
        Ingredient roastedChicken = new Ingredient("Roasted Chicken", "1.00");
        Meal meal1 = new Meal("Chicken Rice");
        meal1.addIngredient(steamedChicken);
        Meal meal2 = new Meal("Chicken Rice");
        meal2.addIngredient(roastedChicken);
        assertNotEquals(meal1, meal2);
        logger.info("Equality is false");
    }

    @Test
    void equals_differentNameSameIngredient_false() throws EZMealPlanException {
        logger.fine("Running equals_differentNameSameIngredient_false()");
        Ingredient chicken = new Ingredient("Chicken", "1.00");
        Meal meal1 = new Meal("Chicken Rice");
        meal1.addIngredient(chicken);
        Meal meal2 = new Meal("Chicken Rice Upsized");
        meal2.addIngredient(chicken);
        assertNotEquals(meal1, meal2);
        logger.info("Equality is false");
    }

    @Test
    void equals_differentNameDifferentIngredient_false() throws EZMealPlanException {
        logger.fine("Running equals_differentNameDifferentIngredient_false()");
        Ingredient chicken = new Ingredient("Chicken", "1.00");
        Ingredient duck = new Ingredient("Duck", "1.20");
        Meal meal1 = new Meal("Chicken Rice");
        meal1.addIngredient(chicken);
        Meal meal2 = new Meal("Duck Rice");
        meal2.addIngredient(duck);
        assertNotEquals(meal1, meal2);
        logger.info("Equality is false");
    }

    @Test
    void equals_differentCapitalisationSameIngredient_true()
            throws EZMealPlanException {
        logger.fine("Running equals_differentCapitalisationSameIngredient_true()");
        Ingredient chicken = new Ingredient("Chicken", "1.00");
        Meal meal1 = new Meal("Chicken Rice");
        meal1.addIngredient(chicken);
        Meal meal2 = new Meal("chicken rice");
        meal2.addIngredient(chicken);
        assertEquals(meal1, meal2);
        logger.info("Equality is true");
    }

    @Test
    void addIngredient_ingredients_correctIngredientListAndPrice()
            throws EZMealPlanException {
        logger.fine("Running addIngredient_ingredients_correctIngredientListAndPrice()");
        Ingredient chicken = new Ingredient("Chicken", "1.00");
        Ingredient rice = new Ingredient("Rice", "0.50");
        Ingredient cucumber = new Ingredient("Cucumber", "0.30");
        Meal meal1 = new Meal("Chicken Rice");
        meal1.addIngredient(chicken);
        meal1.addIngredient(rice);
        meal1.addIngredient(cucumber);
        assertEquals(1.8, meal1.getPrice());
        assertEquals("[Chicken ($1.00), Rice ($0.50), Cucumber ($0.30)]", meal1.getIngredientList().toString());
        logger.info("Ingredient list and price is correct");
    }

    @Test
    void toString_meal_correctFormat() throws EZMealPlanException {
        logger.fine("Running toString_meal_correctFormat()");
        Meal meal = new Meal("Chicken Rice");
        Ingredient chicken = new Ingredient("Chicken", "1.00");
        Ingredient rice = new Ingredient("Rice", "0.50");
        Ingredient cucumber = new Ingredient("Cucumber", "0.30");
        meal.addIngredient(chicken);
        meal.addIngredient(rice);
        meal.addIngredient(cucumber);
        assertEquals("Chicken Rice ($1.80)", meal.toString());
        logger.info("Printing format is correct");
    }
}
