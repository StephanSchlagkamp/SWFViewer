package application.listener;

import java.io.File;

public interface FileSelectListener {
	public void setWorkLoadFile(File file, long start, long end, int threads);
}
