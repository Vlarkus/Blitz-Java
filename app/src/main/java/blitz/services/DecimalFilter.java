/*
 * Copyright 2024 Valery Rabchanka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package blitz.services;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * A custom document filter that validates input based on a regular expression.
 * Primarily used for restricting input to decimal formats.
 * 
 * @author Valery Rabchanka
 */
public class DecimalFilter extends DocumentFilter {

    // -=-=-=- FIELDS -=-=-=-

    private String regex;

    // -=-=-=- CONSTRUCTORS -=-=-=-

    /**
     * Initializes the DecimalFilter with a given regular expression.
     * 
     * @param r the regular expression to validate input against
     */
    public DecimalFilter(String r) {
        super();
        regex = r;
    }

    // -=-=-=- METHODS -=-=-=-

    /**
     * Called when text is inserted into the document. Validates the new text based on the regex.
     * 
     * @param fb    the FilterBypass to use for insert
     * @param offset the offset into the document to insert the content
     * @param text  the string to insert
     * @param attr  the attributes of the inserted content
     * @throws BadLocationException if the insertion is not valid
     */
    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        StringBuilder sb = new StringBuilder();
        sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.insert(offset, text);

        if (isValid(sb.toString())) {
            super.insertString(fb, offset, text, attr);
        }
    }

    /**
     * Called when text in the document is replaced. Validates the new text based on the regex.
     * 
     * @param fb      the FilterBypass to use for replacement
     * @param offset  the offset into the document to start replacing
     * @param length  the number of characters to replace
     * @param text    the string to replace the content with
     * @param attrs   the attributes of the inserted content
     * @throws BadLocationException if the replacement is not valid
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        StringBuilder sb = new StringBuilder();
        sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.replace(offset, offset + length, text);

        if (isValid(sb.toString())) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    /**
     * Checks if the given text matches the regular expression.
     * 
     * @param text the text to validate
     * @return true if the text is valid according to the regex, false otherwise
     */
    private boolean isValid(String text) {
        return text.isEmpty() || text.matches(regex);
    }
}
