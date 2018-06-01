package windowsScheduler;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

	private int quantum, switchTime;
	private List<Thread> queue = new ArrayList<>();
	
	//in ms normally switchTime between 1 and 10, quantum between 10 and 100
	public Scheduler(int quantum, int switchTime) {
		this.quantum = quantum;
		this.switchTime = switchTime;
	}
	
	public void schedule(List<Thread> threads) {
		
	}
	
	public int getQuantum() {
		return quantum;
	}
	
	public int getSwitchTime() {
		return switchTime;
	}
	
	public String toString() {
		return null;
	}
}
