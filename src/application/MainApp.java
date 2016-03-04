package application;

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
		new MainWindow();
	}
}
