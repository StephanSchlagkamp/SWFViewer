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
	
	public long getMinTime() {
		long minTime = Long.MAX_VALUE;
		for (int i = 0; i < lanes.size(); i++) {
			lanes.get(i).getEntries().sort(new WorkLoadLaneEntry.CUIBeginComparator());
			if (!lanes.get(i).getEntries().isEmpty()) {
				long tempTime = lanes.get(i).getEntries().get(0).getBegin();
				if (minTime > tempTime) {
					minTime = tempTime;
				}
			}
		}
		return minTime;
	}
	
	public long getMaxTime() {
		long maxTime = Long.MIN_VALUE;
		for (int i = 0; i < lanes.size(); i++) {
			lanes.get(i).getEntries().sort(new WorkLoadLaneEntry.CUIEndComparator());
			if (!lanes.get(i).getEntries().isEmpty()) {
				long tempTime = lanes.get(i).getEntries().get(lanes.get(i).getEntries().size() - 1).getEnd();
				if (maxTime < tempTime) {
					maxTime = tempTime;
				}
			}
		}
		return maxTime;
	}
	
	public int getMaxCoresUsed() {
		for (int i = lanes.size()-1; i >= 0; i--) {
			if(!lanes.get(i).getEntries().isEmpty()){
				return i+1;
			}
		}
		return 0;
	}
	
	public void clear() {
		if(lanes != null) {
			for (int i = 0; i < lanes.size(); i++) {
				lanes.get(i).clear();
			}
		}
	}
}
