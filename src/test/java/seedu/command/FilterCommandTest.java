package seedu.command;


import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.InvalidFilterMethodException;
import seedu.exceptions.InvalidMcostException;
import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.meallist.Meals;
import seedu.storage.Storage;
import seedu.ui.UserInterface;

import java.io.IOException;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FilterCommandTest {
    private static final Logger logger = Logger.getLogger(FilterCommandTest.class.getName());
    final MealManager mealManager = new MealManager();
    final UserInterface ui = new UserInterface();

    public FilterCommandTest() {
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

    private void addMeals() {
        List<Meal> mealList = Storage.loadPresetMeals();
        Meals mainMeal = mealManager.getMainMeals();
        for (Meal meal : mealList) {
            addMeal(meal, mainMeal);
        }
    }

    private void addMeal(Meal meal, Meals mainMeal) {
        try {
            mealManager.addMeal(meal, mainMeal);
        } catch (EZMealPlanException ezMealPlanException) {
            System.err.println(ezMealPlanException.getMessage());
        }
    }


    @Test
    public void filterCommand_success() {
        mealManager.getMainMeals().getList().clear();
        logger.fine("running filterCommand_success()");
        String[] validFilterCommands = {"filter /mname a", "filter /ing b,c", "filter /mcost 2", "filter /mname Mname"
                , "filter /ing Ing", "filter /mcost 5"};
        runValidFilterCommands(validFilterCommands);
        addMeals();
        runValidFilterCommands(validFilterCommands);
        logger.info("filterCommand_success() passed");
    }

    @Test
    public void filterCommand_fail() {
        logger.fine("running filterCommand_fail()");
        mealManager.getMainMeals().getList().clear();
        addMeals();
        checkMissingFilterKeyword();
        checkInvalidPrice();
        logger.info("filterCommand_fail() passed");
    }

    private void checkInvalidPrice() {
        logger.fine("running checkInvalidPrice()");
        checkNegativePrice();
        checkInvalidPriceFormat();
        logger.info("checkInvalidPrice() passed");
    }

    private void checkInvalidPriceFormat() {
        String testName = "checkInvalidPriceFormat()";
        String invalidPrice = "filter /mcost mcost";
        String expectedMessage = new InvalidMcostException().getMessage();
        checkInvalidFilterInput(testName, expectedMessage, invalidPrice);
    }

    private void checkNegativePrice() {
        String testName = "checkNegativePrice()";
        String negativePrice = "filter /mcost -2";
        String expectedMessage = new InvalidMcostException().getMessage();
        checkInvalidFilterInput(testName, expectedMessage, negativePrice);
    }

    private void checkMissingFilterKeyword() {
        String testName = "checkMissingFilterKeyword()";
        String expectedMessage = new InvalidFilterMethodException().getMessage();
        String missingFilterKeyword = "filter 1";
        checkInvalidFilterInput(testName, expectedMessage, missingFilterKeyword);
    }

    private void checkInvalidFilterInput(String testName, String expectedMessage, String input) {
        logger.fine("running " + testName);
        try {
            FilterCommand filterCommand = new FilterCommand(input);
            filterCommand.execute(mealManager, ui);
            fail();
        } catch (EZMealPlanException ezMealPlanException) {
            assertEquals(expectedMessage, ezMealPlanException.getMessage());
            logger.info("Matching exception caught!");
            logger.info(testName + " passed");
        }
    }

    private void runValidFilterCommands(String[] validFilterCommands) {
        for (String filterCommand : validFilterCommands) {
            FilterCommand command = new FilterCommand(filterCommand);
            try {
                command.execute(mealManager, ui);
                logger.info("Filter command executed successfully");
            } catch (EZMealPlanException ezMealPlanException) {
                System.err.println(ezMealPlanException.getMessage());
                fail();
            }
        }
    }
}
