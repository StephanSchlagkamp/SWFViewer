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

/**
 *Call the static methods of this class to generate {@linkplain WorkLoadTrace} objects
 */
public class WorkLoadTraceFactory {
	
	private WorkLoadTraceFactory(){}
	
	/**
	 * Loads workload-trace data from an *.swf file. This file must follow the official SWF syntax.
	 * @param file The {@linkplain File} to load
	 * @return A {@linkplain WorkLoadTrace} Object containing the trace data of the {@linkplain File}
	 */
	public static WorkLoadTrace loadSWFTraceFromFile(File file) {
		SWFWorkLoadProfile profile = new SWFWorkLoadProfile();
		return new WorkLoadTrace(new SWFWorkLoadProfile(), extractWorkLoads(profile, loadLines(file)));
	}
	
	/*public static WorkLoadTrace loadOtherTraceFromFile(File file) {
		OtherWorkLoadProfile profile = new OtherWorkLoadProfile();
		return new WorkLoadTrace(new OtherWorkLoadProfile(), extractWorkLoads(profile, loadLines(file)));
	}*/
	
	/**
	 * Load lines from an {@linkplain File} into an {@linkplain ArrayList}. Every Line is represented by a {@linkplain String}
	 * @param file The {@linkplain File} to load.
	 * @return An {@linkplain ArrayList} containing the individual lines of the File.
	 */
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

	/**
	 * Extracts and packs the data of the passed {@linkplain ArrayList} of lines into an {@linkplain ArrayList} of {@linkplain WorkLoad}s.
	 * @param profile A {@linkplain WorkLoadProfile} that determines what data will be loaded into the {@linkplain WorkLoad}s and which data-separator is going to be used.
	 * @param lines An {@linkplain ArrayList} that must contain lines of Data compliant to the passed {@linkplain WorkLoadProfile}s settings
	 * @return An {@linkplain ArrayList} of {@linkplain WorkLoad}s that contain the filtered data of the {@linkplain ArrayList} of lines.
	 */
	private static ArrayList<WorkLoad> extractWorkLoads(WorkLoadProfile profile, ArrayList<String> lines) {
		ArrayList<WorkLoad> workLoads = new ArrayList<WorkLoad>(lines.size());
		for (int i = 0, n = lines.size(); i < n; i++) {
			workLoads.add(new WorkLoad(profile, lines.get(i)));
		}
		return workLoads;
	}
}
