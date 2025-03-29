package seedu.food;


import seedu.exceptions.EZMealPlanException;


import org.junit.jupiter.api.Test;
import seedu.exceptions.InvalidPriceException;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class IngredientTest {
    private static final Logger logger = Logger.getLogger(IngredientTest.class.getName());

    public IngredientTest() {
        String fileName = "IngredientTest.log";
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
    public void createIngredient_ingredient_success() throws EZMealPlanException {
        logger.fine("Running createIngredient_ingredient_success()");
        String ingredientName = "salt";
        double ingredientPrice = 2.5;
        Ingredient newIngredient = new Ingredient(ingredientName, ingredientPrice);
        String expectedOutput = "salt ($2.50)";
        assertEquals(expectedOutput, newIngredient.toString());
        logger.info("Ingredient successfully created");
    }

    @Test
    public void equals_sameNameSamePrice_true() throws EZMealPlanException {
        logger.fine("Running equals_sameNameSamePrice_true()");
        String ingredient1Name = "salt";
        double ingredient1Price = 2.5;
        String ingredient2Name = "salt";
        double ingredient2Price = 2.5;
        Ingredient newIngredient1 = new Ingredient(ingredient1Name, ingredient1Price);
        Ingredient newIngredient2 = new Ingredient(ingredient2Name, ingredient2Price);
        assertEquals(newIngredient1, newIngredient2);
        logger.info("Equality is true");
    }

    @Test
    public void equals_sameNameDifferentPrice_true() throws EZMealPlanException {
        logger.fine("Running equals_sameNameDifferentPrice_true()");
        String ingredient1Name = "salt";
        double ingredient1Price = 2.5;
        String ingredient2Name = "salt";
        double ingredient2Price = 2;
        Ingredient newIngredient1 = new Ingredient(ingredient1Name, ingredient1Price);
        Ingredient newIngredient2 = new Ingredient(ingredient2Name, ingredient2Price);
        assertEquals(newIngredient1, newIngredient2);
        logger.info("Equality is true");
    }

    @Test
    public void equals_differentNameDifferentPrice_false() throws EZMealPlanException {
        logger.fine("Running equals_differentNameDifferentPrice_false()");
        String ingredient1Name = "salt";
        double ingredient1Price = 2.5;
        String ingredient2Name = "pepper";
        double ingredient2Price = 2;
        Ingredient newIngredient1 = new Ingredient(ingredient1Name, ingredient1Price);
        Ingredient newIngredient2 = new Ingredient(ingredient2Name, ingredient2Price);
        assertNotEquals(newIngredient1, newIngredient2);
        logger.info("Equality is false");
    }

    @Test
    public void equals_differentNameSamePrice_false() throws EZMealPlanException {
        logger.info("Running equals_differentNameSamePrice_false()");
        String ingredient1Name = "salt";
        double ingredient1Price = 2.5;
        String ingredient2Name = "pepper";
        double ingredient2Price = 2.5;
        Ingredient newIngredient1 = new Ingredient(ingredient1Name, ingredient1Price);
        Ingredient newIngredient2 = new Ingredient(ingredient2Name, ingredient2Price);
        assertNotEquals(newIngredient1, newIngredient2);
        logger.fine("Equality is false");
    }

    @Test
    void equals_differentCapitalisationSameName_true() throws InvalidPriceException {
        logger.fine("Running equals_differentCapitalisationSameName_true()");
        String ingredient1Name = "salt";
        double ingredient1Price = 2.5;
        String ingredient2Name = "Salt";
        double ingredient2Price = 2.5;
        Ingredient newIngredient1 = new Ingredient(ingredient1Name, ingredient1Price);
        Ingredient newIngredient2 = new Ingredient(ingredient2Name, ingredient2Price);
        assertEquals(newIngredient1, newIngredient2);
        logger.info("Equality is true");
    }

    @Test
    public void setPrice_negativePrice_exceptionThrown() {
        logger.fine("Running setPrice_negativePrice_exceptionThrown()");
        String ingredientName = "salt";
        double ingredientPrice = -2.5;
        try {
            new Ingredient(ingredientName, ingredientPrice);
            logger.warning("Expected exception but none was thrown");
            fail();
        } catch (InvalidPriceException invalidPriceException) {
            assertEquals("The price of the salt must be greater than or equals to 0.\n",
                    invalidPriceException.getMessage());
            logger.info("Exception thrown with correct message");
        }
    }

    @Test
    public void setPrice_zeroPrice_success() throws InvalidPriceException {
        logger.fine("Running setPrice_zeroPrice_success()");
        String ingredientName = "salt";
        double ingredientPrice = 0;
        Ingredient newIngredient = new Ingredient(ingredientName, ingredientPrice);
        assertEquals(ingredientPrice, newIngredient.getPrice());
        logger.info("Ingredient successfully created");
    }
}
