package reversi.field;

import java.util.Objects;

/**
 * An abstract class representing a two-dimensional array.
 *
 * @param <T> the type of objects contained in the array
 */
public abstract class TwoDimensionalArray<T> {
    private final int rowsCount;
    private final int columnsCount;
    T[][] body;

    protected TwoDimensionalArray(int rows, int columns) {
        body = null;
        rowsCount = rows;
        columnsCount = columns;
    }

    public T getElementAt(int row, int column) {
        return Objects.requireNonNull(body)[row][column];
    }

    public void setElementAt(int row, int column, T element) {
        Objects.requireNonNull(body)[row][column] = element;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColumnsCount() {
        return columnsCount;
    }
}
