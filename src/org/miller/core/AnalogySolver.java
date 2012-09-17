package org.miller.core;
/*
 * @author Arvind Krishnaa Jagannathan
 */
import java.io.IOException;
import org.miller.utilities.AnalogySolverHelper;
/*
 * The driver class, which takes in all the propositinal representations and returns their solution
 */
public class AnalogySolver 
{
	public static void main(String [] args)
	{
		String [] fileNames = new String[]{"1-1.txt","1-2.txt","1-3.txt","1-4.txt"};
		String [] answers = new String[4];
                AnalogySolverHelper helper = AnalogySolverHelper.getInstance();
                try 
		{
			int qno=0;
			for(String fileName: fileNames)
			{
				++qno;
                                int answer = helper.solvePuzzle(fileName);
                                if(answer == -1)
                                {
                                    answers[qno-1]="\nThe solution to Question ("+qno+") is, Sorry I am not able to find out an answer. All solutions have been eliminated";
                                }
                                
                                else if(answer == 0)
                                {
                                    answers[qno-1]="\nThe solution to Question ("+qno+") is, Sorry I am not able to find out an answer.";
                                }
                                else
                                {
                                    answers[qno-1]="\nThe solution to Question ("+qno+") is: "+ answer+"";
                                }
                        }
                        System.out.println("\n-----------------Summary-------------------------");
                        for(String str: answers)
                        {
                            System.out.println(str);
                            System.out.println("*************************************************************");
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
}