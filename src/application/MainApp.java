package application;

import java.io.File;

import javax.swing.UIManager;
import application.frames.MainWindow;

/**
 *Entrypoint of the standalone application. Call main(...) to start the viewer.
 */
public class MainApp {
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		MainWindow window = new MainWindow();
		
		if(args.length > 0) {
			File file = null;
			long offset = 0;
			long start = 0;
			long end = Long.MAX_VALUE;
			int threads = 100000;
			
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
				case "-file":
					try {
					file = new File(args[i+1]);
					} catch (Exception e) {
						e.printStackTrace();
						file = null;
					}
					break;

				case "-start":
					start = Long.parseLong(args[i+1]);
					break;

				case "-end":
					end = Long.parseLong(args[i+1]);
					break;

				case "-offset":
					offset = Long.parseLong(args[i+1]);
					break;

				case "-threads":
					threads = Integer.parseInt(args[i+1]);
					break;

				default:
					break;
				}
			}
			if(file != null && file.exists() &! file.isDirectory())
				window.setWorkLoadFile(file, offset, start, end, threads);
			else 
				System.err.println("File: " + file + "is invalid!");
		}
	}
}
