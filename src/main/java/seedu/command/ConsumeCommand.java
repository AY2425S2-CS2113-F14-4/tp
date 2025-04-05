package seedu.command;

import seedu.checkers.ConsumeChecker;
import seedu.exceptions.EZMealPlanException;
import seedu.logic.MealManager;
import seedu.ui.UserInterface;
import seedu.food.Inventory;


import java.util.ArrayList;

import java.util.List;
import java.util.logging.Logger;

public class ConsumeCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String CONSUME = "consume";
    private static final List<String> ingredients = new ArrayList<>();


    public ConsumeCommand(String userInput) {
        validUserInput = userInput.trim();
    }

    /**
     * Executes the consume command by removing each specified ingredient from the inventory.
     *
     * @param mealManager the MealManager that provides access to the inventory.
     * @param ui          the UserInterface for printing messages.
     */
    @Override
    public void execute(MealManager mealManager, UserInterface ui) throws EZMealPlanException {

        if (!checkValidUserInput()) {
            logger.severe("Invalid consume command input detected.");
            return;
        }

        int afterConsumeIndex = validUserInput.indexOf(CONSUME) + CONSUME.length();
        String args = validUserInput.substring(afterConsumeIndex).trim();
        parseIngredients(args);
        // Retrieve the inventory from the MealManager.
        Inventory inventory = mealManager.getInventory();
        // Process each ingredient name provided in the command.
        for (String ingredientName : ingredients) {
            if (inventory.removeIngredient(ingredientName)) {
                ui.printConsumed(ingredientName);
            } else {
                ui.printIngredientNotFound(ingredientName);
            }
        }
        ingredients.clear();
    }

    static void parseIngredients(String args) {
        if (args.isEmpty()) {
            return;
        }
        // Split using "/ing" as the delimiter.
        String ing = "/ing";
        int afterIngIndex = args.indexOf(ing) + ing.length();
        String argsAfterIng = args.substring(afterIngIndex).trim();
        String[] tokens = argsAfterIng.split(",");
        for (String token : tokens) {
            token = token.trim();
            if (!token.isEmpty()) {
                ingredients.add(token);
            }
        }
    }

    private boolean checkValidUserInput() throws EZMealPlanException {
        ConsumeChecker checker = new ConsumeChecker(validUserInput);
        checker.check();
        return checker.isPassed();
    }
}
