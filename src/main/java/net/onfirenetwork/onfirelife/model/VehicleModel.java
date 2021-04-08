package net.onfirenetwork.onfirelife.model;

import org.javawebstack.orm.annotation.*;

import java.sql.Timestamp;

@Table("vehicles")
@MorphType("vehicle")
@RelationField("vehicle_id")
@Dates
@SoftDelete
public class VehicleModel extends Model {

    @Column
    int id;
    @Column
    Integer characterId;
    @Column
    net.onfirenetwork.onfirelife.VehicleModel model;
    @Column
    Integer color;
    @Column
    Double fuel = 0.0;
    @Column(size = 0)
    String inventory = "[]";
    @Column
    Timestamp createdAt;
    @Column
    Timestamp updatedAt;
    @Column
    Timestamp deletedAt;

}
