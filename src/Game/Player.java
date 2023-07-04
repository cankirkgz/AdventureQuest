package Game;

import Game.GameCharacters.GameChar;
import Game.GameCharacters.Archer;
import Game.GameCharacters.Knight;
import Game.GameCharacters.Samurai;

import java.util.Scanner;

public class Player {
    private String playerName;
    private int damage;
    private int health;
    private int initHealth;

    private int money;
    private String charName;
    private Inventory inventory;

    Scanner scanner = new Scanner(System.in);

    public Player(String name) {
        this.playerName = name;
        this.inventory = new Inventory();
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public int getTotalDamage() {
        try {
            return (damage + inventory.getWeapon().getDamage());
        } catch (Exception e) {
            System.out.println("HATA!!!: " + e.toString());
        }
        return damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health < 0)
            health = 0;
        this.health = health;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getInitHealth() {
        return initHealth;
    }

    public void setInitHealth(int initHealth) {
        this.initHealth = initHealth;
    }

    public void selectChar() {

        GameChar[] gameChar = {new Samurai(), new Archer(), new Knight()};

        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Şimdi bir karakter seçme zamanı! Seçebileceğin 3 karakter var. Samuray, okçu ya da şovalye.");
        for (GameChar g : gameChar)
            getSpecies(g.getName(), g.getDamage(), g.getHealth(), g.getMoney());

        System.out.println(gameChar[0].getName() + " için '1', \n"
                + gameChar[1].getName() + " için '2', \n"
                + gameChar[2].getName() + " için '3' yazınız.");
        System.out.println("---------------------------------------------------------------------------");
        int character = getPlayerInput(1, 3);
        initPlayer(gameChar[character - 1]);
    }

    private void initPlayer(GameChar gameChar) {
        setCharName(gameChar.getName());
        setHealth(gameChar.getHealth());
        setDamage(gameChar.getDamage());
        setMoney(gameChar.getMoney());
        setInitHealth(gameChar.getHealth());
        System.out.println("Harika! Seçtiğin karakter " + getCharName());
        System.out.println("Özelliklerin:");
        System.out.println("- Hasar: " + getDamage());
        System.out.println("- Sağlık: " + getHealth());
        System.out.println("- Para: " + getMoney());
        System.out.println("Oyuna başlıyorsun...");
        System.out.println("---------------------------------------------------------------------------");
    }

    private void getSpecies(String name, int damage, int health, int money) {
        String result = name + " karakterinin özellikleri: \tSağlık: " + health + "\tHasar: " + damage
                + "\tPara: " + money;
        System.out.println(result);
    }

    public void getInfo() {
        String result = getCharName() + " karakterinin özellikleri: \tSağlık: " + getHealth() + "\tHasar: " + getTotalDamage()
                + "\tPara: " + getMoney() + "\nSilahınız: " + getInventory().getWeapon().getName()
                + "\nZırhınız: " + getInventory().getArmor().getName()
                + "\nBloklama: " + getInventory().getArmor().getBlock();
        System.out.println(result);
    }

    public void showInventory() {
        String inventory = this.getInventory().toString();
        System.out.println(inventory);
    }

    private int getPlayerInput(int min, int max) {
        int input = scanner.nextInt();
        while (input < min || input > max) {
            System.out.println("Hatalı giriş yaptınız. Lütfen " + min + "-" + max + " aralığında bir sayı seçiniz.");
            input = scanner.nextInt();
        }
        return input;
    }
}
