package scheduler;

import java.util.Objects;

/**
 * The type Burst pair.
 */
public class BurstPair {
    private int cpuBurst;
    private int ioBurst;

    /**
     * Instantiates a new Burst pair.
     *
     * @param cpuBurst the cpu burst
     * @param ioBurst  the io burst
     */
    public BurstPair(int cpuBurst, int ioBurst) {
        this.cpuBurst = cpuBurst;
        this.ioBurst = ioBurst;
    }

    /**
     * Gets cpu burst.
     *
     * @return the cpu burst
     */
    public int getCpuBurst() {
        return cpuBurst;
    }

    /**
     * Decrease cpu burst.
     */
    public void decreaseCpuBurst() {
        cpuBurst--;
    }

    /**
     * Gets io burst.
     *
     * @return the io burst
     */
    public int getIoBurst() {
        return ioBurst;
    }

    /**
     * Sets io burst.
     *
     * @param ioBurst the io burst
     */
    public void setIoBurst(int ioBurst) {
        this.ioBurst = ioBurst;
    }

    /**
     * Decrease io burst.
     */
    public void decreaseIoBurst() {
        ioBurst--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BurstPair burstPair = (BurstPair) o;
        return cpuBurst == burstPair.cpuBurst &&
                ioBurst == burstPair.ioBurst;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpuBurst, ioBurst);
    }
}
