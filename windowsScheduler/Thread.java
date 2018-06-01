package windowsScheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Thread implements Comparable<Thread>{
	private static final String NO_DIGIT = "\\D";
	
	private String id;
	private int priority, arrivalTime;
	private List<Integer> cpuBursts, ioBursts;

	public Thread(String id, int priority, int arrivalTime, List<Integer> cpuBursts,List<Integer> ioBursts) {
		this.id = id;
		this.priority = priority;
		this.arrivalTime = arrivalTime;
		this.cpuBursts = cpuBursts;
		this.ioBursts = ioBursts;
	}

	public static List<Thread> readThreads(String filename) throws FileNotFoundException {
		List<Thread> threads = new ArrayList<>();
		try (Scanner in = new Scanner(new File(filename))) {
			while (in.hasNextLine()) {
				String threadLine = in.nextLine();
				int BegPos = threadLine.indexOf("[");
				String id = threadLine.substring(0,BegPos);
				int EndPos = threadLine.indexOf("]");
				int priority = Integer.parseInt(threadLine.substring(BegPos+1, EndPos));
				BegPos = threadLine.indexOf(":")+1;
				EndPos = threadLine.indexOf(",");
				int arrivalTime = Integer.parseInt(threadLine.substring(BegPos+1, EndPos));
				List<Integer> cpuBursts = new ArrayList<>();
				List<Integer> ioBursts = new ArrayList<>();
				BegPos = threadLine.indexOf("(");
				Scanner scanner = new Scanner(threadLine.substring(BegPos).trim());
				while(scanner.hasNext()) {
					cpuBursts.add(scanner.useDelimiter(NO_DIGIT).nextInt());
					ioBursts.add(scanner.useDelimiter(NO_DIGIT).nextInt());
					if(scanner.hasNext(NO_DIGIT + "*")) {
						scanner.skip(NO_DIGIT + ".{2}");
					}
				}
				threads.add(new Thread(id, priority, arrivalTime, cpuBursts, ioBursts));
				scanner.close();
			}
		}
		Collections.sort(threads, (x,y) -> y.compareTo(x));
		return threads;
	}
	
	public String getID() {
		return id;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public List<Integer> getCPUBursts(){
		return cpuBursts;
	}
	
	public List<Integer> getIOBursts(){
		return ioBursts;
	}
	
	public String toString() {
		String thread = id + "[" + priority + "]" + ": " + arrivalTime;
		for(int i = 0;i < cpuBursts.size();i++) {
			thread+= ", (" + cpuBursts.get(i) + "," + ioBursts.get(i) + ")";
		}
		return thread;
	}

	@Override
	public int compareTo(Thread other) {
		return this.arrivalTime - other.arrivalTime;
	}
}
