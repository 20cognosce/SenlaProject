package task5.controller.entity;

import task5.controller.IAction;

public class MenuItem {
    private final String title;
    private final IAction action;
    private final Menu nextMenu;
    private final Menu previousMenu;

    public MenuItem(String title, IAction action, Menu nextMenu, Menu previousMenu) {
        this.title = title;
        this.action = action;
        this.nextMenu = nextMenu;
        this.previousMenu = previousMenu;
    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    public Menu getPreviousMenu() {
        return previousMenu;
    }

    public String getTitle() {
        return title;
    }

    public void doAction(){
        action.execute();
    }

}
