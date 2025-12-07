package designpatterns16.design_patterns.state;

import designpatterns16.items.Device;

//defines states for medical devices' maintenance cycles.
//consumables don't have states. They're discarded when used.
public interface DeviceState {
    void beginUse(Device device);
    void sendForMaintenance(Device device);
    void endUse(Device device);
    void completeMaintenance(Device device);
}
