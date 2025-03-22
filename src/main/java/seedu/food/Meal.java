package seedu.food;

import seedu.exceptions.InvalidPriceException;

import java.util.ArrayList;
import java.util.List;

public class Meal extends Product {
    private final List<Ingredient> ingredientList = new ArrayList<>();

    public Meal(String mealName) throws InvalidPriceException {
        double tempMealPrice = 1; // buffer value for the meal price
        setName(mealName);
        setPrice(tempMealPrice);
    }

    public void setPrice(double mealPrice) throws InvalidPriceException {
        super.setPrice(mealPrice);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    @Override
    public boolean equals(Object otherMeal) {
        if (otherMeal instanceof Meal other) {
            return this.getName().equalsIgnoreCase(other.getName()) &&
                    this.ingredientList.equals(other.getIngredientList());
        }
        return false;
    }

    // If searching for an ingredient is a behavior of a single meal,
    // you can include a method to return matching ingredients.
    public List<Ingredient> findIngredient(String ingredientName) {
        String lcIngredientName = ingredientName.toLowerCase();
        List<Ingredient> matchingIngredList = new ArrayList<>();
        for (Ingredient ingredient : ingredientList) {
            if (ingredient.getName().toLowerCase().contains(lcIngredientName)) {
                matchingIngredList.add(ingredient);
            }
        }
        return matchingIngredList;
    }

    public String toDataString() {
        StringBuilder stringBuilder = new StringBuilder();
        // Append the meal name.
        stringBuilder.append(getName());
        // Append each ingredient in the required format: " | ingredientName (price)"
        for (Ingredient ingredient : ingredientList) {
            stringBuilder.append(" | ");
            stringBuilder.append(ingredient.getName());
            stringBuilder.append(" (");
            stringBuilder.append(String.format("%.2f", ingredient.getPrice()));
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }
}
