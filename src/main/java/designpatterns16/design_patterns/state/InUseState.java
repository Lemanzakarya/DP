package designpatterns16.design_patterns.state;

import designpatterns16.items.Device;

//device is in use, after use it may need maintenance.
public class InUseState implements DeviceState {

    @Override
    public void beginUse(Device device) {
        System.out.println("Device " + device.getName() + " is already in use.");
    }

    @Override
    public void sendForMaintenance(Device device) {
        System.out.println("Cannot send device " + device.getName() + " for maintenance while in use. End use first.");
    }

    @Override
    public void endUse(Device device) {
        System.out.println("Device " + device.getName() + " use ended.");
        if (device.needsMaintenance()) {
            System.out.println("Device " + device.getName() + " needs maintenance after " + device.getUseCount() + " uses.");
            device.setState(new NeedsMaintenanceState());
        } else {
            device.setState(new UsableState());
        }
    }

    @Override
    public void completeMaintenance(Device device) {
        System.out.println("Device " + device.getName() + " is in use. Cannot complete maintenance.");
    }
}
