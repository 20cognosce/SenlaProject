package com.senla.controller;

import com.senla.controller.impl.CantNavigateFurtherException;


public interface Navigator {
    void doAction();

    void printMenu();

    void navigate(Integer index) throws CantNavigateFurtherException;
}
