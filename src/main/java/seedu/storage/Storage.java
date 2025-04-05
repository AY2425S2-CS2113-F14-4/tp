package seedu.storage;

import seedu.exceptions.EZMealPlanException;
import seedu.food.Ingredient;
import seedu.food.Inventory;
import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.presetmeals.PresetMeals;
import seedu.ui.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    static File wishListFile;
    static File recipesListFile;
    static File inventoryListFile;
    private static final String WISH_LIST_FILE_PATH = "data/wishList.txt";
    private static final String RECIPES_LIST_FILE_PATH = "data/recipesList.txt";
    private static final String INVENTORY_LIST_FILE_PATH = "data/inventoryList.txt";

    public static File getWishListFile() {
        return wishListFile;
    }

    public static File getRecipesListFile() {
        return recipesListFile;
    }

    public static File getInventoryListFile() {
        return inventoryListFile;
    }

    public static String getWishListFilePath() {
        return WISH_LIST_FILE_PATH;
    }

    public static String getRecipesListFilePath() {
        return RECIPES_LIST_FILE_PATH;
    }

    public static void createListFiles() throws IOException {
        wishListFile = new File(WISH_LIST_FILE_PATH);
        recipesListFile = new File(RECIPES_LIST_FILE_PATH);
        inventoryListFile = new File(INVENTORY_LIST_FILE_PATH);
        createListFile(recipesListFile);
        createListFile(wishListFile);
        createListFile(inventoryListFile);
    }

    public static void createListFile(File listFile) throws IOException {
        // Ensure the parent directories exist (if there are any)
        if (listFile.getParentFile() != null) {
            listFile.getParentFile().mkdirs();
        }
        // Create the file if it doesn't already exist
        if (!listFile.exists()) {
            listFile.createNewFile();
        }
    }

    public static void loadExistingInventory(MealManager mealManager) throws FileNotFoundException {
        Inventory ingredients = mealManager.getInventory();
        if (inventoryListFile.exists()) {
            Scanner scanner = new Scanner(inventoryListFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                checkValidIngredients(line, ingredients);
            }
            scanner.close();
        }
    }

    public static List<Meal> loadExistingList(File selectedFile) throws IOException {
        List<Meal> meals = new ArrayList<>();
        if (selectedFile.exists()) {
            Scanner scanner = new Scanner(selectedFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                // Split the line by "|" with optional spaces around it.
                String[] parts = line.split("\\s*\\|\\s*");
                int minLengthToHaveIng = 2;
                if (parts.length < minLengthToHaveIng) {
                    continue; // Skip lines that don't have ingredients.
                }
                checkValidMeal(parts, meals);
            }
            scanner.close();
        }
        return meals;
    }

    public static List<Meal> loadPresetMeals() {
        String[] allInitialisedMeals = PresetMeals.createPresetMeals();
        List<Meal> meals = new ArrayList<>();
        for (String eachInitialisedMeal : allInitialisedMeals) {
            String[] parts = eachInitialisedMeal.split("\\s*\\|\\s*");
            int minLengthToHaveIng = 2;
            if (parts.length < minLengthToHaveIng) {
                continue; // Skip initialised meal that don't have ingredients if there is any.
            }
            checkValidMeal(parts, meals);
        }
        return meals;
    }

    private static void checkValidMeal(String[] parts, List<Meal> meals) {
        //Throw error message if detected an ingredient with invalid price and skips to the next meal.
        try {
            checkMealsBeforeAdd(parts, meals);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    private static void checkMealsBeforeAdd(String[] parts, List<Meal> meals)
            throws Exception {
        // The first part is the meal name.
        int mealNameIndex = 0;
        String mealName = parts[mealNameIndex];
        Meal meal = addIngredientsToMeal(mealName, parts);
        // Optionally, compute the meal's total price as the sum of ingredient prices.
        meals.add(meal);
    }

    private static Meal addIngredientsToMeal(String mealName, String[] parts)
            throws Exception {
        Meal meal = new Meal(mealName);
        // For each remaining part, extract ingredient name and its actual price.
        for (int i = 1; i < parts.length; i++) {
            Ingredient ingredient = getIngredient(parts[i]);
            meal.addIngredient(ingredient);
        }
        return meal;
    }

    private static Ingredient getIngredient(String parts) throws Exception {
        String ingredientStr = parts.trim();
        int openBracketIndex = ingredientStr.indexOf("(");
        int closeBracketIndex = ingredientStr.indexOf(")");
        int notFoundIndex = -1;
        if (openBracketIndex == notFoundIndex || closeBracketIndex == notFoundIndex) {
            // If no valid price is found, you could either skip or use a default value.
            throw new IllegalArgumentException("Invalid ingredient format: " + ingredientStr);
        }
        int startIndex = 0;
        int indexAdjustment = 1;
        int afterOpenBracketIndex = openBracketIndex + indexAdjustment;
        String ingredientName = ingredientStr.substring(startIndex, openBracketIndex).trim();
        String priceStr = ingredientStr.substring(afterOpenBracketIndex, closeBracketIndex).trim();
        return new Ingredient(ingredientName, priceStr);
    }

    public static void writeToFile(String input, String filePath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.append(input).append(System.lineSeparator());
        }
    }

    public static void clearFile(String filePath, UserInterface ui) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
        } catch (IOException ioException) {
            ui.printMessage(ioException.getMessage());
        }
    }

    public static String getInventoryListFilePath() {
        return INVENTORY_LIST_FILE_PATH;
    }


    private static void checkValidIngredients(String line, Inventory ingredients) {
        try {
            Ingredient newIngredient = Ingredient.fromData(line);
            ingredients.addIngredient(newIngredient);
        } catch (EZMealPlanException ezMealPlanException) {
            System.err.println(ezMealPlanException.getMessage());
        }
    }
}
