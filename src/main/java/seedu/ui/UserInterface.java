package seedu.ui;

import seedu.food.Ingredient;
import seedu.food.Meal;
import seedu.meallist.MainMeals;
import seedu.meallist.Meals;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;

    public UserInterface() {
        scanner = new Scanner(System.in);
    }

    public static void printMessage(String s) {
        System.out.println(s);
    }

    public String readInput() {
        String userCmd = "";
        if (scanner.hasNextLine()) {
            userCmd = scanner.nextLine();
            userCmd = userCmd.trim();
        }
        return userCmd;
    }

    public void printGreetingMessage() {
        System.out.println("Hello! This is EzMealPlan");
        System.out.println("Let me help you in planning your meals.");
    }

    public void printGoodbye() {
        scanner.close();
        System.out.print("Bye. Hope to see you again soon!");
    }

    public void printUnknownCommand(String userInput) {
        System.out.println("Invalid command: " + userInput);
        System.out.println("me no understand what you talking.\n");
    }

    public void printErrorMessage(Exception exception) {
        System.out.println(exception.getMessage());
    }

    public void printAddMealMessage(Meal meal, Meals mealList) {
        String mealListName = mealList instanceof MainMeals ? "main meal list" : "user meal List";
        String successAddMealMessage = "You have successfully added a meal: " + meal + " into " + mealListName + ".";
        System.out.println(successAddMealMessage);
        List<Meal> meals = mealList.getList();
        printMealList(meals, mealListName);
        String totalMealsMessage = "Currently, you have " + meals.size() +
                " meals in " + mealListName + ".\n";
        System.out.println(totalMealsMessage);
    }

    public void printIngredientList(Meal meal) {
        List<Ingredient> ingredientList = meal.getIngredientList();
        System.out.println("Here are the ingredients for " + meal + ":");
        int count = 0;
        for (Ingredient ingredient : ingredientList) {
            count++;
            System.out.println("    " + count + ". " + ingredient);
        }
        System.out.println();
    }

    public void printMealList(List<Meal> meals, String mealListName) {
        if (meals.isEmpty()) {
            System.out.println("No meals found in " + mealListName + ".\n");
            return;
        }

        System.out.println("Here are the meals in " + mealListName + ":");
        int count = 0;
        for (Meal meal : meals) {
            count++;
            System.out.println("    " + count + ". " + meal);
        }
        System.out.println();
    }

    public void printRemovedMessage(Meal meal, int size) {
        System.out.println(meal + " has been removed from your meal list!");
        System.out.printf("You have %d meals in your meal list.\n", size);
    }

    public void printDeletedMessage(Meal meal, int size) {
        System.out.println(meal + " has been removed from the global meal list!");
        System.out.printf("There are now %d meals in the global meal list.\n", size);
    }

    public void prompt() {
        System.out.println("How may I help you?");
    }

    public void printClearedList() {
        printMessage("All meals cleared from your meal list!");
    }

    public void printByeCommandHelp() {
        System.out.println("Entering the bye command will gracefully exits the software");
        System.out.println("Sample input: bye");
        System.out.println("Sample output: Bye. Hope to see you again soon!");
    }

    public void printGeneralHelp() {
        System.out.println("you have not entered any command line options");
    }

    public void printMealCommandHelp() {
        System.out.println("Entering the meal command will list out all the meals you " +
                "have selected from the main list.");
        System.out.println("Sample input: meal");
        System.out.println("Sample output:");
        System.out.println("               1. Chicken Rice");
        System.out.println("               2. Fish Ball Noodles");
    }

    public void printListCommandHelp() {
        System.out.println("Entering the list command will list out all the meals from the main list.");
        System.out.println("Sample input: list");
        System.out.println("Sample output:");
        System.out.println("               1. Chicken Rice");
        System.out.println("               2. Fish Ball Noodles");
    }

    public void printCreateCommandHelp() {
        System.out.println("Entering the create command will create a new meal");
        System.out.println("Sample input: create /mname Hokkien Mee /ing yellow noodle (1), thick " +
                "bee hoon (1), prawn (1.2), egg (0.5), pork lard (0.2), squid (1.5), lime (0.1)");
        System.out.println("Sample output:");
        System.out.println("               1. Chicken Rice ($3.00)");
        System.out.println("               2. Fish Ball Noodles ($3.00)");
        System.out.println("               3. Hokkien Mee ($5.50)");
    }

    public void printRemoveCommandHelp() {
        System.out.println("Entering the remove command will remove a meal in the user's meal list");
        System.out.println("Sample input: remove 1 ");
        System.out.println("Sample output:");
        System.out.println("               OK.  Chicken Rice ($3.00) have been removed from the meal list.");
    }

    public void printSelectCommandHelp() {
        System.out.println("Entering the select command will add the selected meal in the meal list");
        System.out.println("Sample input: select 1 ");
        System.out.println("Sample output:");
        System.out.println("               OK.  Chicken Rice ($3.00) have been added to the meal list.");
    }

    public void printFilterCommandHelp() {
        System.out.println("Entering the filter command will filter a meal in the main list");
        System.out.println("There are three filter option by cost, by ingredient or by meal name");
        System.out.println("Sample input: filter /mcost 5.50");
        System.out.println("Sample output:");
        System.out.println("               1. Chicken Rice ($5.50)");
        System.out.println("Sample input: filter /ing  Fish Ball");
        System.out.println("Sample output:");
        System.out.println("               1. Chicken Rice ($5.50)");
        System.out.println("Sample input: filter /manme Hokkien Mee");
        System.out.println("Sample output:");
        System.out.println("               1. Hokkien Mee ($5.50)");
    }

    public void printDeleteCommandHelp() {
        System.out.println("Entering the delete command will delete a meal in the main list");
        System.out.println("Sample input: delete 1 ");
        System.out.println("Sample output:");
        System.out.println("               OK.  Chicken Rice ($3.00) have been deleted from the main list.");
    }

    public void printViewCommandHelp() {
        System.out.println("Entering the view command will view a view all the ingredients of the selected meal");
        System.out.println("Sample input: view 1 ");
        System.out.println("Sample output:");
        System.out.println("               1. yellow noodle");
        System.out.println("               2. bee hoon");
        System.out.println("               3. prawn");
        System.out.println("               4. egg");
        System.out.println("               5. pork lard");
        System.out.println("               6. squid");
        System.out.println("               7. lime");
    }

    public void printClearCommandHelp() {
        System.out.println("Entering the clear command will clear all the meals in the meal list");
        System.out.println("Sample input: clear");
        System.out.println("Sample output:");
        System.out.println("               The meal list has been cleared.");
    }

    public void printHelpCommandHelp() {
        System.out.println("Entering the help command followed by the command that requires help " +
                "will give brief explanation of the command");
        System.out.println("Sample input: help bye");
        System.out.println("Sample output:");
        System.out.println("                Entering the bye command will gracefully exits the software");
        System.out.println("                    Sample input: bye");
        System.out.println("                    Sample output: Bye. Hope to see you again soon!");
    }
}
