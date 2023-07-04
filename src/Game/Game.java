package Game;

import Game.Locations.*;
import java.util.Scanner;

public class Game {
    private Player player; // Oyuncu nesnesi
    private Location location; // Konum nesnesi
    private Scanner scanner = new Scanner(System.in); // Kullanıcıdan giriş almak için Scanner nesnesi

    // Empty constructor for the Game class
    public Game() {}

    // Constructor for the Game class
    public Game(Player player, Location location) {
        this.player = player;
        this.location = location;
    }

    // Getter and setter methods for player
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    // Getter and setter methods for location
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    // Start the game
    public void start() {
        System.out.println("Macera oyununa hoş geldiniz!");
        System.out.print("Lütfen bir isim giriniz: ");
        String playerName = scanner.next();
        player = new Player(playerName); // Oyuncu nesnesi oluşturuluyor // Create a player object
        System.out.println("Hoşgeldin " + player.getPlayerName());
        System.out.println("Bir zamanlar uzak bir diyarın sakinleri huzur içinde yaşarken, " +
                "korkunç bir karanlık güç dünyayı tehdit etmeye başladı. \nBu güç, kötülük dolu " +
                "canavarları ortaya çıkarıyor ve masum insanları korku içinde bırakıyordu.");
        player.selectChar(); // Oyuncunun karakteri seçiliyor // Let the player select a character

        Location[] locations = {
                new SaveHouse(player), // Güvenli Ev konumu
                new ToolStore(player), // Mağaza konumu
                new Cave(player), // Mağara konumu
                new Forest(player), // Orman konumu
                new River(player), // Nehir konumu
                new Mine(player)
        };

        while (true) {
            player.getInfo(); // Oyuncu bilgileri gösteriliyor
            System.out.println();
            System.out.println("########### Nereye gitmek istiyorsun? ###########");
            System.out.println();
            System.out.println("1. Güvenli Ev\n2. Mağaza\n3. Mağara\n4. Orman\n5. Nehir\n6. Maden\n7. Envanteri gör\n0. Çıkış yap");
            System.out.print("Lütfen seçimin için bir sayı gir: ");
            int loc = scanner.nextInt();

            while (loc < 0 || loc > 7) { // Geçersiz bir seçim yapılırsa tekrar giriş isteniyor
                System.out.println("Hatalı giriş yaptınız. Lütfen 0, 1, 2, 3, 4, 5, 6 veya 7 sayılarından birini seçiniz.");
                loc = scanner.nextInt();
            }

            if (loc == 0) {
                System.out.println("Başarıyla çıkış yapıldı.");
                break;
            }

            if (loc == 7) {
                player.showInventory(); // Oyuncunun envanteri gösteriliyor
            } else if (!locations[loc - 1].onLocation()) { // Seçilen konumda işlem yapılamazsa oyun kontrol ediliyor
                if (player.getInventory().checkAward()) {
                    System.out.println();
                } else {
                    System.out.println("GAME OVER.");
                }
                break;
            }
        }
    }
}
