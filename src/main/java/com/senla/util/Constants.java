package com.senla.util;

import java.io.File;

public final class Constants {

    public static final File roomJson = new File("src/main/resources/json/room.json");
    public static final File guestJson = new File("src/main/resources/json/guest.json");
    public static final File maintenanceJson = new File("src/main/resources/json/maintenance.json");
    public static final File archivedGuestJson = new File("src/main/resources/json/archived_guest.json");
    public static final File defaultConfig = new File("src/main/resources/json/default.json");

    public static final String configPropertiesPath = "src/main/resources/config.properties";
    public static final String dateFormat = "dd.MM.yyyy";

    private Constants() {
        // restrict instantiation
    }
}
