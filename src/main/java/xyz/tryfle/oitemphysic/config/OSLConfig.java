package xyz.tryfle.oitemphysic.config;

import net.ornithemc.osl.config.api.ConfigScope;
import net.ornithemc.osl.config.api.LoadingPhase;
import net.ornithemc.osl.config.api.config.BaseConfig;
import net.ornithemc.osl.config.api.config.option.BooleanOption;
import net.ornithemc.osl.config.api.config.option.FloatOption;
import net.ornithemc.osl.config.api.serdes.FileSerializerType;
import net.ornithemc.osl.config.api.serdes.SerializerTypes;

public class OSLConfig extends BaseConfig {

    public final static BooleanOption toggled = new BooleanOption("Toggle mod", "Toggles the entire mod's logic.", true);
    public final static FloatOption fallingRotationSpeed = new FloatOption("Falling rotation speed", "Speed that items rotate while they fall", 1.0F);

    @Override
    public String getNamespace() {
        return null;
    }

    @Override
    public String getName() {
        return "ItemPhysicLite";
    }

    @Override
    public String getSaveName() {
        return "oitemphysic.json";
    }

    @Override
    public ConfigScope getScope() {
        return ConfigScope.GLOBAL;
    }

    @Override
    public LoadingPhase getLoadingPhase() {
        return LoadingPhase.START;
    }

    @Override
    public FileSerializerType<?> getType() {
        return SerializerTypes.JSON;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void init() {
        registerOptions("ItemPhysicConfig", toggled, fallingRotationSpeed);
    }
}
