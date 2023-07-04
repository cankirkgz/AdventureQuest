package Game.Locations;

import Game.Armors.Armor;
import Game.Armors.HeavyArmor;
import Game.Armors.LightArmor;
import Game.Armors.MediumArmor;
import Game.Inventory;
import Game.Obstacles.Obstacle;
import Game.Obstacles.Snake;
import Game.Player;
import Game.Weapons.Gun;
import Game.Weapons.Rifle;
import Game.Weapons.Sword;
import Game.Weapons.Weapon;

import java.util.Random;
import java.util.Scanner;

public abstract class BattleLocations extends Location {
    private Obstacle obstacle; // Savaş mekanındaki engel/engeller
    private String award; // Bölgenin ödülü
    private int maxObstacle; // Maksimum engel sayısı
    private Player currentPlayer; // Geçerli oyuncu
    private Obstacle currentObstacle; // Geçerli engel
    private Scanner scanner; // Kullanıcıdan giriş almak için kullanılan tarayıcı
    private int MAX_HEALTH; // Maksimum sağlık seviyesi



    public BattleLocations(Player player, String name, Obstacle obstacle, String award, int maxObstacle) {
        super(player, name);
        this.obstacle = obstacle;
        this.award = award;
        this.maxObstacle = maxObstacle;
        this.currentPlayer = getPlayer();
        this.currentObstacle = getObstacle();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public boolean onLocation() {
        MAX_HEALTH = this.getObstacle().getHealth();
        if (checkThisAward()) {
            System.out.println("Bu bölgedeki ödülü zaten kazandınız. Diğer bölgelerde savaşın!");
            return true;
        } else {
            int obstacleNumber = randomObstacleNumber();
            System.out.println("---------------------------------------------------------------");
            System.out.println("Şu anda " + getName() + " bölgesindesiniz.");
            System.out.println("Dikkatli olun. Bu bölgede " + obstacleNumber + " adet " + getObstacle().getName() + " yaşıyor!");
            System.out.println("1. Savaş\n2. Kaç");
            int selectedNumber = getPlayerInput(1, 2);
            if (selectedNumber == 1) {
                System.out.println("Oyuncu mağaraya gitti ve karşısına " + obstacleNumber + " adet " + getObstacle().getName() + " çıktı.");
                System.out.println("\nSavaş başladı!");
                if (combat(obstacleNumber)) {
                    System.out.println(getName() + " bölgesindeki tüm düşmanları yendiniz!!!");
                    if (!checkThisAward()) {
                        evaluateAward();
                        earnAward();
                    }
                    return true;
                }
                if (currentPlayer.getHealth() <= 0) {
                    System.out.println("Öldünüz!");
                    return false;
                }
            } else if (selectedNumber == 2) {
                System.out.println(getName() + " bölgesinden kaçıldı.");
                if (currentPlayer.getHealth() <= 0) {
                    return false;
                }
            }
            return true;
        }
    }

    // Ödülün değerlendirilmesi ve oyuncuya verilmesi
    private void evaluateAward() {
        switch (award) {
            case "Para":
                currentPlayer.setMoney(currentPlayer.getMoney() + this.getObstacle().getLoot());
                System.out.println("Para ödülünü kazandınız!");
                break;
            case "Silah":
                // Silah ödülü kazanıldığında yapılacak işlemler
                break;
            case "Zırh":
                // Zırh ödülü kazanıldığında yapılacak işlemler
                break;
        }
    }

    // Bu bölgedeki ödülün daha önce kazanılıp kazanılmadığının kontrolü
    private boolean checkThisAward() {
        switch (award) {
            case "Yemek":
                return currentPlayer.getInventory().hasFood();
            case "Odun":
                return currentPlayer.getInventory().hasFirewood();
            case "Su":
                return currentPlayer.getInventory().hasWater();
        }
        return false;
    }

    // Ödülün kazanılması
    private void earnAward() {
        switch (award) {
            case "Yemek":
                currentPlayer.getInventory().setFood(true);
                System.out.println("Yemek ödülünü kazandınız!");
                break;
            case "Odun":
                currentPlayer.getInventory().setFirewood(true);
                System.out.println("Odun ödülünü kazandınız!");
                break;
            case "Su":
                currentPlayer.getInventory().setWater(true);
                System.out.println("Su ödülünü kazandınız!");
                break;
        }
    }

    // Savaş mekanizmasının gerçekleştirilmesi
    private boolean combat(int obstacleNumber) {
        Obstacle[] obstacles = createObstacles(obstacleNumber);
        for (int i = 0; i < obstacleNumber; i++) {
            obstacles[i].setHealth(MAX_HEALTH);
            boolean isPlayerAttackingFirst = getRandomBoolean();
            System.out.println("----------------------------------- Savaşa girildi -----------------------------------");
            if (isPlayerAttackingFirst)
                System.out.println("Siz başlıyorsunuz!");
            else
                System.out.println("Canavar başlıyor!");
            int round = 1;
            printPlayerStats();
            printEnemyStats(obstacles[i]);
            while (currentPlayer.getHealth() > 0 && obstacles[i].getHealth() > 0) {
                if (isPlayerAttackingFirst) {
                    System.out.println("1. VUR\n2. KAÇ");
                    int selectedNumber = getPlayerInput(1, 2);
                    if (selectedNumber == 1) {
                        System.out.println("Round " + round + ":");
                        combatFight(obstacles[i], isPlayerAttackingFirst);
                    } else if (selectedNumber == 2) {
                        return false;
                    }
                } else {
                    System.out.println("Canavar başlıyor!");
                    int obstacleDamage = getAdjustedObstacleDamage(obstacles[i]);
                    currentPlayer.setHealth(currentPlayer.getHealth() - obstacleDamage);
                    System.out.println("Canavar size vurdu. Sağlık seviyeniz: " + currentPlayer.getHealth());
                    if (currentPlayer.getHealth() > 0) {
                        while (true) {
                            System.out.println("1. VUR\n2. KAÇ");
                            int selectedNumber = getPlayerInput(1, 2);
                            if (selectedNumber == 1) {
                                System.out.println("Round " + round + ":");
                                combatFight(obstacles[i], isPlayerAttackingFirst);
                                break;
                            } else if (selectedNumber == 2) {
                                return false;
                            }
                        }
                    }
                }
                round++;
            }
            if (obstacles[i].getHealth() < currentPlayer.getHealth()) {
                calculateEnemiesLoot(obstacles[i], i + 1);
            } else if (obstacles[i].getHealth() > currentPlayer.getHealth()) {
                System.out.println("Düşman sizi öldürdü...");
                return false;
            }
        }
        return true;
    }

    private void calculateEnemiesLoot(Obstacle obstacle, int enemyCount) {
        if (obstacle.getName().equals("Yılan")) {
            int lootChance = new Random().nextInt(100) + 1;
            Inventory inventory = currentPlayer.getInventory();
            if (lootChance <= 15) {
                currentPlayer.getInventory().setWeapon(generateRandomWeapon());
                System.out.println("Tebrikler! " + enemyCount + ". yılanı yendiniz.");
            } else if (lootChance <= 30) {
                currentPlayer.getInventory().setArmor(generateRandomArmor());
                System.out.println("Tebrikler! " + enemyCount + ". yılanı yendiniz.");
            } else if (lootChance <= 55) {
                System.out.println("Tebrikler! " + enemyCount + ". yılanı yendiniz.");
                int moneyEarned = generateRandomMoney();
                System.out.println(moneyEarned + " para kazandınız!");
                currentPlayer.setMoney(currentPlayer.getMoney() + moneyEarned);
            } else {
                System.out.println(enemyCount + ". yılanı yendiniz, ancak hiçbir şey kazanamadınız...");
            }
        } else {
            System.out.println("Tebrikler! " + enemyCount + ". düşmanı yendiniz." +
                    "\nDüşmanın üzerinden " + obstacle.getLoot() + " kadar ganimet çıktı.");
            currentPlayer.setMoney(currentPlayer.getMoney() + obstacle.getLoot());
        }
    }

    private Weapon generateRandomWeapon() {
        int weaponChance = new Random().nextInt(100) + 1;
        if (weaponChance <= 20) {
            return new Rifle();
        } else if (weaponChance <= 50) {
            return new Sword();
        } else {
            return new Gun();
        }
    }

    private Armor generateRandomArmor() {
        int armorChance = new Random().nextInt(100) + 1;
        if (armorChance <= 20) {
            return new HeavyArmor();
        } else if (armorChance <= 50) {
            return new MediumArmor();
        } else {
            return new LightArmor();
        }
    }

    private int generateRandomMoney() {
        int moneyChance = new Random().nextInt(100) + 1;
        if (moneyChance <= 20) {
            return 10;
        } else if (moneyChance <= 50) {
            return 5;
        } else {
            return 1;
        }
    }

    // Rastgele bir boolean değer döndürme
    private boolean getRandomBoolean() {
        return new Random().nextBoolean();
    }

    // Kullanıcı girişini alırken sınırlarını kontrol etme
    private int getPlayerInput(int min, int max) {
        int selectedNumber = scanner.nextInt();
        while (!(selectedNumber >= min && selectedNumber <= max)) {
            System.out.println("Hatalı giriş yaptınız. Lütfen " + min + " veya " + max + " sayısından birini seçin.");
            selectedNumber = scanner.nextInt();
        }
        return selectedNumber;
    }

    // Savaş sırasındaki dövüş mekanizması
    private void combatFight(Obstacle obstacle, boolean isPlayerAttackingFirst) {
        if (isPlayerAttackingFirst) {
            obstacle.setHealth(obstacle.getHealth() - currentPlayer.getTotalDamage());
            System.out.println("Canavara vurdunuz. Canavarın sağlık seviyesi: " + obstacle.getHealth());
            if (obstacle.getHealth() > 0) {
                int obstacleDamage = getAdjustedObstacleDamage(obstacle);
                currentPlayer.setHealth(currentPlayer.getHealth() - obstacleDamage);
                System.out.println("Canavar size vurdu. Sağlık seviyeniz: " + currentPlayer.getHealth());
            }
        } else {
            if (currentPlayer.getHealth() > 0) {
                obstacle.setHealth(obstacle.getHealth() - currentPlayer.getTotalDamage());
                System.out.println("Canavara vurdunuz. Canavarın sağlık seviyesi: " + obstacle.getHealth());
            }
        }
    }

    // Düşmanın hasarını ayarlamak için oyuncunun zırhını kontrol etme
    private int getAdjustedObstacleDamage(Obstacle obstacle) {
        int obstacleDamage = obstacle.getDamage() - currentPlayer.getInventory().getArmor().getBlock();
        if (obstacleDamage < 0)
            obstacleDamage = 0;
        return obstacleDamage;
    }

    // Belirli bir sayıda engel oluşturmak için yılan oluşturma
    private Obstacle[] createObstacles(int obstacleNumber) {
        Obstacle[] obstacles = new Obstacle[obstacleNumber];
        for (int i = 0; i < obstacleNumber; i++) {
            obstacles[i] = createRandomSnake();
        }
        return obstacles;
    }

    // Rastgele bir yılan oluşturma
    private Obstacle createRandomSnake() {
        if (this.getObstacle().getName().equals("Yılan")) {
            return (new Snake());
        } else
            return this.getObstacle();
    }

    // Düşmanın istatistiklerini yazdırma
    private void printEnemyStats(Obstacle obstacle) {
        System.out.println("\n" + obstacle.getName() + " canavarının değerleri\n---------------------------------");
        System.out.println("Canavarın sağlık seviyesi: " + obstacle.getHealth()
                + "\nCanavarın hasar seviyesi: " + obstacle.getDamage());
    }

    // Oyuncunun istatistiklerini yazdırma
    private void printPlayerStats() {
        System.out.println("\nOyuncunun değerleri\n---------------------------------");
        System.out.println("Oyuncunun sağlık seviyesi: " + currentPlayer.getHealth()
                + "\nOyuncunun hasar seviyesi: " + currentPlayer.getTotalDamage()
                + "\nOyuncunun parası: " + currentPlayer.getMoney()
                + "\nSilah: " + currentPlayer.getInventory().getWeapon().getName()
                + "\nZırh: " + currentPlayer.getInventory().getArmor().getName());
    }

    // Rastgele engel sayısı seçimi
    private int randomObstacleNumber() {
        return new Random().nextInt(maxObstacle) + 1;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }
}
