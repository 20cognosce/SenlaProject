package javacourse.task5.controller;

import javacourse.task5.controller.impl.CantNavigateFurtherException;


public interface Navigator {
    void doAction();

    void printMenu();

    void navigate(Integer index) throws CantNavigateFurtherException;
}
