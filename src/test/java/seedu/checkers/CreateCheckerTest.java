package seedu.checkers;

import seedu.command.Command;
import seedu.command.CreateCommand;
import seedu.exceptions.EZMealPlanException;
import seedu.food.Ingredient;
import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.ui.UserInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCheckerTest {
    private static final Logger logger = Logger.getLogger(CreateCheckerTest.class.getName());
    final MealManager mealManager = new MealManager();
    final UserInterface ui = new UserInterface();

    public static void main(String[] args) {
        String fileName = "CreateCheckerTest.log";
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
    public void create_checker_success() throws EZMealPlanException {
        main(null);
        String validUserInput = "create /mname chicken rice /ing chicken breast (2.5)," +
                " rice (1.5), egg (0.5), cucumber (1)";
        Command command = new CreateCommand(validUserInput);
        command.execute(mealManager, ui);
        List<Meal> mealList = mealManager.getMainMeals().getList();
        int expectedMealListSize = 1;
        assertEquals(expectedMealListSize, mealList.size());
        checkExpectedStrings(mealList);
    }

    private static void checkExpectedStrings(List<Meal> mealList) {
        int zeroIndex = 0;
        Meal meal = mealList.get(zeroIndex);
        String expectedMealString = "chicken rice ($5.50)";
        assertEquals(expectedMealString, meal.toString());
        ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) meal.getIngredientList();
        String[] expectedIngredientList = {"chicken breast ($2.50)", "cucumber ($1.00)", "egg ($0.50)", "rice ($1.50)"};
        for (int i = 0; i < expectedIngredientList.length; i++) {
            assertEquals(expectedIngredientList[i], ingredientList.get(i).toString());
        }
    }

    @Test
    public void duplicate_meal_catch() {
        main(null);
        String[] validUserInputs = formDuplicateMealsList();
        for (String userInput : validUserInputs) {
            try {
                String fineMsg = "Running duplicate_meal_catch().";
                logger.fine(fineMsg);
                Command command = new CreateCommand(userInput);
                command.execute(mealManager, ui);
            } catch (EZMealPlanException ezMealPlanException) {
                String expectedOutput = "This meal: chicken rice already exists in the main meal list.\n";
                assertEquals(expectedOutput, ezMealPlanException.getMessage());
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

    @Test
    public void duplicate_ingredient_catch() {
        main(null);
        String userInput = "create /mname chicken breast /ing chicken breast (2.5)," +
                " chicken breast(1.5)";
        Command command = new CreateCommand(userInput);
        String fineMsg = "Running duplicate_ingredient_catch().";
        logger.fine(fineMsg);
        try {
            command.execute(mealManager, ui);
        } catch (EZMealPlanException ezMealPlanException) {
            String expectedOutput = "Ingredient: chicken breast already exists in the meal: chicken breast.\n";
            assertEquals(expectedOutput, ezMealPlanException.getMessage());
            String dupIngMsg = "Duplicate ingredients caught!";
            logger.info(dupIngMsg);
        }
    }

}
