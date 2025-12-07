package designpatterns16.design_patterns.state;

import designpatterns16.items.Device;

//device is in need of maintenance and cannot be used.
public class NeedsMaintenanceState implements DeviceState {
    
    @Override
    public void beginUse(Device device) {
        System.out.println("Device " + device.getName() + " needs maintenance. Cannot be used.");
    }

    @Override
    public void sendForMaintenance(Device device) {
        System.out.println("Device " + device.getName() + " is being sent for maintenance.");
        device.setState(new InMaintenanceState());
    }

    @Override
    public void endUse(Device device) {
        System.out.println("Device " + device.getName() + " is not in use. It needs maintenance.");
    }

    @Override
    public void completeMaintenance(Device device) {
        System.out.println("Device " + device.getName() + " is not in maintenance yet. Send for maintenance first.");
    }
}
