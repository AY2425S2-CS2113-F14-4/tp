package seedu.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.InvalidPriceException;
import seedu.food.Ingredient;
import seedu.food.Meal;
import seedu.meallist.MealList;
import seedu.meallist.RecipesList;

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
    public UserInterfaceTest() {
        String fileName = "UserInterfaceTest.log";
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
        // Redirect System.out to capture printed output for testing.
        System.setOut(new PrintStream(outContent));
        ui = new UserInterface();
    }

    @AfterEach
    void tearDown() {
        // Restore original System.out after each test and reset captured output.
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    void printGreetingMessage_noInput_success() {
        logger.fine("running printGreetingMessage_noInput_success()");
        ui.printGreetingMessage();
        String expected = "Hello! This is EzMealPlan" + ls +
                          "Let me help you in planning your meals." + ls;
        assertEquals(expected, outContent.toString(), "Greeting message output does not match.");
        logger.info("printGreetingMessage_noInput_success() passed");
    }

    @Test
    void printGoodbye_noInput_success() {
        logger.fine("running printGoodbye_noInput_success()");
        ui.printGoodbye();
        String expected = "Bye. Hope to see you again soon!";
        assertEquals(expected, outContent.toString(), "Goodbye message output does not match.");
        logger.info("printGoodbye_noInput_success() passed");
    }

    @Test
    void printUnknownCommand_invalidInput_success() {
        logger.fine("running printUnknownCommand_invalidInput_success()");
        ui.printUnknownCommand("unknown_cmd");
        String expected = "Invalid command: unknown_cmd" + ls +
                          "me no understand what you talking." + ls;
        assertEquals(expected, outContent.toString(), "Unknown command message output does not match.");
        logger.info("printUnknownCommand_invalidInput_success() passed");
    }

    @Test
    void printErrorMessage_exceptionInput_success() {
        logger.fine("running printErrorMessage_exceptionInput_success()");
        Exception exception = new Exception("Test exception message");
        ui.printErrorMessage(exception);
        String expected = "Test exception message" + ls;
        assertEquals(expected, outContent.toString(), "Error message output does not match.");
        logger.info("printErrorMessage_exceptionInput_success() passed");
    }

    @Test
    void printAddMealMessage_validMeal_success() throws EZMealPlanException {
        logger.fine("running printAddMealMessage_validMeal_success()");
        Meal mockMeal = new Meal("Chicken Rice");
        MealList mockMealList = new RecipesList();
        mockMealList.addMeal(mockMeal);

        ui.printAddMealMessage(mockMeal, mockMealList);
        String expected = "You have successfully added a meal: Chicken Rice ($0.00) into "+
                          mockMealList.getMealListName() +"." + ls +
                          "Here are the meals in "+ mockMealList.getMealListName() + ":" + ls +
                          "    1. Chicken Rice ($0.00)" + ls +
                          ls +
                          "Currently, you have 1 meals in " + mockMealList.getMealListName() + "." + ls;
        assertEquals(expected, outContent.toString(), "Add meal message output does not match.");
        logger.info("printAddMealMessage_validMeal_success() passed");
    }

    @Test
    void printIngredientList_validMeal_success() throws EZMealPlanException {
        logger.fine("running printIngredientList_validMeal_success()");
        Meal meal = new Meal("Test Meal");
        meal.getIngredientList().add(new Ingredient("Chicken Breast", "2.50"));
        meal.getIngredientList().add(new Ingredient("Rice", "1.00"));

        ui.printIngredientList(meal);
        String expected = "Here are the ingredients for " + meal + ":" + ls +
                          "    1. Chicken Breast ($2.50)" + ls +
                          "    2. Rice ($1.00)" + ls + ls;
        assertEquals(expected, outContent.toString(), "Ingredient list output does not match.");
        logger.info("printIngredientList_validMeal_success() passed");
    }

    @Test
    void printMealList_validMealList_success() throws InvalidPriceException {
        logger.fine("running printMealList_validMealList_success()");
        List<Meal> meals = List.of(new Meal("Chicken Rice"), new Meal("Fish Ball Noodles"));
        String recipesListName = new RecipesList().getMealListName();
        ui.printMealList(meals, recipesListName);
        String expected = "Here are the meals in " + recipesListName +":" + ls +
                          "    1. Chicken Rice ($0.00)" + ls +
                          "    2. Fish Ball Noodles ($0.00)" + ls + ls;
        assertEquals(expected, outContent.toString(), "Meal list output does not match.");
        logger.info("printMealList_validMealList_success() passed");
    }

    @Test
    void printRemovedMessage_validMeal_success() throws InvalidPriceException {
        logger.fine("running printRemovedMessage_validMeal_success()");
        Meal mockMeal = new Meal("Chicken Rice");
        ui.printRemovedMessage(mockMeal, 2);
        String expected = "Chicken Rice ($0.00) has been removed from your meal list!" + ls +
                          "You have 2 meals in your meal list.\n";
        assertEquals(expected, outContent.toString(), "Removed message output does not match.");
        logger.info("printRemovedMessage_validMeal_success() passed");
    }

    @Test
    void printDeletedMessage_validMeal_success() throws InvalidPriceException {
        logger.fine("running printDeletedMessage_validMeal_success()");
        Meal mockMeal = new Meal("Chicken Rice");
        ui.printDeletedMessage(mockMeal, 5);
        String expected = "Chicken Rice ($0.00) has been removed from the recipes list!" + ls +
                          "There are now 5 meals in the recipes list.\n";
        assertEquals(expected, outContent.toString(), "Deleted message output does not match.");
        logger.info("printDeletedMessage_validMeal_success() passed");
    }

    @Test
    void prompt_noInput_success() {
        logger.fine("running prompt_noInput_success()");
        ui.prompt();
        String expected = "How may I help you?" + ls;
        assertEquals(expected, outContent.toString(), "Prompt output does not match.");
        logger.info("prompt_noInput_success() passed");
    }

    @Test
    void printClearedList_noInput_success() {
        logger.fine("running printClearedList_noInput_success()");
        ui.printClearedList();
        String expected = "All meals cleared from your wishlist!" + ls;
        assertEquals(expected, outContent.toString(), "Cleared list message output does not match.");
        logger.info("printClearedList_noInput_success() passed");
    }

    @Test
    void printByeCommandHelp_noInput_success() {
        logger.fine("running printByeCommandHelp_noInput_success()");
        ui.printByeCommandHelp();
        String expected = "Entering the bye command will gracefully exits the software" + ls +
                          "Sample input: bye" + ls +
                          "Sample output: Bye. Hope to see you again soon!" + ls;
        assertEquals(expected, outContent.toString(), "Bye command help output does not match.");
        logger.info("printByeCommandHelp_noInput_success() passed");
    }

    @Test
    void printGeneralHelp_noInput_success() {
        logger.fine("running printGeneralHelp_noInput_success()");
        ui.printGeneralHelp();
        String expected = "you have not entered any command line options" + ls;
        assertEquals(expected, outContent.toString(), "General help output does not match.");
        logger.info("printGeneralHelp_noInput_success() passed");
    }

    @Test
    void printWishlistCommandHelp_noInput_success() {
        logger.fine("running printWishlistCommandHelp_noInput_success()");
        ui.printWishlistCommandHelp();
        String expected = "Entering the wishlist command will list out all the meals you have selected from the " +
                          "recipes list." + ls +
                          "Sample input: wishlist" + ls +
                          "Sample output:" + ls +
                          "               1. Chicken Rice" + ls +
                          "               2. Fish Ball Noodles" + ls;
        assertEquals(expected, outContent.toString(), "Wishlist command help output does not match.");
        logger.info("printWishlistCommandHelp_noInput_success() passed");
    }

    @Test
    void printRecipesCommandHelp_noInput_success() {
        logger.fine("running printRecipesCommandHelp_noInput_success()");
        ui.printRecipesCommandHelp();
        String expected = "Entering the recipes command will list out all the meals from the recipes list." + ls +
                          "Sample input: recipes" + ls +
                          "Sample output:" + ls +
                          "               1. Chicken Rice" + ls +
                          "               2. Fish Ball Noodles" + ls;
        assertEquals(expected, outContent.toString(), "Recipes command help output does not match.");
        logger.info("printRecipesCommandHelp_noInput_success() passed");
    }

    @Test
    void printCreateCommandHelp_noInput_success() {
        logger.fine("running printCreateCommandHelp_noInput_success()");
        ui.printCreateCommandHelp();
        String expected = "Entering the create command will create a new meal" + ls +
                          "Sample input: create /mname Hokkien Mee /ing yellow noodle (1)" +
                          ", thick bee hoon (1), prawn (1.2), " +
                          "egg (0.5), pork lard (0.2), squid (1.5), lime (0.1)" + ls +
                          "Sample output:" + ls +
                          "               1. Chicken Rice ($3.00)" + ls +
                          "               2. Fish Ball Noodles ($3.00)" + ls +
                          "               3. Hokkien Mee ($5.50)" + ls;
        assertEquals(expected, outContent.toString(), "Create command help output does not match.");
        logger.info("printCreateCommandHelp_noInput_success() passed");
    }

    @Test
    void printRemoveCommandHelp_noInput_success() {
        logger.fine("running printRemoveCommandHelp_noInput_success()");
        ui.printRemoveCommandHelp();
        String expected = "Entering the remove command will remove a meal in the wishlist" + ls +
                          "Sample input: remove 1 " + ls +
                          "Sample output:" + ls +
                          "               OK.  Chicken Rice ($3.00) have been removed from the wishlist." + ls;
        assertEquals(expected, outContent.toString(), "Remove command help output does not match.");
        logger.info("printRemoveCommandHelp_noInput_success() passed");
    }

    @Test
    void printSelectCommandHelp_noInput_success() {
        logger.fine("running printSelectCommandHelp_noInput_success()");
        ui.printSelectCommandHelp();
        String expected = "Entering the select command will add the selected meal from the filtered or unfiltered " +
                          "recipes list into the wishlist" + ls +
                          "Sample input (filtered by ingredient(s)): select 1 /ing yellow noodle, fish" + ls +
                          "Sample input (filtered by meal cost): select 1 /mcost 2" + ls +
                          "Sample input (filtered by meal name(s)): select 1 /mname fish, ball" + ls +
                          "Sample input: select 1" + ls +
                          "Sample output based on the sample input 'select 1':" + ls +
                          "               OK.  Chicken Rice ($3.00) have been added to the wishlist." + ls;
        assertEquals(expected, outContent.toString(), "Select command help output does not match.");
        logger.info("printSelectCommandHelp_noInput_success() passed");
    }

    @Test
    void printFilterCommandHelp_noInput_success() {
        logger.fine("running printFilterCommandHelp_noInput_success()");
        ui.printFilterCommandHelp();
        String expected = "Entering the filter command will filter a meal in the recipes list" + ls +
                          "There are three filter option by cost, by ingredient or by meal name" + ls +
                          "Sample input: filter /mcost 5.50" + ls +
                          "Sample output:" + ls +
                          "               1. Chicken Rice ($5.50)" + ls +
                          "Sample input: filter /ing  Fish Ball" + ls +
                          "Sample output:" + ls +
                          "               1. Chicken Rice ($5.50)" + ls +
                          "Sample input: filter /mname Hokkien Mee" + ls +
                          "Sample output:" + ls +
                          "               1. Hokkien Mee ($5.50)" + ls;
        assertEquals(expected, outContent.toString(), "Filter command help output does not match.");
        logger.info("printFilterCommandHelp_noInput_success() passed");
    }

    @Test
    void printDeleteCommandHelp_noInput_success() {
        logger.fine("running printDeleteCommandHelp_noInput_success()");
        ui.printDeleteCommandHelp();
        String expected = "Entering the delete command will delete a meal in the recipes list" + ls +
                          "Sample input: delete 1 " + ls +
                          "Sample output:" + ls +
                          "               OK.  Chicken Rice ($3.00) have been deleted from the recipes list." + ls;
        assertEquals(expected, outContent.toString(), "Delete command help output does not match.");
        logger.info("printDeleteCommandHelp_noInput_success() passed");
    }

    @Test
    void printViewCommandHelp_noInput_success() {
        logger.fine("running printViewCommandHelp_noInput_success()");
        ui.printViewCommandHelp();
        String expected = "Entering the view command will give a view of all the ingredients of the selected meal" +
                          ls +
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
        logger.info("printViewCommandHelp_noInput_success() passed");
    }

    @Test
    void printClearCommandHelp_noInput_success() {
        logger.fine("running printClearCommandHelp_noInput_success()");
        ui.printClearCommandHelp();
        String expected = "Entering the clear command will clear all the meals in the wishlist" + ls +
                          "Sample input: clear" + ls +
                          "Sample output:" + ls +
                          "               The wishlist has been cleared." + ls;
        assertEquals(expected, outContent.toString(), "Clear command help output does not match.");
        logger.info("printClearCommandHelp_noInput_success() passed");
    }

    @Test
    void printHelpCommandHelp_noInput_success() {
        logger.fine("running printHelpCommandHelp_noInput_success()");
        ui.printHelpCommandHelp();
        String expected = "Entering the help command followed by the command that requires help will give brief" +
                          " explanation of the command" + ls +
                          "Sample input: help bye" + ls +
                          "Sample output:" + ls +
                          "                Entering the bye command will gracefully exits the software" + ls +
                          "                    Sample input: bye" + ls +
                          "                    Sample output: Bye. Hope to see you again soon!" + ls;
        assertEquals(expected, outContent.toString(), "Help command help output does not match.");
        logger.info("printHelpCommandHelp_noInput_success() passed");
    }
}
