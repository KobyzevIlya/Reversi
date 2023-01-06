package ru.hse.reversi.utility;

public class IntegerPair {
    private final int first;
    private final int second;

    public IntegerPair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        return getFirst() == ((IntegerPair) object).getFirst() && getSecond() == ((IntegerPair) object).getSecond();
    }
}
