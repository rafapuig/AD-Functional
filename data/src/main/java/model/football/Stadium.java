package model.football;

public record Stadium(String name) {
    @Override
    public String toString() {
        return name;
    }
}
