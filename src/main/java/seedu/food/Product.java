package seedu.food;

import seedu.exceptions.InvalidPriceException;

import java.util.logging.Logger;


public abstract class Product {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    protected String name;
    protected double price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of a product, which must be greater than or equals to 0.
     *
     * @throws InvalidPriceException If specified price is less than 0.
     */
    public void setPrice(double price) throws InvalidPriceException {
        double zeroDollars = 0.00;
        String name = getName();
        if (price < zeroDollars || price > Double.MAX_VALUE) {
            logger.severe("Trigger InvalidPriceException()!");
            throw new InvalidPriceException(name);
        }
        this.price = price;
    }

    public String toString() {
        String price = String.format("%.2f", getPrice());
        return getName() + " ($" + price + ")";
    }
}
