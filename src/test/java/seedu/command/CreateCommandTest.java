package seedu.command;

import seedu.exceptions.DuplicateIngredientException;
import seedu.exceptions.DuplicateMealException;
import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.InvalidIngredientFormatException;
import seedu.exceptions.InvalidPriceException;
import seedu.food.Ingredient;
import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.ui.UserInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreateCommandTest {
    private static final Logger logger = Logger.getLogger(CreateCommandTest.class.getName());
    final MealManager mealManager = new MealManager();
    final UserInterface ui = new UserInterface();

    public CreateCommandTest() {
        String fileName = "CreateCommandTest.log";
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
    public void createCommand_success() throws EZMealPlanException {
        logger.fine("running createCommand_success()");
        String validUserInput = "create /mname chicken rice /ing chicken breast (2.5)," +
                                " rice (1.5), egg (0.5), cucumber (1)";
        Command command = new CreateCommand(validUserInput);
        command.execute(mealManager, ui);
        int mealListSize = mealManager.getMainMeals().size();
        int expectedMealListSize = 1;
        assertEquals(expectedMealListSize, mealListSize);
        checkExpectedStrings();
        logger.info("create_success() test passed");
    }

    @Test
    public void createCommand_fail() {
        logger.fine("running createCommand_fail()");
        duplicate_ingredient_catch();
        duplicate_meal_catch();
        invalidPriceFormat();
        logger.info("createCommand_fail() test passed");
    }

    private void invalidPriceFormat() {
        checkPrices_cannotParsed_intoInteger();
        check_negative_prices();
    }

    private void check_negative_prices() {
        String[] negativePrices = {"create /mname test /ing ing(-1)", "create /mname test /ing ing(0)"
                , "create /mname test /ing ing(-0.5000)"};
        for (String negativePrice : negativePrices) {
            Command command = new CreateCommand(negativePrice);
            try {
                command.execute(mealManager, ui);
            } catch (EZMealPlanException ezMealPlanException) {
                String ingName = "ing";
                assertEquals(new InvalidPriceException(ingName).getMessage(), ezMealPlanException.getMessage());
                logger.info("Matching exception caught for check_negative_prices()");
            }
        }
    }

    private void checkPrices_cannotParsed_intoInteger() {
        String[] invalidPrices = {"create /mname test /ing ing(jj)", "create /mname test /ing ing(.d)"
                , "create /mname test /ing ing(s)"};
        for (String invalidPrice : invalidPrices) {
            Command command = new CreateCommand(invalidPrice);
            try {
                command.execute(mealManager, ui);
            } catch (EZMealPlanException ezMealPlanException) {
                assertEquals(new InvalidIngredientFormatException().getMessage(), ezMealPlanException.getMessage());
                logger.info("Matching exception caught for checkPrices_cannotParsed_intoInteger()");
            }
        }
    }

    private void checkExpectedStrings() {
        int zeroIndex = 0;
        Meal meal = mealManager.getMainMeals().getList().get(zeroIndex);
        String expectedMealString = "chicken rice ($5.50)";
        assertEquals(expectedMealString, meal.toString());
        String mealStringMsg = "Matching meal string.";
        logger.info(mealStringMsg);
        ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) meal.getIngredientList();
        String[] expectedIngredientList = {"chicken breast ($2.50)", "cucumber ($1.00)", "egg ($0.50)", "rice ($1.50)"};
        for (int i = 0; i < expectedIngredientList.length; i++) {
            assertEquals(expectedIngredientList[i], ingredientList.get(i).toString());
            String ingStringMsg = "Matching ingredient string.";
            logger.info(ingStringMsg);

        }
    }

    private void duplicate_meal_catch() {
        String[] validUserInputs = formDuplicateMealsList();
        for (String userInput : validUserInputs) {
            try {
                String fineMsg = "Running duplicate_meal_catch().";
                logger.fine(fineMsg);
                Command command = new CreateCommand(userInput);
                command.execute(mealManager, ui);
            } catch (EZMealPlanException ezMealPlanException) {
                String chickenRice = "chicken rice";
                String listName = "main meal list";
                assertEquals(new DuplicateMealException(chickenRice, listName).getMessage()
                        , ezMealPlanException.getMessage());
                String dupMealMsg = "Duplicate meal caught!";
                logger.info(dupMealMsg);
            }
        }
        checkExpectedListSize();
    }

    private void checkExpectedListSize() {
        int actualSize = mealManager.getMainMeals().getList().size();
        int expectedSize = 3;
        assertEquals(expectedSize, actualSize);
        System.out.println();
        for (Meal meal : mealManager.getMainMeals().getList()) {
            System.out.println(meal.toString());
        }
    }

    private static String[] formDuplicateMealsList() {
        String firstInput = "create /mname chicken rice /ing chicken breast (3.5)," +
                            " rice (1.00), egg (0.6), cucumber (1.5)";
        String secondInput = "create /mname chicken rice /ing chicken breast (2.5)," +
                             " rice (1.5), egg (0.5), cucumber (1)";
        String thirdInput = "create /mname chicken rice /ing chicken breast (2)," +
                            " rice (2), egg (0.4), cucumber (1.1)";
        String fourthInput = "create /mname chicken rice /ing chicken breast (2)," +
                             " rice (2), egg (0.4), cucumber (1.1), tomato (2)";
        String fifthInput = "create /mname hainanese chicken rice /ing chicken breast (2)," +
                            " rice (2), egg (0.4), cucumber (1.1), tomato (2)";
        return new String[]{firstInput, secondInput, thirdInput, fourthInput, fifthInput};
    }

    private void duplicate_ingredient_catch() {
        String userInput = "create /mname chicken breast /ing chicken breast (2.5)," +
                           " chicken breast(1.5)";
        Command command = new CreateCommand(userInput);
        String fineMsg = "Running duplicate_ingredient_catch().";
        logger.fine(fineMsg);
        try {
            command.execute(mealManager, ui);
        } catch (EZMealPlanException ezMealPlanException) {
            String chickenBreast = "chicken breast";
            assertEquals(new DuplicateIngredientException(chickenBreast, chickenBreast).getMessage()
                    , ezMealPlanException.getMessage());
            String dupIngMsg = "Duplicate ingredients caught!";
            logger.info(dupIngMsg);
        }
    }
}
