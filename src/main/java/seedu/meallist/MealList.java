package seedu.meallist;

import seedu.exceptions.DuplicateMealException;
import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.MealNotFoundException;
import seedu.exceptions.RemoveIndexOutOfRangeException;
import seedu.food.Meal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class MealList {
    protected final List<Meal> mealList = new ArrayList<>();
    protected String mealListName;

    public List<Meal> getList() {
        return mealList;
    }

    public String getMealListName() {
        return mealListName;
    }

    // Adds a new meal to the specified list after checking for duplicates
    public void addMeal(Meal newMeal) throws EZMealPlanException {
        checkDuplicateMeal(newMeal);
        mealList.add(newMeal);
        mealList.sort(Comparator.comparing(meal -> meal.getName().toLowerCase()));
    }

    /**
     * Checks whether newMeal already exists in the mealList.
     */
    private void checkDuplicateMeal(Meal newMeal) throws EZMealPlanException {
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
            return mealList.remove(index);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            int indexAdjustment = 1;
            throw new RemoveIndexOutOfRangeException(index + indexAdjustment, mealList.size());
        }
    }

    /**
     * Returns the size of the meal list.
     */
    public int size() {
        return mealList.size();
    }

    /**
     * Returns the index of a specified meal object in the list, starting from index 0.
     *
     * @throws MealNotFoundException if the specified meal cannot be found.
     */
    public int getIndex(Meal meal) throws MealNotFoundException {
        int index = mealList.indexOf(meal);
        if (index == -1) {
            throw new MealNotFoundException(meal);
        } else {
            return index;
        }
    }

    public boolean contains(Meal meal) {
        return mealList.contains(meal);
    }
}
