package seedu.command;

import seedu.checkers.RecommendChecker;
import seedu.exceptions.EZMealPlanException;
import seedu.logic.MealManager;
import seedu.ui.UserInterface;
import seedu.meallist.MealList;
import seedu.food.Meal;
import seedu.food.Ingredient;
import seedu.food.Inventory;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RecommendCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String RECOMMEND = "recommend";
    private static final String ING = "/ing";

    /**
     * Constructs a RecommendCommand.
     * Expected user input format: "recommend /ing Chicken"
     */
    public RecommendCommand(String userInput) throws EZMealPlanException {
        validUserInput = userInput.trim();
    }

    @Override
    public void execute(MealManager mealManager, UserInterface ui) throws EZMealPlanException {
        // Validate input using RecommendChecker.
        RecommendChecker checker = new RecommendChecker(validUserInput);
        checker.check();
        if (!checker.isPassed()) {
            logger.severe("Invalid recommend command input detected.");
            return;
        }

        // Extract the ingredient keyword.
        // Expected input format: "recommend /ing Chicken"
        int afterRecommendIndex = validUserInput.toLowerCase().indexOf(RECOMMEND) + RECOMMEND.length();
        String args = validUserInput.substring(afterRecommendIndex).trim();
        int ingIndex = args.toLowerCase().indexOf(ING);
        if (ingIndex == -1) {
            logger.severe("Ingredient marker '/ing' not found in input.");
            ui.printMessage("Missing ingredient marker '/ing'.");
            return;
        }
        int afterIngIndex = ingIndex + ING.length();
        String extractedKeyword = args.substring(afterIngIndex).trim();
        if (extractedKeyword.isEmpty()) {
            logger.severe("No ingredient specified after '/ing'.");
            ui.printMessage("No ingredient specified.");
            return;
        }

        // Proceed with recommendation logic.
        Inventory inventory = mealManager.getInventory();

        // First, filter the user meal list (wishlist) by the ingredient keyword.
        MealList userMeals = mealManager.getWishList();
        List<Meal> candidateMeals = filterMealsByIngredient(userMeals.getList(), extractedKeyword);

        // If no matching meals in the user list, try the main meal list.
        if (candidateMeals.isEmpty()) {
            MealList mainMeals = mealManager.getRecipesList();
            candidateMeals = filterMealsByIngredient(mainMeals.getList(), extractedKeyword);
        }

        if (candidateMeals.isEmpty()) {
            ui.printMessage("No meal found containing ingredient: " + extractedKeyword);
            return;
        }

        // Randomly select a meal from the candidate meals.
        Random random = new Random();
        Meal selectedMeal = candidateMeals.get(random.nextInt(candidateMeals.size()));

        // Build the output message.
        StringBuilder sb = new StringBuilder();
        sb.append("Recommended Meal: ").append(selectedMeal.getName())
                .append(" (").append(selectedMeal).append(")")
                .append(System.lineSeparator());
        sb.append("Ingredients:").append(System.lineSeparator());

        List<Ingredient> mealIngredients = selectedMeal.getIngredientList();
        for (int i = 0; i < mealIngredients.size(); i++) {
            Ingredient ing = mealIngredients.get(i);
            sb.append("   ").append(i + 1).append(". ").append(ing.toString())
                    .append(System.lineSeparator());
        }

        // Determine which ingredients are missing from the inventory.
        List<String> missingIngredients = mealIngredients.stream()
                .filter(ing -> !inventory.hasIngredient(ing.getName().toLowerCase()))
                .map(Ingredient::getName)
                .collect(Collectors.toList());
        if (missingIngredients.isEmpty()) {
            sb.append("You have all the necessary ingredients for this meal.");
        } else {
            sb.append("Missing Ingredients: ").append(String.join(", ", missingIngredients));
        }

        ui.printMessage(sb.toString());
    }

    /**
     * Filters a list of meals by checking if any ingredient name contains the given keyword.
     */
    private List<Meal> filterMealsByIngredient(List<Meal> meals, String keyword) {
        return meals.stream()
                .filter(meal -> meal.getIngredientList().stream()
                        .anyMatch(ing -> ing.getName().toLowerCase().contains(keyword.toLowerCase())))
                .collect(Collectors.toList());
    }
}
