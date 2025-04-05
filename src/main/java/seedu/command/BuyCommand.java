package seedu.command;

import seedu.checkers.BuyChecker;
import seedu.exceptions.EZMealPlanException;
import seedu.food.Inventory;
import seedu.food.Ingredient;
import seedu.logic.MealManager;
import seedu.ui.UserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BuyCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String BUY = "buy";
    private final List<Ingredient> ingredients = new ArrayList<>();

    public BuyCommand(String userInput) {
        validUserInput = userInput.trim();
        this.lowerCaseInput = userInput.toLowerCase();
    }

    /**
     * Executes the buy command by validating the input, parsing the ingredients, and
     * adding each specified ingredient into the inventory.
     *
     * @param mealManager the MealManager providing access to the inventory.
     * @param ui          the UserInterface for printing messages.
     * @throws EZMealPlanException if the input format is invalid.
     */
    @Override
    public void execute(MealManager mealManager, UserInterface ui) throws EZMealPlanException {
        if (!checkValidUserInput()) {
            logger.severe("Invalid buy command input detected.");
            return;
        }

        parseIngredientsForBuy();

        Inventory inventory = mealManager.getInventory();
        for (Ingredient ingredient : ingredients) {
            // Add the ingredient (with name and price) into the inventory list.
            inventory.addIngredient(ingredient);
            ui.printBought(ingredient);
        }
        ingredients.clear();
    }

    /**
     * Validates the user input using the BuyChecker.
     *
     * @return true if input is valid.
     * @throws EZMealPlanException if any validation error occurs.
     */
    private boolean checkValidUserInput() throws EZMealPlanException {
        BuyChecker checker = new BuyChecker(validUserInput);
        checker.check();
        return checker.isPassed();
    }

    /**
     * Parses the ingredients from the user input after the "buy" keyword.
     */
    private void parseIngredientsForBuy() {
        int afterBuyIndex = validUserInput.indexOf(BUY) + BUY.length();
        String args = validUserInput.substring(afterBuyIndex).trim();
        if (!args.isEmpty()) {
            parseIngredients(args);
        }
    }

    /**
     * Splits the input string to extract individual ingredient tokens.
     *
     * @param args the argument string containing ingredient information.
     */
    private void parseIngredients(String args) {
        final String ingKeyword = "/ing";
        int ingIndex = args.indexOf(ingKeyword);
        if (ingIndex != -1) {
            String ingredientsStr = args.substring(ingIndex + ingKeyword.length()).trim();
            String[] tokens = ingredientsStr.split(",");
            for (String token : tokens) {
                token = token.trim();
                processIngredientToken(token);
            }
        }
    }

    /**
     * Processes a single ingredient token.
     * Expected token format: "IngredientName (Price)"
     *
     * @param token the token to process.
     */
    private void processIngredientToken(String token) {
        if (!token.isEmpty()) {
            int openParenIndex = token.lastIndexOf('(');
            int closeParenIndex = token.lastIndexOf(')');
            String name = token.substring(0, openParenIndex).trim();
            String priceStr = token.substring(openParenIndex + 1, closeParenIndex).trim();
            addParsedIngredient(name, priceStr);
        }
    }

    /**
     * Converts the price string to a double and creates an Ingredient.
     *
     * @param name the ingredient name.
     * @param priceStr the price string.
     */
    private void addParsedIngredient(String name, String priceStr) {
        try {

            Ingredient ingredient = new Ingredient(name, priceStr);
            ingredients.add(ingredient);
        } catch (NumberFormatException | EZMealPlanException exception) {
            System.out.println(exception.getMessage());
            logger.severe("Unexpected error while parsing ingredient: " + name);
        }
    }
}
