package com.senla.javacourse.controller;

import com.senla.javacourse.controller.impl.CantNavigateFurtherException;


public interface Navigator {
    void doAction();

    void printMenu();

    void navigate(Integer index) throws CantNavigateFurtherException;
}
