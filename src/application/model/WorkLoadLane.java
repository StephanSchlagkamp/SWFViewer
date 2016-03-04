package application.model;

import java.util.ArrayList;

import application.model.WorkLoadLaneEntry.CUIEndComparator;

/**
 * Can hold {@link WorkLoadLaneEntry}s and can determine if they are overlapping with the already added {@link WorkLoadLaneEntry}s.
 */
public class WorkLoadLane {
	
	private ArrayList<WorkLoadLaneEntry> intervals = new ArrayList<WorkLoadLaneEntry>();

	
	/**
	 * @param cui The {@link WorkLoadLaneEntry} to check for overlapping with the already added {@link WorkLoadLaneEntry}s.
	 * @return false if there was a conflict or if cui is null.
	 */
	public boolean isFitsOnLane(WorkLoadLaneEntry cui) {
		if(cui == null) {
			return false;
		}
		for (int i = intervals.size() - 1; i >= 0; i--) {
			if(cui.isRightOf(intervals.get(i))) {
				return true;
			} else if(intervals.get(i).isCollision(cui)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Adds the passed {@link WorkLoadLaneEntry} to this {@link WorkLoadLane} and sorts with {@link CUIEndComparator}.
	 * @param cui The {@link WorkLoadLaneEntry} to add to this {@link WorkLoadLane}.
	 */
	public void addLaneEntry(WorkLoadLaneEntry cui) {
		intervals.add(cui);
		intervals.sort(new WorkLoadLaneEntry.CUIEndComparator());
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
