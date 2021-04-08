package net.onfirenetwork.onfirelife.listener;

import net.onfirenetwork.onfirelife.model.BanModel;
import net.onfirenetwork.onfirelife.model.PlayerModel;
import net.onfirenetwork.onsetjava.plugin.event.EventHandler;
import net.onfirenetwork.onsetjava.plugin.event.player.PlayerSteamAuthEvent;
import org.javawebstack.orm.Repo;

public class PlayerListener {

    @EventHandler
    public void onSteamAuth(PlayerSteamAuthEvent e) {
        PlayerModel playerModel = Repo.get(PlayerModel.class).where("steamId", e.getPlayer().getSteamId()).first();
        if(playerModel == null) {
            playerModel = new PlayerModel();
            playerModel.setSteamId(e.getPlayer().getSteamId());
        }
        playerModel.setUsername(e.getPlayer().getName());
        playerModel.save();
        BanModel ban = Repo.get(BanModel.class).where("playerId", playerModel.getId()).first();
        if(ban != null) {
            e.getPlayer().kick("You are banned: " + ban.getReason());
            return;
        }
    }

}
