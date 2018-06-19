package scheduler;

import java.util.*;

/**
 * The type Main.
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments in the following order:
     *             filename to read threads from,
     *             quantum,
     *             switch time
     * @throws Exception if something goes wrong
     */
    public static void main(String[] args) throws Exception{

        if (args.length != 3)
            throw new IllegalArgumentException("wrong number of arguments");

        //read Threads from file
        List<Thread> readThreads = Thread.readThreads(args[0]);

        //initiate other parameters
        int quantum = Integer.parseInt(args[1]);
        int switchTime = Integer.parseInt(args[2]);
        Clock clock = new Clock();
        Scheduler scheduler = new Scheduler(quantum, switchTime, clock);

        //execute scheduler as long as there are read treads and scheduler isn't  done
        while (!readThreads.isEmpty() || !scheduler.isDone()) {
            Queue<Thread> arrivingThreads = new ArrayDeque<>();
            for (Thread thread : readThreads) {
                if (thread.getArrivalTime() <= clock.getTime()) {
                    arrivingThreads.add(thread);
                }
            }
            readThreads.removeAll(arrivingThreads);
            scheduler.enqueue(arrivingThreads);
            scheduler.schedule();
            clock.tick();
        }
    }
}