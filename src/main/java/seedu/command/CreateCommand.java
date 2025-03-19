package seedu.command;

import seedu.checkers.CreateChecker;
import seedu.exceptions.DuplicateIngredientException;
import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.IngredientPriceFormatException;
import seedu.exceptions.InvalidPriceException;
import seedu.food.Ingredient;
import seedu.food.Meal;
import seedu.food.Product;
import seedu.logic.MealManager;
import seedu.ui.UserInterface;
import seedu.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;


public class CreateCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public CreateCommand(String userInputText) {
        setValidUserInput(userInputText);
    }

    @Override
    public void execute(MealManager mealManager, UserInterface ui) throws EZMealPlanException {
        boolean isValidUserInput = checkValidUserInput(ui);
        if (!isValidUserInput) {
            logger.severe("Huge issue detected! The user input format remains invalid despite " +
                    "passing all the checks for input formatting error.");
        }
        assert isValidUserInput;
        Meal newMeal = createNewMeal();
        List<Meal> mainMealList = mealManager.getMainMealList();
        mealManager.addMeal(newMeal, mainMealList);
        try {
            Storage.writeMainList(newMeal.toDataString());
        } catch (IOException e) {
            UserInterface.printMessage("Error writing to file: " + e.getMessage());
        }
        String mealListName = "main meal list";
        ui.printAddMealMessage(newMeal, mainMealList, mealListName);
    }

    private Meal createNewMeal() throws EZMealPlanException {
        String mname = "/mname";
        String ing = "/ing";
        int afterMnameIndex = validUserInput.indexOf(mname) + mname.length();
        int ingIndex = validUserInput.indexOf(ing);
        String mealName = validUserInput.substring(afterMnameIndex, ingIndex).trim();
        logger.fine("The user is now creating a new meal: " + mealName + ".");
        Meal newMeal = new Meal(mealName);
        addAllIngredients(ing, newMeal);
        double mealPrice = computeMealPrice(newMeal);
        newMeal.setPrice(mealPrice);
        return newMeal;
    }

    private String[] extractIngredients(String ing) {
        int afterIngIndex = validUserInput.indexOf(ing) + ing.length();
        String ingredients = validUserInput.substring(afterIngIndex).trim();
        String splitRegex = "\\s*,\\s*";
        return ingredients.split(splitRegex);
    }

    private void addAllIngredients(String ing, Meal newMeal) throws EZMealPlanException {
        String[] ingredientArray = extractIngredients(ing);
        for (String ingredient : ingredientArray) {
            String[] ingredientNamePrice = getNamePrice(ingredient);
            int nameIndex = 0;
            int priceIndex = 1;
            String ingredientName = ingredientNamePrice[nameIndex];
            String ingredientPrice = ingredientNamePrice[priceIndex];
            addIngredient(ingredientName, ingredientPrice, newMeal);
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

    private boolean checkValidUserInput(UserInterface ui) throws EZMealPlanException {
        CreateChecker checker = new CreateChecker(validUserInput);
        checker.check();
        return checker.isPassed();
    }

    public double computeMealPrice(Meal meal) {
        ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) meal.getIngredientList();
        double totalPrice = 0;
        for (Ingredient ingredient : ingredientList) {
            totalPrice += ingredient.getPrice();
        }
        return totalPrice;
    }

    public void addIngredient(String ingredientName, String ingredientPrice, Meal meal)
            throws InvalidPriceException, IngredientPriceFormatException, DuplicateIngredientException {
        double ingredientPriceDouble = checkValidIngPrice(ingredientName, ingredientPrice);
        Ingredient newIngredient = new Ingredient(ingredientName, ingredientPriceDouble);
        checkDuplicateIngredients(newIngredient, meal);
        ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) meal.getIngredientList();
        ingredientList.add(newIngredient);
    }

    private void checkDuplicateIngredients(Ingredient newIngredient, Meal meal) throws DuplicateIngredientException {
        String mealName = meal.getName();
        ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) meal.getIngredientList();
        for (Ingredient ingredient : ingredientList) {
            if (newIngredient.equals(ingredient)) {
                String ingredientName = newIngredient.getName();
                String message = "Triggers DuplicateIngredientException()!";
                logger.warning(message);
                throw new DuplicateIngredientException(ingredientName, mealName);
            }
        }
    }

    private static double checkValidIngPrice(String ingredientName, String ingredientPrice)
            throws IngredientPriceFormatException {
        try {
            double hundred = 100.0;
            double ingredientPriceDouble = Double.parseDouble(ingredientPrice);
            return Math.round(ingredientPriceDouble * hundred) / hundred;
        } catch (NumberFormatException numberFormatException) {
            String message = "Triggers IngredientPriceFormatException()!";
            logger.warning(message);
            throw new IngredientPriceFormatException(ingredientName);
        }
    }
}
