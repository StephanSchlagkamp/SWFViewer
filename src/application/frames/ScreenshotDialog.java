package application.frames;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javafx.scene.layout.Pane;

/**
 * An Dialog that allows you to save a Screenshot of the {@linkplain Pane} as
 * png.
 */
public class ScreenshotDialog implements ActionListener {
	
	private JFrame parent;
	
	public ScreenshotDialog(JFrame parent) {
		this.parent = parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		System.err.println("Export Screenshot selected");
		Container c = parent.getContentPane();
		BufferedImage im = new BufferedImage(c.getWidth(), c.getHeight(), BufferedImage.TYPE_INT_ARGB);
		c.paint(im.getGraphics());
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (!file.getAbsolutePath().endsWith(".png") || !file.getAbsolutePath().endsWith(".PNG")) {
				file = new File(file.getAbsolutePath() + ".png");
			}
			try {
				ImageIO.write(im, "PNG", file);
				System.err.println("Exported Screenshot to: " + file.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
