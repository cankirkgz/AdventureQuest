package Game;

import Game.Armors.Armor;
import Game.Weapons.Weapon;

public class Inventory {
    private Weapon weapon;
    private Armor armor;
    private boolean food;
    private boolean firewood;
    private boolean water;

    // Constructor for the Inventory class
    public Inventory() {
        this.weapon = new Weapon(0, "Yumruk", 0, 0) {};
        this.armor = new Armor(0, "Yok", 0, 0) {};
        this.food = false;
        this.firewood = false;
        this.water = false;
    }

    // Getter and setter methods for weapon
    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        if (weapon.getId() > this.weapon.getId()) {
            this.weapon = weapon;
            System.out.println("Yeni silah kazandınız: " + weapon.getName());
        } else {
            System.out.println("Daha iyi bir silahınız zaten var: " + this.weapon.getName());
        }
    }

    // Getter and setter methods for armor
    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        if (armor.getId() > this.armor.getId()) {
            this.armor = armor;
            System.out.println("Yeni zırh kazandınız: " + armor.getName());
        } else {
            System.out.println("Daha iyi bir zırhınız zaten var: " + this.armor.getName());
        }
    }

    // Getter and setter methods for food
    public boolean hasFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    // Getter and setter methods for firewood
    public boolean hasFirewood() {
        return firewood;
    }

    public void setFirewood(boolean firewood) {
        this.firewood = firewood;
    }

    // Getter and setter methods for water
    public boolean hasWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    // Generates a string representation of the inventory
    public String toString() {
        String foodStatus = hasFood() ? "Mevcut" : "Mevcut değil";
        String firewoodStatus = hasFirewood() ? "Mevcut" : "Mevcut değil";
        String waterStatus = hasWater() ? "Mevcut" : "Mevcut değil";

        String result = "ENVANTERİN\n" +
                "--------------------------------------------------\n" +
                "Silah: " + getWeapon().getName() + "\n" +
                "Zırh: " + getArmor().getName() + "\n" +
                "Yemek: " + foodStatus + "\n" +
                "Odun: " + firewoodStatus + "\n" +
                "Su: " + waterStatus + "\n" +
                "--------------------------------------------------";

        return result;
    }

    // Checks if the player has obtained all the necessary items
    public boolean checkAward() {
        return hasWater() && hasFood() && hasFirewood();
    }
}
