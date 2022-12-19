import java.io.Serializable;
import java.util.Objects;
import pkg.*;

public class Weapon implements Serializable {
    private int damage;
    private String name;
    private String abbreviation;
    private int price;
    private String sprite;
    private int x;
    private int y;
    public Weapon(int dmg, String weaponName, int value) {
        name = weaponName;
        abbreviation = abbreviate(name);
        damage = dmg;
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
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public String getName() {
        return name;
    }
    public void setName(String newName) {
        name = newName;
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
    public void setPrice(int newPrice) {
        price = newPrice;
    }
    public String toString() {
        return "Weapon name: " + name + ", price: " + price + ", damage: " + damage + ".";
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
        Weapon weapon = (Weapon) o;
        return damage == weapon.getDamage() && name.equals(weapon.getName()) && price == weapon.getPrice();
    }
}
