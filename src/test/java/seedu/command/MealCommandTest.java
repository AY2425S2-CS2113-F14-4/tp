package seedu.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import seedu.exceptions.EZMealPlanException;
import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.ui.UserInterface;

public class MealCommandTest {
    private static final Logger logger = Logger.getLogger(MealCommandTest.class.getName());

    public MealCommandTest() {
        String fileName = "MealCommandTest.log";
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

    /**
     * Define a UserInterface class for testing, to capture the params of printMealList.
     */
    public static class TestUserInterface extends UserInterface {
        List<Meal> capturedMeals;
        String capturedListName;

        @Override
        public void printMealList(List<Meal> meals, String mealListName) {

            this.capturedMeals = new ArrayList<>(meals);
            this.capturedListName = mealListName;
        }
    }

    @Test
    public void testExecute_mealCommand_printsUserChosenMeals() throws EZMealPlanException {
        logger.fine("running testExecute_mealCommand_printsUserChosenMeals()");
        MealManager mealManager = new MealManager();
        Meal meal1 = new Meal("Meal A");
        Meal meal2 = new Meal("Meal B");
        mealManager.getUserMeals().getList().add(meal1);
        mealManager.getUserMeals().getList().add(meal2);

        TestUserInterface testUI = new TestUserInterface();
        MealCommand mealCommand = new MealCommand();
        mealCommand.execute(mealManager, testUI);

        assertEquals("user chosen meals", testUI.capturedListName);
        List<Meal> expectedMeals = new ArrayList<>();
        expectedMeals.add(meal1);
        expectedMeals.add(meal2);
        assertIterableEquals(expectedMeals, testUI.capturedMeals);
        logger.fine("testExecute_mealCommand_printsUserChosenMeals() passed");
    }
}
