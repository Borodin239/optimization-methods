package lab01.optimizations;

public class Dot {
    private double x;
    private double y;
    private Color color;

    public Dot(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Dot() {}

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) { this.color = color; }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
