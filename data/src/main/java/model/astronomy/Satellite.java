package model.astronomy;

import java.util.StringJoiner;

public class Satellite {

    private String name;

    public String getName() {
        return name;
    }

    public Satellite(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
