package application.model;

import java.util.ArrayList;

public class WorkLoadTrace {
	
	private ArrayList<WorkLoad> workloads;
	private WorkLoadProfile profile;

	public WorkLoadTrace(WorkLoadProfile profile, ArrayList<WorkLoad> workLoads){
		this.profile = profile;
		this.workloads = workLoads;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(profile.toString());
		for (int i = 0; i < workloads.size(); i++) {
			builder.append(System.lineSeparator()).append(workloads.get(i).toString());
		}
		return builder.toString();
	}
	
	public ArrayList<WorkLoad> getWorkloads(String field,long min, long max) {
		ArrayList<WorkLoad> returnWorkLoads = new ArrayList<WorkLoad>();
		for (int i = 0; i < workloads.size(); i++) {
			WorkLoad tempWL = workloads.get(i);
			long tempValue = Long.valueOf(tempWL.getEntry(field));
			if(tempValue >= min && tempValue <= max) {
				returnWorkLoads.add(tempWL);
			}
		}
		return returnWorkLoads;
	}
	
	public ArrayList<WorkLoad> getWorkloads(){
		return workloads;
	}
	
	public long getStartTime() {
		return Long.valueOf(workloads.get(0).getEntry("tsubmit"));
	}
}
