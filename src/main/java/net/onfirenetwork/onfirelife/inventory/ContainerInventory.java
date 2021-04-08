package net.onfirenetwork.onfirelife.inventory;

import lombok.Getter;
import org.javawebstack.abstractdata.AbstractObject;

public class ContainerInventory {

    @Getter
    String title;
    final InventoryArea items;

    public ContainerInventory(String title, int size) {
        this.title = title;
        this.items = new InventoryArea(size);
    }

    public ContainerInventory(int size, AbstractObject data) {
        this.title = data.get("title").string();
        this.items = new InventoryArea(size, data.get("items").array());
    }

    public ContainerInventory(AbstractObject data) {
        this(data.get("size").number().intValue(), data);
    }

    public ContainerInventory setTitle(String title) {
        this.title = title;
        return this;
    }

    public InventoryArea getItems() {
        return items;
    }

    public int add(ItemStack item) {
        return items.add(item);
    }

    public int size() {
        return items.size();
    }

    public AbstractObject getData() {
        return new AbstractObject()
                .set("title", title)
                .set("size", size())
                .set("items", items.getData());
    }

}
