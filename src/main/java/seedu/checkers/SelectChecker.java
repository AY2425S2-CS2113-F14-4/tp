package seedu.checkers;

import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.MissingMealIndexException;


public class SelectChecker extends FilterSelectChecker {
    public SelectChecker(String userInputText, String filterMethod) {
        this.userInput = userInputText.trim();
        this.lowerCaseInput = userInput.toLowerCase();
        this.filterOrSelect = "select";
        this.filterMethod = filterMethod;
    }

    @Override
    public void check() throws EZMealPlanException {
        super.check();
        indexStringCheck();
        setPassed(true);
    }

    private void indexStringCheck() throws EZMealPlanException {
        String indexString = getIndexString().trim();
        if (indexString.isEmpty()) {
            setPassed(false);
            throw new MissingMealIndexException(filterOrSelect);
        }
    }

    private String getIndexString() {
        String keyword = "";
        int afterSelectIndex = lowerCaseInput.indexOf(filterOrSelect) + filterOrSelect.length();
        keyword = switch (filterMethod) {
        case byIng -> ing;
        case byMcost -> mcost;
        case byMname -> mname;
        default -> keyword;
        };
        if (keyword.isEmpty()) {
            return userInput.substring(afterSelectIndex);
        }
        int keywordIndex = lowerCaseInput.indexOf(keyword);
        return userInput.substring(afterSelectIndex, keywordIndex);
    }
}
