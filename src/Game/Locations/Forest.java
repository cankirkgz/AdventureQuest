package Game.Locations;

import Game.Obstacles.Obstacle;
import Game.Obstacles.Vampire;
import Game.Player;

public class Forest extends BattleLocations{
    public Forest(Player player) {
        super(player, "Orman", new Vampire(), "Odun", 3);
    }


}
