package seedu.command;

import seedu.exceptions.DuplicateMealException;
import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.InvalidMcostException;
import seedu.exceptions.InvalidSelectIndexException;
import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.meallist.MealList;
import seedu.meallist.WishList;
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

public class SelectCommandTest {
    private static final Logger logger = Logger.getLogger(SelectCommandTest.class.getName());
    final MealManager mealManager = new MealManager();
    final UserInterface ui = new UserInterface();

    public SelectCommandTest() {
        String fileName = "SelectCommandTest.log";
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
    public void selectCommand_success() {
        mealManager.getRecipesList().getList().clear();
        mealManager.getWishList().getList().clear();
        logger.fine("running selectCommand_success()");
        String[] validSelectCommands = {"select 2 /mname a", "select 1 /ing b,c", "select 2 /mcost 2.00"
                , "select 4 /mname Mname", "select 2 /ing Ing", "select 1 /mcost 5.00"};
        runValidSelectCommands(validSelectCommands);
        addMeals();
        runValidSelectCommands(validSelectCommands);
        logger.info("selectCommand_success() passed");
    }

    @Test
    public void selectCommand_fail() {
        logger.fine("running selectCommand_fail()");
        mealManager.getRecipesList().getList().clear();
        mealManager.getWishList().getList().clear();
        addMeals();
        checkInvalidPrice();
        checkSelectDuplicateMeal();
        checkInvalidSelectIndex();
        checkIndexOutOfRange();
        logger.info("selectCommand_fail() passed");
    }

    private void checkInvalidSelectIndex() {
        checkInvalidIndexFormat();
        checkIndexOutOfRange();
    }

    private void checkInvalidIndexFormat() {
        String testName = "checkInvalidIndexFormat()";
        String[] invalidIndexSelectCommands = {"select a /mname chicken", "select u /ing chicken"
                , "select b /mcost 4.50", "select c"};
        for (String invalidIndexSelectCommand : invalidIndexSelectCommands) {
            String expectedMessage = new InvalidSelectIndexException().getMessage();
            checkInvalidSelectInput(testName, expectedMessage, invalidIndexSelectCommand);
        }
    }

    private void checkIndexOutOfRange() {
        String testName = "checkIndexOutOfRange()";
        String[] invalidIndexSelectCommands = {"select -1 /mname chicken", "select 10 /ing seafood"
                , "select 0 /mcost 4.50", "select 1000"};
        for (String invalidIndexSelectCommand : invalidIndexSelectCommands) {
            String expectedMessage = new InvalidSelectIndexException().getMessage();
            checkInvalidSelectInput(testName, expectedMessage, invalidIndexSelectCommand);
        }
    }

    private void checkInvalidPrice() {
        logger.fine("running checkInvalidPrice()");
        checkNegativePrice();
        checkInvalidPriceFormat();
        logger.info("checkInvalidPrice() passed");
    }

    private void checkInvalidPriceFormat() {
        String testName = "checkInvalidPriceFormat()";
        double one = 1.00;
        String[] invalidPrices = {"select 2 /mcost mcost", "select 2 /mcost 1", "select 2 /mcost 1.0"
                , "select 2 /mcost 1.0005", "select 2 /mcost .1", "select 2 /mcost .10"
                , "select 2 /mcost " + (Double.MAX_VALUE + one)};
        String expectedMessage = new InvalidMcostException().getMessage();
        for (String invalidPrice : invalidPrices) {
            checkInvalidSelectInput(testName, expectedMessage, invalidPrice);
        }
    }

    private void checkNegativePrice() {
        String testName = "checkNegativePrice()";
        String negativePrice = "select 2 /mcost -2.00";
        String expectedMessage = new InvalidMcostException().getMessage();
        checkInvalidSelectInput(testName, expectedMessage, negativePrice);
    }


    private void checkSelectDuplicateMeal() {
        String testName = "checkSelectDuplicateMeal()";
        String[] validSelectCommands = {"select 2 /mname chicken", "select 1 /ing chicken", "select 2 /mcost 4.50"
                , "select 10"};
        String[] mealNames = {"Chicken Satay", "Chicken Rice", "Claypot Rice", "Braised Duck Rice"};
        addMealsIntoUserList(validSelectCommands);
        String listName = new WishList().getMealListName();
        for (int i = 0; i < mealNames.length; i++) {
            String expectedMessage = new DuplicateMealException(mealNames[i], listName).getMessage();
            checkInvalidSelectInput(testName, expectedMessage, validSelectCommands[i]);
        }
    }

    private void addMealsIntoUserList(String[] validSelectCommands) {
        for (String validSelectCommand : validSelectCommands) {
            try {
                SelectCommand command = new SelectCommand(validSelectCommand);
                command.execute(mealManager, ui);
            } catch (EZMealPlanException ezMealPlanException) {
                System.err.println(ezMealPlanException.getMessage());
                fail();
            }
        }
    }

    private void checkInvalidSelectInput(String testName, String expectedMessage, String input) {
        logger.fine("running " + testName);
        try {
            SelectCommand selectCommand = new SelectCommand(input);
            selectCommand.execute(mealManager, ui);
            fail();
        } catch (EZMealPlanException ezMealPlanException) {
            assertEquals(expectedMessage, ezMealPlanException.getMessage());
            logger.info("Matching exception caught!");
            logger.info(testName + " passed");
        }
    }

    private void runValidSelectCommands(String[] validSelectCommands) {
        for (String selectCommand : validSelectCommands) {
            SelectCommand command = new SelectCommand(selectCommand);
            try {
                command.execute(mealManager, ui);
                logger.info("Select command executed successfully");
            } catch (EZMealPlanException ezMealPlanException) {
                System.err.println(ezMealPlanException.getMessage());
                fail();
            }
        }
    }

    private void addMeals() {
        List<Meal> mealList = Storage.loadPresetMeals();
        MealList recipesList = mealManager.getRecipesList();
        for (Meal meal : mealList) {
            addMeal(meal, recipesList);
        }
    }

    private void addMeal(Meal meal, MealList mealList) {
        try {
            mealManager.addMeal(meal, mealList);
        } catch (EZMealPlanException ezMealPlanException) {
            System.err.println(ezMealPlanException.getMessage());
        }
    }
}
