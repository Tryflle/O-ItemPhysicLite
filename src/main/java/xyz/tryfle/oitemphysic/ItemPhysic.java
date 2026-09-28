package xyz.tryfle.oitemphysic;

import net.ornithemc.osl.config.api.ConfigManager;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import xyz.tryfle.oitemphysic.config.OSLConfig;

public class ItemPhysic implements ClientModInitializer {

    @Override
    public void initClient() {
        ConfigManager.register(new OSLConfig());
    }
}
