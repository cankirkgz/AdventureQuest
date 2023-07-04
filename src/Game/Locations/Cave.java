package Game.Locations;

import Game.Obstacles.Obstacle;
import Game.Obstacles.Zombie;
import Game.Player;

public class Cave extends BattleLocations{
    public Cave(Player player) {
        super(player, "Mağara", new Zombie(), "Yemek", 3);
    }


}
