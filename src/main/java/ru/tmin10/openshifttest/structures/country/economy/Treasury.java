package ru.tmin10.openshifttest.structures.country.economy;

public class Treasury {
    private final float gold;
    private final float cc;
    private final long energy;

    public Treasury(float gold, float cc, long energy) {
        this.gold = gold;
        this.cc = cc;
        this.energy = energy;
    }

    public float getGold() {
        return gold;
    }

    public float getCc() {
        return cc;
    }

    public long getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return "Treasury{" +
                "gold=" + gold +
                ", cc=" + cc +
                ", energy=" + energy +
                '}';
    }
}
