import java.nio.file.*;
import java.util.*;
import java.io.*;
import pkg.*;
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
    static final String PIANO = "Piano Land";
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
            System.out.print("(Please limit your name to 28 characters) ");
        }
        //setting up stats
        player = new Player(10, 4, 20,10, playerName);
        Spell fire = new Spell("Fire", 3, 3, false);
        Spell lightning = new Spell("Lightning", 5, 5, false);
        Spell leech = new Spell("Leech", 2, 4, true);
        Spell[] spells = {fire, lightning, leech};
        player.setSpells(spells);
        player.addQuest(new Quest("Kill Rapula", 1, 0, 10000, 5000, new Enemy(250, 69, "Rapula", 500)));
        System.out.println("Welcome, " + playerName + ", to this world.\nRapula has been torturing this world with his terrible soundcloud raps.");
        System.out.println("Go! Save this world from Rapula's trash music!");
        while (true) {
            curLocation = GRASSLANDS;
            menu();
        }
    }
    public static void menu() {
        Random rand = new Random();
        background.undraw();
        if (curLocation.equals(RAPULA_CASTLE))
            System.out.println("\nThis is it. Rapula's Castle. Go fulfill your destiny.");
        else if (curLocation.equals(PIANO))
            System.out.println("Pianos are overrated.");
        else
            System.out.println("\nYou are now in " + curLocation + ".");
        switch (curLocation) {
            case GRASSLANDS -> {
                Enemy e = new Enemy(20, 5, "goblin", 10);
                e.setWeakness(player.getSpells()[2].getName());
                Quest killGob = new Quest("Kill 5 Goblins", 5, 0, 10, 10, e);
                if (!player.hasQuest(killGob) && player.getLevel() >= 3)
                    player.addQuest(killGob);
                if (Music.isPlaying())
                    Music.stop();
                background = new Picture(picturePath.toAbsolutePath() + "\\grasslands_bg.png");
                background.draw();
                Music.play(musicPath.toAbsolutePath() + "\\grasslands.wav");
            }
            case DARK_CAVE -> {
                Enemy e = new Enemy(40, 20, "Elder Tarantula", 30);
                Quest killElder = new Quest("Defeat the Elder Tarantula", 1, 0, 25, 25, e);
                if (!player.hasQuest(killElder) && player.getLevel() >= 10)
                    player.addQuest(killElder);
                if (Music.isPlaying())
                    Music.stop();
                Music.play(musicPath.toAbsolutePath() + "\\cave.wav");
            }
            case ANCIENT_JUNGLE -> {
                Enemy e = new Enemy(100, 30, "Nature's Heart", 100);
                e.setWeakness(player.getSpells()[0].getName());
                Quest killHeart = new Quest("Defeat Nature's Heart", 1, 0, 50, 50, e);
                if (!player.hasQuest(killHeart) && player.getLevel() >= 20)
                    player.addQuest(killHeart);
                if (Music.isPlaying())
                    Music.stop();
                Music.play(musicPath.toAbsolutePath() + "\\jungle.wav");
            }
            case CLOUD_KINGDOM -> {
                Enemy e = new Enemy(120, 40, "Verilyx, King of Wings", 120);
                Quest verilyx = new Quest("Defeat Verilyx, ruler of the Cloud Kingdom", 1, 0, 75, 75, e);
                if (!player.hasQuest(verilyx) && player.getLevel() >= 35)
                    player.addQuest(verilyx);
                if (Music.isPlaying())
                    Music.stop();
                Music.play(musicPath.toAbsolutePath() + "\\cloud.wav");
            }
            case RAPULA_CASTLE -> {
                if (Music.isPlaying())
                    Music.stop();
                Music.play(musicPath.toAbsolutePath() + "\\rapula.wav");
            }
            case PIANO -> {
                Enemy e = new Enemy(2000, 500, "Gaia, the Earth-Mother", 2500);
                Quest killGaia = new Quest("Defeat Gaia", 1, 0, 1000, 1000, e);
                if (!player.hasQuest(killGaia) && player.getLevel() >= 55) {
                    ArrayList<Quest> q = player.getQuests();
                    q.add(killGaia);
                    player.setQuests(q);
                }
                if (Music.isPlaying())
                    Music.stop();
                Music.play(musicPath.toAbsolutePath() + "\\greece.wav");
            }
        }
        Music.loop();
        //player's options
        Picture options = new Picture(picturePath.toAbsolutePath() + "\\menu_options.png");
        options.draw();
        ClickArea[] optionAreas = new ClickArea[5];
        for (int c = 0; c < optionAreas.length; c++) {
            if (c == 0)
                optionAreas[c] = new ClickArea((c * 213), 581, 213 + (c * 213), 749);
            else
                optionAreas[c] = new ClickArea(55 + (c * 213), 581, 267 + (c * 213), 749);
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
                    case PIANO -> Greece();
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
                 enemy.setWeakness(player.getSpells()[0].getName());
            }
            case 1 -> enemy = new Enemy(7, 1, "chicken", 3);
            case 2 -> enemy = new Enemy(10, 4, "boar", 5);
            case 3 -> {
                enemy = new Enemy(20, 5, "goblin", 10);
                enemy.setWeakness(player.getSpells()[2].getName());
                enemy.addItem(new Item("goblin flesh", 2, 2, 50));
                enemy.addItem(new Weapon(10, "goblin knife", 50, 5, true));
            }
            default -> enemy = new Enemy(1, 0, "rare glitch", 0);
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
                enemy.setWeakness(player.getSpells()[0].getName());
            }
            case 1 -> {
                enemy = new Enemy(20, 10, "adult spider", 25);
                enemy.setWeakness(player.getSpells()[0].getName());
            }
            case 2 -> {
                enemy = new Enemy(10, 10, "vampire bat", 22);
                enemy.setWeakness(player.getSpells()[2].getName());
                enemy.addItem(new Weapon(10, "vampire fang", 10, 33, false));
            }
            case 3 -> {
                enemy = new Enemy(40, 20, "Elder Tarantula", 30);
                enemy.addItem(new Item("Tarantula fur", 50, 1, 10));
            }
            default -> enemy = new Enemy(1, 0, "rare glitch", 0);
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
                enemy.setWeakness(player.getSpells()[1].getName());
            }
            case 1 -> {
                enemy = new Enemy(10, 20, "sentient vines", 35);
                enemy.setWeakness(player.getSpells()[0].getName());
            }
            case 2 -> {
                enemy = new Enemy(100, 0, "\uD83E\uDEA8", 50);
                enemy.addItem(new Item("coal", 10, 7, 75));
                enemy.addItem(new Item("iron", 25, 4, 50));
                enemy.addItem(new Item("gold", 100, 2, 20));
                enemy.addItem(new Item("diamond", 1000, 1, 1));
            }
            case 3 -> {
                enemy = new Enemy(100, 30, "Nature's Heart", 100);
                enemy.setWeakness(player.getSpells()[0].getName());
                enemy.addItem(new Item("Nature's Heart's heart", 250, 1, 15));
                challenge = true;
            }
            default -> enemy = new Enemy(1, 0, "rare glitch", 0);
        }
        doBattle(enemy);
    }
    public static void cloudKingdom() {
        Random rand = new Random();
        int enemyValue = rand.nextInt(4);
        Enemy enemy = new Enemy(1, 0, "placeholder", 0);
        switch (enemyValue) {
            case 0 -> {
                enemy = new Enemy(70, 30, "kingdom defender", 75);
                enemy.setWeakness(player.getSpells()[1].getName());
            }
            case 1 -> {
                enemy = new Enemy(60, 60, "balance patrol", 70);
                enemy.setWeakness(player.getSpells()[1].getName());
            }
            case 2 -> enemy = new Enemy(85, 45, "Verilyx's guard", 90);
            case 3 -> {
                enemy = new Enemy(120, 40, "Verilyx, King of Wings", 120);
                enemy.addItem(new Item("Verilyx's feather", 100, 1, 99));
                enemy.addItem(new Item("Verilyx's feather", 100, 2, 75));
                enemy.addItem(new Item("Verilyx's feather", 100, 5, 50));
                enemy.addItem(new Item("Verilyx's feather", 100, 10, 25));
                enemy.addItem(new Item("Verilyx's feather", 100, 50, 10));
                enemy.addItem(new Item("Verilyx's feather", 100, 100, 1));
                challenge = true;
            }
            default -> enemy = new Enemy(1, 0, "rare glitch", 0);
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
                enemy.addItem(new Item("Rapula's mixtape", 1, 1, 100));
                System.out.println("Yo yo yo, it's Rapula in the house. My house. What are you doing in my house? Scram, kid, before I make a diss track on you!");
            }
            case 1 -> {
                enemy = new Enemy(200, 50, "Allenator, the Punk Rock Werewolf", 300);
                enemy.addItem(new Item("Allenator's Guitar", 1000, 1, 10));
            }
        }
        doBattle(enemy);
    }
    public static void Greece() {
        Random rand = new Random();
        Enemy enemy = new Enemy(1, 0, "placeholder", 0);
        int enemyValue = rand.nextInt(15);
        challenge = true;
        switch (enemyValue) {
            case 0 -> {
                enemy = new Enemy(500, 85, "Hestia", 750);
                enemy.addItem(new Item("Hestia's hearth", 1000, 1, 10));
            }
            case 1 -> {
                enemy = new Enemy(400, 100, "Dionysus", 750);
                enemy.addItem(new Item("Dionysus's wine", 1000, 1, 10));
            }
            case 2 -> {
                enemy = new Enemy(300, 150, "Hermes", 750);
                enemy.addItem(new Item("Talaria", 1500, 1, 10));
                enemy.setWeakness(player.getSpells()[1].getName());
            }
            case 3 -> {
                enemy = new Enemy(600, 50, "Aphrodite", 750);
                enemy.setWeakness(player.getSpells()[2].getName());
            }
            case 4 -> {
                enemy = new Enemy(600, 100, "Demeter", 800);
                enemy.setWeakness(player.getSpells()[0].getName());
            }
            case 5 -> {
                enemy = new Enemy(800, 125, "Hephaestus", 900);
                enemy.addItem(new Weapon(175, "Hephaestus's hammer", 2000, 1, true));
            }
            case 6 -> {
                enemy = new Enemy(400, 200, "Apollo", 900);
                enemy.addItem(new Item("Apollo's mini-sun", 2000, 1, 10));
            }
            case 7 -> {
                enemy = new Enemy(600, 150, "Artemis", 900);
                enemy.addItem(new Item("Artemis's bow", 2000, 1, 10));
            }
            case 8 -> {
                enemy = new Enemy(700, 200, "Athena", 1000);
                enemy.addItem(new Weapon(190, "Athena's spear", 1000, 1, true));
                enemy.addItem(new Item("Athena's Guide to a Perfect War", 10000, 1, 1));
            }
            case 9 -> {
                enemy = new Enemy(1000, 150, "Hera", 1100);
            }
            case 10 -> {
                enemy = new Enemy(900, 300, "Ares", 1000);
                enemy.addItem(new Item("Ares's Guide to a Perfect Battle", 10000, 1, 1));
            }
            case 11 -> {
                enemy = new Enemy(1000, 200, "Hades", 1200);
                enemy.addItem(new Weapon(200, "Hades's Scyth", 3500, 5, true));
            }
            case 12 -> {
                enemy = new Enemy(1250, 150, "Poseidon", 1200);
                enemy.addItem(new Weapon(250, "Poseidon's Trident", 4000, 3, true));
            }
            case 13 -> {
                enemy = new Enemy(1500, 300, "Zeus", 1500);
                enemy.addItem(new Wand(300, "Pure Lightning Rod", 5000, 1, true));
            }
            case 14 -> {
                enemy = new Enemy(2000, 500, "Gaia, the Earth-Mother", 2500);
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
	player.draw();
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
            Canvas.pause(1000);
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
	    Canvas.pause(5000);
	    text.undraw();
            //win condition
            if (enemy.getHealth() <= 0) {
                playerWon = true;
                battleOver = true;
                break;
            }
            enemy.attack(player);
            if (challenge)
                text = new Word(enemy.getName() + " attacked!");
            else
                text = new Word("The " + enemy.getName() + " attacked!");
	    text.translate(6, 577);
	    text.draw();
	    Canvas.pause(1000);
	    text.undraw();
            //looks nicer/more appropriate with this
            if (player.getHealth() < 0) {
                player.setHealth(0);
            }
            text = new Word("\n" + player.getName() + " has " + player.getHealth() + "/" + player.getMaxHealth() + " health left.");
	    text.translate(6, 577);
	    text.draw();
	    Canvas.pause(5000);
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
	    Canvas.pause(5000);
	    text.undraw();
            //players should be able to keep using spells at some point
            text = new Word("You regenerated 1 mana.");
            player.setCurrentMana(player.getCurrentMana() + 1);
	    text.translate(6, 577);
	    text.draw();
	    Canvas.pause(1000);
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
            enemy.drop(player);
	    text.undraw();
            for (int c = 0; c < player.getQuests().size(); c++) {
                if (player.getQuests().get(c).checkSubject(enemy))
                    player.resolveQuest(c);
            }
            if (enemy.getName().equals("Rapula")) {
                System.out.println("Your journey is done... or is it?");
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
            textBox.undraw();
	    player.undraw();
            menu();
        }
        else if (!playerWon) {
            Music.play(musicPath.toAbsolutePath() + "\\death.wav");
            System.out.println("\nYou lose...");
            System.out.print("Press enter to continue. ");
            sc.nextLine();
            String useless = sc.nextLine();
	    player.undraw();
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
        ClickArea shop = new ClickArea(319,335, 501,473);
        ClickArea cave = new ClickArea(-2, -2, -1 ,-1);
        ClickArea jungle = new ClickArea(-2, -2, -1 ,-1);
        ClickArea cloud = new ClickArea(-2, -2, -1 ,-1);
        ClickArea rap = new ClickArea(-2,-2, -1, -1);
        ClickArea piano = new ClickArea(-2,-2, -1,-1);
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
        if (!rapulaDefeated && player.getLevel() < 60)
            locks[4].draw();
        else
            piano = new ClickArea(1017,83, 1248, 311);
        while (true) {
            Canvas.pause(100);
            if (LastClick.click.inArea(grass)) {
                curLocation = GRASSLANDS;
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
                                curLocation = RAPULA_CASTLE;
                                map.undraw();
                                for (Picture lock: locks) {
                                    lock.undraw();
                                }
                                menu();
                                break;
                            }
                            if (player.getLevel() >= 60 || rapulaDefeated) {
                                if (LastClick.click.inArea(piano)) {
                                    curLocation = PIANO;
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
    }
    public static void villageShop() {
        Music.stop();
        Music.play(musicPath.toAbsolutePath() + "\\shop.wav");
        Music.loop();
        //appear once you get the last weapons
        Shop shop = new Shop();
        while (true) {
            //checks the amount of weapons the player bought, updates shop based on that
            switch (player.getWeaponsBought()) {
                case 0 -> shop.setWeapon(new Weapon(2, "staff", 50, false));
                case 1 -> shop.setWeapon(new Weapon(5, "sword", 100, false));
                case 2 -> shop.setWeapon(new Weapon(10, "axe", 250, false));
                case 3 -> shop.setWeapon(new Weapon(20, "hf blade", 2500, false));
                case 4 -> shop.setWeapon(new Weapon(45, "Music Sword", 10000, false));
                case 5 -> shop.setWeapon(new Weapon(145, "Satan's Lance", 20000, false));
                default -> {
                    String wpn = "Satan's Lance";
                    int dmg = 150;
                    int price = 20000;
                    for (int c = 0; c < player.getWeaponsBought() - 5; c++) {
                        wpn += "+";
                        dmg += 50;
                        price += 5000;
                    }
                    shop.setWeapon(new Weapon(dmg, wpn, price, false));
                }
            }
            switch (player.getArmorBought()) {
                case 0 -> shop.setArmor(new Armor(2, "tunic", 50, false));
                case 1 -> shop.setArmor(new Armor(5, "armor", 100, false));
                case 2 -> shop.setArmor(new Armor(10, "Tuxedo", 250, false));
                case 3 -> shop.setArmor(new Armor(20, "Gundam", 2500, false));
                case 4 -> shop.setArmor(new Armor(45, "Music Suit", 10000, false));
                case 5 -> shop.setArmor(new Armor(145, "God's Robes", 20000, false));
                default -> {
                    String amr = "God's Robes";
                    int def = 150;
                    int price = 20000;
                    for (int c = 0; c < player.getWeaponsBought() - 5; c++) {
                        amr += "+";
                        def += 50;
                        price += 5000;
                    }
                    shop.setWeapon(new Weapon(def, amr, price, false));
                }
            }
            switch (player.getWeaponsBought()) {
                case 0 -> shop.setWand(new Wand(2, "branch", 50, false));
                case 1 -> shop.setWand(new Wand(5, "wand", 100, false));
                case 2 -> shop.setWand(new Wand(10, "Starlight", 250, false));
                case 3 -> shop.setWand(new Wand(25, "Phoenix", 2500, false));
                case 4 -> shop.setWand(new Wand(50, "Music Baton", 10000, false));
                case 5 -> shop.setWand(new Wand(150, "Jesus's Blessings", 20000, false));
                default -> {
                    String wnd = "Jesus's Blessings";
                    int dmg = 150;
                    int price = 20000;
                    for (int c = 0; c < player.getWeaponsBought() - 5; c++) {
                        wnd += "+";
                        dmg += 50;
                        price += 5000;
                    }
                    shop.setWeapon(new Weapon(dmg, wnd, price, false));
                }
            }

            boolean invalidInput = true;
            while (invalidInput) {
                try {
                    System.out.print("\nSelect an option: 1 to shop, or 2 to exit the village. ");
                    int choice = sc.nextInt();
                    if (choice > 2 || choice < 1) {
                        System.out.println("Invalid input.");
                        invalidInput = true;
                    }
                    else {
                        invalidInput = false;
                        switch (choice) {
                            case 1 -> {
                                System.out.println("\nWeapon: " + shop.getWeapon() + "\tCost: " + shop.getWeaponPrice() + " (press 1 to buy)");
                                System.out.println("Armor: " + shop.getArmor() + "\tCost: " + shop.getArmorPrice() + " (press 2 to buy)");
                                System.out.println("Wand: " + shop.getWand() + "\tCost: " + shop.getWandPrice() + " (press 3 to buy)");
                                System.out.println("Press 4 to exit.");
                                boolean invInput = true;
                                while (invInput) {
                                    try {
                                        int buy = sc.nextInt();
                                        if (buy > 4 || buy < 1) {
                                            System.out.println("Invalid input.");
                                            invInput = true;
                                        }
                                        else {
                                            invInput = false;
                                            switch (buy) {
                                                case 1 -> {
                                                    if (player.getCoins() >= shop.getWeaponPrice()) {
                                                        player.buyWeapon(shop);
                                                    }
                                                }
                                                case 2 -> {
                                                    if (player.getCoins() >= shop.getArmorPrice()) {
                                                        player.buyArmor(shop);
                                                    }
                                                }
                                                case 3 -> {
                                                    if (player.getCoins() >= shop.getWandPrice()) {
                                                        player.buyWand(shop);
                                                    }
                                                }
                                                case 4 -> {
                                                    break;
                                                }
                                            }

                                        }

                                    }
                                    catch (InputMismatchException inputException) {
                                        System.out.println("Invalid input.");
                                        sc.nextLine();
                                    }
                                }
                            }
                            case 2 -> {
                                while(true) {
                                    travel();
                                }
                            }
                        }
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    sc.nextLine();
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
        Word playerweapon = new Word("" + player.getCurrWeapon().getName());
        playerweapon.translate(800, 409);
        Word playerarmor = new Word("" + player.getCurrArmor().getName());
        playerarmor.translate(800, 479);
        Word playerwand = new Word("" + player.getCurrWand().getName());
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
        System.out.println("Here are your current quests: ");
        System.out.println(player.getQuests());
        for (int c = 0; c < player.getQuests().size(); c++) {
            player.resolveQuest(c);
        }
        System.out.println("Here's your quests after resolving completed ones: ");
        System.out.println(player.getQuests());
    }

    /*public static void inventory() {
        boolean invalidInput = true;
        while (invalidInput) {
            try {
                System.out.println("Select 1 for items, 2 for weapons, 3 for armor, 4 for wands, or 5 to go back: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> {
                        System.out.println(player.getItems());
                        invalidInput = false;
                    }
                    case 2 -> {
                        invalidInput = false;
                        boolean invInput = true;
                        while (invInput) {
                            try {
                                System.out.println(player.getWeapons());
                                System.out.println("Select 1 to equip by name, 2 to equip by index, or 3 to go back: ");
                                int option = sc.nextInt();
                                switch (option) {
                                    case 1 -> {
                                        invInput = false;
                                        boolean invInp = true;
                                        while (invInp) {
                                            try {
                                                System.out.println("Select the index to equip it, or press 0 to go back: ");
                                                int equip = sc.nextInt();
                                                if (equip - 1 >= 0 && equip - 1 < player.getWeapons().size()) {
                                                    player.equipWeapon(equip - 1);
                                                    System.out.println("Weapon equipped!");
                                                    invInp = false;
                                                }
                                                else if (equip == 0)
                                                    invInp = false;
                                                else {
                                                    System.out.println("Invalid input. Try again.");
                                                }
                                            } catch (InputMismatchException bru) {
                                                System.out.println("Invalid input. Try again.");
                                                sc.nextLine();
                                            }
                                        }
                                    }
                                    case 2 -> {
                                        invInput = false;
                                        boolean invInp = true;
                                        while (invInp) {
                                            System.out.println("Input the weapon name, or press 0 to exit: ");
                                            String name = sc.nextLine();
                                            if (player.findWeaponLower(name) != null) {
                                                player.equipWeapon(player.findWeaponLower(name));
                                                invInp = false;
                                            }
                                            else if (name == "0") {
                                                invInp = false;
                                            }
                                            else
                                                System.out.println("Invalid input. Try again.");
                                        }
                                    }
                                    case 3 -> invInput = false;
                                }
                            }
                            catch (InputMismatchException bruh) {
                                System.out.println("Invalid input. Try again.");
                                sc.nextLine();
                            }
                        }
                    }
                    case 3 -> {
                        invalidInput = false;
                        boolean invInput = true;
                        while (invInput) {
                            try {
                                System.out.println(player.getArmors());
                                System.out.println("Select 1 to equip by name, 2 to equip by index, or 3 to go back: ");
                                int option = sc.nextInt();
                                switch (option) {
                                    case 1 -> {
                                        invInput = false;
                                        boolean invInp = true;
                                        while (invInp) {
                                            try {
                                                System.out.println("Select the index to equip it, or press 0 to go back: ");
                                                int equip = sc.nextInt();
                                                if (equip - 1 >= 0 && equip - 1 < player.getArmors().size()) {
                                                    player.equipArmor(equip - 1);
                                                    System.out.println("Armor equipped!");
                                                    invInp = false;
                                                }
                                                else if (equip == 0)
                                                    invInp = false;
                                                else {
                                                    System.out.println("Invalid input. Try again.");
                                                }
                                            } catch (InputMismatchException bru) {
                                                System.out.println("Invalid input. Try again.");
                                                sc.nextLine();
                                            }
                                        }
                                    }
                                    case 2 -> {
                                        invInput = false;
                                        boolean invInp = true;
                                        while (invInp) {
                                            System.out.println("Input the armor name, or press 0 to exit: ");
                                            String name = sc.nextLine();
                                            if (player.findArmorLower(name) != null) {
                                                player.equipArmor(player.findArmorLower(name));
                                                invInp = false;
                                            }
                                            else if (name == "0") {
                                                invInp = false;
                                            }
                                            else
                                                System.out.println("Invalid input. Try again.");
                                        }
                                    }
                                    case 3 -> invInput = false;
                                }
                            }
                            catch (InputMismatchException bruh) {
                                System.out.println("Invalid input. Try again.");
                                sc.nextLine();
                            }
                        }
                    }
                    case 4 -> {
                        invalidInput = false;
                        boolean invInput = true;
                        while (invInput) {
                            try {
                                System.out.println(player.getWands());
                                System.out.println("Select 1 to equip by name, 2 to equip by index, or 3 to go back: ");
                                int option = sc.nextInt();
                                switch (option) {
                                    case 1 -> {
                                        invInput = false;
                                        boolean invInp = true;
                                        while (invInp) {
                                            try {
                                                System.out.println("Select the index to equip it, or press 0 to go back: ");
                                                int equip = sc.nextInt();
                                                if (equip - 1 >= 0 && equip - 1 < player.getWands().size()) {
                                                    player.equipWand(equip - 1);
                                                    System.out.println("Wand equipped!");
                                                    invInp = false;
                                                }
                                                else if (equip == 0)
                                                    invInp = false;
                                                else {
                                                    System.out.println("Invalid input. Try again.");
                                                }
                                            } catch (InputMismatchException bru) {
                                                System.out.println("Invalid input. Try again.");
                                                sc.nextLine();
                                            }
                                        }
                                    }
                                    case 2 -> {
                                        invInput = false;
                                        boolean invInp = true;
                                        while (invInp) {
                                            System.out.println("Input the wand name, or press 0 to exit: ");
                                            String name = sc.nextLine();
                                            if (player.findWandLower(name) != null) {
                                                player.equipWand(player.findWandLower(name));
                                                invInp = false;
                                            }
                                            else if (name == "0") {
                                                invInp = false;
                                            }
                                            else
                                                System.out.println("Invalid input. Try again.");
                                        }
                                    }
                                    case 3 -> invInput = false;
                                }
                            }
                            catch (InputMismatchException bruh) {
                                System.out.println("Invalid input. Try again.");
                                sc.nextLine();
                            }
                        }
                    }
                    case 5 -> invalidInput = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Try again.");
                sc.nextLine();
            }
        }
        menu();
    } */

    //copied this from https://www.tutorialspoint.com/java/java_serialization.htm lol
    public static void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream(savePath + "\\" + player.getName() + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(player);
            out.close();
            fileOut.close();
            Picture saved = new Picture(picturePath.toAbsolutePath() + "\\saved.png");
            saved.draw();
            Canvas.pause(500);
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
            Picture loaded = new Picture(picturePath.toAbsolutePath() + "\\loaded.png");
            loaded.draw();
            Canvas.pause(500);
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
        System.out.println(LastClick.click);
    }

    public void keyPress(String s) {
        // enter code here

    }
}