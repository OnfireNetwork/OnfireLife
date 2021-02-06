package net.onfirenetwork.onfirelife.command;

import net.onfirenetwork.onsetjava.Onset;
import net.onfirenetwork.onsetjava.entity.Player;
import net.onfirenetwork.onsetjava.entity.Vehicle;
import net.onfirenetwork.onsetjava.plugin.CommandExecutor;

public class VCommand implements CommandExecutor {

    public boolean onCommand(Player player, String command, String[] args) {
        if(args.length == 0) {
            player.sendMessage("/v <model>");
            return true;
        }
        Vehicle vehicle = Onset.getServer().createVehicle(player.getLocationAndHeading(), Integer.parseInt(args[0]));
        player.enterVehicle(vehicle);
        player.sendMessage("Vehicle spawned!");
        return true;
    }

}
