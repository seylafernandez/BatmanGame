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

	public Image backgroundPic;

	//heroes
	public Image batmanPic;
	public Image jokerPic;
	public Image harleyPic;
	public Image robinPic;

	//hearts
	public Image heartPicThree;
	public Image heartPicTwo;
	public Image heartPicOne;
	public Image heartPicZero;

	//endings
	public Image win;
	public Image lose;

	//Declare the objects used in the program
	//These are things that are made up of more than one variable type
	public Hero batman;
	public Hero joker;
	public Hero robin;
	public Hero harley;


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

		//background
		backgroundPic = Toolkit.getDefaultToolkit().getImage("GothamDark.png");

		//batman constructor
		batman = new Hero("batman", (int) (Math.random() * 800), (int) (Math.random() * 500), 150, 100);
		batmanPic = Toolkit.getDefaultToolkit().getImage("batman.png");

		//joker constructor
		joker = new Hero("joker", (int) (Math.random() * 800), (int) (Math.random() * 500), 150, 150);
		jokerPic = Toolkit.getDefaultToolkit().getImage("joker.png");

		//harley constructor
		harley = new Hero("harley", 750, 500, 100, 150);
		harleyPic = Toolkit.getDefaultToolkit().getImage("harley.png");

		//robin constructor
		robinPic = Toolkit.getDefaultToolkit().getImage("Robin.png");
		robin = new Hero("robin", 150, 50, 200, 150);

		//lives constructors
		heartPicThree = Toolkit.getDefaultToolkit().getImage("threeheart.png");
		heartPicTwo = Toolkit.getDefaultToolkit().getImage("twoheart.png");
		heartPicOne = Toolkit.getDefaultToolkit().getImage("oneheart.png");
		heartPicZero = Toolkit.getDefaultToolkit().getImage("zeroheart.png");

		//ending constructors
		win = Toolkit.getDefaultToolkit().getImage("win.png");
		lose = Toolkit.getDefaultToolkit().getImage("lose.png");
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

			//crash methods
			JokerBatmanCrash();
			BatmanRobinCrash();
			JokerRobinCrash();
			JokerHarleyCrash();
			BatmanHarleyCrash();
			HarleyRobinCrash();

			render();  // paint the graphics
			pause(18); // sleep for 10 ms
		}
	}


	public void moveThings() {

		batman.bounce();
		//everyone exits screen if batman dies
		if (batman.lives < 0) {
			batman.isAlive = false;
			joker.xpos = 1500;
			harley.xpos = 1500;
			robin.xpos = 1500;
		}

		joker.bounce();
		//everyone exits screen if joker dies
		if (joker.lives < 0) {
			joker.isAlive = false;
			batman.xpos = 1500;
			harley.xpos = 1500;
			robin.xpos = 1500;
		}

		robin.bounce();
		//robin dies if loses all lives
		if (robin.lives < 0) {
			robin.isAlive = false;
		}

		harley.bounce();
		//harley dies if loses all lives
		if (harley.lives < 0) {
			harley.isAlive = false;
		}

	}

	//crash methods


	//when joker and batman crash, each loses life
	public void JokerBatmanCrash() {
		if (joker.isAlive && batman.isAlive) {
			if (joker.rec.intersects(batman.rec) && joker.isCrashing == false) {

				System.out.println("batman hit joker, joker lives  " + joker.lives);
				joker.isCrashing = true;
				joker.lives = joker.lives - 1; //loses life
				joker.dx = -5; //changes direction
			}

			//to avoid repetition
			if (!joker.rec.intersects(batman.rec)) {
				joker.isCrashing = false;
			}

			if (batman.rec.intersects(joker.rec) && batman.isCrashing == false) {

				System.out.println("joker hit batman, batman lives " + batman.lives);
				batman.isCrashing = true;
				batman.lives = batman.lives - 1; //loses life
				batman.dx = -5;
			}

			if (!batman.rec.intersects(joker.rec)) {
				batman.isCrashing = false;
			}

		}
	}

	//when harley and robin crash each lose life
	public void HarleyRobinCrash() {
		if (harley.isAlive && robin.isAlive) {

			if (harley.rec.intersects(robin.rec) && harley.isCrashing == false) {

				System.out.println("robin hit harley, harley lives " + harley.lives);
				harley.isCrashing = true;
				harley.lives = harley.lives - 1;
			}

			if (!harley.rec.intersects(robin.rec)) {
				harley.isCrashing = false;
			}

			if (robin.rec.intersects(harley.rec) && robin.isCrashing == false) {

				System.out.println("harley hit robin, robin lives " + robin.lives);
				robin.isCrashing = true;
				robin.lives = robin.lives - 1;
			}

			if (!robin.rec.intersects(harley.rec)) {
				robin.isCrashing = false;
			}
		}

	}


	//when batman and robin meet give one life
	public void BatmanRobinCrash(){
		if (batman.isAlive && robin.isAlive) {
			if (batman.rec.intersects(robin.rec) && batman.isMeeting == false) {
				batman.isMeeting = true;
				if (batman.lives < 3) {
					batman.lives = batman.lives + 1; //gains life
					System.out.println("batman met robin, batman lives " + batman.lives);
				}
				if (!batman.rec.intersects(robin.rec)) {
					batman.isMeeting = false;
				}
			}
			if (robin.rec.intersects(batman.rec) && robin.isMeeting == false) {
				robin.isMeeting = true;
				if (robin.lives < 3) {
					robin.lives = robin.lives + 1;
					System.out.println("robin met batman, robin lives " + robin.lives);
				}
				if (!robin.rec.intersects(batman.rec)) {
					robin.isMeeting = false;
				}
			}
		}
	}

	//when joker and harley meet each gain life
	public void JokerHarleyCrash(){
		if (joker.isAlive && harley.isAlive) {
			if (joker.rec.intersects(harley.rec) && joker.isMeeting == false) {
				joker.isMeeting = true;
				if (joker.lives <= 2) {
					joker.lives = joker.lives + 1;
					System.out.println("joker met harley, joker lives "  + joker.lives);
				}
				if (!joker.rec.intersects(harley.rec)) {
					joker.isMeeting = false;
				}
			}
			if (harley.rec.intersects(joker.rec) && harley.isMeeting == false) {
				harley.isMeeting = true;
				if (harley.lives <= 2) {
					harley.lives = harley.lives + 1;
					System.out.println("harley met joker, harley lives " + harley.lives);
				}
				if (!harley.rec.intersects(joker.rec)) {
					harley.isMeeting = false;
				}
			}
		}
	}

	//when joker and robin hit only robin loses life
	public void JokerRobinCrash(){
		if (robin.rec.intersects(joker.rec) && robin.isHitting == false) {
			robin.isHitting = true;
			robin.lives = robin.lives - 1;
			System.out.println("joker hit robin, robin lives " + robin.lives);
		}
		if (!robin.rec.intersects(joker.rec)) {
			robin.isHitting = false;
		}

		if (joker.rec.intersects(robin.rec) && joker.isHitting == false) {
			joker.isHitting = true;

			System.out.println("joker hit robin");

		}
		if (!joker.rec.intersects(robin.rec)) {
			joker.isHitting = false;
		}

	}

	//when batman and harley hit only harley loses life
	public void BatmanHarleyCrash(){
		if (harley.rec.intersects(batman.rec) && harley.isHitting == false) {
			harley.isHitting = true;
			harley.lives = harley.lives - 1;
			System.out.println("batman hit harley, harley lives =" + harley.lives);
		}
		if (!harley.rec.intersects(batman.rec)) {
			harley.isHitting = false;
		}

		if (batman.rec.intersects(harley.rec) && batman.isHitting == false) {
			batman.isHitting = true;

			System.out.println("batman hit harley");
		}
		if (!batman.rec.intersects(harley.rec)) {
			batman.isHitting = false;
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


		if (batman.isAlive == true && joker.isAlive == true) {

			//render batman
			if (batman.isAlive == true) {
				g.drawImage(batmanPic, batman.xpos, batman.ypos, batman.width, batman.height, null);
				//render lives
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
					g.drawImage(heartPicThree, batman.xpos + 55, batman.ypos - 30, 100, 30, null);
				}
			}

			//render joker
			if (joker.isAlive == true) {
				g.drawImage(jokerPic, joker.xpos, joker.ypos, joker.width, joker.height, null);
				//g.drawRect(joker.rec.x, joker.rec.y, joker.rec.width, joker.rec.height);

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
					g.drawImage(heartPicThree, joker.xpos + 55, joker.ypos - 30, 100, 30, null);
				}

			}

			if (robin.isAlive == true) {
				g.drawImage(robinPic, robin.xpos, robin.ypos, robin.width, robin.height, null);

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
					g.drawImage(heartPicThree, robin.xpos + 55, robin.ypos - 30, 100, 30, null);
				}

			}

			if (harley.isAlive == true) {
				g.drawImage(harleyPic, harley.xpos, harley.ypos, harley.width, harley.height, null);

				if (harley.lives == 0) {
					g.drawImage(heartPicZero, harley.xpos + 10, harley.ypos - 30, 100, 30, null);
				}
				if (harley.lives == 1) {
					g.drawImage(heartPicOne, harley.xpos + 10, harley.ypos - 30, 100, 30, null);
				}
				if (harley.lives == 2) {
					g.drawImage(heartPicTwo, harley.xpos + 10, harley.ypos - 30, 100, 30, null);
				}
				if (harley.lives >= 3) {
					g.drawImage(heartPicThree, harley.xpos + 10, harley.ypos - 30, 100, 30, null);
				}

			}
		}
		//ending screens
		//if both die, "tie" and nothing happens

		if (batman.isAlive == true && joker.isAlive == false){
			g.drawImage(win,250,100,500,500, null); }

		if (batman.isAlive == false  && joker.isAlive == true){
			g.drawImage(lose, 250,100,500,500,null);
		}

		g.dispose();
		bufferStrategy.show();
	}

}








