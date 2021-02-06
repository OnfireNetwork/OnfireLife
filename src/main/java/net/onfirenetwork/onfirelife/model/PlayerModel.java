package net.onfirenetwork.onfirelife.model;

import lombok.Getter;
import lombok.Setter;
import org.javawebstack.orm.annotation.*;

import java.sql.Timestamp;

@Table("players")
@MorphType("player")
@RelationField("player_id")
@Dates
@SoftDelete
@Getter
@Setter
public class PlayerModel extends Model {

    @Column
    int id;
    @Column
    String steamId;
    @Column
    String username;
    @Column
    Timestamp createdAt;
    @Column
    Timestamp updatedAt;
    @Column
    Timestamp deletedAt;

}
