package scheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The type Thread. Simulates a Thread in Windows Operation System
 */
public class Thread {
    private static final String NO_DIGIT = "\\D";

    private String id;
    private int priority, arrivalTime;
    private List<BurstPair> burstPairs;

    /**
     * Instantiates a new Thread.
     *
     * @param id          the id of a thread
     * @param priority    its priority
     * @param arrivalTime the arrival time
     * @param burstPairs  the burst pairs
     */
    public Thread(String id, int priority, int arrivalTime, List<BurstPair> burstPairs) {
        this.id = id;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.burstPairs = burstPairs;
    }

    /**
     * Read threads list.
     *
     * @param filename the filename
     * @return the list
     * @throws FileNotFoundException the file not found exception
     */
    public static List<Thread> readThreads(String filename) throws FileNotFoundException {
        List<Thread> threads = new ArrayList<>();
        try (Scanner in = new Scanner(new File(filename))) {
            while (in.hasNextLine()) {
                String threadLine = in.nextLine();
                int BegPos = threadLine.indexOf("[");
                String id = threadLine.substring(0, BegPos);
                int EndPos = threadLine.indexOf("]");
                int priority = Integer.parseInt(threadLine.substring(BegPos + 1, EndPos));
                BegPos = threadLine.indexOf(":") + 1;
                EndPos = threadLine.indexOf(",");
                int arrivalTime = Integer.parseInt(threadLine.substring(BegPos + 1, EndPos));
                BegPos = threadLine.indexOf("(");
                Scanner scanner = new Scanner(threadLine.substring(BegPos).trim());
                List<BurstPair> burstPairs = new ArrayList<>();
                while (scanner.hasNext()) {
                    int cpuBurst = (scanner.useDelimiter(NO_DIGIT).nextInt());
                    int ioBurst = (scanner.useDelimiter(NO_DIGIT).nextInt());
                    burstPairs.add(new BurstPair(cpuBurst, ioBurst));
                    if (scanner.hasNext(NO_DIGIT + "*")) {
                        scanner.skip(NO_DIGIT + ".{2}");
                    }
                }
                threads.add(new Thread(id, priority, arrivalTime, burstPairs));
                scanner.close();
            }
        }
        return threads;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets priority.
     *
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Gets arrival time.
     *
     * @return the arrival time
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Gets burst pairs.
     *
     * @return the burst pairs
     */
    public List<BurstPair> getBurstPairs() {
        return burstPairs;
    }

    @Override
    public String toString() {
        String thread = id + "[" + priority + "]" + ": " + arrivalTime;
        for (int i = 0; i < burstPairs.size(); i++) {
            thread += ", (" + burstPairs.get(i).getCpuBurst() + "," + burstPairs.get(i).getIoBurst() + ")";
        }
        return thread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thread thread = (Thread) o;
        return priority == thread.priority &&
                arrivalTime == thread.arrivalTime &&
                Objects.equals(id, thread.id) &&
                Objects.equals(burstPairs, thread.burstPairs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, priority, arrivalTime, burstPairs);
    }
}
