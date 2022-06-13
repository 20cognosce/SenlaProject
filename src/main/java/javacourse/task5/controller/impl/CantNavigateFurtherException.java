package javacourse.task5.controller.impl;

public class CantNavigateFurtherException extends IndexOutOfBoundsException {
    public CantNavigateFurtherException() {
        super("Selected option does not have a submenu");
    }
}
