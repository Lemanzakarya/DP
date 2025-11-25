package designpatterns16.design_patterns.state;

//defines states for medical devices' maintenance cycles.
//consumables don't have states. They're discarded when used.
public interface DeviceState {
    beginUse(Device);
    sendForMaintenance(Device);
    endUse(Device);
}
