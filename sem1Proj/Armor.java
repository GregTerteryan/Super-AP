import java.io.Serializable;
import java.util.Objects;
import pkg.*;

public class Armor implements Serializable {
    private int defense;
    private String name;
    private String abbreviation;
    private int price;
    private String sprite;
    private int x;
    private int y;
    public Armor(int def, String name, int value) {
        this.name = name;
        abbreviation = abbreviate(name);
        defense = def;
        price = value;
        sprite = null;
    }
    private String abbreviate(String str) {
        char[] arr = str.toCharArray();
        String result = "";
        for (char c: arr) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                result += "";
            }
            else {
                result += c;
            }
        }
        return result;
    }
    public int getDefense() {
        return defense;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        abbreviation = abbreviate(name);
    }
    public void setAbbreviation(String abbr) {
        abbreviation = abbr;
    }
    public String getAbbreviation() {
        return abbreviation;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String toString() {
        return "Armor name: " + name + ", price: " + price + ", damage: " + defense + ".";
    }
    public void setSprite(String file, int x, int y) {
        sprite = file;
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public String getSprite() {
        return sprite;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Armor armor = (Armor) o;
        return defense == armor.getDefense() && name.equals(armor.getName()) && price == armor.getPrice();
    }
}
