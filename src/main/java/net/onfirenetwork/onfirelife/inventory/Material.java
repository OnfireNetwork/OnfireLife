package net.onfirenetwork.onfirelife.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.javawebstack.abstractdata.AbstractArray;
import org.javawebstack.abstractdata.AbstractObject;

@AllArgsConstructor
@Getter
public enum Material {

    DEMO_APPLE("Apple", Category.FOOD, 10, new AbstractObject()),
    DEMO_STEAK("Steak", Category.FOOD, 3, new AbstractObject());

    final String name;
    final Category category;
    final int max;
    final AbstractObject template;

    public enum Category {
        GENERIC,
        GUN,
        FOOD,
        TOOL
    }

    public AbstractObject getData() {
        return new AbstractObject()
                .set("name", name)
                .set("category", category.name())
                .set("max", max);
    }

    public static AbstractObject getConfig() {
        AbstractObject config = new AbstractObject();
        for(Material value : values())
            config.set(value.name(), value.getData());
        return config;
    }

}
