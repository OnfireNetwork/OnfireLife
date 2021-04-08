package net.onfirenetwork.onfirelife.inventory;

import lombok.Getter;
import org.javawebstack.abstractdata.AbstractObject;

public class ItemStack {

    @Getter
    final AbstractObject data;

    public ItemStack(Material type) {
        this(type, 1);
    }

    public ItemStack(Material type, int amount) {
        data = new AbstractObject().set("metadata", type.getTemplate().clone());
        setType(type).setAmount(amount);
    }

    public ItemStack(AbstractObject data) {
        this.data = data;
    }

    public ItemStack setType(Material material) {
        data.set("type", material.name());
        return this;
    }

    public Material getType() {
        return Material.valueOf(data.get("type").string());
    }

    public ItemStack setAmount(int amount) {
        data.set("amount", amount);
        return this;
    }

    public int getAmount() {
        return data.get("amount").number().intValue();
    }

    private AbstractObject getMetadata() {
        return data.get("metadata").object();
    }

    public ItemStack setName(String name) {
        getMetadata().set("name", name);
        return this;
    }

    public String getName() {
        return getMetadata().get("name").string();
    }

    public ItemStack setAmmo(int ammo) {
        getMetadata().set("ammo", ammo);
        return this;
    }

    public int getAmmo() {
        return getMetadata().get("ammo").number().intValue();
    }

    public boolean isSimilar(ItemStack other) {
        if(other == null)
            return false;
        if(getType() != other.getType())
            return false;
        return true;
    }

    public ItemStack clone() {
        return new ItemStack(data.clone().object());
    }

}
