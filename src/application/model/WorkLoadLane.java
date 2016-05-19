package application.model;

import java.util.ArrayList;

import application.model.WorkLoadLaneEntry.CUIEndComparator;

/**
 * Can hold {@link WorkLoadLaneEntry}s and can determine if they are overlapping with the already added {@link WorkLoadLaneEntry}s.
 */
public class WorkLoadLane {
	
	private ArrayList<WorkLoadLaneEntry> intervals = new ArrayList<WorkLoadLaneEntry>();
	private WorkLoadLaneEntry.CUIEndComparator comparator = new WorkLoadLaneEntry.CUIEndComparator();
	
	/**
	 * @param cui The {@link WorkLoadLaneEntry} to check for overlapping with the already added {@link WorkLoadLaneEntry}s.
	 * @return false if there was a conflict or if cui is null.
	 */
	public int isFitsOnLane(WorkLoadLaneEntry cui) {
		for (int i = intervals.size() - 1; i >= 0; i--) {
			if(cui.isRightOf(intervals.get(i))) {
				return (i == intervals.size()-1) ? 2 : 1;
			} else if(intervals.get(i).isCollision(cui)) {
				return 0;
			}
		}
		
		return 1;
	}
	
	/**
	 * Adds the passed {@link WorkLoadLaneEntry} to this {@link WorkLoadLane} and sorts with {@link CUIEndComparator}.
	 * @param cui The {@link WorkLoadLaneEntry} to add to this {@link WorkLoadLane}.
	 */
	public void addLaneEntry(WorkLoadLaneEntry cui, boolean sort) {
		intervals.add(cui);
		if(sort)
			intervals.sort(comparator);
	}
	
	/**
	 * @return An {@link ArrayList} containing all {@link WorkLoadLaneEntry}s of this {@link WorkLoadLane}.
	 * <br>DON'T modify this {@link ArrayList}!
	 */
	public ArrayList<WorkLoadLaneEntry> getEntries() {
		return intervals;
	}
	
	/**
	 * Clears the {@link WorkLoadLane}.
	 */
	public void clear() {
		intervals.clear();
	}
}
