package net.onfirenetwork.onfirelife.model;

import lombok.Getter;
import lombok.Setter;
import org.javawebstack.orm.annotation.*;

import java.sql.Timestamp;

@Table("characters")
@MorphType("character")
@RelationField("character_id")
@Dates
@SoftDelete
@Getter
@Setter
public class CharacterModel extends Model {

    @Column
    int id;
    @Column
    Integer playerId;
    @Column
    String firstName;
    @Column
    String lastName;
    @Column
    Double balance = 0.0;
    @Column
    Double cash = 0.0;
    @Column(size = 0)
    String inventory = "[]";
    @Column
    Integer x;
    @Column
    Integer y;
    @Column
    Integer z;
    @Column
    Timestamp createdAt;
    @Column
    Timestamp updatedAt;
    @Column
    Timestamp deletedAt;

}
