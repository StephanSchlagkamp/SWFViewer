package application.model;

import java.awt.Color;
import java.util.Comparator;

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

	public boolean isCollision(WorkLoadLaneEntry cui) {
		return (begin <= cui.begin && end >= cui.begin)//ist cui.begin in diesen intervall oder
				||(begin <= cui.end && end >= cui.end)//ist cui.end in diesen intervall oder
				||(cui.begin <= begin && cui.end >=end);//ist dieses intervall von cui eingeschlossen
	}

	static class CUIComparator implements Comparator<WorkLoadLaneEntry>{
		@Override
		public int compare(WorkLoadLaneEntry o1, WorkLoadLaneEntry o2) {
			return (int)(o1.begin-o2.begin);
		}
	}
}
