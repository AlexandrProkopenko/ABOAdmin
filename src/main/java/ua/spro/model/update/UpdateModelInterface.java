package ua.spro.model.update;

public interface UpdateModelInterface {

    void onExit();

    boolean needUpdate();

    void newInfoAdded();

    void unitIsUpdated();

    void interruptUpdatingThread();

    Thread getUpdatingThread();

    void setUpdatingThread(Thread updatingThread);

    boolean getUpdating();
}
