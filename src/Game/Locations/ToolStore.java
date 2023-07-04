package Game.Locations;

import Game.Armors.Armor;
import Game.Armors.HeavyArmor;
import Game.Armors.LightArmor;
import Game.Armors.MediumArmor;
import Game.Player;
import Game.Weapons.Gun;
import Game.Weapons.Rifle;
import Game.Weapons.Sword;
import Game.Weapons.Weapon;

public class ToolStore extends NormalLocations{
    public ToolStore(Player player) {
        super(player, "Mağaza");
    }
    Player currentUser = this.getPlayer();

    @Override
    public boolean onLocation() {
        System.out.println("------ Mağazaya hoş geldin! İhtiyaçlarını karşılamak için buradayız. ------");
        boolean showMenu = true;
        while (showMenu) {
            System.out.println("\nMağazada bulunan eşyalar:");
            System.out.println("1- Silahlar:");
            System.out.println("2- Zırhlar:");
            System.out.println("3- Çıkış yap:");
            System.out.print("Seçiminizi yapınız: ");

            int selected = scanner.nextInt();
            while (!((selected == 1) || (selected == 2) || (selected == 3))) {
                System.out.println("Hatalı giriş yaptınız. Lütfen 1, 2 ya da 3 sayılarından birini seçiniz.");
                selected = scanner.nextInt();
            }

            switch (selected) {
                case 1:
                    printWeapon();
                    break;
                case 2:
                    printArmor();
                    break;
                case 3:
                    System.out.println("Tekrar bekleriz.");
                    showMenu = false;
                    return (true);
            }
        }
        return true;
    }

    private void printWeapon(){
        Weapon[] weapons = {new Gun(), new Sword(), new Rifle()};
        System.out.println("Silah seçildi.");
        System.out.println("Silahlar:");
        for (Weapon weapon : weapons) {
            System.out.println(weapon.getId() + ". " + weapon.getName() + " - Hasar: " + weapon.getDamage() + ", Ücret: " + weapon.getPrice());
        }
        System.out.println("\nLütfen satın almak istediğin eşyanın ID numarasını gir. Çıkış yapmak için '0' tuşuna bas:\n");
        int selected = scanner.nextInt();
        if (selected == 0)
            return;
        while (!((selected == 1) || (selected == 2) || (selected == 3))) {
            System.out.println("Hatalı giriş yaptınız. Lütfen 1, 2 ya da 3 sayılarından birini seçiniz.");
            selected = scanner.nextInt();
        }
        buyWeapon(weapons[selected-1]);
    }

    private void buyWeapon(Weapon weapon) {
        if (weapon.getPrice() > currentUser.getMoney()) {
            System.out.println("Yeterli paranız bulunmamaktadır. Paranız: " + currentUser.getMoney());
        } else {
            System.out.println(weapon.getName() + " silahını satın aldınız! ");
            int currentMoney = currentUser.getMoney() - weapon.getPrice();
            currentUser.setMoney(currentMoney);
            System.out.println("Kalan paranız: " + currentUser.getMoney());
            currentUser.getInventory().setWeapon(weapon);
            System.out.println("Yeni silahınız: " + currentUser.getInventory().getWeapon().getName());
        }
    }
    private void printArmor(){
        Armor[] armors = {new LightArmor(), new MediumArmor(), new HeavyArmor()};
        System.out.println("Zırh seçildi.");
        System.out.println("Zırhlar:");
        for (Armor armor : armors) {
            System.out.println(armor.getId() + ". " + armor.getName() + " - Engelleme: " + armor.getBlock() + ", Ücret: " + armor.getPrice());
        }
        System.out.println("\nLütfen satın almak istediğin eşyanın ID numarasını gir. Çıkış yapmak için '0' tuşuna bas:\n");
        int selected = scanner.nextInt();
        if (selected == 0)
            return;
        while (!((selected == 1) || (selected == 2) || (selected == 3))) {
            System.out.println("Hatalı giriş yaptınız. Lütfen 1, 2 ya da 3 sayılarından birini seçiniz.");
            selected = scanner.nextInt();
        }
        buyArmor(armors[selected-1]);
    }
    private void buyArmor(Armor armor) {
        if (armor.getPrice() > currentUser.getMoney()) {
            System.out.println("Yeterli paranız bulunmamaktadır. Paranız: " + currentUser.getMoney());
        } else {
            System.out.println(armor.getName() + " zırhını satın aldınız! ");
            int currentMoney = currentUser.getMoney() - armor.getPrice();
            currentUser.setMoney(currentMoney);
            System.out.println("Kalan paranız: " + currentUser.getMoney());
            currentUser.getInventory().setArmor(armor);
            System.out.println("Yeni zırhınız: " + currentUser.getInventory().getArmor().getName());
        }
    }
}
