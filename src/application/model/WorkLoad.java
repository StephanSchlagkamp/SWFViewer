package application.model;

import java.util.HashMap;

public class WorkLoad {
	
	private HashMap<String,String> data;
	private WorkLoadProfile profile;

	public WorkLoad(WorkLoadProfile profile, String traceLine){
		this.profile = profile;
		String[] traceValues = null;
		if(traceLine != null){
			traceValues = traceLine.split(profile.getSeparator());
		}
		traceLine.split(profile.getSeparator());
		if(profile != null && traceValues != null) {
			String[] keys = profile.getLabels();
			data = new HashMap<String,String>(traceValues.length);
			for (int i = 0, j = 0, n = traceValues.length, m = keys.length; i < n && (j < m || !profile.isOnlyDefinedFields()) ; i++) {
				if (!traceValues[i].isEmpty()) {
					if (j < keys.length) {
						data.put(keys[j], traceValues[i]);
						j++;
					} else {
						data.put(String.valueOf(i), traceValues[i]);
					}
				}
			}
		}
		data.remove(WorkLoadProfile.SKIP_FIELD);
	}
	
	public String getEntry(String label){
		if(data != null)
			return data.get(label);
		else
			return null;
	}
	
	public WorkLoadProfile getProfile() {
		return profile;
	}
	
	@Override
	public String toString() {
		return data.entrySet().toString();
	}
}
