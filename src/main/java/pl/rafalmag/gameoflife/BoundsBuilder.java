package pl.rafalmag.gameoflife;

public class BoundsBuilder {
    private int minX, minY, maxX, maxY;

    public BoundsBuilder withMinX(int minX) {
        this.minX = minX;
        return this;
    }

    public BoundsBuilder withMinY(int minY) {
        this.minY = minY;
        return this;
    }

    public BoundsBuilder withMaxX(int maxX) {
        this.maxX = maxX;
        return this;
    }

    public BoundsBuilder withMaxY(int maxY) {
        this.maxY = maxY;
        return this;
    }

    public Bounds build() {
        return new Bounds(minX, minY, maxX, maxY);
    }
}
