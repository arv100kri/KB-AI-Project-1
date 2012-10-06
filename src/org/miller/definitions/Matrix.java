package org.miller.definitions;
/*
 * @author Arvind Krishnaa Jagannathan
 */
import java.util.ArrayList;
import java.util.List;

/*
 * A class which acts as a container for several frames
 */
public class Matrix 
{
	private List<Frame> frames = new ArrayList<Frame>();
	private int Id;	//Id can only be 1 or 2. Could have used an enum, but not now.
	
        /*
         * Get the identifer of the matrix
         * @return The frame identifier
         */
	public int getId() {
		return Id;
	}
        
        /*
         * Set the identifier for the matrix
         * @param id The frame identifier
         */
	public void setId(int id) {
		if(id==1 || id==2 || id==3)
		{
			Id = id;
		}
	}
        
        /*
         * Adds a frame to list of frames in the matrix
         * @param frame The frame to be added to the list
         */
	public void addFrame(Frame frame)
	{
		frames.add(frame);
	}
        
        /*
         * Returns the list of frames in the matrix
         * @return The list of frames
         */
	public List<Frame> getFrames() {
		return frames;
	}
	@Override
	public String toString() {
		String returner ="ID: "+Id+"\n";
		for(Frame frame: frames)
		{
			returner+=frame;
		}
		
		return returner;
	}
}
