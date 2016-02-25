package application.model;

import java.util.ArrayList;

public class Machine {
	
	private ArrayList<Core> cores;
	
	public Machine(int numCores) {
		cores = new ArrayList<Core>(numCores);
		for (int i = 0; i < numCores; i++) {
			cores.add(new Core());
		}
	}
	
	public int addToschedule(CoreUsageInterval cui) {
		for (int i = 0; i < cores.size(); i++) {
			if(cores.get(i).canBeExecutedOnThisCore(cui)) {
				cores.get(i).addCoreUsageInterval(cui);
				return i;
			}
		}
		return -1;
	}
	
	public void clearSchedule(){
		if(cores != null) {
			for (int i = 0; i < cores.size(); i++) {
				cores.get(i).clearCoreUsageIntervals();
			}
		}
	}
}
