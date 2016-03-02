package application.model;

import java.util.ArrayList;

public class WorkLoadLane {
	
	private ArrayList<WorkLoadLaneEntry> intervals = new ArrayList<WorkLoadLaneEntry>();

	public boolean isFitsOnLane(WorkLoadLaneEntry cui) {
		for (int i = 0; i < intervals.size(); i++) {
			if(intervals.get(i).isCollision(cui)) {
				return false;
			}
		}
		return true;
	}
	
	public void addLaneEntry(WorkLoadLaneEntry cui) {
		intervals.add(cui);
		intervals.sort(new WorkLoadLaneEntry.CUIComparator());
	}
	
	public ArrayList<WorkLoadLaneEntry> getEntries() {
		return intervals;
	}
	
	public void clear() {
		intervals.clear();
	}
}
