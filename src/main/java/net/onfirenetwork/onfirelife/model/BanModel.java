package net.onfirenetwork.onfirelife.model;

import lombok.Getter;
import lombok.Setter;
import org.javawebstack.orm.annotation.*;

import java.sql.Timestamp;

@Table("bans")
@MorphType("ban")
@RelationField("ban_id")
@Dates
@SoftDelete
@Getter
@Setter
public class BanModel extends Model {

    @Column
    int id;
    @Column
    Integer playerId;
    @Column
    Integer adminId;
    @Column
    String reason;
    @Column
    Timestamp createdAt;
    @Column
    Timestamp updatedAt;
    @Column
    Timestamp deletedAt;

}
