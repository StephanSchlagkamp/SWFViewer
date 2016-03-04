package application.model;

import java.awt.Color;
import java.util.Comparator;

/**
 * Holds the {@link WorkLoad} that it was derived from, and interval begin/end, the amount of resources used by the {@link WorkLoad} and a {@link Color} to display the {@link WorkLoadLaneEntry} with.
 */
public class WorkLoadLaneEntry {
	private WorkLoad workLoad;
	private long begin, end;
	private int laneCount;
	private Color c;

	public WorkLoadLaneEntry(WorkLoad workLoad, long begin, long end, int laneCount, Color c) {
		this.workLoad = workLoad;
		this.begin = begin;
		this.end = end;
		this.laneCount = laneCount;
		this.c = c;
	}

	public long getBegin() {
		return begin;
	}

	public long getEnd() {
		return end;
	}
	
	public int getLaneCount() {
		return laneCount;
	}
	
	public WorkLoad getWorkLoad() {
		return workLoad;
	}
	
	public Color getColor() {
		return c;
	}

	/**
	 * @param cui The other {@link WorkLoadLaneEntry} to check with for overlapping.
	 * @return true if they overlap.
	 */
	public boolean isCollision(WorkLoadLaneEntry cui) {
		return (begin <= cui.begin && end >= cui.begin)//ist cui.begin in diesen intervall oder
				||(begin <= cui.end && end >= cui.end)//ist cui.end in diesen intervall oder
				||(cui.begin <= begin && cui.end >=end);//ist dieses intervall von cui eingeschlossen
	}

	/**
	 * @param cui The other {@link WorkLoadLaneEntry} to compare with.
	 * @return true if this {@link WorkLoadLaneEntry} is right of the {@link WorkLoadLaneEntry} cui.
	 */
	public boolean isRightOf(WorkLoadLaneEntry cui) {
		return begin >= cui.end;
	}

	/**
	 * A comparator to sort {@link WorkLoadLaneEntry}s by the interval beginning.
	 */
	static class CUIBeginComparator implements Comparator<WorkLoadLaneEntry>{
		@Override
		public int compare(WorkLoadLaneEntry o1, WorkLoadLaneEntry o2) {
			return (int)(o1.begin-o2.begin);
		}
	}

	/**
	 * A comparator to sort {@link WorkLoadLaneEntry}s by the interval ending.
	 */
	static class CUIEndComparator implements Comparator<WorkLoadLaneEntry>{
		@Override
		public int compare(WorkLoadLaneEntry o1, WorkLoadLaneEntry o2) {
			return (int)(o1.end-o2.end);
		}
	}
}
