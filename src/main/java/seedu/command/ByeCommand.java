package seedu.command;

import seedu.food.Meal;
import seedu.food.Ingredient;
import seedu.storage.Storage;
import seedu.logic.MealManager;
import seedu.ui.UserInterface;

import java.io.IOException;
import java.util.List;

public class ByeCommand extends Command {
    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(MealManager mealManager, UserInterface ui) {
        updateRecipesListFile(mealManager,ui);
        updateWishListFile(mealManager, ui);
        updateInventoryFile(mealManager, ui);
        ui.printGoodbye();
    }

    private void updateWishListFile(MealManager mealManager, UserInterface ui) {
        List<Meal> wishList = mealManager.getWishList().getList();
        String wishListFilePath = Storage.getWishListFilePath();
        clearAndUpdateFile(wishList, wishListFilePath, ui);
    }

    private void updateRecipesListFile(MealManager mealManager, UserInterface ui) {
        List<Meal> recipesList = mealManager.getRecipesList().getList();
        String recipesListFilePath = Storage.getRecipesListFilePath();
        clearAndUpdateFile(recipesList, recipesListFilePath, ui);
    }


    private void clearAndUpdateFile(List<Meal> mealList, String filePath, UserInterface ui) {
        Storage.clearFile(filePath, ui);
        writeMealsToFile(mealList, filePath, ui);
    }

    private static void writeMealsToFile(List<Meal> mealList, String filePath, UserInterface ui) {
        for (Meal newMeal : mealList) {
            try {
                Storage.writeToFile(newMeal.toDataString(), filePath);
            } catch (IOException ioException) {
                ui.printMessage("Error writing to file: " + ioException.getMessage());
            }
        }
    }

    private static void writeIngredientsToFile(List<Ingredient> ingredientList, String filePath, UserInterface ui) {
        for (Ingredient ingredient : ingredientList) {
            try {
                Storage.writeToFile(ingredient.toDataString(), filePath);
            } catch (IOException ioException) {
                ui.printMessage("Error writing to file: " + ioException.getMessage());
            }
        }
    }

    private void clearAndUpdateFileForIngredients(List<Ingredient> ingredientList, String filePath, UserInterface ui) {
        Storage.clearFile(filePath, ui);
        writeIngredientsToFile(ingredientList, filePath, ui);
    }

    private void updateInventoryFile(MealManager mealManager, UserInterface ui) {
        // Retrieve the list of ingredients from the inventory.
        List<Ingredient> ingredientList = mealManager.getInventory().getIngredients();
        // Get the file path for the inventory list.
        String inventoryFilePath = Storage.getInventoryListFilePath();
        // Clear the existing file and write the new list.
        clearAndUpdateFileForIngredients(ingredientList, inventoryFilePath,ui);
    }
}
