package designpatterns16.design_patterns.state;

import designpatterns16.items.Device;

//device is in maintenance and cannot be used.
public class InMaintenanceState implements DeviceState {
    
    @Override
    public void beginUse(Device device) {
        System.out.println("Device " + device.getName() + " is in maintenance. Cannot be used.");
    }

    @Override
    public void sendForMaintenance(Device device) {
        System.out.println("Device " + device.getName() + " is already in maintenance.");
    }

    @Override
    public void endUse(Device device) {
        System.out.println("Device " + device.getName() + " is not in use. It is in maintenance.");
    }

    @Override
    public void completeMaintenance(Device device) {
        System.out.println("Maintenance completed for device " + device.getName() + ". Device is now usable.");
        device.resetUseCount();
        device.setState(new UsableState());
    }
}
