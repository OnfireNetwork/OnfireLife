package net.onfirenetwork.onfirelife.model;

import lombok.Getter;
import lombok.Setter;
import org.javawebstack.orm.annotation.*;

@Table("item_prices")
@MorphType("item_price")
@RelationField("item_price_id")
@Getter
@Setter
public class ItemPriceModel extends Model {

    @Column(id = true, size = 255)
    String item;
    @Column
    Double price = 0.0;
    @Column
    Double basePrice = 0.0;
    @Column
    Integer bought = 0;
    @Column
    Integer sold = 0;

}
