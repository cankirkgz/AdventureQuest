package Game.Locations;

import Game.Player;

public abstract class NormalLocations extends Location{
    public NormalLocations(Player player, String name) {
        super(player, name);
    }

    @Override
    public boolean onLocation() {
        return true;
    }
}
