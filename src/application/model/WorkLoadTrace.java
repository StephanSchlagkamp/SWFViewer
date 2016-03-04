package application.model;

import java.util.ArrayList;

import application.model.swf.SWFWorkLoadProfile;

/**
 * Represents an workload-trace. Has a {@link WorkLoadProfile} and an {@link ArrayList} of {@link WorkLoad}s.
 */
public class WorkLoadTrace {
	
	private ArrayList<WorkLoad> workloads;
	private WorkLoadProfile profile;

	public WorkLoadTrace(WorkLoadProfile profile, ArrayList<WorkLoad> workLoads){
		this.profile = profile;
		this.workloads = workLoads;
	}

	/**
	 * @param min The time intervals beginning
	 * @param max The time intervals ending
	 * @return The {@link WorkLoad}s that are overlapping the specified interval.
	 */
	public ArrayList<WorkLoad> getWorkloads(long min, long max) {
		ArrayList<WorkLoad> returnWorkLoads = new ArrayList<WorkLoad>();
		for (int i = 0; i < workloads.size(); i++) {
			WorkLoad tempWL = workloads.get(i);
			long runStart = Long.valueOf(tempWL.getEntry(SWFWorkLoadProfile.SUBMIT_TIME))+Long.valueOf(tempWL.getEntry(SWFWorkLoadProfile.WAIT_TIME));
			long runEnd = runStart+Long.valueOf(tempWL.getEntry(SWFWorkLoadProfile.RUN_TIME));
			if(runStart <= max && runStart >= min || runEnd <= max && runEnd >= min || runStart <= min && runEnd >= max) {
				returnWorkLoads.add(tempWL);
			}
		}
		return returnWorkLoads;
	}
	
	/**
	 * @return An {@link ArrayList} of all {@link WorkLoad}s held by this {@link WorkLoadTrace}.
	 */
	public ArrayList<WorkLoad> getWorkloads(){
		return workloads;
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
}
