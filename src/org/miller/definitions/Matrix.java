package org.miller.definitions;

import java.util.ArrayList;
import java.util.List;

public class Matrix 
{
	private List<Frame> frames = new ArrayList<Frame>();
	private int Id;	//Id can only be 1 or 2. Could have used an enum, but not now.
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		if(id==1 || id==2 || id==3)
		{
			Id = id;
		}
	}
	public void addFrame(Frame frame)
	{
		frames.add(frame);
	}
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
