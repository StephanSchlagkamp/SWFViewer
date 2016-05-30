package application;

import java.io.File;
import java.util.Date;

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
		window.setSize(1280, 768);
		
		if(args.length > 0) {
			File file = null;
			long offset = new Date().getTime()/1000L;
			long start = 0;
			long end = Long.MAX_VALUE;
			int threads = 100000;
			
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
				case "-file":
				case "-f":
					try {
					file = new File(args[i+1]);
					} catch (Exception e) {
						e.printStackTrace();
						file = null;
					}
					break;

				case "-start":
				case "-s":
				case "-begin":
				case "-b":
					start = Long.parseLong(args[i+1]);
					break;

				case "-end":
				case "-e":
					end = Long.parseLong(args[i+1]);
					break;

				case "-offset":
				case "-o":
					offset = Long.parseLong(args[i+1]);
					break;

				case "-threads":
				case "-t":
				case "-resources":
				case "-r":
					threads = Integer.parseInt(args[i+1]);
					break;

				default:
					break;
				}
			}
			if(file != null && file.exists() && !file.isDirectory())
				window.setWorkLoadFile(file, offset, start, end, threads);
			else 
				System.err.println("File: " + file + "is invalid!");
		}
	}
}
