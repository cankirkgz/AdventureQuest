package Game.Locations;

import Game.Obstacles.Bear;
import Game.Player;

public class River extends BattleLocations{
    public River(Player player) {
        super(player, "Nehir", new Bear(), "Su", 2);
    }


}
