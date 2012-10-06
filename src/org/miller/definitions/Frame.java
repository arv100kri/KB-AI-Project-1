package org.miller.definitions;

import java.util.ArrayList;
import java.util.List;

public class Frame {
	private List<Shape> shapes = new ArrayList<Shape>();
	private List<Relation> frameRelations = new ArrayList<Relation>();
	private String Id;
	
	
	public void addToShapes(Shape shape)
	{
		shapes.add(shape);
	}
	
	public void addRelation(Relation relation)
	{
		frameRelations.add(relation);
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public List<Shape> getShapes() {
		return shapes;
	}

	public List<Relation> getFrameRelations() {
		return frameRelations;
	}
	
	@Override
	public String toString()
	{
		String returner = "";
		returner+="Frame"+Id+"\nShapes: ";
		for(Shape shape: shapes)
		{
			returner+=shape+" ";
		}
		returner+="\nRelations: ";
		for(Relation relation: frameRelations)
		{
			returner+=relation+",";
		}
		returner+="\n";
		return returner;
	}
	
}
