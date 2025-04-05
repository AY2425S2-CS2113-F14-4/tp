package seedu.food;


import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.IngredientPriceFormatException;

import org.junit.jupiter.api.Test;


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
        String ingredientPrice = "2.50";
        Ingredient newIngredient = new Ingredient(ingredientName, ingredientPrice);
        String expectedOutput = "salt ($2.50)";
        assertEquals(expectedOutput, newIngredient.toString());
        logger.info("Ingredient successfully created");
    }

    @Test
    public void equals_sameNameSamePrice_true() throws EZMealPlanException {
        logger.fine("Running equals_sameNameSamePrice_true()");
        String ingredient1Name = "salt";
        String ingredient1Price = "2.50";
        String ingredient2Name = "salt";
        String ingredient2Price = "2.50";
        Ingredient newIngredient1 = new Ingredient(ingredient1Name, ingredient1Price);
        Ingredient newIngredient2 = new Ingredient(ingredient2Name, ingredient2Price);
        assertEquals(newIngredient1, newIngredient2);
        logger.info("Equality is true");
    }

    @Test
    public void equals_sameNameDifferentPrice_true() throws EZMealPlanException {
        logger.fine("Running equals_sameNameDifferentPrice_true()");
        String ingredient1Name = "salt";
        String ingredient1Price = "2.50";
        String ingredient2Name = "salt";
        String ingredient2Price = "2.00";
        Ingredient newIngredient1 = new Ingredient(ingredient1Name, ingredient1Price);
        Ingredient newIngredient2 = new Ingredient(ingredient2Name, ingredient2Price);
        assertEquals(newIngredient1, newIngredient2);
        logger.info("Equality is true");
    }

    @Test
    public void equals_differentNameDifferentPrice_false() throws EZMealPlanException {
        logger.fine("Running equals_differentNameDifferentPrice_false()");
        String ingredient1Name = "salt";
        String ingredient1Price = "2.50";
        String ingredient2Name = "pepper";
        String ingredient2Price = "2.00";
        Ingredient newIngredient1 = new Ingredient(ingredient1Name, ingredient1Price);
        Ingredient newIngredient2 = new Ingredient(ingredient2Name, ingredient2Price);
        assertNotEquals(newIngredient1, newIngredient2);
        logger.info("Equality is false");
    }

    @Test
    public void equals_differentNameSamePrice_false() throws EZMealPlanException {
        logger.info("Running equals_differentNameSamePrice_false()");
        String ingredient1Name = "salt";
        String ingredient1Price = "2.50";
        String ingredient2Name = "pepper";
        String ingredient2Price = "2.50";
        Ingredient newIngredient1 = new Ingredient(ingredient1Name, ingredient1Price);
        Ingredient newIngredient2 = new Ingredient(ingredient2Name, ingredient2Price);
        assertNotEquals(newIngredient1, newIngredient2);
        logger.fine("Equality is false");
    }

    @Test
    void equals_differentCapitalisationSameName_true() throws EZMealPlanException {
        logger.fine("Running equals_differentCapitalisationSameName_true()");
        String ingredient1Name = "salt";
        String ingredient1Price = "2.50";
        String ingredient2Name = "Salt";
        String ingredient2Price = "2.50";
        Ingredient newIngredient1 = new Ingredient(ingredient1Name, ingredient1Price);
        Ingredient newIngredient2 = new Ingredient(ingredient2Name, ingredient2Price);
        assertEquals(newIngredient1, newIngredient2);
        logger.info("Equality is true");
    }

    @Test
    public void setPrice_negativePrice_exceptionThrown() {
        logger.fine("Running setPrice_negativePrice_exceptionThrown()");
        String ingredientName = "salt";
        String ingredientPrice = "-2.50";
        try {
            new Ingredient(ingredientName, ingredientPrice);
            logger.warning("Expected exception but none was thrown");
            fail();
        } catch (EZMealPlanException ezMealPlanException) {
            assertEquals(new IngredientPriceFormatException(ingredientName).getMessage(),
                    ezMealPlanException.getMessage());
            logger.info("Exception thrown with correct message");
        }
    }

    @Test
    public void setPrice_zeroPrice_success() throws EZMealPlanException {
        logger.fine("Running setPrice_zeroPrice_success()");
        String ingredientName = "salt";
        String ingredientPrice = "0.00";
        double expectedPrice = Double.parseDouble(ingredientPrice);
        Ingredient newIngredient = new Ingredient(ingredientName, ingredientPrice);
        assertEquals(expectedPrice, newIngredient.getPrice());
        logger.info("Ingredient successfully created");
    }

    @Test
    public void setPrice_invalidPriceFormat() {
        logger.fine("Running setPrice_invalidPriceFormat()");
        String ingredientName = "salt";
        String[] invalidIngredientPrices = {"1.005", ".01", "1", ".", "1.2", ".1"};
        for( String invalidIngredientPrice : invalidIngredientPrices ) {
            try{
                new Ingredient(ingredientName,invalidIngredientPrice);
                fail();
            } catch (EZMealPlanException ezMealPlanException) {
                assertEquals(new IngredientPriceFormatException(ingredientName).getMessage(),
                        ezMealPlanException.getMessage());
                logger.info("Exception thrown with correct message");
            }
        }
        logger.info("setPrice_invalidPriceFormat() passed");
    }

    @Test
    public void invalidPrice_aboveDoubleMaxValue(){
        logger.fine("Running invalidPrice_AboveDoubleMaxValue()");
        String ingredientName = "salt";
        double one = 1;
        String invalidIngredientPrice = Double.toString(Double.MAX_VALUE + one);
        try{
            new Ingredient(ingredientName, invalidIngredientPrice);
        } catch (EZMealPlanException ezMealPlanException) {
            assertEquals(new IngredientPriceFormatException(ingredientName).getMessage(),
                    ezMealPlanException.getMessage());
            logger.info("Exception thrown with correct message");
        }
        logger.info("setPrice_invalidPrice_aboveDoubleMaxValue() passed");
    }
}
