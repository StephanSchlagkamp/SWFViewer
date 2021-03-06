package application.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import application.factory.WorkLoadTraceFactory;
import application.listener.FileSelectListener;
import application.model.WorkLoadTrace;

/**
 * The {@linkplain MainWindow} acts as the Application {@linkplain JFrame} that manages the whole Application.
 * <br>It manages its own {@linkplain GraphPanel} and {@linkplain FileDialog}.
 * <p>Call setWorkLoadFile(...) to prepare and display a SWF {@linkplain WorkLoadTrace}.
 */
public class MainWindow extends JFrame  implements FileSelectListener{
	private static final long serialVersionUID = 1L;

	private static Dimension PLOT_FRAME_PADDING = new Dimension(64, 128);

	private FileDialog fileDialog;
	private GraphPanel graphPanel;
	
	private WorkLoadTrace trace;
	
	public MainWindow(){
		/* Getting display information */
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		/* Setting frame size */
		setBounds(PLOT_FRAME_PADDING.width, PLOT_FRAME_PADDING.height, screenSize.width-2*PLOT_FRAME_PADDING.width, screenSize.height-2*PLOT_FRAME_PADDING.height);

		/* Constructing the open-file-frame */
		fileDialog = new FileDialog().setFileListener(this);

		graphPanel = new GraphPanel();
		add(graphPanel);
		
		/* Constructing the JMenuBar */
		JMenuBar menuBar = new JMenuBar();

		/* Constructing the JMenu for Exiting the application */
		JMenu fileMenu = new JMenu("File");

		/* Constructing the JMenuItem for closing the application */
		JMenuItem openButton = new JMenuItem("Open Workload-Trace...");
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				fileDialog.setVisible(true);
			}
		});

		/* Constructing the JMenuItem for exporting a screenshot */
		JMenuItem saveButton = new JMenuItem("Export Screenshot...");
		saveButton.addActionListener(new ScreenshotDialog(this));

		/* Constructing the JMenuItem for closing the application */
		JMenuItem exitButton = new JMenuItem("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("Exit selected");
				dispose();
			}
		});

		/* Constructing the JMenu for starting/stopping the simulation */
		//JMenu viewMenu = new JMenu("View"); // TODO add view menu

		/* Constructing the JMenuItem for setting scope properties */
		JMenuItem changeViewButton = new JMenuItem("View Settings"); // TODO add view menu
		changeViewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("View Settings selected");
			}
		});

		/* Constructing the file menu */
		fileMenu.add(openButton);
		fileMenu.add(saveButton);
		fileMenu.add(exitButton);

		/* Constructing the view menu */
		//viewMenu.add(changeViewButton); // TODO add view menu

		/* Constructing the menu bar */
		menuBar.add(fileMenu);
		//menuBar.add(viewMenu); // TODO add view menu

		/* Adding the menu bar to the main frame*/
		setJMenuBar(menuBar);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
		fileDialog.setVisible(false);
	}

	@Override
	public void setWorkLoadFile(final File file, final long offset, final long start, final long end, final int threads) {
		fileDialog.setVisible(false);
		this.setVisible(true);
		this.toFront();
		
		graphPanel.setVisible(false);
		trace = WorkLoadTraceFactory.loadSWFTraceFromFile(file);
		graphPanel.setOffsetX(start);
		graphPanel.setTimeOffset(offset);
		graphPanel.setVirtualLengthX(end - start);
		graphPanel.setLaneCount(threads);
		graphPanel.setWorkLoads(trace.getWorkloads(start, end));
		graphPanel.build();
		graphPanel.setVisible(true);
	}
	
	@Override
	public void dispose() {
		fileDialog.dispose();
		super.dispose();
	}
}
