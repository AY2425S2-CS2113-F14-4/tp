package seedu.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.InvalidPriceException;
import seedu.food.Ingredient;
import seedu.food.Meal;
import seedu.meallist.MainMeals;
import seedu.meallist.Meals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserInterfaceTest {

    private static final Logger logger = Logger.getLogger(UserInterfaceTest.class.getName());
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final String ls = System.lineSeparator();
    private UserInterface ui;

    // Main method to set up the logger so that messages are written to the .log file.
    public static void main(String[] args) {
        String fileName = "RemoveDeleteCheckerTest.log";
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

    @BeforeEach
    void setUp() {
        // Call main(null) to set up logger.
        main(null);
        // Redirect System.out to capture printed output for testing.
        System.setOut(new PrintStream(outContent));
        ui = new UserInterface();
    }

    @AfterEach
    void tearDown() {
        // Call main(null) to set up logger if needed.
        main(null);
        // Restore original System.out after each test and reset captured output.
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    void printGreetingMessage_noInput_success() {
        main(null);
        ui.printGreetingMessage();
        String expected = "Hello! This is EzMealPlan" + ls +
                "Let me help you in planning your meals." + ls;
        assertEquals(expected, outContent.toString(), "Greeting message output does not match.");
    }

    @Test
    void printGoodbye_noInput_success() {
        main(null);
        ui.printGoodbye();
        String expected = "Bye. Hope to see you again soon!";
        assertEquals(expected, outContent.toString(), "Goodbye message output does not match.");
    }

    @Test
    void printUnknownCommand_invalidInput_success() {
        main(null);
        ui.printUnknownCommand("unknown_cmd");
        String expected = "Invalid command: unknown_cmd" + ls +
                "me no understand what you talking." + ls;
        assertEquals(expected, outContent.toString(), "Unknown command message output does not match.");
    }

    @Test
    void printErrorMessage_exceptionInput_success() {
        main(null);
        Exception exception = new Exception("Test exception message");
        ui.printErrorMessage(exception);
        String expected = "Test exception message" + ls;
        assertEquals(expected, outContent.toString(), "Error message output does not match.");
    }

    @Test
    void printAddMealMessage_validMeal_success() throws EZMealPlanException {
        main(null);
        Meal mockMeal = new Meal("Chicken Rice");
        Meals mockMealList = new MainMeals();
        mockMealList.addMeal(mockMeal);

        ui.printAddMealMessage(mockMeal, mockMealList);
        String expected = "You have successfully added a meal: Chicken Rice ($0.00) into main meal list." + ls +
                "Here are the meals in main meal list:" + ls +
                "    1. Chicken Rice ($0.00)" + ls +
                ls +
                "Currently, you have 1 meals in main meal list." + ls;
        assertEquals(expected, outContent.toString(), "Add meal message output does not match.");
    }

    @Test
    void printIngredientList_validMeal_success() throws InvalidPriceException {
        main(null);
        Meal meal = new Meal("Test Meal");
        meal.getIngredientList().add(new Ingredient("Chicken Breast", 2.5));
        meal.getIngredientList().add(new Ingredient("Rice", 1.0));

        ui.printIngredientList(meal);
        String expected = "Here are the ingredients for " + meal + ":" + ls +
                "    1. Chicken Breast ($2.50)" + ls +
                "    2. Rice ($1.00)" + ls + ls;
        assertEquals(expected, outContent.toString(), "Ingredient list output does not match.");
    }

    @Test
    void printMealList_validMealList_success() throws InvalidPriceException {
        main(null);
        List<Meal> meals = List.of(new Meal("Chicken Rice"), new Meal("Fish Ball Noodles"));
        ui.printMealList(meals, "main meal list");
        String expected = "Here are the meals in main meal list:" + ls +
                "    1. Chicken Rice ($0.00)" + ls +
                "    2. Fish Ball Noodles ($0.00)" + ls + ls;
        assertEquals(expected, outContent.toString(), "Meal list output does not match.");
    }

    @Test
    void printRemovedMessage_validMeal_success() throws InvalidPriceException {
        main(null);
        Meal mockMeal = new Meal("Chicken Rice");
        ui.printRemovedMessage(mockMeal, 2);
        String expected = "Chicken Rice ($0.00) has been removed from your meal list!" + ls +
                "You have 2 meals in your meal list.";
        assertEquals(expected, outContent.toString(), "Removed message output does not match.");
    }

    @Test
    void printDeletedMessage_validMeal_success() throws InvalidPriceException {
        main(null);
        Meal mockMeal = new Meal("Chicken Rice");
        ui.printDeletedMessage(mockMeal, 5);
        String expected = "Chicken Rice ($0.00) has been removed from the global meal list!" + ls +
                "There are now 5 meals in the global meal list.";
        assertEquals(expected, outContent.toString(), "Deleted message output does not match.");
    }

    @Test
    void prompt_noInput_success() {
        main(null);
        ui.prompt();
        String expected = "How may I help you?" + ls;
        assertEquals(expected, outContent.toString(), "Prompt output does not match.");
    }

    @Test
    void printClearedList_noInput_success() {
        main(null);
        ui.printClearedList();
        String expected = "All meals cleared from your meal list!" + ls;
        assertEquals(expected, outContent.toString(), "Cleared list message output does not match.");
    }

    @Test
    void printByeCommandHelp_noInput_success() {
        main(null);
        ui.printByeCommandHelp();
        String expected = "Entering the bye command will gracefully exits the software" + ls +
                "Sample input: bye" + ls +
                "Sample output: Bye. Hope to see you again soon!" + ls;
        assertEquals(expected, outContent.toString(), "Bye command help output does not match.");
    }

    @Test
    void printGeneralHelp_noInput_success() {
        main(null);
        ui.printGeneralHelp();
        String expected = "you have not entered any command line options" + ls;
        assertEquals(expected, outContent.toString(), "General help output does not match.");
    }

    @Test
    void printMealCommandHelp_noInput_success() {
        main(null);
        ui.printMealCommandHelp();
        String expected = "Entering the meal command will list out all the meals you have selected from the " +
                "main list." + ls +
                "Sample input: meal" + ls +
                "Sample output:" + ls +
                "               1. Chicken Rice" + ls +
                "               2. Fish Ball Noodles" + ls;
        assertEquals(expected, outContent.toString(), "Meal command help output does not match.");
    }

    @Test
    void printListCommandHelp_noInput_success() {
        main(null);
        ui.printListCommandHelp();
        String expected = "Entering the list command will list out all the meals from the main list." + ls +
                "Sample input: list" + ls +
                "Sample output:" + ls +
                "               1. Chicken Rice" + ls +
                "               2. Fish Ball Noodles" + ls;
        assertEquals(expected, outContent.toString(), "List command help output does not match.");
    }

    @Test
    void printCreateCommandHelp_noInput_success() {
        main(null);
        ui.printCreateCommandHelp();
        String expected = "Entering the create command will create a new meal" + ls +
                "Sample input: create /mname Hokkien Mee /ing yellow noodle (1), thick bee hoon (1), prawn (1.2), " +
                "egg (0.5), pork lard (0.2), squid (1.5), lime (0.1)" + ls +
                "Sample output:" + ls +
                "               1. Chicken Rice ($3.00)" + ls +
                "               2. Fish Ball Noodles ($3.00)" + ls +
                "               3. Hokkien Mee ($5.50)" + ls;
        assertEquals(expected, outContent.toString(), "Create command help output does not match.");
    }

    @Test
    void printRemoveCommandHelp_noInput_success() {
        main(null);
        ui.printRemoveCommandHelp();
        String expected = "Entering the remove command will remove a meal in the user's meal list" + ls +
                "Sample input: remove 1 " + ls +
                "Sample output:" + ls +
                "               OK.  Chicken Rice ($3.00) have been removed from the meal list." + ls;
        assertEquals(expected, outContent.toString(), "Remove command help output does not match.");
    }

    @Test
    void printSelectCommandHelp_noInput_success() {
        main(null);
        ui.printSelectCommandHelp();
        String expected = "Entering the select command will add the selected meal in the meal list" + ls +
                "Sample input: select 1 " + ls +
                "Sample output:" + ls +
                "               OK.  Chicken Rice ($3.00) have been added to the meal list." + ls;
        assertEquals(expected, outContent.toString(), "Select command help output does not match.");
    }

    @Test
    void printFilterCommandHelp_noInput_success() {
        main(null);
        ui.printFilterCommandHelp();
        String expected = "Entering the filter command will filter a meal in the main list" + ls +
                "There are three filter option by cost, by ingredient or by meal name" + ls +
                "Sample input: filter /mcost 5.50" + ls +
                "Sample output:" + ls +
                "               1. Chicken Rice ($5.50)" + ls +
                "Sample input: filter /ing  Fish Ball" + ls +
                "Sample output:" + ls +
                "               1. Chicken Rice ($5.50)" + ls +
                "Sample input: filter /manme Hokkien Mee" + ls +
                "Sample output:" + ls +
                "               1. Hokkien Mee ($5.50)" + ls;
        assertEquals(expected, outContent.toString(), "Filter command help output does not match.");
    }

    @Test
    void printDeleteCommandHelp_noInput_success() {
        main(null);
        ui.printDeleteCommandHelp();
        String expected = "Entering the delete command will delete a meal in the main list" + ls +
                "Sample input: delete 1 " + ls +
                "Sample output:" + ls +
                "               OK.  Chicken Rice ($3.00) have been deleted from the main list." + ls;
        assertEquals(expected, outContent.toString(), "Delete command help output does not match.");
    }

    @Test
    void printViewCommandHelp_noInput_success() {
        main(null);
        ui.printViewCommandHelp();
        String expected = "Entering the view command will view a view all the ingredients of the selected meal" + ls +
                "Sample input: view 1 " + ls +
                "Sample output:" + ls +
                "               1. yellow noodle" + ls +
                "               2. bee hoon" + ls +
                "               3. prawn" + ls +
                "               4. egg" + ls +
                "               5. pork lard" + ls +
                "               6. squid" + ls +
                "               7. lime" + ls;
        assertEquals(expected, outContent.toString(), "View command help output does not match.");
    }

    @Test
    void printClearCommandHelp_noInput_success() {
        main(null);
        ui.printClearCommandHelp();
        String expected = "Entering the clear command will clear all the meals in the meal list" + ls +
                "Sample input: clear" + ls +
                "Sample output:" + ls +
                "               The meal list has been cleared." + ls;
        assertEquals(expected, outContent.toString(), "Clear command help output does not match.");
    }

    @Test
    void printHelpCommandHelp_noInput_success() {
        main(null);
        ui.printHelpCommandHelp();
        String expected = "Entering the help command followed by the command that requires help will give brief" +
                " explanation of the command" + ls +
                "Sample input: help bye" + ls +
                "Sample output:" + ls +
                "                Entering the bye command will gracefully exits the software" + ls +
                "                    Sample input: bye" + ls +
                "                    Sample output: Bye. Hope to see you again soon!" + ls;
        assertEquals(expected, outContent.toString(), "Help command help output does not match.");
    }
}
