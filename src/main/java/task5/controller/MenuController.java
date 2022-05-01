package task5.controller;

import task5.config.DI.DependencyNavigatorAutowired;
import task5.controller.action.ConsoleReaderUtil;

public class MenuController {
    @DependencyNavigatorAutowired(navigatorClass = Navigator.class)
    Navigator navigator;

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
                    exception.printStackTrace();
                }
            } catch (IndexOutOfBoundsException e) {
                continue;
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Press Enter key to continue...");
            ConsoleReaderUtil.readLine();
        }
    }
}
