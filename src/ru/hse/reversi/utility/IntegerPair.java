package ru.hse.reversi.utility;

/**
 * 
 * The IntegerPair class represents an pair of integers.
 */
public class IntegerPair {
    private final int first;
    private final int second;

    /**
     * Constructs a new IntegerPair object with the specified values.
     *
     * @param first  the first integer of the pair
     * @param second the second integer of the pair
     */
    public IntegerPair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first integer of the pair.
     *
     * @return the first integer of the pair
     */
    public int getFirst() {
        return first;
    }

    /**
     * Returns the second integer of the pair.
     *
     * @return the second integer of the pair
     */
    public int getSecond() {
        return second;
    }

    /**
     * Returns true if the specified object is an IntegerPair object
     * with the same values for the first and second integers.
     *
     * @param object the object to compare
     * @return true if the specified object is equal to this IntegerPair object,
     *         false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;

        return getFirst() == ((IntegerPair) object).getFirst() && getSecond() == ((IntegerPair) object).getSecond();
    }
}
