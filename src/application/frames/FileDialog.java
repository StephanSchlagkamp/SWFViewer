package application.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;

import application.listener.FileChoosenListener;

public class FileDialog extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Dimension FRAME_SIZE = new Dimension(512, 256);
	private JFileChooser fileChooser;
	private FileChoosenListener listener;
	
	public FileDialog(){
		
		setLayout(null);

		Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(d.width/2-FRAME_SIZE.width/2, d.height/3, FRAME_SIZE.width, FRAME_SIZE.height);
		
		final FileDialog frame = this;
		
		fileChooser = new JFileChooser();

		JButton openButton = new JButton("Select File...");
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION && listener != null) {
					listener.setFile(fileChooser.getSelectedFile());
					frame.setVisible(false);
				}
			}
		});
		openButton.setBounds(0, 0, 512-16, 32);
		add(openButton);

		JTextField startInputField = new JTextField();
		startInputField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//saveValue
			}
		});
		startInputField.setBounds(0,32+4,(512-16)/2-2,32);
		add(startInputField);

		JTextField endInputField = new JTextField();
		endInputField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//save value
			}
		});
		endInputField.setBounds((512-16)/2+2,32+4,(512-16)/2,32);

		add(endInputField);
		JTextField coreInputField = new JTextField();
		coreInputField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//save value
			}
		});
		coreInputField.setBounds(0,32+4+32+4,(512-16)/2-2,32);
		add(coreInputField);
	}
	
	public FileDialog setFileListener(FileChoosenListener listener){
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
