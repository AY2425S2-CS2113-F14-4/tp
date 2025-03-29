package seedu.checkers;

import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.InvalidIngIndexException;
import seedu.exceptions.InvalidMcostIndexException;
import seedu.exceptions.InvalidMnameIndexException;
import seedu.exceptions.MissingIngredientException;
import seedu.exceptions.MissingMealCostException;
import seedu.exceptions.MissingMealNameException;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class FilterCheckerTest {
    private static final Logger logger = Logger.getLogger(FilterCheckerTest.class.getName());
    final String filter = "filter";

    public FilterCheckerTest() {
        String fileName = "FilterCheckerTest.log";
        setupLogger(fileName);
    }

    private static void setupLogger(String fileName) {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        logger.addHandler(consoleHandler);
        createLogFile(fileName);
    }

    private static void createLogFile(String fileName) {
        try {
            FileHandler fileHandler = new FileHandler(fileName, true);
            fileHandler.setLevel(Level.FINE);
            logger.addHandler(fileHandler);
        } catch (IOException ioException) {
            logger.log(Level.SEVERE, "File logger is not working.", ioException);
        }
    }

    @Test
    public void filterChecker_success() throws EZMealPlanException {
        logger.info("running filterchecker_success()");
        String[] validFilterStrings = {"filter /mname a", "filter /mname a,b", "filter /ing c", "filter /ing c,d"
                , "filter /mcost 1"};
        for (String filterString : validFilterStrings) {
            String filterMethod = getFilterMethod(filterString);
            FilterChecker checker = new FilterChecker(filterString, filterMethod);
            checker.check();
            assertTrue(checker.isPassed());
            logger.info("Valid filter command input.");
        }
        logger.info("filterChecker_success() passed");
    }

    private static String getFilterMethod(String filterString) {
        String filterMethod = "";
        if (filterString.contains("/mname")) {
            filterMethod = "byMname";
        } else if (filterString.contains("/mcost")) {
            filterMethod = "byMcost";
        } else if (filterString.contains("/ing")) {
            filterMethod = "byIng";
        }
        return filterMethod;
    }

    @Test
    public void filterChecker_fail() {
        checkInvalidMcostIndex();
        checkInvalidMcostFormat();
        checkInvalidMnameIndex();
        checkInvalidMnameFormat();
        checkInvalidIngIndex();
        checkInvalidIngFormat();
    }

    private void checkInvalidIngFormat() {
        String testName = "checkInvalidIngFormat()";
        String[] invalidIngFormatStrings = {"filter /ing", "filter/ing   "};
        String expectedMessage = new MissingIngredientException(filter).getMessage();
        checkInvalidFilterInput(testName, invalidIngFormatStrings, expectedMessage);
    }

    private void checkInvalidIngIndex() {
        String testName = "checkInvalidIngIndex()";
        String[] invalidIngIndexStrings = {"/ing filter", "/ing filter a,b", "a,b /ing filter", "/ing a,b filter"};
        String expectedMessage = new InvalidIngIndexException(filter).getMessage();
        checkInvalidFilterInput(testName, invalidIngIndexStrings, expectedMessage);
    }

    private void checkInvalidMnameFormat() {
        String testName = "checkInvalidMnameFormat()";
        String[] invalidMnameFormatStrings = {"filter /mname", "filter/mname   "};
        String expectedMessage = new MissingMealNameException(filter).getMessage();
        checkInvalidFilterInput(testName, invalidMnameFormatStrings, expectedMessage);
    }

    private void checkInvalidMnameIndex() {
        String testName = "checkInvalidMnameIndex()";
        String[] invalidMnameIndexStrings = {"/mname filter", "/mname a,b filter", "a,b /mname filter"
                , "/mnamefilter a,b"};
        String expectedMessage = new InvalidMnameIndexException(filter).getMessage();
        checkInvalidFilterInput(testName, invalidMnameIndexStrings, expectedMessage);
    }

    private void checkInvalidMcostFormat() {
        String testName = "checkInvalidMcostFormat()";
        String[] invalidMnameIndexStrings = {"filter /mcost", "filter/mcost  "};
        String expectedMessage = new MissingMealCostException(filter).getMessage();
        checkInvalidFilterInput(testName, invalidMnameIndexStrings, expectedMessage);
    }

    private void checkInvalidMcostIndex() {
        String testName = "checkInvalidMcostIndex()";
        String[] invalidMnameIndexStrings = {"/mcost filter", "/mcost 1 filter", "1 /mcost filter"
                , "/mcostfilter 0.5"};
        String expectedMessage = new InvalidMcostIndexException(filter).getMessage();
        checkInvalidFilterInput(testName, invalidMnameIndexStrings, expectedMessage);
    }

    private static void checkInvalidFilterInput(String testName, String[] testStrings, String expectedMessage) {
        logger.fine("running " + testName);
        for (String invalidIngIndexString : testStrings) {
            String filterMethod = getFilterMethod(invalidIngIndexString);
            FilterChecker checker = new FilterChecker(invalidIngIndexString, filterMethod);
            try {
                checker.check();
                fail();
            } catch (EZMealPlanException ezMealPlanException) {
                assertEquals(expectedMessage, ezMealPlanException.getMessage());
                logger.info("Matching exception caught!");
                logger.info(testName + " passed");
            } finally {
                assertFalse(checker.isPassed());
                logger.info("Input is expected to fail.");
            }
        }
    }
}
