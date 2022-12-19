import pkg.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class Player implements Serializable {
    private int health;
    private int maxHealth;
    private int damage;
    private Spell[] spells = {new Spell("nothing", 0 , 0, false)};
    private int exp;
    private int neededExp;
    private int coins;
    private String name;
    private int totalMana;
    private int currentMana;
    private int level;
    private int weaponsBought = 0;
    private int armorBought = 0;
    private int wandsBought = 0;
    private ArrayList<Quest> quests = new ArrayList<Quest>();
    private Weapon weapon;
    private Armor armor;
    private Wand wand;
    public Player(int maxHp, int dmg, int expNeeded, int manaTotal, String playerName) {
        level = 1;
        health = maxHp;
        maxHealth = maxHp;
        damage = dmg;
        name = playerName;
        weapon = new Weapon(0, "nothing", 0);
        weapon.setSprite(Paths.get("pictures").toAbsolutePath() + "\\characters\\32.png", 0, 0);
        armor = new Armor(0, "clothes", 0);
        armor.setSprite(Paths.get("pictures").toAbsolutePath() + "\\characters\\32.png", 0, 0);
        wand = new Wand(0, "twig", 0);
        wand.setSprite(Paths.get("pictures").toAbsolutePath() + "\\wands\\twig.png", 252, 479);
        exp = 0;
        neededExp = expNeeded;
        coins = 0;
        totalMana = manaTotal;
        currentMana = totalMana;
    }
    public Player() {
        level = 1;
        health = 20;
        damage = 4;
        name = "Player";
        totalMana = 20;
        currentMana = totalMana;
        weapon = new Weapon(0, "nothing", 0);
        armor = new Armor(0, "regular clothes", 0);
        wand = new Wand(0, "twig", 0);
        exp = 0;
        neededExp = 1;
        coins = 0;
    }
    public void setLevel(int newLevel) {
        level = newLevel;
    }
    public void setHealth(int newHealth) {
        health = newHealth;
    }
    public void setMaxHealth(int newMaxHealth) {
        maxHealth = newMaxHealth;
    }
    public void setCurrentMana(int newCurrMana) {
        currentMana = newCurrMana;
    }
    public void setDamage(int newDamage) {
        damage = newDamage;
    }
    public void setSpells(Spell[] spellArr) {
        spells = spellArr;
    }
    public void setTotalMana(int newTotalMana) {
        totalMana = newTotalMana;
    }
    public void setCoins(int amtCoins) {
        coins = amtCoins;
    }
    public void setQuests(ArrayList<Quest> newQuests) {
        quests = newQuests;
    }
    public Weapon getWeapon() {
        return weapon;
    }
    public Armor getArmor() {
        return armor;
    }
    public Wand getWand() { return wand;}
    public int getLevel() {
        return level;
    }
    public int getCurrentMana() {
        return currentMana;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public int getCoins() {
        return coins;
    }
    public ArrayList<Quest> getQuests() {
        return quests;
    }
    public Spell[] getSpells() {
        return spells;
    }
    public int getTotalMana() {
        return totalMana;
    }
    public void setName(String newName) {
        name = newName;
    }
    public void setExp(int newExp) {
        exp = newExp;
    }
    public void setNeededExp(int newNeededExp) {
        neededExp = newNeededExp;
    }
    public int getExp() {
        return exp;
    }
    public int getNeededExp() {
        return neededExp;
    }
    public int getHealth() {
        return health;
    }
    public int getDamage() {
        return damage;
    }
    public String getName() {
        return name;
    }
    public int getArmorBought() {
        return armorBought;
    }
    public int getWeaponsBought() {
        return weaponsBought;
    }
    public int getWandsBought() {
        return wandsBought;
    }
    public void setArmorBought(int armorBought) {
        this.armorBought = armorBought;
    }
    public void setWandsBought(int wandsBought) {
        this.wandsBought = wandsBought;
    }
    public void setWeaponsBought(int weaponsBought) {
        this.weaponsBought = weaponsBought;
    } 
    public void attack(Enemy enemy)
    {
        int multiplier = 1;
        if (crit()) {
            multiplier *= 2;
            System.out.println("Critical hit!");
        }
        if (miss()) {
            multiplier *= 0;
            System.out.println("It missed...");
        }
        enemy.setHealth(enemy.getHealth() - (damage + weapon.getDamage()) * multiplier);
        //filler
    }
    public void spellAttack(Enemy enemy, int spellChoice) {
        spellChoice--;
        int multiplier = 1;
        try {
            if (spells[spellChoice].getManaCost() <= currentMana) {
                if (spells[spellChoice].getName().equals(enemy.getWeakness())) {
                    multiplier *= 2;
                    System.out.println("\nVery effective!");
                }
                if (crit()) {
                    multiplier *= 2;
                    System.out.println("Critical hit!");
                }
                if (miss()) {
                    multiplier *= 0;
                    System.out.println("It missed...");
                }
                enemy.setHealth(enemy.getHealth() - (spells[spellChoice].getDamage() + wand.getDamage()) * multiplier);
                currentMana -= spells[spellChoice].getManaCost();
                if (spells[spellChoice].heals()) {
                    setHealth(spells[spellChoice].getDamage() * multiplier / 2);
                    if (getHealth() > getMaxHealth()) {
                        setHealth(getMaxHealth());
                    }
                }
            }
            else {
                System.out.println("Not enough mana...");
            }
            if (currentMana < 0)
                currentMana = 0;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Not a valid option, please choose a number from 1 to the amount of spells, inclusive.");
        }
    }

    public boolean crit() {
        return ((int)(Math.random() * 100) == 1);
    }
    public boolean miss() {
        return ((int)(Math.random() * 100) == 1);
    }

    public void buyWeapon(Shop weaponShop) {
        if (coins >= weaponShop.getWeaponPrice()) {
            weapon = weaponShop.getWeapon();
            coins -= weaponShop.getWeaponPrice();
        }
    }
    public void buyArmor(Shop armorShop) {
        if (coins >= armorShop.getArmorPrice()) {
            armor = armorShop.getArmor();
            coins -= armorShop.getArmorPrice();
        }
    }
    public void buyWand(Shop wandShop) {
        if (coins >= wandShop.getWandPrice()) {
            wand = wandShop.getWand();
            coins -= wandShop.getWandPrice();
        }
    }
    public boolean hasQuest(Quest quest) {
        for(Quest check: quests) {
            if (check.equals(quest))
                return true;
        }
        return false;
    }
    public void resolveQuest(int index) {
        if (quests.get(index).isDone()) {
            coins += quests.get(index).getCoinReward();
            exp += quests.get(index).getExpReward();
            quests.remove(index);
        }
    }
    public void addQuest(Quest quest) {
        quests.add(quest);
    }
    public void setQuest(int place, Quest quest) {
        quests.set(place, quest);
    }
    public void removeQuest(int place) {
        quests.remove(place);
    }
    public void removeQuest(Quest quest) {
        quests.remove(quest);
    }
}