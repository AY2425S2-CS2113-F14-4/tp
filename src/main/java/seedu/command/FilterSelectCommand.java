package seedu.command;

import seedu.checkers.FilterChecker;
import seedu.checkers.FilterSelectChecker;
import seedu.checkers.SelectChecker;
import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.InvalidFilterMethodException;
import seedu.exceptions.InvalidMcostException;

import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.ui.UserInterface;

import java.util.List;

public abstract class FilterSelectCommand extends Command {
    String filterOrSelect;
    String ing = "/ing";
    String mname = "/mname";
    String mcost = "/mcost";
    String filterMethod = "";
    final String byIng = "byIng";
    final String byMname = "byMname";
    final String byMcost = "byMcost";


    protected boolean checkValidUserInput(String filterOrSelect) throws EZMealPlanException {
        getFilterMethod();
        FilterSelectChecker checker = getFilterSelectChecker(filterOrSelect, filterMethod);
        checker.check();
        return checker.isPassed();
    }

    private FilterSelectChecker getFilterSelectChecker(String filterOrSelect, String filterMethod) {
        String filter = "filter";
        return filterOrSelect.equals(filter) ? new FilterChecker(validUserInput, filterMethod) :
                new SelectChecker(validUserInput, filterMethod);
    }

    private void getFilterMethod() throws EZMealPlanException {
        boolean isContainIng = this.lowerCaseInput.contains(ing);
        boolean isContainMname = this.lowerCaseInput.contains(mname);
        boolean isContainMcost = this.lowerCaseInput.contains(mcost);
        if (isContainIng && !isContainMname && !isContainMcost) {
            filterMethod = byIng;
            return;
        } else if (!isContainIng && isContainMname && !isContainMcost) {
            filterMethod = byMname;
            return;
        } else if (!isContainIng && !isContainMname && isContainMcost) {
            filterMethod = byMcost;
            return;
        }
        String select = "select";
        boolean isSelect = filterOrSelect.equals(select);
        if (isSelect && !(isContainMcost && isContainIng && isContainMname)) {
            return;
        }
        throw new InvalidFilterMethodException();
    }

    protected List<Meal> getFilteredMealList(MealManager mealManager)
            throws EZMealPlanException {
        return switch (filterMethod) {
        case byIng -> filterByIngList(mealManager);
        case byMname -> filterByMnameList(mealManager);
        case byMcost -> filterByMcostList(mealManager);
        default -> mealManager.getMainMeals().getList();
        };
    }

    private List<Meal> filterByMcostList(MealManager mealManager) throws EZMealPlanException {
        int afterMcostIndex = this.lowerCaseInput.indexOf(mcost) + mcost.length();
        String mcostInput = this.validUserInput.substring(afterMcostIndex).trim();
        double mcostDouble = checkValidMcostPrice(mcostInput);
        return mealManager.filteringByMcost(mcostDouble);
    }

    private double checkValidMcostPrice(String mcostInput) throws EZMealPlanException {
        double mcostDouble;
        try {
            mcostDouble = Double.parseDouble(mcostInput);
            checkMcostPositive(mcostDouble);
        } catch (NumberFormatException numberFormatException) {
            throw new InvalidMcostException();
        }
        return mcostDouble;
    }

    private static void checkMcostPositive(double mcostDouble) {
        int zero = 0;
        if (mcostDouble <= zero) {
            throw new NumberFormatException();
        }
    }

    private List<Meal> filterByMnameList(MealManager mealManager) {
        int afterMnameIndex = this.lowerCaseInput.indexOf(mname) + mname.length();
        String mnameInput = this.validUserInput.substring(afterMnameIndex).trim();
        String splitRegex = "\\s*,\\s*";
        String[] mealNameArray = mnameInput.split(splitRegex);
        return mealManager.filteringByMname(mealNameArray);
    }

    private List<Meal> filterByIngList(MealManager mealManager) {
        int afterIngIndex = this.lowerCaseInput.indexOf(ing) + ing.length();
        String ingInput = this.validUserInput.substring(afterIngIndex).trim();
        String splitRegex = "\\s*,\\s*";
        String[] ingredientsArray = ingInput.split(splitRegex);
        return mealManager.filteringByIng(ingredientsArray);
    }

    public void printFilteredMealList(List<Meal> filteredMealList, UserInterface ui) {
        String mealCost = "meal cost";
        String mealName = "meal name";
        String ingredients = "ingredient(s)";
        if (filteredMealList.isEmpty()) {
            System.out.println("The filtered meal list is empty.");
            return;
        }
        String filterMessage = "the meal list filtered by ";
        String inputMessage = getString(mealCost, ingredients, mealName);
        String message = filterMessage + inputMessage;
        ui.printMealList(filteredMealList, message);
    }

    protected String getString(String mealCost, String ingredients, String mealName) {
        String inputMessage = "";
        switch (filterMethod) {
        case byMcost:
            inputMessage = mealCost;
            break;
        case byIng:
            inputMessage = ingredients;
            break;
        case byMname:
            inputMessage = mealName;
            break;
        default:
            break;
        }
        return inputMessage;
    }
}
