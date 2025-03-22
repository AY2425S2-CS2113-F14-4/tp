package seedu.logic;

import seedu.exceptions.EZMealPlanException;
import seedu.food.Ingredient;
import seedu.food.Meal;
import seedu.meallist.MainMeals;
import seedu.meallist.Meals;
import seedu.meallist.UserMeals;

import java.util.ArrayList;
import java.util.List;

public class MealManager {
    Meals chosenMeals;
    private final Meals userMeals = new UserMeals();
    private final Meals mainMeals = new MainMeals();


    public Meals getUserMeals() {
        return userMeals;
    }

    public Meals getMainMeals() {
        return mainMeals;
    }


    // Adds a new meal to the specified list after checking for duplicates
    public void addMeal(Meal newMeal, Meals mealsInput) throws EZMealPlanException {
        chosenMeals = mealsInput instanceof MainMeals ? getMainMeals() : getUserMeals();
        chosenMeals.checkDuplicateMeal(newMeal);
        chosenMeals.addMeal(newMeal);
    }


    public List<Meal> filteringByMcost(double mcostDouble) {
        List<Meal> filteredMealList = new ArrayList<>();
        List<Meal> mainMealList = getMainMeals().getList();
        for (Meal meal : mainMealList) {
            if (meal.getPrice() == mcostDouble) {
                filteredMealList.add(meal);
            }
        }
        return filteredMealList;
    }

    public List<Meal> filteringByMname(String[] mealNameArray) {
        List<Meal> filteredMealList = new ArrayList<>();
        List<Meal> mainMealList = getMainMeals().getList();
        for (Meal meal : mainMealList) {
            boolean isMatchingMname = checkMname(mealNameArray, meal);
            if (isMatchingMname) {
                filteredMealList.add(meal);
            }
        }
        return filteredMealList;
    }

    private static boolean checkMname(String[] mealNameArray, Meal meal) {
        boolean isMealNameContains = true;
        String getMealName = meal.getName().toLowerCase();
        for (String mealName : mealNameArray) {
            mealName = mealName.toLowerCase();
            if (!getMealName.contains(mealName)) {
                isMealNameContains = false;
                break;
            }
        }
        return isMealNameContains;
    }

    public List<Meal> filteringByIng(String[] ingredientsArray) {
        List<Meal> filteredMealList = new ArrayList<>();
        List<Meal> mainMealList = getMainMeals().getList();
        for (Meal meal : mainMealList) {
            boolean isMealContainsIng = checkIngPerMeal(ingredientsArray, meal);
            if (isMealContainsIng) {
                filteredMealList.add(meal);
            }
        }
        return filteredMealList;
    }

    private static boolean checkIngPerMeal(String[] ingArray, Meal meal) {
        boolean isMealContainsIng = true;
        ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) meal.getIngredientList();
        ArrayList<String> ingredientsNameList = new ArrayList<>();
        ingredientList.forEach(ingredient -> ingredientsNameList.add(ingredient.getName()));
        int overallCount = getOverallCount(ingArray, ingredientsNameList);
        if (overallCount < ingArray.length) {
            isMealContainsIng = false;
        }
        return isMealContainsIng;
    }

    private static int getOverallCount(String[] ingArray, ArrayList<String> ingredientsNameList) {
        int overallCount = 0;
        int zero = 0;
        for (String ingName : ingArray) {
            ingName = ingName.toLowerCase();
            int eachCount = getEachCount(ingName, ingredientsNameList);
            if (eachCount > zero) {
                overallCount++;
            }
        }
        return overallCount;
    }

    private static int getEachCount(String ingName, ArrayList<String> ingredientsNameList) {
        int eachCount = 0;
        for (String ingredientName : ingredientsNameList) {
            ingredientName = ingredientName.toLowerCase();
            if (ingredientName.contains(ingName)) {
                eachCount++;
            }
        }
        return eachCount;
    }

    public Meal removeMeal(int index, Meals mealsInput) throws EZMealPlanException {
        chosenMeals = mealsInput instanceof MainMeals ? getMainMeals() : getUserMeals();
        return chosenMeals.removeMeal(index);
    }

    public void compareLists() {
        List<Meal> mainMealList = mainMeals.getList();
        List<Meal> userMealList = userMeals.getList();
        for (Meal meal : userMealList) {
            if (!mainMealList.contains(meal)) {
                try {
                    addMeal(meal, mainMeals);
                } catch (EZMealPlanException ezMealPlanException) {
                    ezMealPlanException.getMessage();
                }
            }
        }
    }
}
