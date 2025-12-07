package designpatterns16.design_patterns.state;

import designpatterns16.items.Device;

//device is ready for use.
public class UsableState implements DeviceState {
    
    @Override
    public void beginUse(Device device) {
        System.out.println("Device " + device.getName() + " is now in use.");
        device.incrementUseCount();
        device.setState(new InUseState());
    }

    @Override
    public void sendForMaintenance(Device device) {
        System.out.println("Device " + device.getName() + " is being sent for maintenance.");
        device.setState(new InMaintenanceState());
    }

    @Override
    public void endUse(Device device) {
        System.out.println("Device " + device.getName() + " is not in use. Already in UsableState.");
    }

    @Override
    public void completeMaintenance(Device device) {
        System.out.println("Device " + device.getName() + " is already usable. No maintenance needed.");
    }
}
