package net.onfirenetwork.onfirelife.config;

import lombok.Getter;
import org.javawebstack.orm.wrapper.MySQL;
import org.javawebstack.orm.wrapper.SQL;

@Getter
public class DatabaseConfig {

    String host = "localhost";
    Integer port = 3306;
    String database;
    String username;
    String password;

    public SQL connect() {
        return new MySQL(host, port, database, username, password);
    }

}
