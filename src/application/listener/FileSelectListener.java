package application.listener;

import java.io.File;

import application.frames.FileDialog;

/**
 * This Interface can be implemented to allow an object to listen to a {@linkplain FileDialog}
 */
public interface FileSelectListener {
	/**
	 * @param file The {@linkplain File} to load.
	 * @param offset The time offset.
	 * @param start The beginning of the displayed time frame.
	 * @param endThe end of the displayed time frame.
	 * @param threads The number of Resources.
	 */
	public void setWorkLoadFile(File file,long offset, long start, long end, int threads);
}
