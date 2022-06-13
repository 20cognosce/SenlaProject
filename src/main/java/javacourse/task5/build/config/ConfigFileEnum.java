package javacourse.task5.build.config;

import java.io.File;

public enum ConfigFileEnum {
    ROOM_JSON(Constants.roomJson),
    GUEST_JSON(Constants.guestJson),
    MAINTENANCE_JSON(Constants.maintenanceJson),
    DEFAULT_JSON(Constants.defaultConfig),
    ARCHIVED_GUEST_JSON(Constants.archivedGuestJson);

    private final File configFile;
    ConfigFileEnum(File configFile) {
        this.configFile = configFile;
    }

    File getConfigFile() {
        return this.configFile;
    }
}
