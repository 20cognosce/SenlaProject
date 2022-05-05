package task9.exer4;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeLoggerThread extends Thread {
    int seconds;
    LocalDateTime systemTime;

    public TimeLoggerThread(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void run() {
        LocalDateTime nowTime;
        systemTime = LocalDateTime.now();

        while (true) {
            nowTime = LocalDateTime.now();
            if (Duration.between(systemTime, nowTime).getSeconds() == seconds) {
                systemTime = nowTime;
                System.out.println(systemTime.truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME));
            }
        }
    }
}
