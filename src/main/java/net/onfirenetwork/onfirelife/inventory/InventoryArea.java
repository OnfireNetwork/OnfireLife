package net.onfirenetwork.onfirelife.inventory;

import org.javawebstack.abstractdata.AbstractArray;

public class InventoryArea {

    final ItemStack[] items;

    public InventoryArea(int size) {
        items = new ItemStack[size];
    }

    public InventoryArea(int size, AbstractArray data) {
        this(size);
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).isObject())
                set(i, new ItemStack(data.get(i).object()));
        }
    }

    public ItemStack get(int i) {
        return items[i];
    }

    public InventoryArea set(int i, ItemStack item) {
        items[i] = item;
        return this;
    }

    public int size() {
        return items.length;
    }

    public int add(ItemStack item) {
        int remaining = item.getAmount();
        for(int i = 0; i < size() && remaining > 0; i++) {
            if(items[i] != null && !items[i].isSimilar(item))
                continue;
            if(items[i] == null) {
                items[i] = item.clone();
                items[i].setAmount(Math.min(item.getType().getMax(), remaining));
                remaining -= items[i].getAmount();
            } else {
                int added = Math.min(items[0].getType().getMax() - items[0].getAmount(), remaining);
                items[i].setAmount(items[i].getAmount() + added);
                remaining -= added;
            }
        }
        return item.getAmount() - remaining;
    }

    public int getAmount(ItemStack item) {
        int amount = 0;
        for(int i = 0; i < size(); i++) {
            if(items[i] != null && items[i].isSimilar(item))
                amount += items[i].getAmount();
        }
        return amount;
    }

    public boolean has(ItemStack item) {
        return getAmount(item) >= item.getAmount();
    }

    public int remove(ItemStack item) {
        int remaining = item.getAmount();
        for(int i = size() - 1; i >= 0 && remaining > 0; i--) {
            if(items[i] == null || !items[i].isSimilar(item))
                continue;
            int removed = Math.min(items[i].getAmount(), remaining);
            if(removed == items[i].getAmount()) {
                items[i] = null;
            } else {
                items[i].setAmount(items[i].getAmount() - removed);
            }
            remaining -= removed;
        }
        return item.getAmount() - remaining;
    }

    public AbstractArray getData() {
        AbstractArray data = new AbstractArray();
        for(int i = 0; i < size(); i++) {
            if(items[i] != null)
                data.set(i, items[i].getData());
        }
        return data;
    }

}
