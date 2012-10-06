package org.miller.definitions;
/*
 * @author Arvind Krishnaa Jagannathan
 */
import java.util.ArrayList;
import java.util.List;
/*
 * Class frame that defines an abstraction of a container with
 * 1. Several shapes 
 * 2. Relations among the shapes
 */
public class Frame {
	private List<Shape> shapes = new ArrayList<Shape>();
	private List<Relation> frameRelations = new ArrayList<Relation>();
	private String Id;
	
        /*
         * Adds a shape to the shapes list
         * @param shape The shape to be added to the list
         */
	public void addToShapes(Shape shape)
	{
		shapes.add(shape);
	}
	
        /*
         * Adds a relation to the relations list
         * @param relation The relation to be added to the list
         */
	public void addRelation(Relation relation)
	{
		frameRelations.add(relation);
	}
        
        /*
         * Returns the Id of the frame
         * @return The identifier of the frame
         */
	public String getId() {
		return Id;
	}
        
        /*
         * Sets the Id of the frame
         * @param id The frame identifier
         */
	public void setId(String id) {
		Id = id;
	}
        
        /*
         * Get the list of shapes in this frame
         * @return The list of shapes
         */
	public List<Shape> getShapes() {
		return shapes;
	}
        
        /*
         * Get the list of relations among the shapes
         * @return The list of relationships
         */
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
