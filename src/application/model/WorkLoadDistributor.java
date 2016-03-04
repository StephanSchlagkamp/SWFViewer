package application.model;

import java.util.ArrayList;

/**
 * The {@linkplain WorkLoadDistributor} is used to distribute the {@linkplain WorkLoadLaneEntry}s onto the resources aka {@linkplain WorkLoadLane}s.
 */
public class WorkLoadDistributor {
	
	private ArrayList<WorkLoadLane> lanes;
	
	/**
	 * Creates a new {@link WorkLoadDistributor}.
	 * @param numCores The number of {@link WorkLoadLane}s to create.
	 */
	public WorkLoadDistributor(int numCores) {
		lanes = new ArrayList<WorkLoadLane>(numCores);
		for (int i = 0; i < numCores; i++) {
			lanes.add(new WorkLoadLane());
		}
	}
	
	/**
	 * Returns the internal {@link ArrayList} of the specified {@link WorkLoadLane}.
	 * @param laneId The {@link WorkLoadLane} to get the {@link WorkLoadLaneEntry}s of.
	 * @return An {@link ArrayList} of {@link WorkLoadLaneEntry}s of the specified {@link WorkLoadLane}.
	 */
	public ArrayList<WorkLoadLaneEntry> getEntriesOnLane(int laneId) {
		return lanes.get(laneId).getEntries();
	}
	
	/**
	 * Tries to fit the {@link WorkLoadLaneEntry} onto a {@link WorkLoadLane}.
	 * @param cui The {@link WorkLoadLaneEntry} to fit onto a {@link WorkLoadLane}.
	 * @return The index of the {@link WorkLoadLane} inside the {@link WorkLoadDistributor}s internal {@link ArrayList} if successful, otherwise -1.
	 */
	public int addToschedule(WorkLoadLaneEntry cui) {
		for (int i = 0; i < lanes.size(); i++) {
			if(lanes.get(i).isFitsOnLane(cui)) {
				lanes.get(i).addLaneEntry(cui);
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * @return The smallest start time of any of the {@link WorkLoadLaneEntry}s added to this {@link WorkLoadDistributor}.
	 */
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
	
	/**
	 * @return The biggest end time of any of the {@link WorkLoadLaneEntry}s added to this {@link WorkLoadDistributor}.
	 */
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
	
	/**
	 * @return The number of lanes used currently by the {@link WorkLoadLaneEntry}s added to this {@link WorkLoadDistributor}.
	 */
	public int getMaxCoresUsed() {
		for (int i = lanes.size()-1; i >= 0; i--) {
			if(!lanes.get(i).getEntries().isEmpty()){
				return i+1;
			}
		}
		return 0;
	}
	
	
	/**
	 * Removes all {@link WorkLoadLaneEntry}s from this {@link WorkLoadDistributor}.
	 */
	public void clear() {
		if(lanes != null) {
			for (int i = 0; i < lanes.size(); i++) {
				lanes.get(i).clear();
			}
		}
	}
}
