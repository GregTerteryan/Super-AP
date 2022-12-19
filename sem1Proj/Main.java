import java.nio.file.*;
import java.util.*;
import java.io.*;
import pkg.*;

/*
Alr homeboy here's the rundown: Picture (or rather the base Java thing that it uses) isn't Serializable
You need to replace all the sprites with Strings and then have the unsaved pictures only in Main
Do this for enemies and change up the way sprites are put in the shop
 */

public class Main implements InputControl, InputKeyControl{
    static Scanner sc = new Scanner(System.in);
    static Player player;
    static boolean rapulaDefeated = false;
    static boolean challenge = false;
    static String curLocation;
    static final String GRASSLANDS = "the grasslands";
    static final String DARK_CAVE = "a dark cave";
    static final String ANCIENT_JUNGLE = "the Ancient Jungle";
    static final String CLOUD_KINGDOM = "the Cloud Kingdom";
    static final String RAPULA_CASTLE = "Rapula's Castle";
    static Path musicPath = Paths.get("music");
    static Path savePath = Paths.get("saves");
    static Path picturePath = Paths.get("pictures");
    static KeyController kC;
    static MouseController mC;
    static Picture background = new Picture(1, 1);
    public static void main(String[] args) {
        LastClick.setUp();
        kC = new KeyController(Canvas.getInstance(),new Main());
        mC = new MouseController(Canvas.getInstance(),new Main());
        //getting the player's name
        System.out.print("Ah, another traveller. Pray tell, what is your name? ");
        String playerName = "123456789012345678901234567890";
        while (true) {
            playerName = sc.nextLine();
            if (playerName.length() <= 25)
                break;
            System.out.println();
            System.out.print("(Please limit your name to 25 characters) ");
        }
        //setting up stats
        player = new Player(10, 4, 20,10, playerName);
        Spell fire = new Spell("Fire", 3, 3, false);
        Spell lightning = new Spell("Lightning", 5, 5, false);
        Spell leech = new Spell("Leech", 2, 4, true);
        Spell[] spells = {fire, lightning, leech};
        player.setSpells(spells);
        System.out.println("Welcome, " + playerName + ", to this world.\nRapula has been torturing this world with his terrible soundcloud raps.");
        System.out.println("Go! Save this world from Rapula's trash music!");
        System.out.println("(Switch to the Java window)");
        while (true) {
            curLocation = GRASSLANDS;
            menu();
        }
    }
    public static void menu() {
        Random rand = new Random();
        background.undraw();
        LastClick.setUp();
        switch (curLocation) {
            case GRASSLANDS -> {
                if (Music.isPlaying())
                    Music.stop();
                background = new Picture(picturePath.toAbsolutePath() + "\\grasslands_bg.png");
                background.draw();
                Music.play(musicPath.toAbsolutePath() + "\\grasslands.wav");
            }
            case DARK_CAVE -> {
                if (Music.isPlaying())
                    Music.stop();
                background = new Picture(picturePath.toAbsolutePath() + "\\cave_bg.png");
                background.draw();
                Music.play(musicPath.toAbsolutePath() + "\\cave.wav");
            }
            case ANCIENT_JUNGLE -> {
                if (Music.isPlaying())
                    Music.stop();
                background = new Picture(picturePath.toAbsolutePath() + "\\jungle_bg.png");
                background.draw();
                Music.play(musicPath.toAbsolutePath() + "\\jungle.wav");
            }
            case CLOUD_KINGDOM -> {
                if (Music.isPlaying())
                    Music.stop();
                background = new Picture(picturePath.toAbsolutePath() + "\\cloud_bg.png");
                background.draw();
                Music.play(musicPath.toAbsolutePath() + "\\cloud.wav");
            }
            case RAPULA_CASTLE -> {
                if (Music.isPlaying())
                    Music.stop();
                background = new Picture(picturePath.toAbsolutePath() + "\\rapula_bg.png");
                background.draw();
                Music.play(musicPath.toAbsolutePath() + "\\rapula.wav");
            }
        }
        Music.loop();
        //player's options
        Picture options = new Picture(picturePath.toAbsolutePath() + "\\menu_options.png");
        options.draw();
        ClickArea[] optionAreas = new ClickArea[5];
        for (int c = 0; c < optionAreas.length; c++) {
            optionAreas[c] = new ClickArea((c * 268), 581, 213 + (c * 268), 749);
        }
        while (true) {
            Canvas.pause(100);
            options.draw();
            for (ClickArea option: optionAreas) {
                option.activate();
            }
            if (LastClick.click.inArea(optionAreas[0])) {
                for (ClickArea option: optionAreas) {
                    option.deactivate();
                }
                options.undraw();
                switch (curLocation) {
                    case GRASSLANDS -> grasslands();
                    case DARK_CAVE -> darkCave();
                    case ANCIENT_JUNGLE -> ancientJungle();
                    case CLOUD_KINGDOM -> cloudKingdom();
                    case RAPULA_CASTLE -> rapulaCastle();
                }
            } else if (LastClick.click.inArea(optionAreas[1])) {
                for (ClickArea option: optionAreas) {
                    option.deactivate();
                }
                background.undraw();
                options.undraw();
                LastClick.click.reset();
                travel();
            } else if (LastClick.click.inArea(optionAreas[2])) {
                for (ClickArea option: optionAreas) {
                    option.deactivate();
                }
                options.undraw();
                LastClick.click.reset();
                stats();

            } else if (LastClick.click.inArea(optionAreas[3])) {
                options.undraw();
                checkQuests();
            } else if (LastClick.click.inArea(optionAreas[4])) {
                for (ClickArea option: optionAreas) {
                    option.deactivate();
                }
                options.undraw();
                LastClick.click.reset();
                Picture saveLoad = new Picture(picturePath.toAbsolutePath() + "\\save_load.png");
                saveLoad.draw();
                ClickArea save = new ClickArea(434, 581, 646, 749);
                ClickArea load = new ClickArea(647, 581, 859, 749);
                ClickArea back = new ClickArea(1107, 616, 1206, 715);
                while (true) {
                    Canvas.pause(100);
                    if (LastClick.click.inArea(save)) {
                        save();
                        LastClick.setUp();
                        break;
                    }
                    else if (LastClick.click.inArea(load)) {
                        load();
                        LastClick.setUp();
                        break;
                    }
                    else if (LastClick.click.inArea(back)) {
                        saveLoad.undraw();
                        save.deactivate();
                        load.deactivate();
                        back.deactivate();
                        LastClick.setUp();
                        break;
                    }
                }
            }
        }
    }
    public static void grasslands() {
        Random rand = new Random();
        int enemyValue = rand.nextInt(4);
        Enemy enemy = new Enemy(1, 0, "placeholder", 0);
        //setting up random enemies and their stats
        switch (enemyValue) {
            case 0 -> {
                 enemy = new Enemy(5, 0, "dummy", 2);
                 enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\grasslands\\dummy.png", 950, 298);
                 enemy.setWeakness(player.getSpells()[0].getName());
            }
            case 1 -> {
                enemy = new Enemy(7, 1, "chicken", 3);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\grasslands\\chicken.png", 950, 344);
            }
            case 2 -> {
                enemy = new Enemy(10, 4, "boar", 5);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\grasslands\\boar.png", 950, 424);
            }
            case 3 -> {
                enemy = new Enemy(20, 5, "goblin", 10);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\grasslands\\goblin.png", 950, 401);
                enemy.setWeakness(player.getSpells()[2].getName());
            }
            default -> {
                enemy = new Enemy(1, 0, "rare glitch", 0);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\glitch.png", 0, 0);
            }
        }
        doBattle(enemy);
    }
    public static void darkCave() {
        Random rand = new Random();
        int enemyValue = rand.nextInt(4);
        Enemy enemy = new Enemy(1, 0, "placeholder", 0);
        switch (enemyValue) {
            case 0 -> {
                enemy = new Enemy(15, 5, "baby spider", 20);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\cave\\baby_spider.png", 1020, 75);
                enemy.setWeakness(player.getSpells()[0].getName());
            }
            case 1 -> {
                enemy = new Enemy(20, 10, "adult spider", 25);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\cave\\adult_spider.png", 1075, 175);
                enemy.setWeakness(player.getSpells()[0].getName());
            }
            case 2 -> {
                enemy = new Enemy(10, 10, "vampire bat", 22);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\cave\\bat.png", 740, 140);
                enemy.setWeakness(player.getSpells()[2].getName());
            }
            case 3 -> {
                enemy = new Enemy(40, 20, "Elder Tarantula", 30);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\cave\\tarantula.png", 720, 315);
            }
            default -> {
                enemy = new Enemy(1, 0, "rare glitch", 0);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\glitch.png", 0, 0);
            }
        }
        doBattle(enemy);
    }
    public static void ancientJungle() {
        Random rand = new Random();
        int enemyValue = rand.nextInt(4);
        Enemy enemy = new Enemy(1, 0, "placeholder", 0);
        switch (enemyValue) {
            case 0 -> {
                enemy = new Enemy(30, 10, "giant pihranha", 50);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\jungle\\piranha.png", 1015, 330);
                enemy.setWeakness(player.getSpells()[1].getName());
            }
            case 1 -> {
                enemy = new Enemy(10, 20, "sentient vines", 35);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\jungle\\vines.png", 798, 398);
                enemy.setWeakness(player.getSpells()[0].getName());
            }
            case 2 -> {
                enemy = new Enemy(100, 0, "boulder", 50);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\jungle\\boulder.png", 960, 330);
            }
            case 3 -> {
                enemy = new Enemy(100, 30, "Nature's Heart", 100);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\jungle\\nature_heart.png", 680, 50);
                enemy.setWeakness(player.getSpells()[0].getName());
                challenge = true;
            }
            default -> {
                enemy = new Enemy(1, 0, "rare glitch", 0);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\glitch.png", 0, 0);
            }
        }
        doBattle(enemy);
    }
    public static void cloudKingdom() {
        Random rand = new Random();
        int enemyValue = rand.nextInt(4);
        Enemy enemy = new Enemy(1, 0, "placeholder", 0);
        switch (enemyValue) {
            case 0 -> {
                enemy = new Enemy(70, 30, "angel", 75);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\cloud\\angel.png", 840, 90);
                enemy.setWeakness(player.getSpells()[1].getName());
            }
            case 1 -> {
                enemy = new Enemy(60, 60, "balance patrol", 70);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\cloud\\balance_patrol.png", 960, 390);
                enemy.setWeakness(player.getSpells()[1].getName());
            }
            case 2 -> {
                enemy = new Enemy(85, 45, "Verilyx's guard", 90);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\could\\v_guard.png", 930, 270);
            }
            case 3 -> {
                enemy = new Enemy(120, 40, "Verilyx, King of Wings", 120);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\cloud\\verilyx.png", 530, 0);
                challenge = true;
            }
            default -> {
                enemy = new Enemy(1, 0, "rare glitch", 0);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\glitch.png", 0, 0);
            }
        }
        doBattle(enemy);
    }
    public static void rapulaCastle() {
        Random rand = new Random();
        Enemy enemy = new Enemy(1, 0, "placeholder", 0);
        int enemyValue = rand.nextInt(2);
        challenge = true;
        switch (enemyValue) {
            case 0 -> {
                enemy = new Enemy(250, 69, "Rapula", 500);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\rapula\\rapula.png", 980, 152);
            }
            case 1 -> {
                enemy = new Enemy(200, 50, "Allenator, the Punk Rock Werewolf", 300);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\rapula\\allenator.png", 640, 330);
            }
            default -> {
                enemy = new Enemy(1, 0, "rare glitch", 0);
                enemy.setSprite(picturePath.toAbsolutePath() + "\\enemies\\glitch.png", 0, 0);
            }
        }
        doBattle(enemy);
    }

    public static void doBattle(Enemy enemy){
        boolean invalidInput = true;
        Music.stop();
        //resetting stats before battle
        player.setHealth(player.getMaxHealth());
        player.setCurrentMana(player.getTotalMana());
        Picture textBox = new Picture(picturePath.toAbsolutePath() + "\\text_box.png");
        Word text = new Word("I don't know HOW you managed to mess this up.");
        Picture player_model = new Picture(picturePath.toAbsolutePath() + "\\player_model.png");
        player_model.translate(278,342);
        Picture player_weapon = new Picture(player.getWeapon().getSprite());
        player_weapon.translate(player.getWeapon().getX(), player.getWeapon().getY());
        Picture player_armor = new Picture(player.getArmor().getSprite());
        player_armor.translate(player.getArmor().getX(), player.getArmor().getY());
        Picture player_wand = new Picture(player.getWand().getSprite());
        player_wand.translate(player.getWand().getX(), player.getWand().getY());
        player_model.draw();
        player_weapon.draw();
        player_armor.draw();
        player_wand.draw();
        Picture en = new Picture(enemy.getSprite());
        en.translate(enemy.getX(), enemy.getY());
        en.draw();
        textBox.draw();
        if (challenge) {
            Music.play(musicPath.toAbsolutePath() + "\\challenge.wav");
            Music.loop();
            Word chal = new Word("You challenge " + enemy.getName() + ".");
	    chal.translate(6, 557);
	    chal.draw();
            challenge = false;
	    Canvas.pause(2500);
	    chal.undraw();
        }
        else {
            Music.play(musicPath.toAbsolutePath() + "\\battlestart.wav");
            Word fill = new Word("\nA " + enemy.getName() + " appeared!");
	        fill.translate(6, 557);
	        fill.draw();
            while (!Music.isDone());
            Music.play(musicPath.toAbsolutePath() + "\\battle.wav");
            Music.loop();
	        fill.undraw();
        }
        boolean battleOver = false;
        boolean playerWon = false;
        ClickArea[] atkOptions = new ClickArea[4];
        for (int L = 0; L < atkOptions.length; L++) {
                atkOptions[L] = new ClickArea(7 + 4 + (353 * L),30 + 551 , 7 + 216 + (353 * L), 30 + 719);
        }
        Picture battle_options = new Picture(picturePath.toAbsolutePath() + "\\battle_options.png");
        int attackChoice = 0;
        while (!battleOver) {
            //making sure that the health and mana doesn't go over max
            if (player.getCurrentMana() > player.getTotalMana()) {
                player.setCurrentMana(player.getTotalMana());
            }
            if (player.getHealth() > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
            textBox.undraw();
            battle_options.draw();
            for (ClickArea lmao:atkOptions) {
                lmao.activate();
            }
            while (true) {
                LastClick.setUp();
                Canvas.pause(100);
                if (LastClick.click.inArea(atkOptions[0])) {
                    attackChoice = 1;
                    break;
                }
                else if (LastClick.click.inArea(atkOptions[1])) {
                    attackChoice = 2;
                    break;
                }
                else if (LastClick.click.inArea(atkOptions[2])) {
                    attackChoice = 3;
                break;
                }
                else if (LastClick.click.inArea(atkOptions[3])) {
                    attackChoice = 4;
                break;
                }
            }
            battle_options.undraw();
            textBox.draw();
            for (ClickArea lol:atkOptions) {
                lol.deactivate();
            }
            switch (attackChoice) {
                case 1 -> {
                    text = new Word(player.getName() + " used a melee attack.");
                    player.attack(enemy);
                }
                case 2 -> {
                    text = new Word(player.getName() + " used Fire.");
                    player.spellAttack(enemy, 1);
                }
                case 3 -> {
                    text = new Word(player.getName() + " used Lightning.");
                    player.spellAttack(enemy, 2);
                }
                case 4 -> {
                    text = new Word(player.getName() + " used Leech.");
                    player.spellAttack(enemy, 3);
                }
            }
            text.translate(6, 577);
            textBox.draw();
            text.draw();
            Canvas.pause(2500);
            text.undraw();
            //looks nicer/more appropriate with this
            if (enemy.getHealth() <= 0) {
                enemy.setHealth(0);
            }
            if (challenge)
                text = new Word(enemy.getName() + " has " + enemy.getHealth() + "/" + enemy.getMaxHealth() + " health left.");
            else
                text = new Word("The " + enemy.getName() + " has " + enemy.getHealth() + "/" + enemy.getMaxHealth() + " health left.");
            text.translate(6, 577);
            text.draw();
            Canvas.pause(2500);
            text.undraw();
            //win condition
            if (enemy.getHealth() <= 0) {
                playerWon = true;
                battleOver = true;
                en.undraw();
                break;
            }
            enemy.attack(player);
            if (challenge)
                text = new Word(enemy.getName() + " attacked!");
            else
                text = new Word("The " + enemy.getName() + " attacked!");
            text.translate(6, 577);
            text.draw();
            Canvas.pause(2500);
            text.undraw();
            //looks nicer/more appropriate with this
            if (player.getHealth() < 0) {
                player.setHealth(0);
            }
            text = new Word("\n" + player.getName() + " has " + player.getHealth() + "/" + player.getMaxHealth() + " health left.");
            text.translate(6, 577);
            text.draw();
            Canvas.pause(2500);
            text.undraw();
            //lose condition
            if (player.getHealth() <= 0) {
                playerWon = false;
                battleOver = true;
                break;
            }
            text = new Word("You have " + player.getCurrentMana() + "/" + player.getTotalMana() + " mana left.");
            text.translate(6, 577);
            text.draw();
            Canvas.pause(2500);
            text.undraw();
            //players should be able to keep using spells at some point
            text = new Word("You regenerated 1 mana.");
            player.setCurrentMana(player.getCurrentMana() + 1);
            text.translate(6, 577);
            text.draw();
            Canvas.pause(2500);
            text.undraw();
        }
        Music.stop();
        if (playerWon) {
	        Music.play(musicPath.toAbsolutePath() + "\\winstart.wav");
	        text = new Word("\nYou win!");
	        text.translate(6,557);
	        text.draw();
	        while (!Music.isDone());
            Music.play(musicPath.toAbsolutePath() + "\\win.wav");
            Music.loop();
	        text.undraw();
            for (int c = 0; c < player.getQuests().size(); c++) {
                if (player.getQuests().get(c).checkSubject(enemy))
                    player.resolveQuest(c);
            }
            if (enemy.getName().equals("Rapula")) {
                rapulaDefeated = true;
            }
            player.setCoins(player.getCoins() + enemy.getExpAmount());
            player.setExp(player.getExp() + enemy.getExpAmount());
            //level up, stats get buffed
            while (player.getExp() >= player.getNeededExp()) {
                text = new Word("Level up!");
		        text.translate(6, 557);
	            text.draw();
	            Canvas.pause(1000);
	            text.undraw();
                player.setExp(player.getExp() - player.getNeededExp());
                player.setNeededExp(player.getNeededExp() + 10);
                player.setMaxHealth(player.getMaxHealth() + 1);
                player.setDamage(player.getDamage() + 1);
                player.setTotalMana(player.getTotalMana() + 1);
                for (int c = 0; c < player.getSpells().length; c++) {
                    player.getSpells()[c].setDamage(player.getSpells()[c].getDamage() + 1);
                }
                player.setLevel(player.getLevel() + 1);
            }
            LastClick.setUp();
            Word contin = new Word("(Click to continue)");
            contin.translate(6, 557);
            contin.draw();
            while (LastClick.click.getX() < 0) {
                Canvas.pause(100);
            }
            contin.undraw();
            textBox.undraw();
            player_model.draw();
            player_weapon.draw();
            player_armor.draw();
            player_wand.draw();
            LastClick.setUp();
            menu();
        }
        else if (!playerWon) {
            Music.play(musicPath.toAbsolutePath() + "\\death.wav");
            Word L = new Word("You lose...");
            L.translate(6, 557);
            L.draw();
            LastClick.setUp();
            while (LastClick.click.getX() < 0) {
                Canvas.pause(100);
            }
            L.undraw();
            player_model.draw();
            player_weapon.draw();
            player_armor.draw();
            player_wand.draw();
            en.undraw();
            menu();
        }
    }
    public static void travel() {
        LastClick.click.reset();
        Picture map = new Picture(picturePath.toAbsolutePath() + "\\worldmap.png");
        map.draw();
        Picture[] locks = new Picture[5];
        for (int c = 0; c < locks.length; c++) {
            locks[c] = new Picture(picturePath.toAbsolutePath() + "\\lock.png");
        }
        locks[0].translate(40,54);
        locks[1].translate(530, 54);
        locks[2].translate(529,441);
        locks[3].translate(1012,442);
        locks[4].translate(1012,55);
        ClickArea grass = new ClickArea(45, 470, 276, 697);
        ClickArea shop = new ClickArea(1028,117, 1210,255);
        ClickArea cave = new ClickArea(-2, -2, -1 ,-1);
        ClickArea jungle = new ClickArea(-2, -2, -1 ,-1);
        ClickArea cloud = new ClickArea(-2, -2, -1 ,-1);
        ClickArea rap = new ClickArea(-2,-2, -1, -1);
        if (player.getLevel() < 8)
            locks[0].draw();
        else
            cave = new ClickArea(45, 82, 276, 310);
        if (player.getLevel() < 15)
            locks[1].draw();
        else
            jungle = new ClickArea(535, 82, 765, 310);
        if (player.getLevel() < 30)
            locks[2].draw();
        else
            cloud = new ClickArea(535,470, 765,697);
        if (player.getLevel() < 50)
            locks[3].draw();
        else
            rap = new ClickArea(1017,470, 1247,697);
        while (true) {
            Canvas.pause(100);
            if (LastClick.click.inArea(grass)) {
                curLocation = GRASSLANDS;
                Enemy e = new Enemy(20, 5, "goblin", 10);
                e.setWeakness(player.getSpells()[2].getName());
                Quest killGob = new Quest("Kill 5 Goblins", 5, 0, 10, 10, e);
                if (!player.hasQuest(killGob) && player.getLevel() >= 3 && player.getQuests().size() < 3)
                    player.addQuest(killGob);
                map.undraw();
                for (Picture lock: locks) {
                    lock.undraw();
                }
                menu();
                break;
            }
            else if (LastClick.click.inArea(shop)) {
                map.undraw();
                for (Picture lock: locks) {
                    lock.undraw();
                }
                villageShop();
            }
            if (player.getLevel() >= 8) {
                if (LastClick.click.inArea(cave)) {
                    Enemy e = new Enemy(40, 20, "Elder Tarantula", 30);
                    Quest killElder = new Quest("Defeat the Elder Tarantula", 1, 0, 50, 50, e);
                    if (!player.hasQuest(killElder) && player.getLevel() >= 10 && player.getQuests().size() < 3)
                        player.addQuest(killElder);
                    curLocation = DARK_CAVE;
                    map.undraw();
                    for (Picture lock: locks) {
                        lock.undraw();
                    }
                    menu();
                    break;
                }
                if (player.getLevel() >= 15) {
                    if (LastClick.click.inArea(jungle)) {
                        Enemy e = new Enemy(100, 30, "Nature's Heart", 100);
                        e.setWeakness(player.getSpells()[0].getName());
                        Quest killHeart = new Quest("Defeat Nature's Heart", 1, 0, 100, 100, e);
                        if (!player.hasQuest(killHeart) && player.getLevel() >= 20 && player.getQuests().size() < 3)
                            player.addQuest(killHeart);
                        curLocation = ANCIENT_JUNGLE;
                        map.undraw();
                        for (Picture lock: locks) {
                            lock.undraw();
                        }
                        menu();
                        break;
                    }
                    if (player.getLevel() >= 30) {
                        if (LastClick.click.inArea(cloud)) {
                            Enemy e = new Enemy(120, 40, "Verilyx, King of Wings", 120);
                            Quest verilyx = new Quest("Defeat Verilyx, ruler of the Cloud Kingdom", 1, 0, 250, 250, e);
                            if (!player.hasQuest(verilyx) && player.getLevel() >= 35 && player.getQuests().size() < 3)
                                player.addQuest(verilyx);
                            curLocation = CLOUD_KINGDOM;
                            map.undraw();
                            for (Picture lock: locks) {
                                lock.undraw();
                            }
                            menu();
                            break;
                        }
                        if (player.getLevel() >= 50) {
                            if (LastClick.click.inArea(rap)) {
                                if (player.getQuests().size() < 3) {
                                    player.addQuest(new Quest("Defeat Rapula", 1, 0, 10000, 5000, new Enemy(250, 69, "Rapula", 500)));
                                }
                                curLocation = RAPULA_CASTLE;
                                map.undraw();
                                for (Picture lock: locks) {
                                    lock.undraw();
                                }
                                menu();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public static void villageShop() {
        Music.stop();
        background = new Picture(picturePath.toAbsolutePath() + "\\shop_bg.png");
        background.draw();
        Music.play(musicPath.toAbsolutePath() + "\\shop.wav");
        Music.loop();
        Shop shop = new Shop();
        while (true) {
            //checks the amount of weapons the player bought, updates shop based on that
            switch (player.getWeaponsBought()) {
                case 0 -> {
                    shop.setWeapon(new Weapon(2, "knife", 50));
                    shop.getWeapon().setSprite(picturePath.toAbsolutePath() + "\\weapons\\knife.png", 420, 370);
                }
                case 1 -> {
                    shop.setWeapon(new Weapon(5, "sword", 100));
                    shop.getWeapon().setSprite(picturePath.toAbsolutePath() + "\\weapons\\sword.png", 418, 312);
                }
                case 2 -> {
                    shop.setWeapon(new Weapon(10, "axe", 250));
                    shop.getWeapon().setSprite(picturePath.toAbsolutePath() + "\\weapons\\axe.png", 416, 274);
                }
                case 3 -> {
                    shop.setWeapon(new Weapon(25, "Riptide", 2500));
                    shop.getWeapon().setSprite(picturePath.toAbsolutePath() + "\\weapons\\riptide.png", 418, 291);
                }
                case 4 -> {
                    shop.setWeapon(new Weapon(50, "Baton", 10000));
                    shop.getWeapon().setSprite(picturePath.toAbsolutePath() + "\\weapons\\baton.png", 418, 268);
                }
                case 5 -> {
                    shop.setWeapon(new Weapon(150, "Baton+", 20000));
                    shop.getWeapon().setSprite(picturePath.toAbsolutePath() + "\\weapons\\baton.png", 418, 268);
                }
                default -> shop.setWeapon(null);
            }
            switch (player.getArmorBought()) {
                case 0 -> {
                    shop.setArmor(new Armor(2, "tunic", 50));
                    shop.getArmor().setSprite(picturePath.toAbsolutePath() + "\\armor\\tunic.png", 290, 400);
                }
                case 1 -> {
                    shop.setArmor(new Armor(5, "iron armor", 100));
                    shop.getArmor().setSprite(picturePath.toAbsolutePath() + "\\armor\\iron_armor.png", 290, 400);
                }
                case 2 -> {
                    shop.setArmor(new Armor(10, "Tuxedo", 250));
                    shop.getArmor().setSprite(picturePath.toAbsolutePath() + "\\armor\\tuxedo.png", 290, 415);
                }
                case 3 -> {
                    shop.setArmor(new Armor(25, "Drip", 2500));
                    shop.getArmor().setSprite(picturePath.toAbsolutePath() + "\\armor\\tuxedo.png", 269, 411);
                }
                case 4 -> {
                    shop.setArmor(new Armor(50, "Tuba", 10000));
                    shop.getArmor().setSprite(picturePath.toAbsolutePath() + "\\armor\\tuba.png", 303, 404);
                }
                case 5 -> {
                    shop.setArmor(new Armor(150, "Tuba+", 20000));
                    shop.getArmor().setSprite(picturePath.toAbsolutePath() + "\\armor\\tuba.png", 303, 404);
                }
                default -> shop.setArmor(null);
            }
            switch (player.getWandsBought()) {
                case 0 -> {
                    shop.setWand(new Wand(2, "branch", 50));
                    shop.getWand().setSprite(picturePath.toAbsolutePath() + "\\wands\\branch.png", 233, 477);
                }
                case 1 -> {
                    shop.setWand(new Wand(5, "magician wand", 100));
                    shop.getWand().setSprite(picturePath.toAbsolutePath() + "\\wands\\magician_wand.png", 245, 477);
                }
                case 2 -> {
                    shop.setWand(new Wand(10, "Star Wand", 250));
                    shop.getWand().setSprite(picturePath.toAbsolutePath() + "\\wands\\star_wand.png", 238,482);
                }
                case 3 -> {
                    shop.setWand(new Wand(25, "Potter Wand", 2500));
                    shop.getWand().setSprite(picturePath.toAbsolutePath() + "\\wands\\potter_wand.png", 247, 477);
                }
                case 4 -> {
                    shop.setWand(new Wand(50, "Flute", 10000));
                    shop.getWand().setSprite(picturePath.toAbsolutePath() + "\\wands\\flute.png", 214, 474);
                }
                case 5 -> {
                    shop.setWand(new Wand(150, "Flute+", 20000));
                    shop.getWand().setSprite(picturePath.toAbsolutePath() + "\\wands\\flute.png", 214, 474);
                }
                default -> shop.setWand(null);
            }
            ClickArea back = new ClickArea(27, 56, 126, 155);
            ClickArea buyWeap = new ClickArea(-2, -2, -1, -1);
            ClickArea buyArm = new ClickArea(-2, -2, -1, -1);
            ClickArea buyWand = new ClickArea(-2, -2, -1, -1);
            Word weap = new Word("Sold Out");
            weap.translate(305, 220);
            Word arm = new Word("Sold Out");
            arm.translate(305, 322);
            Word wan = new Word("Sold Out");
            wan.translate(305, 423);
            if (shop.getWeapon() != null) {
                weap = new Word(shop.getWeapon().getName() + " DMG:" + shop.getWeapon().getDamage() + " PRICE:" + shop.getWeaponPrice());
                weap.translate(305, 220);
                buyWeap = new ClickArea(291,217 , 1102, 320);
            }
            if (shop.getArmor() != null) {
                arm = new Word(shop.getArmor().getName() + " DMG:" + shop.getArmor().getDefense() + " PRICE:" + shop.getArmorPrice());
                arm.translate(305, 322);
                buyArm = new ClickArea(291,321 , 1102, 424);
            }
            if (shop.getWand() != null) {
                wan = new Word(shop.getWeapon().getName() + " DMG:" + shop.getWeapon().getDamage() + " PRICE:" + shop.getWeaponPrice());
                wan.translate(305, 423);
                buyWand = new ClickArea(291,425 , 1102, 528);
            }
            weap.draw();
            arm.draw();
            wan.draw();
            LastClick.setUp();
            while (true) {
                Canvas.pause(100);
                if (LastClick.click.inArea(back)) {
                    LastClick.setUp();
                    weap.undraw();
                    arm.undraw();
                    wan.undraw();
                    buyWeap.deactivate();
                    buyArm.deactivate();
                    buyWand.deactivate();
                    background.undraw();
                    travel();
                }
                else if (LastClick.click.inArea(buyWeap) && shop.getWeapon() != null) {
                    player.buyWeapon(shop);
                    LastClick.setUp();
                    weap.undraw();
                    arm.undraw();
                    wan.undraw();
                    buyWeap.deactivate();
                    buyArm.deactivate();
                    buyWand.deactivate();
                    break;
                }
                else if (LastClick.click.inArea(buyArm) && shop.getArmor() != null) {
                    player.buyArmor(shop);
                    LastClick.setUp();
                    weap.undraw();
                    arm.undraw();
                    wan.undraw();
                    buyWeap.deactivate();
                    buyArm.deactivate();
                    buyWand.deactivate();
                    break;
                }
                else if (LastClick.click.inArea(buyWand) && shop.getWand() != null) {
                    player.buyWand(shop);
                    LastClick.setUp();
                    weap.undraw();
                    arm.undraw();
                    wan.undraw();
                    buyWeap.deactivate();
                    buyArm.deactivate();
                    buyWand.deactivate();
                    break;
                }
            }
        }
    }
    public static void stats() {
        Picture stats_screen = new Picture(picturePath.toAbsolutePath() + "\\stats_screen.png");
        Word playername = new Word(player.getName());
        playername.translate(406,128);
        Word playerlevel = new Word("" + player.getLevel());
        playerlevel.translate(406,198);
        Word playerhp = new Word("" + player.getMaxHealth());
        playerhp.translate(406, 269);
        Word playerdmg = new Word("" + player.getDamage());
        playerdmg.translate(406, 339);
        Word playermana = new Word("" + player.getTotalMana());
        playermana.translate(406, 409);
        Word playerexp = new Word(player.getExp() + "/" + player.getNeededExp());
        playerexp.translate(406, 479);
        Word playercoins = new Word("$" + player.getCoins());
        playercoins.translate(406, 550);
        Word playerfire = new Word("" + player.getSpells()[0].getDamage());
        playerfire.translate(800, 198);
        Word playerlightning = new Word("" + player.getSpells()[1].getDamage());
        playerlightning.translate(800, 268);
        Word playerleech = new Word("" + player.getSpells()[2].getDamage());
        playerleech.translate(800, 339);
        Word playerweapon = new Word("" + player.getWeapon().getAbbreviation());
        playerweapon.translate(800, 409);
        Word playerarmor = new Word("" + player.getArmor().getAbbreviation());
        playerarmor.translate(800, 479);
        Word playerwand = new Word("" + player.getWand().getAbbreviation());
        playerwand.translate(800, 549);
        stats_screen.draw();
        playername.draw();
        playerlevel.draw();
        playerhp.draw();
        playerdmg.draw();
        playermana.draw();
        playerexp.draw();
        playercoins.draw();
        playerfire.draw();
        playerlightning.draw();
        playerleech.draw();
        playerweapon.draw();
        playerarmor.draw();
        playerwand.draw();
        ClickArea back = new ClickArea(73,84 , 172,184);
        while (!LastClick.click.inArea(back)) {
            Canvas.pause(100);
        }
        back.deactivate();
        playername.undraw();
        playerlevel.undraw();
        playerhp.undraw();
        playerdmg.undraw();
        playermana.undraw();
        playerexp.undraw();
        playercoins.undraw();
        playerfire.undraw();
        playerlightning.undraw();
        playerleech.undraw();
        playerweapon.undraw();
        playerarmor.undraw();
        playerwand.undraw();
        stats_screen.undraw();
    }

    public static void checkQuests() {
        Picture questBoard = new Picture(picturePath.toAbsolutePath() + "\\quest_board.png");
        questBoard.draw();
        Word quest1 = new Word(" ");
        Word quest2 = new Word(" ");
        Word quest3 = new Word(" ");
        if (player.getQuests().size() > 0) {
            quest1 = new Word(player.getQuests().get(0).getQuest() + " " + player.getQuests().get(0).getDone() + "/" + player.getQuests().get(0).getNeeded());
            quest1.translate(269, 119);
            quest1.draw();
        }
        if (player.getQuests().size() > 1) {
            quest2 = new Word(player.getQuests().get(1).getQuest() + " " + player.getQuests().get(1).getDone() + "/" + player.getQuests().get(1).getNeeded());
            quest2.translate(269, 119 + 44);
            quest2.draw();
        }
        if (player.getQuests().size() > 2) {
            quest3 = new Word(player.getQuests().get(2).getQuest() + " " + player.getQuests().get(2).getDone() + "/" + player.getQuests().get(2).getNeeded());
            quest3.translate(269, 119 + 88);
            quest3.draw();
        }
        ClickArea back = new ClickArea(73,84 , 172,184);
        while (!LastClick.click.inArea(back)) {
            Canvas.pause(100);
        }
        if (player.getQuests().size() > 0) {
            quest1.undraw();
        }
        if (player.getQuests().size() > 1) {
            quest2.undraw();
        }
        if (player.getQuests().size() > 2) {
            quest3.undraw();
        }
        questBoard.undraw();
    }

    //copied this from https://www.tutorialspoint.com/java/java_serialization.htm lol
    public static void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream(savePath + "\\" + player.getName() + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(player);
            out.close();
            fileOut.close();
            Word saved = new Word("Saved!");
            saved.translate(93, 605);
            saved.draw();
            Canvas.pause(1000);
            saved.undraw();
        }
        catch (IOException i) {
            i.printStackTrace();
            System.out.println("error");
        }
    }
    public static void load() {
        String name = new String(player.getName());
        player = null;
        try {
            FileInputStream fileIn = new FileInputStream(savePath + "\\" + name + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            player = (Player) in.readObject();
            in.close();
            fileIn.close();
            Word loaded = new Word("Loaded!");
            loaded.translate(93, 605);
            loaded.draw();
            Canvas.pause(1000);
            loaded.undraw();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Player class not found");
            c.printStackTrace();
            return;
        }
    }
    public void onMouseClick(double x, double y) {
        // enter code here
        LastClick.click.setX((int)x);
        LastClick.click.setY((int)y);
    }

    public void keyPress(String s) {
        // enter code here

    }
}