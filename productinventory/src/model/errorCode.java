package model;

/**
 * Enumeration values that represent valid error types, used to generate error dialogues during runtime
 * 1. MAXMINVAL - Max Must be greater than min
 * 2. POSVALUES - all values must be positive (cannot be negative)
 * 3. STOCKLEVEL - Stock level must be greater than max and more than min
 * 4. VALIDVALUES - Values must be valid for the data type associated with that input
 * 5. ASSOCPARTSELECTED - no associated part was selected in the UI
 * 6. ALREADYASSOCPART - same associated part is assigned to the product and can't be added again
 * 7. NOITEMTODELETE - an item to delete was not selected in the UI
 * 8. ASSOCPARTDELERR - Product cannot be removed until it's associated parts have been removed
 * 9. NOMODIFYSELECTED - item to modify wasn't selected in the UI
 * 10. SEARCHFAIL - No search items were found. Returns search requirements
 */
public enum errorCode {
    MAXMINVAL,
    POSVALUES,
    STOCKLEVEL,
    VALIDVALUES,
    ASSOCPARTSELECTED,
    ALREADYASSOCPART,
    NOITEMTODELETE,
    ASSOCPARTDELERR,
    NOMODIFYSELECTED,
    SEARCHFAIL
}
