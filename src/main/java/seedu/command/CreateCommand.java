package seedu.command;

import seedu.checkers.CreateChecker;
import seedu.exceptions.EZMealPlanException;
import seedu.food.Ingredient;
import seedu.food.Meal;
import seedu.food.Product;
import seedu.logic.MealManager;
import seedu.meallist.MealList;
import seedu.ui.UserInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;

public class CreateCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public CreateCommand(String userInputText) {
        validUserInput = userInputText.trim();
        lowerCaseInput = userInputText.toLowerCase();
    }

    @Override
    public void execute(MealManager mealManager, UserInterface ui) throws EZMealPlanException {
        boolean isValidUserInput = checkValidUserInput();
        if (!isValidUserInput) {
            logger.severe("Huge issue detected! The user input format remains invalid despite " +
                    "passing all the checks for input formatting error.");
        }
        assert isValidUserInput;

        Meal newMeal = createNewMeal();
        MealList recipesList = mealManager.getRecipesList();
        mealManager.addMeal(newMeal, recipesList);
        ui.printAddMealMessage(newMeal, recipesList);
    }

    private Meal createNewMeal() throws EZMealPlanException {
        String mname = "/mname";
        String ing = "/ing";
        int afterMnameIndex = lowerCaseInput.indexOf(mname) + mname.length();
        int ingIndex = lowerCaseInput.indexOf(ing);
        String mealName = validUserInput.substring(afterMnameIndex, ingIndex).trim();
        logger.fine("The user is now creating a new meal: " + mealName + ".");
        Meal newMeal = new Meal(mealName);
        addAllIngredients(ing, newMeal);
        return newMeal;
    }

    private String[] extractIngredients(String ing) {
        ing = ing.toLowerCase();
        int afterIngIndex = lowerCaseInput.indexOf(ing) + ing.length();
        String ingredients = validUserInput.substring(afterIngIndex).trim();
        String splitRegex = "\\s*,\\s*";
        return ingredients.split(splitRegex);
    }

    private void addAllIngredients(String ing, Meal newMeal) throws EZMealPlanException {
        String[] ingredientArray = extractIngredients(ing);
        for (String ingredientString : ingredientArray) {
            String[] ingredientNamePrice = getNamePrice(ingredientString);
            int nameIndex = 0;
            int priceIndex = 1;
            String ingredientName = ingredientNamePrice[nameIndex];
            String ingredientPrice = ingredientNamePrice[priceIndex];
            Ingredient newIngredient = new Ingredient(ingredientName, ingredientPrice);
            newMeal.addIngredient(newIngredient);
        }
        ArrayList<Ingredient> mealIngredients = (ArrayList<Ingredient>) newMeal.getIngredientList();
        mealIngredients.sort(Comparator.comparing(Product::getName));
    }

    private static String[] getNamePrice(String ingredient) {
        String openBracket = "(";
        String closeBracket = ")";
        int zeroIndex = 0;
        int openBracketIndex = ingredient.indexOf(openBracket);
        int closeBracketIndex = ingredient.indexOf(closeBracket);
        int afterOpenBracketIndex = ingredient.indexOf(openBracket) + openBracket.length();
        String ingredientName = ingredient.substring(zeroIndex, openBracketIndex).trim();
        String ingredientPrice = ingredient.substring(afterOpenBracketIndex, closeBracketIndex).trim();
        return new String[]{ingredientName, ingredientPrice};
    }

    private boolean checkValidUserInput() throws EZMealPlanException {
        CreateChecker checker = new CreateChecker(validUserInput);
        checker.check();
        return checker.isPassed();
    }
}
