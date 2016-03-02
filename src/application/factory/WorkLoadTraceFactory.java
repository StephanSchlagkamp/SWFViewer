package application.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import application.model.WorkLoad;
import application.model.WorkLoadProfile;
import application.model.WorkLoadTrace;
import application.model.swf.SWFWorkLoadProfile;

public class WorkLoadTraceFactory {
	
	private WorkLoadTraceFactory(){}
	
	public static WorkLoadTrace loadSWFTraceFromFile(File file) {
		SWFWorkLoadProfile profile = new SWFWorkLoadProfile();
		return new WorkLoadTrace(new SWFWorkLoadProfile(), extractWorkLoads(profile, loadLines(file)));
	}
	
	/*public static WorkLoadTrace loadOtherTraceFromFile(File file) {
		OtherWorkLoadProfile profile = new OtherWorkLoadProfile();
		return new WorkLoadTrace(new OtherWorkLoadProfile(), extractWorkLoads(profile, loadLines(file)));
	}*/
	
	private static ArrayList<String> loadLines(File file) {
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file), (int)Math.pow(2, 10));
			String line;
			while (reader.ready()) {
				line = reader.readLine();
				if(!line.contains(";"))
					lines.add(line);
			}
			reader.close();
		} catch (Exception e) {
			lines.clear();
			e.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		return lines;
	}

	private static ArrayList<WorkLoad> extractWorkLoads(WorkLoadProfile profile, ArrayList<String> lines) {
		ArrayList<WorkLoad> workLoads = new ArrayList<WorkLoad>(lines.size());
		for (int i = 0, n = lines.size(); i < n; i++) {
			workLoads.add(new WorkLoad(profile, lines.get(i)));
		}
		return workLoads;
	}
}
