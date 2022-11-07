public class Point {
    private int x;
    private int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int[] getArray() {
        int[] arr = {x, y};
        return arr;
    }
    public void reset() {
        x = 0;
        y = 0;
    }
    public boolean inArea(ClickArea area) {
        return (x >= area.getX() && x <= area.getX2() && y >= area.getY() && y <= area.getY2());
    }
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}