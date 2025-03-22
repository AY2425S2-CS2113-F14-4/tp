package seedu.meallist;

import seedu.exceptions.DuplicateMealException;
import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.RemoveIndexOutOfRangeException;
import seedu.food.Meal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Meals {
    protected final List<Meal> mealList = new ArrayList<>();

    public List<Meal> getList() {
        return mealList;
    }

    // Adds a new meal to the specified list after checking for duplicates
    public void addMeal(Meal newMeal) throws EZMealPlanException {
        checkDuplicateMeal(newMeal);
        mealList.add(newMeal);
        mealList.sort(Comparator.comparing(meal -> meal.getName().toLowerCase()));
    }

    // Checks whether the newMeal already exists in the given meal list
    public void checkDuplicateMeal(Meal newMeal) throws EZMealPlanException {
        String mealListName = this instanceof MainMeals ? "main meal list" : "user meal list";
        for (Meal meal : mealList) {
            if (meal.equals(newMeal)) {
                throw new DuplicateMealException(newMeal.getName(), mealListName);
            }
        }
    }

    /**
     * Removes the meal at a specified index and returns it.
     */
    public Meal removeMeal(int index) throws EZMealPlanException {
        try {
            return mealList.remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new RemoveIndexOutOfRangeException(index, mealList.size());
        }
    }

    /**
     * Returns the size of the meal list.
     */
    public int size() {
        return mealList.size();
    }

    /**
     * Returns the index of a specified meal object in the list.
     */
    public int getIndex(Meal meal) {
        return mealList.indexOf(meal);
    }

    public boolean contains(Meal meal) {
        return mealList.contains(meal);
    }
}
