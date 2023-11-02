package model.geography;

public enum SIPrefix {
    NONE("", 1),
    KILO("k", 3);

    final String symbol;

    public String getSymbol() {
        return symbol;
    }

    int power;

    public int getPower() {
        return power;
    }

    final long multiplier;

    public long getMultiplier() {
        return multiplier;
    }

    SIPrefix(String symbol, int n) {
        this.symbol = symbol;
        this.power = n;
        this.multiplier = (long) Math.pow(10.0, n);
    }


}
