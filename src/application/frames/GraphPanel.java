package application.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import application.model.WorkLoad;
import application.model.WorkLoadLaneDistributor;
import application.model.WorkLoadLane;
import application.model.WorkLoadLaneEntry;
import application.model.WorkLoadTrace;
import application.model.swf.SWFWorkLoadProfile;

/**
 * This {@linkplain JPanel} displays the {@linkplain WorkLoadTrace}.
 * <br>Call setWorkloads(...) and the other setter-methods and and then build() to create a meaningful result.
 * <p>The {@linkplain Font} Arial must be available!
 */
@SuppressWarnings("unused")
public class GraphPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private long offsetX = 0L;
	private long lengthX = 0L;
	private int laneCount = 0;
	private float laneSpacing = 0;
	
	private long maxX,minX,MaxCores;
	
	private long timeOffset = 0;
	
	private ArrayList<WorkLoad> workloads;
	private WorkLoadLaneDistributor workloadDistributor;
	
	private Font font = new Font("Arial", Font.PLAIN, 10);
	
	
	/**
	 * Paints the Graph and a little info field.
	 * <br>DON'T call manually.
	 */
	@SuppressWarnings("deprecation")
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
							(int)(laneSpacing*i-1f),
							(int)((wlle.getEnd()-wlle.getBegin())*scaleX),
							(int)Math.max(laneSpacing+0.5f, 1f)
					);
					//System.out.println("WLLE: "+(int)((wlle.getBegin()-offsetX)*scaleX)+", "+(int)(laneSpacing*(i+1))+", "+(int)((wlle.getEnd()-wlle.getBegin())*scaleX)+", "+(int)Math.max(laneSpacing, 1f));
				}
			}
			g2d.setFont(font);
			g2d.setColor(Color.BLACK);
			g2d.drawString("Beginning: "+new java.util.Date(1000*(offsetX+timeOffset)).toGMTString()+",  End: "+new java.util.Date(1000*(offsetX+lengthX+timeOffset)).toGMTString()+",  "+laneCount+" Resources used", 4, h-4);
		} else {
			System.out.println("Nothing to draw!");
		}
	}
	
	/**
	 * Sets the {@linkplain WorkLoad}s to display
	 * <p>Remember to call build to make changes active!
	 * @param workloads The {@linkplain ArrayList} of {@linkplain WorkLoad}s to display.
	 */
	public void setWorkLoads(ArrayList<WorkLoad> workloads) {
		this.workloads = workloads;
	}
	
	/**
	 * Sets the number of {@linkplain WorkLoadLane}s.
	 * <p>Remember to call build to make changes active!
	 * @param laneCount The number of lanes to create when build() is called.
	 */
	public void setLaneCount(int laneCount) {
		laneSpacing = (int)(((float)getHeight())/laneCount+0.5f);
		this.laneCount = laneCount;
	}

	/**
	 * Sets the x-axis offset of the coordinate system.
	 * <br>This DOESN'T use screen-coordinate-space, it uses trace-coordinate-space.
	 * <p>Remember to call build to make changes active!
	 * @param offsetX The offset that is used after build() has been called.
	 */
	public void setOffsetX(long offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * Sets the width of the visible coordinate system.
	 * <br>This DOESN'T use screen-coordinate-space, it uses trace-coordinate-space.
	 * <p>Remember to call build to make changes active!
	 * @param virtualX The width of the displayed interval of the {@linkplain WorkLoadTrace}.
	 */
	public void setVirtualLengthX(long virtualX) {
		this.lengthX = virtualX;
	}

	/**
	 * Sets the time offset to use for displaying the little info field at the bottom of the {@linkplain GraphPanel}.
	 * <br>This DOESN'T affect the displaying of the {@linkplain WorkLoadTrace}.
	 * <p>Remember to call build to make changes active!
	 * @param timeOffset The UNIX time offset.
	 */
	public void setTimeOffset(long timeOffset) {
		this.timeOffset = timeOffset;
	}
	
	/**
	 * Uses the data that was set before to generate a visual representation of the {@linkplain WorkLoadTrace}
	 */
	public void build() {
		setVisible(false);
		Random r = new Random(System.nanoTime());
		if(workloads != null) {
			workloadDistributor = new WorkLoadLaneDistributor(laneCount);
			System.out.println("Building lanes for "+workloads.size()+" workloads");
			for (int i = 0; i < workloads.size(); i++) {
				WorkLoad load = workloads.get(i);
				long startTime = Long.valueOf(load.getEntry(SWFWorkLoadProfile.SUBMIT_TIME))+Long.valueOf(load.getEntry(SWFWorkLoadProfile.WAIT_TIME));
				long endTime = startTime+Long.valueOf(load.getEntry(SWFWorkLoadProfile.RUN_TIME));
				int coreCount = Integer.valueOf(load.getEntry(SWFWorkLoadProfile.ALLOCATED_PROCESSORS));
				System.out.println("WLLE "+i+", start: "+startTime+", end: "+endTime+", cores: "+coreCount);
				
				Color c = getRandomColor(Long.valueOf(workloads.get(i).getEntry(SWFWorkLoadProfile.JOB_ID)));
				WorkLoadLaneEntry entry = new WorkLoadLaneEntry(load,startTime, endTime, coreCount, c);
				for (int j = 0; j < coreCount; j++) {
					workloadDistributor.addToschedule(entry);
				}
			}
			//setOffsetX(workloadDistributor.getMinTime());
			//setVirtualLengthX(workloadDistributor.getMaxTime()-offsetX);
			setLaneCount(workloadDistributor.getMaxCoresUsed());
		}
		setVisible(true);
	}
	
	public static Color getRandomColor(long id) {
		final double golden_ratio_conjugate = 0.618033988749895;
		
		double h = 0;
		for(long i = 0; i < id;i++) {
			h += golden_ratio_conjugate;
			h %= 1d;
		}
		return new Color(Color.HSBtoRGB((float)h, 0.5f, 0.95f));
    }
}
