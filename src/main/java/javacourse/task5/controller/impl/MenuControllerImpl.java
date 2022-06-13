package javacourse.task5.controller.impl;

import javacourse.task5.build.DI.Autowired;
import javacourse.task5.build.factory.Component;
import javacourse.task5.controller.MenuController;
import javacourse.task5.controller.Navigator;
import javacourse.task5.controller.action.ConsoleReaderUtil;
import org.apache.log4j.Logger;

@Component
public class MenuControllerImpl implements MenuController {
    @Autowired
    Navigator navigator;
    private static final Logger logger = Logger.getLogger(MenuControllerImpl.class);

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
                    logger.error(exception.getMessage(), e);
                }
            } catch (IndexOutOfBoundsException e) {
                continue;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

            System.out.println("Press Enter key to continue...");
            ConsoleReaderUtil.readLine();
        }
    }
}
