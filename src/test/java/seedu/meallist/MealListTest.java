package seedu.meallist;

import org.junit.jupiter.api.Test;

import seedu.exceptions.DuplicateMealException;
import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.MealNotFoundException;
import seedu.exceptions.RemoveIndexOutOfRangeException;
import seedu.food.Ingredient;
import seedu.food.Meal;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MealListTest {
    private static final Logger logger = Logger.getLogger(MealListTest.class.getName());
    private final Meal meal1;
    private final Meal meal2;
    private final Meal meal3;

    public MealListTest() throws EZMealPlanException {
        String fileName = "MealListTest.log";
        setupLogger(fileName);
        meal1 = new Meal("Chicken Rice");
        meal1.addIngredient(new Ingredient("Chicken", "1.00"));
        meal2 = new Meal("Apple Pie");
        meal2.addIngredient(new Ingredient("Apple", "0.50"));
        meal3 = new Meal("French Fries");
        meal3.addIngredient(new Ingredient("Potato", "0.80"));

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
    void addMeal_meals_sortedAlphabetically() throws EZMealPlanException {
        logger.fine("Running addMeal_meals_sortedAlphabetically()");
        MealList recipesList = new RecipesList();
        recipesList.addMeal(meal1);
        recipesList.addMeal(meal2);
        recipesList.addMeal(meal3);
        assertEquals("[Apple Pie ($0.50), Chicken Rice ($1.00), French Fries ($0.80)]",
                recipesList.getList().toString());
        logger.info("recipesList is sorted alphabetically and contains all added meals");
    }

    @Test
    void addMeal_duplicateMeal_exceptionThrown() throws EZMealPlanException {
        logger.fine("Running addMeal_duplicateMeal_exceptionThrown()");
        MealList recipesList = new RecipesList();
        recipesList.addMeal(meal1);
        recipesList.addMeal(meal2);
        assertThrows(DuplicateMealException.class, () -> recipesList.addMeal(meal1));
        logger.info("Correct Exception is thrown");
    }

    @Test
    void removeMeal_indexWithinRange_success() throws EZMealPlanException {
        logger.fine("Running removeMeal_indexWithinRange_success()");
        MealList recipesList = new RecipesList();
        recipesList.addMeal(meal1);
        recipesList.addMeal(meal2);
        recipesList.addMeal(meal3);
        recipesList.removeMeal(0);
        assertEquals("[Chicken Rice ($1.00), French Fries ($0.80)]", recipesList.getList().toString());
        logger.info("Meal is successfully removed");
    }

    @Test
    void removeMeal_indexOutOfRange_exceptionThrown() throws EZMealPlanException {
        logger.fine("Running removeMeal_indexOutOfRange_exceptionThrown()");
        MealList recipesList = new RecipesList();
        recipesList.addMeal(meal1);
        recipesList.addMeal(meal2);
        recipesList.addMeal(meal3);
        assertThrows(RemoveIndexOutOfRangeException.class, () -> recipesList.removeMeal(-1));
        assertThrows(RemoveIndexOutOfRangeException.class, () -> recipesList.removeMeal(3));
        logger.info("Correct Exception is thrown");
    }

    @Test
    void getIndex_mealExists_success() throws EZMealPlanException {
        logger.fine("Running getIndex_mealExists_success()");
        MealList recipesList = new RecipesList();
        recipesList.addMeal(meal1);
        recipesList.addMeal(meal2);
        recipesList.addMeal(meal3);
        assertEquals(1, recipesList.getIndex(meal1));
        assertEquals(0, recipesList.getIndex(meal2));
        assertEquals(2, recipesList.getIndex(meal3));
        logger.info("Correct indices are retrieved");
    }

    @Test
    void getIndex_mealDoesNotExist_exceptionThrown() throws EZMealPlanException {
        logger.fine("Running getIndex_mealDoesNotExist_exceptionThrown()");
        MealList recipesList = new RecipesList();
        recipesList.addMeal(meal1);
        recipesList.addMeal(meal2);
        assertThrows(MealNotFoundException.class, () -> recipesList.getIndex(meal3));
        logger.info("Correct Exception is thrown");
    }

    @Test
    void contains_mealExists_true() throws EZMealPlanException {
        logger.fine("Running contains_mealExists_true()");
        MealList recipesList = new RecipesList();
        recipesList.addMeal(meal1);
        recipesList.addMeal(meal2);
        assertTrue(recipesList.contains(meal1));
        logger.info("recipesList contains meal1");
    }

    @Test
    void contains_mealDoesNotExist_false() throws EZMealPlanException {
        logger.fine("Running contains_mealDoesNotExist_false()");
        MealList recipesList = new RecipesList();
        recipesList.addMeal(meal1);
        recipesList.addMeal(meal2);
        assertFalse(recipesList.contains(meal3));
        logger.info("recipesList does not contain meal3");
    }
}
