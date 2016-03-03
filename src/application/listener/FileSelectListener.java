package application.listener;

import java.io.File;

public interface FileSelectListener {
	public void setWorkLoadFile(File file,long offset, long start, long end, int threads);
}
