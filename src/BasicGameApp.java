//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {

	//Variable Definition Section
	//Declare the variables used in the program
	//You can set their initial values too

	//Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

	//Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
	public JPanel panel;

	public BufferStrategy bufferStrategy;

	public Image batmanPic;
	public Image jokerPic;
	public Image explosionPic;
	public Image robinPic;
	public Image backgroundPic;
	public Image heartPic;
	public Image heartPicTwo;
	public Image heartPicOne;
	public Image heartPicZero;

	//Declare the objects used in the program
	//These are things that are made up of more than one variable type
	private Hero batman;
	public Hero joker;
	public Hero robin;

	public int explosionTimer = 0;

	// Main method definition
	// This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


	// This section is the setup portion of the program
	// Initialize your variables and construct your program objects here.
	public BasicGameApp() { // BasicGameApp constructor

		setUpGraphics();

		backgroundPic = Toolkit.getDefaultToolkit().getImage("GothamDark.png");
		//variable and objects
		//create (construct) the objects needed for the game and load up
		batmanPic = Toolkit.getDefaultToolkit().getImage("batman.png"); //load the picture
		batman = new Hero("batman", (int)(Math.random()*700+0),(int)(Math.random()*1000+0)); //construct the astronaut

		jokerPic = Toolkit.getDefaultToolkit().getImage("joker.png");
		explosionPic = Toolkit.getDefaultToolkit().getImage("explosion.png");

		joker = new Hero("joker", (int)(Math.random()*700+0),(int)(Math.random()*1000+0));

		robinPic = Toolkit.getDefaultToolkit().getImage("Robin.png");
		robin = new Hero("robin", (int)(Math.random()*700+0),(int)(Math.random()*1000+0));

		heartPic = Toolkit.getDefaultToolkit().getImage("threeheart.png");

		heartPicTwo = Toolkit.getDefaultToolkit().getImage("twoheart.png");

		heartPicOne = Toolkit.getDefaultToolkit().getImage("oneheart.png");

		heartPicZero = Toolkit.getDefaultToolkit().getImage("zeroheart.png");
	} // end BasicGameApp constructor


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

	// main thread
	// this is the code that plays the game after you set things up
	public void run() {

		//for the moment we will loop things forever.
		while (true) {
			moveThings();  //move all the game objects
			JokerBatmanCrash();
			BatmanJokerCrash();
			BatmanRobinCrash();
			RobinBatmanCrash();
			render();  // paint the graphics
			pause(20); // sleep for 10 ms
		}
	}

	//public void win
			//if batma
	public void moveThings() {
		//calls the move( ) code in the objects
		batman.bounce();
		if (batman.lives<0) {
			batman.isAlive = false;
		}
		joker.bounce();
		if (joker.lives<0) {
			joker.isAlive = false;
		}
		robin.bounce();
		if (batman.lives<0) {
			batman.isAlive = false;
		}

	}


	public void JokerBatmanCrash() {
		if (joker.rec.intersects(batman.rec)&& joker.isCrashing == false) {
			System.out.println("joker losing life" + joker.lives);
			joker.isCrashing = true;
			joker.lives = joker.lives - 1;
		}

		if (!joker.rec.intersects(batman.rec)) {
			joker.isCrashing = false;
		}

	}

	public void BatmanJokerCrash(){
		if (batman.rec.intersects(joker.rec)&& batman.isCrashing == false) {
			System.out.println("batman losing life" + batman.lives);

			batman.isCrashing = true;
			batman.lives = batman.lives - 1;
		}
			if (!batman.rec.intersects(joker.rec)) {
				batman.isCrashing = false;
			}
		}

	public void BatmanRobinCrash(){
		if (batman.rec.intersects(robin.rec) && batman.isCrashing == false) {
			batman.isCrashing = true;
			batman.lives = batman.lives + 1;
			if (!batman.rec.intersects(robin.rec)) {
				batman.isCrashing = false;
			}
		}
	}

	public void RobinBatmanCrash(){
		if (robin.rec.intersects(batman.rec) && robin.isCrashing == false) {
			robin.isCrashing = true;
			robin.lives = robin.lives + 1;
			if (!robin.rec.intersects(batman.rec)) {
				robin.isCrashing = false;
			}
		}
	}

	//Pauses or sleeps the computer for the amount specified in milliseconds
	public void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	//Graphics setup method
	private void setUpGraphics() {
		frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

		panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
		panel.setLayout(null);   //set the layout

		// creates a canvas which is a blank rectangular area of the screen onto which the application can draw
		// and trap input events (Mouse and Keyboard events)
		canvas = new Canvas();
		canvas.setBounds(0, 0, WIDTH, HEIGHT);
		canvas.setIgnoreRepaint(true);

		panel.add(canvas);  // adds the canvas to the panel.

		// frame operations
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
		frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
		frame.setResizable(false);   //makes it so the frame cannot be resized
		frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

		// sets up things so the screen displays images nicely.
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		canvas.requestFocus();
		System.out.println("DONE graphic setup");
	}

	//Paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);

		g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);

		//draw the image of the astronaut


		if (batman.lives == 0) {
			g.drawImage(heartPicZero, batman.xpos + 55, batman.ypos - 30, 100, 30, null);
		}
		if (batman.lives == 1) {
			g.drawImage(heartPicOne, batman.xpos + 55, batman.ypos - 30, 100, 30, null);
		}

		if (batman.lives == 2) {
			g.drawImage(heartPicTwo, batman.xpos + 55, batman.ypos - 30, 100, 30, null);
		}

		if (batman.lives >= 3) {
			g.drawImage(heartPic, batman.xpos + 55, batman.ypos - 30, 100, 30, null);
		}
		if (batman.isAlive == true) {
			g.drawImage(batmanPic, batman.xpos, batman.ypos, batman.width, batman.height, null);
			//g.drawRect(batman.rec.x, batman.rec.y, batman.rec.width, batman.rec.height);
		}

			if (joker.lives == 0) {
				g.drawImage(heartPicZero, joker.xpos + 55, joker.ypos - 30, 100, 30, null);
			}

			if (joker.lives == 1) {
				g.drawImage(heartPicOne, joker.xpos + 55, joker.ypos - 30, 100, 30, null);
			}

			if (joker.lives == 2) {
				g.drawImage(heartPicTwo, joker.xpos + 55, joker.ypos - 30, 100, 30, null);
			}

			if (joker.lives >= 3) {
				g.drawImage(heartPic, joker.xpos + 55, joker.ypos - 30, 100, 30, null);
			}

			if (joker.isAlive == true) {
				g.drawImage(jokerPic, joker.xpos, joker.ypos, joker.width, joker.height, null);
				//g.drawRect(joker.rec.x, joker.rec.y, joker.rec.width, joker.rec.height);
			}
		if (robin.lives == 0) {
			g.drawImage(heartPicZero, robin.xpos + 55, robin.ypos - 30, 100, 30, null);
		}
			if (robin.lives == 1) {
				g.drawImage(heartPicOne, robin.xpos + 55, robin.ypos - 30, 100, 30, null);
			}
			if (robin.lives == 2) {
				g.drawImage(heartPicTwo, robin.xpos + 55, robin.ypos - 30, 100, 30, null);
			}
		if (robin.lives >= 3) {
			g.drawImage(heartPic, robin.xpos + 55, robin.ypos - 30, 100, 30, null);
		}

			if (robin.isAlive == true) {
				g.drawImage(robinPic, robin.xpos, robin.ypos, robin.width, robin.height, null);
				//g.drawRect(robin.rec.x, robin.rec.y, robin.rec.width, robin.rec.height);

			}


			g.dispose();
			bufferStrategy.show();
		}
	}