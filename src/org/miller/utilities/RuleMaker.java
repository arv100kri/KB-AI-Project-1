package org.miller.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.miller.definitions.Frame;
import org.miller.definitions.Relation;
import org.miller.definitions.Shape;

/*
 * This class contains a set of rules which can be applied to the frames in a given
 * matrix to assist in getting an inference
 */

public class RuleMaker {
	public static final RuleMaker s_instance = new RuleMaker();
	public static final int SHAPES = 1;
	public static final int RELATIONS = 2;
	private RuleMaker()
	{
		//singleton
	}
	
	public static RuleMaker getInstance()
	{
		return s_instance;
	}
	
	/*
	 * This function takes as input the list of frames in a matrix
	 * and constructs a integer diff array as follows:
	 * If choice=SHAPES, then
	 * Arr[i] = 0 if i=0
	 * 		  =	NoOfShapes in Frame[i-1] - NoOfShapes in Frame[i] for i>0
	 * Else if choice=RELATIONS, then
	 * Arr[i] = 0 if i=0
	 * 		  =	NoOfRelations in Frame[i-1] - NoOfRelations in Frame[i] for i>0
	 * To analyze for a similar pattern in two matrices, an exact match of the diff arrays
	 * could be used to quickly eliminate non-matching frames. 
	 * For Matrix1, just the list of frames is passed.
	 * For Matrix2, the list of frames + the frame in the answer which needs to be tested is passed
	 */
	public int[] getDiffInNoOfShapesOrRelations(List<Frame>frames, int choice)
	{
		int [] diffArray = new int [frames.size()];
		diffArray[0] = 0;
		if(choice == SHAPES)
		{
			for(int i=1; i<frames.size();i++)
			{
				System.out.println(frames.get(i-1).getShapes().size()+" "+frames.get(i).getShapes().size());
				diffArray[i] = frames.get(i-1).getShapes().size() - frames.get(i).getShapes().size();
			}
		}
		else if(choice == RELATIONS)
		{
			for(int i=1; i<frames.size();i++)
			{
				diffArray[i] = frames.get(i-1).getFrameRelations().size() - frames.get(i).getFrameRelations().size();
			}
		}
		return diffArray;
	}
	
	/* The size of both the diffarrays will be the same
	 * as after choosing an answer, the number of frames in the reference matrix (matrix 1)
	 * and the inference matrix (the completed matrix 2) will be the same.
	 */
	public boolean isDiffMatching(int[] diffArray1, int[] diffArray2)
	{
		for(int i=0; i<diffArray1.length;i++)
		{
			if(diffArray1[i] != diffArray2[i])
			{
				System.out.println("Diff Values do not match. Pruning choice");
				return false;
			}
		}
		return true;
	}
	
	/*
	 * For a given frame, this functions returns a canonical mapping of the shapes in the
	 * frame. For eg., if the shapes in the frame are Rectangle1, Triangle1, Circle1, Square1 (in that order)
	 * Then,
	 * 		Rectangle1 = Shape1
	 * 		Triangle1 = Shape2
	 * 		Circle1 = Shape3
	 * 		Square1 = Shape4
	 */
	private Map<Shape,Shape> buildCanonicalMap(Frame frame)
	{
		Map<Shape,Shape> mapping = new HashMap<Shape,Shape>();
		int shapeid=1;
		for(Shape shape: frame.getShapes())
		{
			Shape cannonicalShape = new Shape();
			cannonicalShape.setType("Shape");
			cannonicalShape.setId(shapeid++);
			mapping.put(shape, cannonicalShape);
		}
		return mapping;
	}
	
	/*
	 * For an ordered triplet <Shape1><Shape2><Relation> in the relationship list of the first frame, 
	 * map the corresponding <Relation> from the relationship list of the second frame
	 * Canonical map is shared by frame1 and frame2
	 * This function may need to be extended/changed if more than two frames are involved.
	 */
	public Map<Relation,String> buildRelationMap(Frame frame1, Frame frame2)
	{
		Map<Shape,Shape> canonicalMap = buildCanonicalMap(frame1);
		int shapeid = canonicalMap.size();
		for(Shape s: frame2.getShapes())
		{
			if(canonicalMap.get(s)==null)
			{
				Shape newShape = new Shape();
				newShape.setType("Shape");
				newShape.setId(shapeid++);
				canonicalMap.put(s, newShape);
			}
		}
		List<Relation> relationList1 = frame1.getFrameRelations();
		List<Relation> relationList2 = frame2.getFrameRelations();
		
		Map<Relation,String> relationMapping = new HashMap<Relation,String>();
		for(Relation relation1 : relationList1)
		{
			Shape shape1 = canonicalMap.get(relation1.getShape1());
			Shape shape2 = canonicalMap.get(relation1.getShape2());
			if(shape2 == null)	//If shape="Frame"
			{
				shape2=relation1.getShape2();
			}
			String relationship = relation1.getRelationship();
			Relation relation = new Relation();
			relation.setRelationship(relationship);
			relation.setShape1(shape1);
			relation.setShape2(shape2);
			for(Relation relation2: relationList2)
			{
				shape1 = canonicalMap.get(relation2.getShape1());
				shape2 = canonicalMap.get(relation2.getShape2());
				if(shape2 == null)	//If shape="Frame"
				{
					shape2=relation2.getShape2();
				}
				relationship = relation2.getRelationship();
				if(relation.getShape1().equals(shape1) && relation.getShape2().equals(shape2))
				{
					//Adds the first match to the map, and deletes it from the list
					relationMapping.put(relation, relationship);
					System.out.println("Added an inference "+relation+"=>"+relationship);
					relationList2.remove(relation2);
					break;
				}
			}
		}
		return relationMapping;
	}
	
	/*
	 * From relationMapping1 and relationMapping2, check if there is a complete overlap
	 * of the keys of mapping2 to that of mapping1. If yes, check for the corresponding value
	 * matching. If both these are true, then the particular frame is the answer. (Checked in the driver routine)
	 */
	
	public boolean isMatching(Map<Relation,String> relationMapping1, Map<Relation,String> relationMapping2)
	{
		Set<Relation> relationMapping2Keys = relationMapping2.keySet();
		for(Relation relation2 : relationMapping2Keys)
		{
			System.out.println("Match for "+relation2+" in reference: "+relationMapping1.get(relation2));
			System.out.println("Actual relationship: "+relationMapping2.get(relation2));
			if(!relationMapping1.get(relation2).equals(relationMapping2.get(relation2)))
			{
				System.out.println("Test failed. Choice Ignored");
				return false;
			}
		}
		return true;
	}

}
