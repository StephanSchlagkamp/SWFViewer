package application.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import application.model.WorkLoadDistributor;
import application.model.WorkLoadLaneEntry;
import application.model.swf.SWFWorkLoadProfile;
import application.model.WorkLoad;

@SuppressWarnings("unused")
public class GraphPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private long offsetX = 0L;
	private long lengthX = 0L;
	private int laneCount = 0;
	private float laneSpacing = 0;
	
	private long maxX,minX,MaxCores;
	
	private ArrayList<WorkLoad> workloads;
	private WorkLoadDistributor workloadDistributor;
	
	private Font font = new Font("Arial", Font.PLAIN, 16);
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int w = getWidth();
		int h = getHeight();

		laneSpacing = h/(float)laneCount;
		
		float scaleX = w/(float)lengthX;
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	
		System.out.println("scaleX: "+scaleX+", laneSpacing: "+laneSpacing);
		
		if (workloads != null) {
			System.out.println("Drawing Entries");
			for (int i = 0; i < laneCount; i++) {
				ArrayList<WorkLoadLaneEntry> wlle_list = workloadDistributor.getEntriesOnLane(i);
				for (int j = 0; j < wlle_list.size(); j++) {
					WorkLoadLaneEntry wlle = wlle_list.get(j);
					g2d.setColor(wlle.getColor());
					g2d.fillRect(
							(int)((wlle.getBegin()-offsetX)*scaleX),
							(int)(laneSpacing*(i-1)),
							(int)((wlle.getEnd()-wlle.getBegin())*scaleX),
							(int)Math.max(laneSpacing, 1f)
					);
					//System.out.println("WLLE: "+(int)((wlle.getBegin()-offsetX)*scaleX)+", "+(int)(laneSpacing*(i+1))+", "+(int)((wlle.getEnd()-wlle.getBegin())*scaleX)+", "+(int)Math.max(laneSpacing, 1f));
				}
			}
			g2d.setFont(font);
			g2d.setColor(Color.GREEN);
			g2d.drawString("Beginning: "+new java.util.Date(1000*offsetX)+", End: "+new java.util.Date(1000*(offsetX+lengthX))+", "+laneCount+" Processors", 0, 16);
		} else {
			System.out.println("Nothing to draw!");
		}
	}
	
	public void setWorkLoads(ArrayList<WorkLoad> workloads) {
		this.workloads = workloads;
	}
	
	public void setLaneCount(int laneCount) {
		laneSpacing = (int)(((float)getHeight())/laneCount+0.5f);
		this.laneCount = laneCount;
	}
	
	public void build() {
		Random r = new Random(System.nanoTime());
		if(workloads != null) {
			workloadDistributor = new WorkLoadDistributor(laneCount);
			System.out.println("Building lanes for "+workloads.size()+" workloads");
			for (int i = 0; i < workloads.size(); i++) {
				WorkLoad load = workloads.get(i);
				long startTime = Long.valueOf(load.getEntry(SWFWorkLoadProfile.SUBMIT_TIME))+Long.valueOf(load.getEntry(SWFWorkLoadProfile.WAIT_TIME));
				long endTime = startTime+Long.valueOf(load.getEntry(SWFWorkLoadProfile.RUN_TIME));
				int coreCount = Integer.valueOf(load.getEntry(SWFWorkLoadProfile.ALLOCATED_PROCESSORS));
				System.out.println("WLLE "+i+", start: "+startTime+", end: "+endTime+", cores: "+coreCount);
				Color c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
				WorkLoadLaneEntry entry = new WorkLoadLaneEntry(load,startTime, endTime, coreCount, c);
				int rv = 0;
				for (int j = 0; j < coreCount && rv != -1; j++) {
					rv = workloadDistributor.addToschedule(entry);
					if(rv == -1) {
						System.out.println("WLLE "+i+" didn't fit onto the lanes at iteration: "+j);
					}
				}
			}
			setOffsetX(workloadDistributor.getMinTime());
			setVirtualLengthX(workloadDistributor.getMaxTime()-offsetX);
			setLaneCount(workloadDistributor.getMaxCoresUsed());
		}
	}

	public void setOffsetX(long offsetX) {
		this.offsetX = offsetX;
	}

	public void setVirtualLengthX(long virtualX) {
		this.lengthX = virtualX;
	}
}
