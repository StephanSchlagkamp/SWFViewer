package application.model;

import java.util.ArrayList;

public class WorkLoadLane {
	
	private ArrayList<WorkLoadLaneEntry> intervals = new ArrayList<WorkLoadLaneEntry>();

	public boolean isFitsOnLane(WorkLoadLaneEntry cui) {
		for (int i = intervals.size() - 1; i >= 0; i--) {
			if(cui.isRightOf(intervals.get(i))) {
				return true;
			} else if(intervals.get(i).isCollision(cui)) {
				return false;
			}
		}
		return true;
	}
	
	public void addLaneEntry(WorkLoadLaneEntry cui) {
		intervals.add(cui);
		intervals.sort(new WorkLoadLaneEntry.CUIEndComparator());
	}
	
	public ArrayList<WorkLoadLaneEntry> getEntries() {
		return intervals;
	}
	
	public void clear() {
		intervals.clear();
	}
}
