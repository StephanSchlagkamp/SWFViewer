package application.frames;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import application.listener.FileSelectListener;

/**
 *An Dialog that allows you to select a {@linkplain File} and set the Data needed to display a trace.
 *Acts just like a normal {@linkplain JFrame} but modifying it is discouraged.
 */
public class FileDialog extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JFileChooser fileChooser;
	private FileSelectListener listener;
	
	private File selected = null;
	
	public FileDialog(){
		final FileDialog frame = this;
		
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "*.swf (Workload Trace)";
			}
			@Override
			public boolean accept(File f) {
				return !f.isFile() || f.getName().contains(".swf");
			}
		});
		
		setLayout(new GridLayout(3, 1, 0, 4));
		
		
		final JButton openButton = new JButton("Select File...");
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION && listener != null) {
					selected = fileChooser.getSelectedFile();
					openButton.setText(selected.getName());
				}
			}
		});
		//openButton.setBounds(0, 0, 512-16, 32);
		add(openButton);

		
		JPanel group0 = new JPanel(new GridLayout(1,8,2,4));

		final JTextField dateOffsetField = new JTextField(""+new Date().getTime()/1000L);
		dateOffsetField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
		group0.add(new Label("Unix Start Time:", Label.RIGHT));
		group0.add(dateOffsetField);


		final JTextField startInputField = new JTextField("0");
		startInputField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
		group0.add(new Label("Interval Begin:", Label.RIGHT));
		group0.add(startInputField);

		
		final JTextField endInputField = new JTextField("1000000");
		endInputField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
		group0.add(new Label("Interval End:", Label.RIGHT));
		group0.add(endInputField);

		
		final JTextField coreInputField = new JTextField("100000");
		coreInputField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
		group0.add(new Label("Max. Processors:", Label.RIGHT));
		group0.add(coreInputField);
		
		add(group0);
		
		
		final JPanel group1 = new JPanel(new GridLayout(1,2,16,4));
		final JButton loadButton = new JButton("Ok");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (selected != null) {
					try {
						listener.setWorkLoadFile(
								selected,
								Long.valueOf(dateOffsetField.getText()),
								Long.valueOf(startInputField.getText()),
								Long.valueOf(endInputField.getText()),
								Integer.valueOf(coreInputField.getText()));
						frame.setVisible(false);
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
						JOptionPane.showMessageDialog(frame, "Only numbers are permitted and all fields must be filled out!");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "No file selected!");
				}
			}
		});
		group1.add(loadButton);

		
		final JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				frame.setVisible(false);
			}
		});
		group1.add(cancelButton);
		
		add(group1);
		
		
		pack();
		setLocationRelativeTo(null);
	}
	
	/**
	 * Sets the {@linkplain FileSelectListener} to notify when the user has chosen a {@linkplain File}.
	 * @param listener The Listener to notify.
	 * @return This {@linkplain FileDialog} for chaining.
	 */
	public FileDialog setFileListener(FileSelectListener listener){
		this.listener = listener;
		return this;
	}
	
	/**
	 * Clean up and Dispose.
	 */
	@Override
	public void dispose() {
		super.dispose();
		listener = null;
		fileChooser = null;
	}
}
