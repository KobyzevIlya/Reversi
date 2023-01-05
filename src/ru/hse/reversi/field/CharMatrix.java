package ru.hse.reversi.field;

public class CharMatrix extends TwoDimensionalArray<Character> implements Cloneable {
    protected CharMatrix(int rows, int columns) {
        super(rows, columns);
        body = new Character[rows][columns];
    }

    @Override
    public CharMatrix clone() {
        CharMatrix clone = new CharMatrix(getRowsCount(), getColumnsCount());
        for (int i = 0; i < getRowsCount(); ++i) {
            for (int j = 0; j < getColumnsCount(); ++j) {
                clone.setElementAt(i, j, getElementAt(i, j));
            }
        }

        return clone;
    }
}
