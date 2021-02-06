package net.onfirenetwork.onfirelife;

import net.onfirenetwork.onfirelife.command.VCommand;
import net.onfirenetwork.onfirelife.config.Config;
import net.onfirenetwork.onfirelife.listener.PlayerListener;
import net.onfirenetwork.onfirelife.model.Model;
import net.onfirenetwork.onsetjava.Onset;
import net.onfirenetwork.onsetjava.plugin.Plugin;
import org.javawebstack.orm.ORM;
import org.javawebstack.orm.ORMConfig;
import org.javawebstack.orm.exception.ORMConfigurationException;
import org.javawebstack.orm.wrapper.SQL;

import java.io.File;

@Plugin(name = "OnfireLife")
public class OnfireLife {

    private Config config;

    public void onEnable() {
        Onset.print("Hello from OnfireLife");
        Onset.registerListener(new PlayerListener());
        config = Config.load(new File("life.yaml"));
        setupDatabase();
        Onset.registerCommand("v", new VCommand());
    }

    public void onDisable() {

    }

    private void setupDatabase() {
        SQL sql = config.getDatabase().connect();
        ORMConfig ormConfig = new ORMConfig()
                .setDefaultSize(255);
        try {
            ORM.register(Model.class.getPackage(), sql, ormConfig);
        } catch (ORMConfigurationException e) {
            e.printStackTrace();
        }
        ORM.autoMigrate();
    }

}
