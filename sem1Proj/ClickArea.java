public class ClickArea {
    private int x;
    private int y;
    private int x2;
    private int y2;
    private int X;
    private int Y;
    private int X2;
    private int Y2;
    public ClickArea(int x, int y, int x2, int y2) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        X = x;
        Y = y;
        X2 = x2;
        Y2 = y2;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getX2() {
        return x2;
    }
    public int getY2() {
        return y2;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setX2(int x2) {
        this.x2 = x2;
    }
    public void setY2(int y2) {
        this.y2 = y2;
    }
    public int getLength() {
        return Math.abs(x - x2);
    }
    public int getWidth() {
        return Math.abs(y - y2);
    }
    public void activate() {
        x = X;
        y = Y;
        x2 = X2;
        y2 = Y2;
    }
    public void deactivate() {
        x = -100;
        y = -100;
        x2 = -99;
        y2 = -99;
    }
}