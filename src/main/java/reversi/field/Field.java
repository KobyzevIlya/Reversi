package reversi.field;

import reversi.utility.FiledSymbols;
import reversi.utility.IntegerPair;

/**
 * 
 * A class representing a field for the Reversi game.
 */
public class Field {
    private CharMatrix fieldBody;

    /**
     * 
     * Constructs a new field with an 8x8 CharMatrix, with all elements initialized
     * as space characters, except for the center four elements, which are
     * initialized as black and white pieces.
     */
    public Field() {
        fieldBody = new CharMatrix(8, 8);

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                fieldBody.setElementAt(i, j, FiledSymbols.SPACE);
            }
        }

        fieldBody.setElementAt(3, 3, FiledSymbols.WHITE);
        fieldBody.setElementAt(3, 4, FiledSymbols.BLACK);
        fieldBody.setElementAt(4, 3, FiledSymbols.BLACK);
        fieldBody.setElementAt(4, 4, FiledSymbols.WHITE);
    }

    /**
     * 
     * Constructs a new field with the same contents as the specified Field object.
     * 
     * @param otherField the Field object to copy the contents from
     */
    public Field(Field otherField) {
        fieldBody = otherField.getField().clone();
    }

    /**
     * 
     * Returns a clone of the CharMatrix representing the field.
     * 
     * @return a clone of the CharMatrix representing the field
     */
    public CharMatrix getField() {
        return fieldBody.clone();
    }

    /**
     * 
     * Sets the field to a clone of the specified CharMatrix.
     * 
     * @param fieldBody the CharMatrix to clone and set as the field
     */
    public void setField(CharMatrix fieldBody) {
        this.fieldBody = fieldBody.clone();
    }

    /**
     * 
     * Sets the specified cell in the field to the specified character.
     * 
     * @param coordinates the coordinates of the cell to set
     * @param character   the character to set the cell to
     */
    public void setCell(IntegerPair coordinates, Character character) {
        fieldBody.setElementAt(coordinates.getFirst(), coordinates.getSecond(), character);
    }
}
