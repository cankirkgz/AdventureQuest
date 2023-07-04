package Game.Locations;

import Game.Inventory;
import Game.Player;

public class SaveHouse extends NormalLocations {
    public SaveHouse(Player player) {
        super(player, "Güvenli Ev"); // NormalLocations sınıfının yapıcısını çağırarak "Güvenli Ev" konumunu oluşturuyoruz
    }

    @Override
    public boolean onLocation() {
        Player currentUser = getPlayer(); // Mevcut oyuncuyu alıyoruz
        currentUser.setHealth(currentUser.getInitHealth()); // Oyuncunun canını yeniliyoruz
        System.out.println("Güvenli Evdesiniz.\nCanınız yenilendi.");

        if (currentUser.getInventory().checkAward()) {
            System.out.println("Tebrikler!!! Tüm ödülleri topladınız. Artık Güvenli evde hayatta kalabilirsiniz." +
                    "\nOynadığınız için teşekkürler.");
            return false; // Tüm ödüller toplandıysa false döndürerek oyunun bitirilmesini sağlıyoruz
        } else {
            return true; // Ödüller toplanmadıysa true döndürerek oyuna devam edilmesini sağlıyoruz
        }
    }
}
