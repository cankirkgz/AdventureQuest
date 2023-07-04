package Game.Locations;

import Game.Obstacles.Snake;
import Game.Player;

public class Mine extends BattleLocations{
    public Mine(Player player) {
        super(player, "Maden", new Snake(), "Ganimet", 5);
    }
}
