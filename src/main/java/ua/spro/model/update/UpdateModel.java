package ua.spro.model.update;

import ua.spro.ABOAdminApp;
import ua.spro.entity.unit.ConnectedUnit;
import ua.spro.model.user.UserModel;
import ua.spro.service.UnitService;
import ua.spro.service.jdbc.UnitServiceImpl;

public class UpdateModel implements UpdateModelInterface {

    private UnitService unitService;
    private ConnectedUnit currentUnit;
    private UserModel userModel;
    private Thread updatingThread;
    private boolean updating;

    public UpdateModel() {
        unitService = new UnitServiceImpl();
        userModel = ABOAdminApp.getUserModel();
        currentUnit = new ConnectedUnit(userModel.getCurrentUser());
        currentUnit.setId( unitService.save(currentUnit) );
        updating = true;
    }

    public boolean needUpdate(){
        return unitService.needUpdate(currentUnit);

    }

    public void newInfoAdded(){
        unitService.newInfoAdded(currentUnit);
    }

    public void unitIsUpdated(){
        unitService.unitIsUpdated(currentUnit);
    }

    public void onExit(){
        unitService.delete(currentUnit);
        updating = false;
        System.out.println("deleting current connected unit");
    }

    public void interruptUpdatingThread(){
        updatingThread.interrupt();
    }

    public Thread getUpdatingThread() {
        return updatingThread;
    }

    public void setUpdatingThread(Thread updatingThread) {
        this.updatingThread = updatingThread;
    }

    public boolean getUpdating() {
        return updating;
    }

    public void setUpdating(boolean updating) {
        this.updating = updating;
    }
}
