package scheduler;

/**
 * The type Clock.
 */
public class Clock {
    /**
     * The Time attribute holding a simple int.
     */
    int time;

    /**
     * Instantiates a new Clock at 0.
     */
    public Clock() {
        time = 0;
    }

    /**
     * Ticks one second.
     */
    public void tick() {
        this.time ++;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public int getTime() {
        return time;
    }
}
