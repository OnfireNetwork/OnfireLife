package net.onfirenetwork.onfirelife.config;

import lombok.Getter;
import org.javawebstack.abstractdata.AbstractElement;
import org.javawebstack.abstractdata.AbstractMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Getter
public class Config {

    DatabaseConfig database = new DatabaseConfig();
    StoreConfig[] stores = new StoreConfig[0];

    public static Config load(File file) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int r;
            FileInputStream fis = new FileInputStream(file);
            while ((r = fis.read(buffer)) != -1)
                baos.write(buffer, 0, r);
            return new AbstractMapper().fromAbstract(AbstractElement.fromYaml(new String(baos.toByteArray(), StandardCharsets.UTF_8)), Config.class);
        } catch (IOException e) {
            return new Config();
        }

    }

}
