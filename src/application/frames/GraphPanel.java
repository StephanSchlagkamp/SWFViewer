package application.frames;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import application.model.Machine;
import application.model.WorkLoad;

public class GraphPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private long offsetX = 0L;
	private long offsetY = 0L;
	private long virtualX = 0L;
	private long virtualY = 0L;
	private int coreCount = 0;
	private int barSpacing = 0;
	
	private ArrayList<WorkLoad> workloads = null;
	private Machine machine = null;
	
	public void setCoreCount(int coreCount) {
		machine = new Machine(this.coreCount = coreCount);
		barSpacing = (int)(getHeight()/coreCount+0.5f);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(workloads != null) {
			for (int i = 0; i < workloads.size(); i++) {
			}
		}
	}

	/*private void drawWorkLoad(Graphics g, WorkLoad w) {
		long tsubmit = Long.valueOf(w.getEntry("tsubmit"));
		long twait = Long.valueOf(w.getEntry("twait"));
		long trun = Long.valueOf(w.getEntry("trun"));
	}*/
	
	public void setWorkloads(ArrayList<WorkLoad> workloads) {
		this.workloads = workloads;
		repaint();
	}
	
	public void clearWorkLoads(){
		workloads.clear();
	}

	public long getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(long offsetX) {
		this.offsetX = offsetX;
	}

	public long getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(long offsetY) {
		this.offsetY = offsetY;
	}

	public long getVirtualX() {
		return virtualX;
	}

	public void setVirtualX(long virtualX) {
		this.virtualX = virtualX;
	}

	public long getVirtualY() {
		return virtualY;
	}

	public void setVirtualY(long virtualY) {
		this.virtualY = virtualY;
	}
}
