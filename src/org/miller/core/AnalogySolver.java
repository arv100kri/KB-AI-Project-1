package org.miller.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.miller.definitions.Frame;
import org.miller.definitions.Matrix;
import org.miller.definitions.Relation;
import org.miller.utilities.RuleMaker;
import org.miller.utilities.SimplePropositionalRepresentationParser;

public class AnalogySolver 
{
	public static void main(String [] args)
	{
		SimplePropositionalRepresentationParser parser = SimplePropositionalRepresentationParser.getInstance();
		RuleMaker ruleMaker = RuleMaker.getInstance();
		String [] fileNames = new String[]{"1-1.txt","1-2.txt","1-3.txt"};
		try 
		{
			int qno=0;
			for(String fileName: fileNames)
			{
				++qno;
				System.out.println("Parsing the structure of the file: "+fileName);
				List<Matrix> matricesList = parser.parseTextFile(fileName);
				Matrix matrix1 = matricesList.get(0);
				Matrix matrix2 = matricesList.get(1);
				Matrix matrix3 = matricesList.get(2);
				List<Frame> frameListOriginal = matrix1.getFrames();
				
				int [] originalDiffShapes = ruleMaker.getDiffInNoOfShapesOrRelations(frameListOriginal, RuleMaker.SHAPES);
				int [] originalDiffRelations = ruleMaker.getDiffInNoOfShapesOrRelations(frameListOriginal, RuleMaker.RELATIONS);
				
				List<ArrayList<Frame>> optionFrames = new ArrayList<ArrayList<Frame>>();
				for(int i=0;i<matrix3.getFrames().size();i++)
				{
					
					List<Frame> choices = new ArrayList<Frame>(); 
					choices.addAll(matrix2.getFrames()); //Specific Case: Would add only one frame
					choices.add(matrix3.getFrames().get(i));
					optionFrames.add((ArrayList<Frame>) choices);
				}
				System.out.println("Number of options: "+ optionFrames.size());
				
				for(int i=0; i<optionFrames.size();i++)
				{
					int [] comparerDiffShapes = ruleMaker.getDiffInNoOfShapesOrRelations(optionFrames.get(i), RuleMaker.SHAPES);
					System.out.println("\nDiffShapes \n________________"+print(originalDiffShapes, comparerDiffShapes));
					int [] comparerDiffRelations = ruleMaker.getDiffInNoOfShapesOrRelations(optionFrames.get(i), RuleMaker.RELATIONS);
					System.out.println("\nDiffRelations \n________________"+print(originalDiffRelations, comparerDiffRelations));
					if(!ruleMaker.isDiffMatching(originalDiffShapes, comparerDiffShapes) || !ruleMaker.isDiffMatching(originalDiffRelations, comparerDiffRelations))
					{
						//Eliminate this choice
						System.out.println("\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
						System.out.println("Frame: "+optionFrames.get(i).get(1).getId()+" is pruned");
						System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
						optionFrames.remove(i--);						
					}
				}
				System.out.println("\n#############################################################################");
				System.out.println("After pruning no. of choices to be checked: "+ optionFrames.size());
				System.out.println("#############################################################################\n");
				
				//Now optionFrames will contain only those relations which are to be checked
				if(optionFrames.size()==0)
				{
					System.out.println("\n*****************************************************************************");
					System.out.println("Sorry I am not able to find out an answer. All solutions have been eliminated");
					System.out.println("*****************************************************************************\n");
					continue;
				}
				
				System.out.println("\n______________________________________________");
				System.out.println("Getting inferences from the reference frames");
				System.out.println("______________________________________________\n");
				Map<Relation, String> relationMappingOriginal = ruleMaker.buildRelationMap(matrix1.getFrames().get(0), matrix1.getFrames().get(1));
				boolean answer = false;
				for(int i=0;i<optionFrames.size();i++)
				{
					System.out.println("\n______________________________________________");
					System.out.println("Getting inferences from the option frame:"+ optionFrames.get(i).get(1).getId()+" ");
					System.out.println("______________________________________________\n");
					Map<Relation,String> relationMappingComparer = ruleMaker.buildRelationMap(optionFrames.get(i).get(0), optionFrames.get(i).get(1));
					if(ruleMaker.isMatching(relationMappingOriginal, relationMappingComparer))
					{
						Frame solutionFrame = optionFrames.get(i).get(1);
						System.out.println("\n***********************************************");
						System.out.println("The answer to the question ("+qno+") is: "+solutionFrame.getId());
						System.out.println("***********************************************\n");
						answer = true;
						break;
					}
				}
				
				if(!answer)
				{
					System.out.println("\n***********************************************");
					System.out.println("Sorry I am not able to find out an answer");
					System.out.println("***********************************************\n");
				}
				
				System.out.println("\n------------------------------------------------------------------------------");
			}
		}
		catch (IOException e) 
		{
			if(e.getClass().getSimpleName().equalsIgnoreCase(" FileNotFoundException"))
			{
				System.out.println("File not found");
			}
			else
			{
				e.printStackTrace();
			}
		}
	}

	private static String print(int[] originalDiffShapes,int[] comparerDiffShapes) 
	{
		String returner="\nOriginal:";
		for(int x: originalDiffShapes)
		{
			returner+=x+" ";
		}
		returner+="\nComparer:";
		for(int x: comparerDiffShapes)
		{
			returner+=x+" ";
		}		
			
		return returner;
	}
}
