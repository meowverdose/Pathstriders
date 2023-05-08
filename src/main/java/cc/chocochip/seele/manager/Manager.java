package cc.chocochip.seele.manager;

public class Manager {
    public ManagerHandler handler;

    protected Manager(ManagerHandler handler) {
        this.handler = handler;
    }
}
