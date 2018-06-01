package windowsScheduler;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		/*
		String s = "(19,29), (34,45)";
		int BegPos = s.indexOf("(");
		Scanner scanner = new Scanner(s.substring(BegPos));
		int cpu = scanner.useDelimiter("\\D").nextInt();
		int io = scanner.useDelimiter("\\D").nextInt();
		scanner.skip(".{3}");
		int cpu2 = scanner.useDelimiter("\\D").nextInt();
		int io2 = scanner.useDelimiter("\\D").nextInt();
		//String cpu = scanner.next(Pattern.compile("\\([0-9+]"));
		//String io = scanner.next(Pattern.compile(",[0-9+]"));
		//int cpu = Integer.parseInt(scanner.next("\\([0-9+]"));
		//int io = Integer.parseInt(scanner.next(",[0-9+]"));
		System.out.println("CPU: " + cpu + " IO: " + io);
		System.out.println("CPU2: " + cpu2 + " IO2: " + io2);
		scanner.close();
		*/
		List<Thread> threads = Thread.readThreads("src\\input.txt");
		
		
		
		for(Thread t:threads) {
			System.out.println(t);
		}
	}

}
