package application.frames;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import application.listener.FileSelectListener;

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

		
		JPanel group0 = new JPanel(new GridLayout(1,6,2,4));
		final JTextField startInputField = new JTextField();
		startInputField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
		group0.add(new Label("Min. Submit Time:", Label.RIGHT));
		group0.add(startInputField);

		
		final JTextField endInputField = new JTextField();
		endInputField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
		group0.add(new Label("Max. Submit Time:", Label.RIGHT));
		group0.add(endInputField);

		
		final JTextField coreInputField = new JTextField();
		coreInputField.setAlignmentX(JTextField.CENTER_ALIGNMENT);
		group0.add(new Label("Number of Cores:", Label.RIGHT));
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
								Long.valueOf(startInputField.getText()),
								Long.valueOf(endInputField.getText()),
								Integer.valueOf(coreInputField.getText()));
						frame.setVisible(false);
					} catch (NumberFormatException nfe) {
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
	
	public FileDialog setFileListener(FileSelectListener listener){
		this.listener = listener;
		return this;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		listener = null;
		fileChooser = null;
	}
}
