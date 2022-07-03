package com.senla.javacourse.controller.impl;

import com.senla.javacourse.build.orm.OrmManagementUtil;
import com.senla.javacourse.controller.MenuController;
import com.senla.javacourse.controller.Navigator;
import com.senla.javacourse.controller.action.ConsoleReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuControllerImpl implements MenuController {
    Navigator navigator;
    private static final Logger logger = LoggerFactory.getLogger(MenuControllerImpl.class);

    public MenuControllerImpl(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void run() {
        int index = -1;
        while (index != 0) {
            navigator.printMenu();
            /*because nextInt() doesn't read newline provided by hitting enter
            and nextLine() read it, so I would have to use two nextLine() after*/
            try {
                index = ConsoleReaderUtil.readInteger();
                navigator.navigate(index);
                continue;
            } catch (CantNavigateFurtherException e) {
                try {
                    navigator.doAction();
                } catch (Exception exception) {
                    logger.error(exception.getMessage(), exception);
                }
            } catch (IndexOutOfBoundsException e) {
                continue;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

            System.out.println("Press Enter key to continue...");
            ConsoleReaderUtil.readLine();
        }

        try {
            OrmManagementUtil.closeConnection();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
