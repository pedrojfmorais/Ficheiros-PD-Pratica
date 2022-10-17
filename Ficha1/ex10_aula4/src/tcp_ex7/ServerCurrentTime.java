package tcp_ex7;

import java.io.Serial;
import java.io.Serializable;

public class ServerCurrentTime implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final int hours;
    private final int minutes;
    private final int seconds;

    public ServerCurrentTime(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return hours + ":" + minutes + ":" + seconds;
    }
}
