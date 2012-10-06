package org.miller.utilities;
/**
 * @author Arvind Krishnaa Jagannathan
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.miller.definitions.Matrix;

/*
 * 
 * Class which integrates the various parts of the logic and presents the solution to any general question
 */
public class AnalogySolverHelper {
    public static final AnalogySolverHelper s_instance = new AnalogySolverHelper();
    private AnalogySolverHelper()
    {
        //singleton
    }
    
    public static AnalogySolverHelper getInstance()
    {
        return s_instance;
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
    
    /*
     * Function to return the solution choice for a given propositional representation
     * This function does the following:
     * 1. Read the contents of the file, and de-serialize into Java object.
     * 2. Construct the relationship map between the reference frames as well as the choice frames
     * 3. Check if the inferences from the reference are matching those from the each choice.
     * 4. If a choice has a match return it
     * 
     * @param fileName The file that contains the propositional representation
     * @return The anwer choice. 0 if the program cannot find a choice, -1 if it thinks there is no choice.
     */
    
    public int solvePuzzle(String fileName) throws IOException
    {
            SimplePropositionalRepresentationParser parser = SimplePropositionalRepresentationParser.getInstance();
            RuleMaker ruleMaker = RuleMaker.getInstance();
            System.out.println("Parsing the structure of the file: "+fileName);
            List<Matrix> matricesList = parser.parseTextFile(fileName);
            Matrix matrix1 = matricesList.get(0);
            Matrix matrix2 = matricesList.get(1);
            Matrix matrix3 = matricesList.get(2);
            List<org.miller.definitions.Frame> frameListOriginal = matrix1.getFrames();
				
            int [] originalDiffShapes = ruleMaker.getDiffInNoOfShapesOrRelations(frameListOriginal, RuleMaker.SHAPES);
            int [] originalDiffRelations = ruleMaker.getDiffInNoOfShapesOrRelations(frameListOriginal, RuleMaker.RELATIONS);
				
            List<ArrayList<org.miller.definitions.Frame>> optionFrames = new ArrayList<ArrayList<org.miller.definitions.Frame>>();
            for(int i=0;i<matrix3.getFrames().size();i++)
            {
	
		List<org.miller.definitions.Frame> choices = new ArrayList<org.miller.definitions.Frame>(); 
		choices.addAll(matrix2.getFrames()); //Specific Case: Would add only one frame
		choices.add(matrix3.getFrames().get(i));
		optionFrames.add((ArrayList<org.miller.definitions.Frame>) choices);
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
                return -1;
            }
				
            System.out.println("\n______________________________________________");
            System.out.println("Getting inferences from the reference frames");
            System.out.println("______________________________________________\n");
            Map<org.miller.definitions.Relation,String> relationMappingOriginal = ruleMaker.buildRelationMap(matrix1.getFrames().get(0), matrix1.getFrames().get(1));
            for(int i=0;i<optionFrames.size();i++)
            {
		System.out.println("\n______________________________________________");
		System.out.println("Getting inferences from the option frame:"+ optionFrames.get(i).get(1).getId()+" ");
		System.out.println("______________________________________________\n");
		Map<org.miller.definitions.Relation,String> relationMappingComparer = ruleMaker.buildRelationMap(optionFrames.get(i).get(0), optionFrames.get(i).get(1));
		if(ruleMaker.isMatching(relationMappingOriginal, relationMappingComparer))
		{
                    org.miller.definitions.Frame solutionFrame = optionFrames.get(i).get(1);
                    System.out.println("\n***********************************************");
                    System.out.println("The answer to the question is: "+solutionFrame.getId());
                    System.out.println("***********************************************\n");
                    return Integer.parseInt(solutionFrame.getId());
		}
            }
                System.out.println("\n***********************************************");
		System.out.println("Sorry I am not able to find out an answer");
		System.out.println("***********************************************\n");
                return 0;
        }
    }