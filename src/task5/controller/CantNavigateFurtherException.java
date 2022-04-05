package task5.controller;

public class CantNavigateFurtherException extends RuntimeException {
    CantNavigateFurtherException() {
        super("Selected option does not have a submenu");
    }
}
