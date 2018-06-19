package scheduler;

import java.util.*;

/**
 * The  Scheduler.
 */
public class Scheduler {

    private Map<Integer, Queue<Thread>> readyQues = new TreeMap<>(Comparator.reverseOrder());
    private static final int NR_PRIORITY_LEVELS = 16;
    private List<Thread> ioList = new ArrayList<>();
    private Thread contextThread, currentThread;

    private int quantum, switchTime;
    private int switchTimeLeft, quantumLeft;
    private boolean currentlySwitching, currentlyCpuBursting;
    private int startTime, endTime;

    private Clock clock;

    /**
     * Instantiates a new Scheduler.
     *
     * @param quantum    the quantum
     * @param switchTime the switch time
     * @param clock      the clock
     */
    public Scheduler(int quantum, int switchTime, Clock clock) {
        this.quantum = quantum;
        this.switchTime = switchTime;
        this.clock = clock;

        // fills every readyqueue with empty queue
        for (int i = 0; i < NR_PRIORITY_LEVELS; i++) {
            readyQues.put(NR_PRIORITY_LEVELS - 1 - i, new ArrayDeque<>());
        }
    }

    /**
     * Enqueue the arrivingThreads.
     *
     * @param threads the threads
     */
    public void enqueue(Queue<Thread> threads) {
        for (Thread thread : threads) {
            int priority = thread.getPriority();
            Queue<Thread> priorityQue = readyQues.get(priority);
            priorityQue.add(thread);
            readyQues.put(priority, priorityQue);
        }
    }

    /**
     * Schedule. main scheduling method.
     */
    public void schedule() {
        if (currentThread == null && containsThreads(readyQues) && !currentlySwitching) {
            Iterator<Queue<Thread>> iterator = readyQues.values().iterator();
            Queue<Thread> queue = iterator.next();
            while (!queue.iterator().hasNext()) {
                if (iterator.hasNext()) queue = iterator.next();
            }
            if (!queue.isEmpty()) currentThread = queue.remove();
        }
        if (currentThread != null && (contextThread == null || !contextThread.equals(currentThread))) {
            doContextSwitch();
        } else if (currentThread != null) {
            doCpuBurst();
        }
        doIo();
    }

    /**
     * helper method to test if Map contains Ques who are not empty
     * @param map which is examined
     * @return if Map contains Ques who are not empty
     */
    private boolean containsThreads(Map<Integer, Queue<Thread>> map) {
        for (Queue<Thread> queue : map.values()) {
            if (!queue.isEmpty()) return true;
        }
        return false;
    }

    /**
     * execute CPU Burst
     */
    private void doCpuBurst() {
        if (!currentlyCpuBursting) {
            currentlyCpuBursting = true;
            quantumLeft = quantum;
            startTime = clock.getTime();
        }
        quantumLeft--;
        currentThread.getBurstPairs().get(0).decreaseCpuBurst();
        cleanUp(currentThread);
        if (currentThread.getBurstPairs().get(0).getCpuBurst() <= 0 || quantumLeft <= 0) {
            currentlyCpuBursting = false;
            endTime = clock.getTime() + 1;
            System.out.println(currentThread.getId() + ": " + startTime + ", " + endTime);
            currentThread.getBurstPairs().get(0).setIoBurst(currentThread.getBurstPairs().get(0).getIoBurst() + 1);
            ioList.add(currentThread);
            currentThread = null;
        }
    }

    /**
     * cleans up empty burstPairs in thread
     * @param thread
     */
    private void cleanUp(Thread thread) {
        if (thread.getBurstPairs().get(0).getCpuBurst() == 0
                && thread.getBurstPairs().get(0).getIoBurst() == 0)
            thread.getBurstPairs().remove(0);
    }

    /**
     * Context Switch
     */
    private void doContextSwitch() {
        if (!currentlySwitching) {
            switchTimeLeft = switchTime;
            currentlySwitching = true;
            currentlyCpuBursting = false;
        }
        switchTimeLeft--;
        if (switchTimeLeft <= 0) {
            currentlySwitching = false;
            contextThread = currentThread;
        }
    }

    /**
     *  simulate IO burst of all threads in ioList
     */
    private void doIo() {
        Queue<Thread> enqueue = new ArrayDeque<>();
        List<Thread> delete = new ArrayList<>();
        for (Thread thread : ioList) {
            thread.getBurstPairs().get(0).decreaseIoBurst();
            cleanUp(thread);
            if (thread.getBurstPairs().isEmpty()) {
                delete.add(thread);
            } else if (thread.getBurstPairs().get(0).getCpuBurst() > 0) {
                enqueue.add(thread);
            }
        }
        enqueue(enqueue);
        ioList.removeAll(enqueue);
        ioList.removeAll(delete);
    }

    /**
     * is the scheduler finished with scheduling the tasks
     *
     * @return the boolean
     */
    public boolean isDone() {
        return (!containsThreads(readyQues) && ioList.isEmpty() && currentThread == null);
    }
}
