import java.io.Serializable;
import java.util.Objects;

public class Wand implements Serializable {
    private int damage;
    private String name;
    public Wand(int dmg, String wandName, int value) {
        name = wandName;
        damage = dmg;
    }
    public Wand(int dmg, String wandName, int value) {
        this(dmg, wandName, value);
        canSell = sellable;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public String toString() {
        String sellable;
        if (canSell) {
            sellable = "can be sold";
        }
        else {
            sellable = "can't be sold";
        }
        return "Weapon name: " + name + ", price: " + super.getPrice() + ", damage: " + damage + ".";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wand wand = (Wand) o;
        return damage == wand.damage && canSell == wand.canSell && name.equals(wand.getName()) && super.getPrice() == wand.getPrice();
    }
}
