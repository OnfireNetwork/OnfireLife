package net.onfirenetwork.onfirelife.inventory;

import lombok.Getter;
import org.javawebstack.abstractdata.AbstractObject;

public class CharacterInventory {

    @Getter
    final InventoryArea generic;
    @Getter
    final InventoryArea clothing;
    @Getter
    final InventoryArea hotbar;
    @Getter
    ItemStack cursor;

    public CharacterInventory() {
        generic = new InventoryArea(30);
        clothing = new InventoryArea(4);
        hotbar = new InventoryArea(4);
        cursor = null;
    }

    public CharacterInventory(AbstractObject data) {
        generic = new InventoryArea(30, data.get("generic").array());
        clothing = new InventoryArea(4, data.get("clothing").array());
        hotbar = new InventoryArea(4, data.get("hotbar").array());
        cursor = data.get("cursor").isNull() ? null : new ItemStack(data.get("cursor").object());
    }

    public CharacterInventory setCursor(ItemStack cursor) {
        this.cursor = cursor;
        return this;
    }

    public int add(ItemStack item) {
        return generic.add(item);
    }

    public AbstractObject getData() {
        AbstractObject data = new AbstractObject();
        data.set("generic", generic.getData());
        data.set("clothing", clothing.getData());
        data.set("hotbar", hotbar.getData());
        data.set("cursor", cursor == null ? null : cursor.getData());
        return data;
    }

}
