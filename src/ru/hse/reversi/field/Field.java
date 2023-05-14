package ru.hse.reversi.field;

import ru.hse.reversi.utility.FiledSymbols;
import ru.hse.reversi.utility.IntegerPair;

public class Field {
    private CharMatrix fieldBody;

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

    public Field(Field otherField) {
        fieldBody = otherField.getField().clone();
    }

    public CharMatrix getField() {
        return fieldBody.clone();
    }

    public void setField(CharMatrix fieldBody) {
        this.fieldBody = fieldBody.clone();
    }

    public void setCell(IntegerPair coordinates, Character character) {
        fieldBody.setElementAt(coordinates.getFirst(), coordinates.getSecond(), character);
    }
}
