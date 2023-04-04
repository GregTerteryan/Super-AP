import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.io.Serializable;
import pkg.*;

public class Enemy implements Serializable{
    private int health;
    private int damage;
    private int maxHealth;
    private String name;
    private int expAmount;
    private String weakness = null;
    private Random rand = new Random();
    private String sprite;
    private int x;
    private int y;
    public Enemy(int hp, int dmg, String enemyName, int expAmt) {
        health = hp;
        maxHealth = hp;
        damage = dmg;
        name = enemyName;
        expAmount = expAmt;
    }
    public Enemy() {
        health = 10;
        damage = 3;
        name = "Monster";
    }
    public void setHealth(int newHealth)
    {
        health = newHealth;
        //filler
    }
    public void setMaxHealth(int newMaxHealth) {
        maxHealth = newMaxHealth;
        //filler
    }
    public void setDamage(int newDamage)
    {
        damage = newDamage;
        //filler
    }
    public void setName(String newName)
    {
        name = newName;
        //filler
    }
    public void setExpAmount(int newExp) {
        expAmount = newExp;
        //filler
    }
    public void setWeakness(String attackName) {
        weakness = attackName;
    }

    public int getHealth()
    {
        return health;
        //filler
    }
    public int getMaxHealth() {
        return maxHealth;
        //filler
    }
    public int getDamage()
    {
        return damage;
        //filler
    }
    public String getName()
    {
        return name;
        //filler
    }
    public int getExpAmount() {
        return expAmount;
        //filler
    }

    public String getWeakness() {
        return weakness;
    }

    public void attack(Player player)
    {
        int multiplier = 1;
        if (crit()) {
            multiplier *=2;
            System.out.println("Ow! Critical hit...");
        }
        if (miss()) {
            multiplier *=0;
            System.out.println("Yes! It missed!");
        }
        if (damage - player.getArmor().getDefense() >= 0)
            player.setHealth(player.getHealth() - (damage- player.getArmor().getDefense()) * multiplier);
        else
            System.out.println("Your defense is so high, you took no damage!");
    }
    public boolean crit() {
        return rand.nextInt(100) == 1;
    }
    public boolean miss() {
        return rand.nextInt(100) == 1;
    }
    public void setSprite(String file, int x, int y) {
        sprite = file;
        this.x = x;
        this.y = y;
    }
    public String getSprite() {
        return sprite;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enemy enemy = (Enemy) o;
        return damage == enemy.damage && maxHealth == enemy.maxHealth && expAmount == enemy.expAmount && Objects.equals(name, enemy.name) && Objects.equals(weakness, enemy.weakness);
    }
}
