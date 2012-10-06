package org.miller.utilities;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.miller.definitions.Frame;
import org.miller.definitions.Matrix;
import org.miller.definitions.Relation;
import org.miller.definitions.Shape;

/*
 * The purpose of this class is to read the propositional representation text file
 * and construct the matrix objects from the raw input.
 * Each matrix object will consist of a set of frames, and each frame a set of shapes
 * The relations among the shapes in a given frame are also extracted from the text file.
 * 
 */
public class SimplePropositionalRepresentationParser {
	public static final SimplePropositionalRepresentationParser s_instance = new SimplePropositionalRepresentationParser();

	/* -------------Constants----------------------*/
	private final String path_prefix = "Representations/";
	private final String MATRIX = "<<Matrix>>";
	private final String MATRIX_ID = "<<MatrixId>>";
	private static final String END_MATRIX = "<<EndMatrix>>";
	private static final String END_FRAME = "<<EndFrame>>";
	private static final String FRAME = "<<Frame>>";
	private static final String FRAME_NAME = "<<FrameName>>";
	private static final String SHAPES = "<<Shapes>>";
	private static final String FRAME_RELATION = "<<Frame-Relation>>";
	/*---------------End of Constant Declaration----------------*/
	
	private SimplePropositionalRepresentationParser()
	{
		//singleton
	}
	
	public static SimplePropositionalRepresentationParser getInstance()
	{
		return s_instance;
	}
	
	/*
	 * Returns a two-element list
	 * Element[0] = Matrix 1
	 * Element[1] = Matrix 2
	 */
	public List<Matrix> parseTextFile(String FileName) throws IOException
	{
		List<Matrix> matrices = new ArrayList<Matrix>();
		String actualPath = path_prefix + FileName;
		FileInputStream fileInputStream = new FileInputStream(actualPath);
		BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(fileInputStream)));
		String line="";
		while((line = br.readLine())!=null)
		{
			if(line.length()>0 && line.contains("/*"))	// Skipping over the comments
			{
				while(true)
				{
					line = br.readLine();
					if(line.contains("*/"))
						break;
				}
			}
			else if(line.contains(MATRIX))
			{
				System.out.println(line);
				Matrix matrix = new Matrix();
				line = br.readLine();
				System.out.println(line);
				while(!line.contains(END_MATRIX))
				{
					if(line.contains(MATRIX_ID))
					{
						String [] splitter = line.split(":");
						matrix.setId(Integer.parseInt(splitter[1]));
					}
					else if(line.contains(FRAME))
					{
						line = br.readLine();
						System.out.println(line);
						Frame frame = new Frame();
						while(!line.contains(END_FRAME))
						{
							String [] pair;
							if(line.contains(FRAME_NAME))
							{
								pair = line.split(":");
								frame.setId(pair[1]);
							}
							else if(line.contains(SHAPES))
							{
								pair = line.split(":");
								System.out.println("Adding Shapes");
								for(String individualShape: pair[1].split(" "))
								{
									Shape shape = getShape(individualShape);
									System.out.println(shape+" added to frame");
									frame.addToShapes(shape);
								}
							}
								
							else if(line.contains(FRAME_RELATION))
							{
								pair = line.split(":");
								System.out.println("Adding relationships");
								if(pair.length == 2)
								{
									for(String rawInput: pair[1].split(","))
									{
										Relation relation = getRelation(rawInput);
										System.out.println(relation+" added to frame");
										frame.addRelation(relation);
									}
								}
							}
							line = br.readLine();
							System.out.println(line);
						}
							System.out.println("Adding the frame \n"+ frame);
							matrix.addFrame(frame);
					}
					line = br.readLine();
					System.out.println(line);
				}
				matrices.add(matrix);
			}
			System.out.println(line);
		}
		System.out.println("--------------------------------------");
		System.out.println("\nThe three matrices are");
		System.out.println("\n---------------\n"+matrices.get(0)+"\n---------------\n"+matrices.get(1)+"\n---------------\n"+matrices.get(2));
		return matrices;
	}

	
	private Relation getRelation(String rawInput) 
	{ 
		System.out.println("Analyzing the relation "+ rawInput);
		String [] actualRelations = rawInput.split(" ");
		System.out.println("Parsed the relation as "+actualRelations[0]+"+"+ actualRelations[1]+"+"+ actualRelations[2] +"!");
		Relation relation = new Relation();
		if(actualRelations.length==3)
		{			
			relation.setShape1(getShape(actualRelations[0]));
			relation.setShape2(getShape(actualRelations[2]));
			relation.setRelationship(actualRelations[1].replaceAll("\"", ""));
		}
		return relation;
	}

	private Shape getShape(String simpleShape) {
		Shape shape = new Shape();
		if(!simpleShape.equals("Frame"))
		{
			String id = simpleShape.replaceAll("\\D+", "");
			String type = simpleShape.replaceAll("[^A-Za-z]", "");
			shape.setId(Integer.parseInt(id));
			shape.setType(type);
		}
		else
		{
			shape.setId(0);
			shape.setType("frame");
		}
		return shape;		
	}


/*	public static void main(String [] args)
	{
		SimplePropositionalRepresentationParser parser = SimplePropositionalRepresentationParser.getInstance();
		try 
		{
			parser.parseTextFile("1-3.txt");
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
*/
}
