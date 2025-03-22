package seedu.storage;

import seedu.exceptions.InvalidPriceException;
import seedu.food.Ingredient;
import seedu.food.Meal;
import seedu.presetmeals.PresetMeals;
import seedu.ui.UserInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    static File userListFile;
    static File mainListFile;
    private static final String USER_LIST_FILE_PATH = "data/userList.txt";
    private static final String MAIN_LIST_FILE_PATH = "data/mainList.txt";

    public static File getUserListFile() {
        return userListFile;
    }

    public static File getMainListFile() {
        return mainListFile;
    }

    public static String getUserListFilePath() {
        return USER_LIST_FILE_PATH;
    }

    public static String getMainListFilePath() {
        return MAIN_LIST_FILE_PATH;
    }

    public static void createListFiles() throws IOException {
        userListFile = new File(USER_LIST_FILE_PATH);
        mainListFile = new File(MAIN_LIST_FILE_PATH);
        createListFile(userListFile);
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
        PresetMeals presetMeals = new PresetMeals();
        String[] allInitialisedMeals = presetMeals.getPresetMeals();
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
        } catch (InvalidPriceException invalidPriceException) {
            System.err.println(invalidPriceException.getMessage());
        }
    }

    private static void checkMealsBeforeAdd(String[] parts, List<Meal> meals) throws InvalidPriceException {
        // The first part is the meal name.
        int mealNameIndex = 0;
        String mealName = parts[mealNameIndex];
        Meal meal = addIngredientsToMeal(mealName, parts);
        // Optionally, compute the meal's total price as the sum of ingredient prices.
        setMealPrice(meal);
        meals.add(meal);
    }

    private static void setMealPrice(Meal meal) throws InvalidPriceException {
        double mealPrice = 0;
        for (Ingredient ing : meal.getIngredientList()) {
            mealPrice += ing.getPrice();
        }
        meal.setPrice(mealPrice);
    }

    private static Meal addIngredientsToMeal(String mealName, String[] parts) throws InvalidPriceException {
        Meal meal = new Meal(mealName);
        // For each remaining part, extract ingredient name and its actual price.
        for (int i = 1; i < parts.length; i++) {
            Ingredient ingredient = getIngredient(parts[i]);
            meal.getIngredientList().add(ingredient);
        }
        return meal;
    }

    private static Ingredient getIngredient(String parts) throws InvalidPriceException {
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
        double ingredientPrice = Double.parseDouble(priceStr);
        return new Ingredient(ingredientName, ingredientPrice);
    }

    public static void writeToFile(String input, String filePath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.append(input).append(System.lineSeparator());
        }
    }

    public static void clearFile(String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
        } catch (IOException ioException) {
            UserInterface.printMessage(ioException.getMessage());
        }
    }

}
