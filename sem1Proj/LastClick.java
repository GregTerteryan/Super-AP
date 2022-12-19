public class LastClick {
    static Point click;
    public static void setUp() {
        click = new Point(-100, -100);
    }
    public void set(Point point) {
        click.setX(point.getX());
        click.setY(point.getY());
    }
    public Point get() {
        return click;
    }
}