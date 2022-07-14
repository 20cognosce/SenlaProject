package com.senla.javacourse.controller.impl;

import com.senla.javacourse.build.orm.OrmManagementUtil;
import com.senla.javacourse.controller.MenuController;
import com.senla.javacourse.controller.Navigator;
import com.senla.javacourse.controller.action.ConsoleReaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Component
public class MenuControllerImpl implements MenuController {
    private final Navigator navigator;

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
                    log.error(exception.getMessage(), exception);
                }
            } catch (IndexOutOfBoundsException e) {
                continue;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            System.out.println("Press Enter key to continue...");
            ConsoleReaderUtil.readLine();
        }

        try {
            OrmManagementUtil.closeConnection();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @PostConstruct
    public void startApplication() {
        run();
    }
}
