package task5.controller;

import task5.controller.impl.CantNavigateFurtherException;


public interface Navigator {
    void doAction();

    void printMenu();

    void navigate(Integer index) throws CantNavigateFurtherException;
}
