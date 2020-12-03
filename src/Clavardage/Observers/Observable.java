package Clavardage.Observers;

public interface Observable {
    void notifyAll(Object... arg);
    void addListener(Listener l);
    void removeListener(Listener l);
}
