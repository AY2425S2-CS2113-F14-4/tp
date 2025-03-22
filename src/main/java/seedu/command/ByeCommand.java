package seedu.command;

import seedu.food.Meal;
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
        List<Meal> mainMealList = mealManager.getMainMeals().getList();
        List<Meal> userMealList = mealManager.getUserMeals().getList();
        clearAndUpdateFile(mainMealList, mealManager);
        clearAndUpdateFile(userMealList, mealManager);
        ui.printGoodbye();
    }


    private void clearAndUpdateFile(List<Meal> mealList, MealManager mealManager) {
        List<Meal> mainMealList = mealManager.getMainMeals().getList();
        String filePath = mainMealList.equals(mealList) ? Storage.getMainListFilePath() : Storage.getUserListFilePath();
        Storage.clearFile(filePath);
        writeMealsToFile(mealList, filePath);
    }

    private static void writeMealsToFile(List<Meal> mealList, String filePath) {
        for (Meal newMeal : mealList) {
            try {
                Storage.writeToFile(newMeal.toDataString(), filePath);
            } catch (IOException ioException) {
                UserInterface.printMessage("Error writing to file: " + ioException.getMessage());
            }
        }
    }
}
