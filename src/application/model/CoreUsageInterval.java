package application.model;

import java.util.Comparator;

public class CoreUsageInterval {
	private long begin, end;

	public CoreUsageInterval(long begin, long end) {
		this.begin = begin;
		this.end = end;
	}

	public long getBegin() {
		return begin;
	}

	public long getEnd() {
		return end;
	}
	
	public boolean getIsConflict(CoreUsageInterval cui) {
		return (begin <= cui.begin && end >= cui.begin)//ist cui.begin in diesen intervall oder
				||(begin <= cui.end && end >= cui.end)//ist cui.end in diesen intervall oder
				||(cui.begin <= begin && cui.end >=end);//ist dieses intervall von cui eingeschlossen
	}

	static class CUIComparator implements Comparator<CoreUsageInterval>{
		@Override
		public int compare(CoreUsageInterval o1, CoreUsageInterval o2) {
			return (int)(o1.begin-o2.begin);
		}
		
	}
}
