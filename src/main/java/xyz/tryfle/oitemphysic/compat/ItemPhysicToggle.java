package xyz.tryfle.oitemphysic.compat;

import org.polyfrost.oneconfig.api.toggle.v1.ModToggle;
import xyz.tryfle.oitemphysic.config.OSLConfig;

public class ItemPhysicToggle implements ModToggle {
    @Override
    public boolean isEnabled() {
        return OSLConfig.toggled.get();
    }

    @Override
    public void setEnabled(boolean enabled) {
        OSLConfig.toggled.set(enabled);
    }
}
