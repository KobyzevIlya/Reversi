package reversi.field;

/**
 * A class representing a two-dimensional array of Character objects.
 */
public class CharMatrix extends TwoDimensionalArray<Character> implements Cloneable {
    /**
     * Constructs a new CharMatrix object with the specified number of rows and columns.
     * 
     * @param rows the number of rows in the matrix
     * @param columns the number of columns in the matrix
     */
    protected CharMatrix(int rows, int columns) {
        super(rows, columns);
        body = new Character[rows][columns];
    }

    /**
     * Returns a clone of this CharMatrix object.
     * 
     * @return a clone of this CharMatrix object
     */
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

