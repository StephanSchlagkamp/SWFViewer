package application.model.swf;

import application.model.WorkLoadProfile;

public class SWFWorkLoadProfile extends WorkLoadProfile {
	
	public static final String JOB_ID = "id";
	public static final String SUBMIT_TIME = "tsub";
	public static final String WAIT_TIME = "twait";
	public static final String RUN_TIME = "trun";
	public static final String ALLOCATED_PROCESSORS = "allproc";
	public static final String REQUESTED_PROCESSORS = "reqproc";

	public SWFWorkLoadProfile() {
		super(JOB_ID, SUBMIT_TIME, WAIT_TIME, RUN_TIME, ALLOCATED_PROCESSORS, SKIP_FIELD, SKIP_FIELD, REQUESTED_PROCESSORS);
	}
}
