package application.model;

import java.util.ArrayList;

public class Core {
	
	private ArrayList<CoreUsageInterval> intervals = new ArrayList<CoreUsageInterval>();

	public boolean canBeExecutedOnThisCore(CoreUsageInterval cui) {
		boolean conflict = false;
		for (int i = 0; i < intervals.size() &! conflict; i++) {
			conflict = intervals.get(i).getIsConflict(cui);
		}
		return conflict;
	}
	
	public void addCoreUsageInterval(CoreUsageInterval cui) {
		intervals.add(cui);
		intervals.sort(new CoreUsageInterval.CUIComparator());
	}
	
	public void clearCoreUsageIntervals() {
		intervals.clear();
	}
}
