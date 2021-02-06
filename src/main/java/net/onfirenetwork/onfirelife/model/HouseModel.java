package net.onfirenetwork.onfirelife.model;

import lombok.Getter;
import lombok.Setter;
import org.javawebstack.orm.annotation.*;

import java.sql.Timestamp;

@Table("houses")
@MorphType("house")
@RelationField("house_id")
@Dates
@SoftDelete
@Getter
@Setter
public class HouseModel extends Model {

    @Column
    int id;
    @Column
    Timestamp createdAt;
    @Column
    Timestamp updatedAt;
    @Column
    Timestamp deletedAt;

}
