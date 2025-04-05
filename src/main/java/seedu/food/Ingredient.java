package seedu.food;

import seedu.exceptions.IngredientPriceFormatException;
import seedu.exceptions.InvalidPriceException;

import java.util.logging.Logger;

public class Ingredient extends Product {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Ingredient(String ingredientName, String ingredientPriceString)
            throws InvalidPriceException, IngredientPriceFormatException {
        setName(ingredientName);
        setPrice(checkValidIngPrice(ingredientPriceString));
    }

    public void setPrice(double price) throws InvalidPriceException {
        super.setPrice(price);
    }

    public String toString() {
        return super.toString();
    }

    /**
     * Overrides the equals method based on the following criteria: Two Ingredient objects are equal if and only if
     * they have the same name (ignoring case).
     */
    @Override
    public boolean equals(Object otherIngredient) {
        if (otherIngredient instanceof Ingredient) {
            String otherName = ((Ingredient) otherIngredient).getName();
            String thisName = this.getName();
            return thisName.equalsIgnoreCase(otherName);
        } else {
            return false;
        }
    }

    private double checkValidIngPrice(String ingredientPrice) throws IngredientPriceFormatException {
        checkTwoDecimalPlace(ingredientPrice);
        try {
            return Double.parseDouble(ingredientPrice);
        } catch (NumberFormatException numberFormatException) {
            String message = "Triggers IngredientPriceFormatException()!";
            logger.warning(message);
            throw new IngredientPriceFormatException(getName());
        }
    }

    private void checkTwoDecimalPlace(String ingredientPrice) throws IngredientPriceFormatException {
        String twoDecimalPlaceRegex = "^\\d+\\.\\d{2}$";
        if (!ingredientPrice.matches(twoDecimalPlaceRegex)) {
            throw new IngredientPriceFormatException(getName());
        }
    }

    // Serializes the ingredient into a string format (e.g., "name|price")
    public String toDataString() {
        return getName() + " | " + getPrice();
    }

    // Deserializes the string back into an Ingredient object
    public static Ingredient fromData(String data) throws InvalidPriceException, IngredientPriceFormatException {
        String[] parts = data.split("\\s*\\|\\s*");
        int validLength = 2;
        if (parts.length < validLength) {
            throw new IllegalArgumentException("Invalid ingredient data: " + data);
        }
        int nameIndex = 0;
        int costIndex = 1;
        String name = parts[nameIndex];
        String price = parts[costIndex];
        return new Ingredient(name, price);
    }
}
