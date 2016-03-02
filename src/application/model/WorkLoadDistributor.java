package application.model;

import java.util.ArrayList;

public class WorkLoadDistributor {
	
	private ArrayList<WorkLoadLane> lanes;
	
	public WorkLoadDistributor(int numCores) {
		lanes = new ArrayList<WorkLoadLane>(numCores);
		for (int i = 0; i < numCores; i++) {
			lanes.add(new WorkLoadLane());
		}
	}
	
	public ArrayList<WorkLoadLaneEntry> getEntriesOnLane(int laneId) {
		return lanes.get(laneId).getEntries();
	}
	
	public int addToschedule(WorkLoadLaneEntry cui) {
		for (int i = 0; i < lanes.size(); i++) {
			if(lanes.get(i).isFitsOnLane(cui)) {
				lanes.get(i).addLaneEntry(cui);
				return i;
			}
		}
		return -1;
	}
	
	public void clear() {
		if(lanes != null) {
			for (int i = 0; i < lanes.size(); i++) {
				lanes.get(i).clear();
			}
		}
	}
}
