package seedu.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.exceptions.EZMealPlanException;
import seedu.food.Ingredient;
import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.ui.UserInterface;

public class ViewCommandTest {

    /**
     * Define a UserInterface class for testing, to capture the params of printMealList.
     */
    public static class TestUserInterface extends UserInterface {
        Meal capturedMeal;
        List <Ingredient> capturedIngredients;

        @Override
        public void printIngredientList(Meal meal) {
            this.capturedMeal = meal;
            this.capturedIngredients = meal.getIngredientList();
        }
    }

    @Test
    public void testExecute_viewCommand_printsMeal() throws EZMealPlanException {

        MealManager mealManager = new MealManager();
        Meal meal1 = new Meal("Main Meal 1");
        Ingredient ingredient1 = new Ingredient("egg", 0.50);
        Ingredient ingredient2 = new Ingredient("rice", 1.0);
        meal1.addIngredient(ingredient1);
        meal1.addIngredient(ingredient2);

        mealManager.getMainMeals().getList().add(meal1);

        ViewCommandTest.TestUserInterface testUI = new ViewCommandTest.TestUserInterface();
        ViewCommand ViewCommand = new ViewCommand("/m 1");
        ViewCommand.execute(mealManager, testUI);

        assertEquals("Main Meal 1 ($1.50)", testUI.capturedMeal.toString());
        List<Ingredient> expectedIngredients = new ArrayList<>();
        expectedIngredients.add(ingredient1);
        expectedIngredients.add(ingredient2);
        assertIterableEquals(expectedIngredients, testUI.capturedIngredients);
    }
}


