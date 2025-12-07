package designpatterns16.items;

import designpatterns16.design_patterns.state.DeviceState;
import designpatterns16.design_patterns.state.UsableState;

//Represents high-value assets.
//(e.g., MR machine, Ultrasound machine).
// Holds its current DeviceState
public class Device extends Item {
    private DeviceState state;
    private int useCount;
    private static final int MAINTENANCE_THRESHOLD = 5; // After 5 uses, needs maintenance

    public Device(String name) {
        super(name);
        this.state = new UsableState();
        this.useCount = 0;
    }

    public DeviceState getState() {
        return state;
    }

    public void setState(DeviceState state) {
        this.state = state;
    }

    public int getUseCount() {
        return useCount;
    }

    public void incrementUseCount() {
        this.useCount++;
    }

    public boolean needsMaintenance() {
        return useCount >= MAINTENANCE_THRESHOLD;
    }

    public void resetUseCount() {
        this.useCount = 0;
    }

    // State management methods
    public void beginUse() {
        state.beginUse(this);
    }

    public void sendForMaintenance() {
        state.sendForMaintenance(this);
    }

    public void endUse() {
        state.endUse(this);
    }

    public void completeMaintenance() {
        state.completeMaintenance(this);
    }
}
