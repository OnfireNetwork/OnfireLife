package net.onfirenetwork.onfirelife;

import net.onfirenetwork.onfirelife.inventory.CharacterInventory;
import net.onfirenetwork.onfirelife.inventory.ContainerInventory;
import net.onfirenetwork.onfirelife.inventory.Material;
import net.onfirenetwork.onsetjava.entity.Player;
import org.javawebstack.abstractdata.AbstractArray;
import org.javawebstack.abstractdata.AbstractElement;
import org.javawebstack.abstractdata.AbstractPrimitive;

public class Vue {

    public void emit(Player player, String name, AbstractElement... params) {
        AbstractArray paramsArray = new AbstractArray();
        for(AbstractElement param : params)
            paramsArray.add(param);
        player.callRemoteEvent("vue:emit", name, paramsArray.toJsonString());
    }

    public void sendItemMaterials(Player player) {
        emit(player, "onset:inventory:materials", Material.getConfig());
    }

    public void updateContainerInventory(Player player, ContainerInventory inventory) {
        emit(player, "onset:inventory:container:update", inventory.getData());
    }

    public void updateCharacterInventory(Player player, CharacterInventory inventory) {
        emit(player, "onset:inventory:character:update", inventory.getData());
    }

    public void openInventory(Player player) {
        emit(player, "onset:inventory:open");
    }

    public void closeInventory(Player player) {
        emit(player, "onset:inventory:close");
    }

    public void switchHotbarSlot(Player player, int slot) {
        emit(player, "onset:inventory:hotbar:switch", new AbstractPrimitive(slot));
    }

}
