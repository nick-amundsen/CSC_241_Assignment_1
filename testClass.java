import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class testClass extends JPanel implements ActionListener{
	
	private static String vertexfile;
	private static String edgefile;
	private static ArrayList<Vertex> vertices;
	private static ArrayList<Edge> edges;
	private static JTextField inputField;
	private static String input = "";
	
    public static void main(String[] args) throws IOException {
    	vertexfile = "vertex.txt";
    	edgefile = "edge.txt";
    	vertices = loadVertexObj(vertexfile);
    	edges = loadEdges(edgefile);
    	displayGraph();
   	}
    
    public testClass() { //Constructor for MyGraph using the JPanel class 
        super();
    }
    
    public static void displayVertices(ArrayList<Vertex> vertices, int width, int height, Graphics g) { //Draws the vertices onto the JPanel
    	double angledif = 0.0; //This will be the base angle to run our cos and sin functions
  		for (int i = 0; i < vertices.size(); i++) { //Iterates through every vertex in the list
  			vertices.get(i).setXcoord(((.5*width)+Math.cos(Math.toRadians(angledif))*200)); //Sets the x pos for that vertex using a function of cos(angle)
  			vertices.get(i).setYcoord(((.5*height)+Math.sin(Math.toRadians(angledif))*200)); //Sets the y pos for that vertex using a function of sin(angle)
  			g.drawOval((int)vertices.get(i).getXcoord(), (int)vertices.get(i).getYcoord(), 40, 40); //Draws the oval in the correct position calling to the x and y values of the vertex
  			g.drawString(vertices.get(i).getName(), (int)vertices.get(i).getXcoord()+10, (int)vertices.get(i).getYcoord()-5); //Prints out the name of the vertex inside the circle
  			angledif = angledif + 360.0/vertices.size(); //Increases the angle by the correct increments
  			
  		}
  		return;
  	}
        
  	public static void displayEdges(ArrayList<Edge> edges, ArrayList<Vertex> vertices, int width, int height, Graphics g) { //Draws the edges onto the JPanel between the vertices  
  		for (int i = 0; i < vertices.size(); i++) { //Iterates through all the vertices to find adj vertices
  			ArrayList<Vertex> adj = findAdjacentVertices(vertices.get(i), vertices, edges); //Creates a arraylist of adj vertices
  			for (int k = 0; k < adj.size(); k++) { //Iterates through all the vertices to find the vertex obj for each
  				g.setColor(Color.BLACK); //Sets color for complete line to black
  				g.drawLine((int)vertices.get(i).getXcoord()+20, (int)vertices.get(i).getYcoord()+20, (int)adj.get(k).getXcoord()+20, (int)adj.get(k).getYcoord()+20); //Draws a line between each vertex
  				g.setColor(Color.GREEN); //Sets color for direction line to green 
  				double greenlineX = (((vertices.get(i).getXcoord()+21)+(adj.get(k).getXcoord()+21))/2.0); //Midpoint formula for the green line x coordinate
  				double greenlineY = (((vertices.get(i).getYcoord()+21)+(adj.get(k).getYcoord()+21))/2.0); //Midpoint formula for the green line y coordinate
  				g.drawLine((int)adj.get(k).getXcoord()+21, (int)adj.get(k).getYcoord()+21, (int)greenlineX, (int)greenlineY); //Draws the green line from the end point to the midpoint	
  			}
  		}
       
  	}
  	
  	@Override
    public void paint(Graphics g) { //Override of the JPanel draw class for our needs, called automatically
		int width = getWidth(); //Gets the width          
        int height = getHeight(); //Gets the height
		super.paint(g); //Calls for the superclass to paint, so our panel is drawn on correctly
		displayVertices(vertices, width, height, g); //Calls for the method to draw the vertices
        displayEdges(edges, vertices, width, height, g); //Calls for the method to draw the edges
        g.setColor(Color.BLACK); //Sets color back to black after edges method
        g.drawString("Half green line is direction flight is traveling, complete green line means flight travels both ways", 10, 80);
        g.drawString(input, 10, 120);
	}
    
    public static void displayGraph() { //Creates our applet and panel to be drawn on
    	testClass graphPanel = new testClass(); //Call of the constructor class
        JFrame graphApp = new JFrame(); //Creates applet
        graphApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //What to do on exit
        graphApp.add(graphPanel); //Adds panel to applet           
        graphApp.setSize(900, 900); //Defines window size
        graphApp.setVisible(true); //Makes sure its visible
        graphPanel.setOpaque(true);
        JLabel l = new JLabel("Please input two vertex names and an option (1 = shortest distance, 2 = fastest time, 3 = cheapest flight, 4 = all options) seperated by spaces. ", JLabel.TRAILING);
        graphPanel.add(l);
        inputField = new JTextField(20);
        l.setLabelFor(inputField);
        graphPanel.add(inputField);
        JButton button = new JButton("Calculate");
        graphPanel.add(new JLabel());
        graphPanel.add(button);
        button.addActionListener(graphPanel);
        graphApp.setContentPane(graphPanel);
        
   	}
    
    public static ArrayList<Vertex> loadVertexObj(String vertexfile) {
       	ArrayList <Vertex> vertices = new ArrayList<Vertex>(); //The arraylist to be returned        
      	try { //Try to open the text file, throw exception if unable to do so
    		FileReader fr = new FileReader(vertexfile); //Opens a file reader to convert the text file into an array
    		BufferedReader br = new BufferedReader(fr); //This reader is used to read the lines of the file
    		String str; //Will be input for name of vertex
    		while((str = br.readLine())!=null){ //Continue while there are lines to read
    			Vertex v = new Vertex(str); //Vertex object to be added to arraylist
    			vertices.add(v); //Adds vertex to arraylist
    		}
    		fr.close(); //Close reader
    	} catch (FileNotFoundException e) {
    		System.out.println("Error: Unable to open file " + vertexfile); //Prints out the error message if the file cannot be found
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
        return vertices;   
   }
    
    public static ArrayList<Edge> loadEdges(String edgefile) {      	   
        ArrayList <Edge> edges = new ArrayList<Edge>(); //Arraylist to be returned
        try { //Try to open the text file, throw exception if unable to do so
			FileReader fr = new FileReader(edgefile); //Opens a file reader to convert the text file into an array
			BufferedReader br = new BufferedReader(fr); //This reader is used to read the lines of the file
			LinkedList<String> inputs = new LinkedList<String>(); //This list will make up the components of the edge object
			String str; //First component
			while((str =br.readLine())!=null){ //Continue while there are lines to read
				inputs.add(str); //Adds the first component, because of the check readline in the loop
				for (int i = 0; i<4; i++) { //Iterates a couple lines to add the rest of the components to the list
					inputs.add(br.readLine());
				}
				Edge input = new Edge(inputs.pop(),inputs.pop(),inputs.pop(),inputs.pop(),inputs.pop()); //Pops all the components in proper order into a new edge object
				edges.add(input); //Adds that edge object into the list
		    }
			fr.close(); //Close reader
		} catch (FileNotFoundException e) {
			System.out.println("Error: Unable to open file " + edgefile); //Prints out the error message if the file cannot be found
		} catch (IOException e) {
			e.printStackTrace();
		}         
        return edges;   
   	}
     
    public static ArrayList<Vertex> findAdjacentVertices(Vertex vertex, ArrayList<Vertex> vertices, ArrayList<Edge> edges) { 
   		ArrayList<Vertex> adjVertices = new ArrayList<Vertex>(); //Arraylist to be returned
   		for(int i = 0; i < edges.size(); i++) { //Iterates through every edge to find relevant ones
			Edge e = edges.get(i); //The edge to be looked at
			if (e.getStart().equals(vertex.getName())) { //If the edge starts with the input vertex
		   		for (int k = 0; k < vertices.size(); k ++) { //Then check through every vertex name to find vertex object
		   			if (e.getEnd().equals(vertices.get(k).getName())) {
		   				adjVertices.add(vertices.get(k)); //Add vertex object to arraylist
		   			} else {
		   				continue;
		   			}
		   		}
			} else {
				continue;
			}
		}
        return adjVertices;   
   	}

	
    @Override
	public void actionPerformed(ActionEvent e) {
		input = inputField.getText();
		repaint();
	}
}


