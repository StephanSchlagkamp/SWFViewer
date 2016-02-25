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
import application.listener.FileChoosenListener;
import application.model.WorkLoadProfile;
import application.model.WorkLoadTrace;

public class WLTViewer extends JFrame  implements FileChoosenListener{
	private static final long serialVersionUID = 1L;

	private static Dimension PLOT_FRAME_PADDING = new Dimension(64, 128);

	private FileDialog fileDialog;
	private GraphPanel graphPanel;
	
	WorkLoadTrace trace = null;
	
	public WLTViewer(){
		/* Getting display information */
		Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		/* Setting frame size */
		setBounds(PLOT_FRAME_PADDING.width, PLOT_FRAME_PADDING.height, d.width-2*PLOT_FRAME_PADDING.width, d.height-2*PLOT_FRAME_PADDING.height);

		/* Constructing the open-file-frame */
		fileDialog = new FileDialog().setFileListener(this);

		graphPanel = new GraphPanel();
		getContentPane().add(graphPanel);
		
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

		/* Constructing the JMenuItem for closing the application */
		JMenuItem saveButton = new JMenuItem("Export Screenshot...");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.err.println("Export Screenshot selected");
			}
		});

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
		JMenu viewMenu = new JMenu("View");

		/* Constructing the JMenuItem for setting scope properties */
		JMenuItem changeViewButton = new JMenuItem("View Settings");
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
		viewMenu.add(changeViewButton);

		/* Constructing the menu bar */
		menuBar.add(fileMenu);
		menuBar.add(viewMenu);

		/* Adding the menu bar to the main frame*/
		setJMenuBar(menuBar);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
		fileDialog.setVisible(false);
	}

	@Override
	public void setFile(File file) {
		fileDialog.setVisible(false);
		setVisible(true);
		trace = WorkLoadTraceFactory.loadTraceFromFile(
			new WorkLoadProfile("id", "tsubmit","twait","trun","allocated_proc","!s","!s","requested_proc").setOnlyDefinedFields(true), file);
		graphPanel.setWorkloads(trace.getWorkloads("tsubmit",0,31692032));
	}
	
	@Override
	public void dispose() {
		fileDialog.dispose();
		super.dispose();
	}
}
