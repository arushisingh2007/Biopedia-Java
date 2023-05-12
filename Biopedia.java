// Arushi Singh
// 04/11/22
// Turned in: 05/19/22
// Biopedia.java
//This is the third verion of the game: with an example of how
//		the questions are going to be read from a file and implemented
//		into the game

//Biopedia.java: This game is to have three options for the user to learn
//test and explore the main organs of the body. First, with learn mode to learn
//about each organ. Quizzes/Tests to quiz and test the user and a fun
//guess the organ which is a bit harder and quicker to play. 
//Goal is to help user learn about 9th grade Organ Biology.

//all the imports
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;	
import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.Adjustable;

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;

public class Biopedia
{
	public Biopedia()
	{	
	}
	
	public static void main(String[] args)
	{
		Biopedia bio = new Biopedia();
		bio.runIt();
	}
	
	public void runIt()
	{
		//All the basic JFrame stuff
		JFrame biology = new JFrame("Biopedia");
		biology.setSize(1100,700);
		biology.setDefaultCloseOperation(biology.EXIT_ON_CLOSE); 
		biology.setLocation(100,15);
		biology.setResizable(true);
		BiopediaHolder bph = new BiopediaHolder();
		biology.getContentPane().add(bph);
		biology.setVisible(true);
	}
}

class BiopediaHolder extends JPanel 
{
	public BiopediaHolder()
	{
		CardLayout myCards = new CardLayout();
		setLayout(myCards);
		
		// this creates and instance of each class which is a JPanel
		// and adds it to cardLayout passing in with the parameters
		// of the overall JPanel bioholder, cardLayout and name information
		Question ask = new Question();
		Multiple ple = new Multiple();
		GuessIt gi = new GuessIt();
		WelcomePagePanel wpp = new WelcomePagePanel(this, myCards);
		add(wpp, "First");
		LearnPanel lp = new LearnPanel(this, myCards); 
		add(lp, "Learn");
		QuizPanel qp = new QuizPanel(this, myCards);
		add(qp, "Quiz");
		OrganPanel op = new OrganPanel(this, myCards);
		add(op, "Organ");
		Organs og = new Organs(this, myCards);
		add(og, "OrgansSh");
		Tests te = new Tests(this, myCards, ask);
		add(te, "Tests");
		OptionOrg oo = new OptionOrg(this, myCards);
		add(oo, "OptionOrg");
		Leaderboard lb = new Leaderboard(this, myCards);
		add(lb, "Leaderboard");
		Written wt = new Written(this, myCards);
		add(wt, "Written");
		MultipleChoice mc = new MultipleChoice(this, myCards);
		add(mc, "Multiple");
		MultipleTest mt = new MultipleTest(this, myCards, ask, ple);
		add(mt, "Choice");
		WrittenTest ws = new WrittenTest(this, myCards, ask, ple);
		add(ws, "Worksheet");
		GuessPanel gp = new GuessPanel(this, myCards, gi);
		add(gp, "Guess");
		//FullPanel fp = new FullPanel(this, myCards, ask);
		//add(fp, "Full");
	}
}

class WelcomePagePanel extends JPanel
{
	private BiopediaHolder panel; //panel which holds everything
	private CardLayout card; //cardLayout
	private File pictFile; //pictFile and picture used as a field variable to be accessed in paintComponent
	private Image picture; //used in paintComponent
	
	public WelcomePagePanel(BiopediaHolder wp, CardLayout cards)
	{
		card = cards;
		panel = wp;
		
		setLayout(null);
		
		JLabel welcomeLabel = new JLabel("Welcome to Biopedia!"); //welcome message
		welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 60));
		welcomeLabel.setBounds(250, -250, 800, 800);
		add(welcomeLabel);
		
		//these are all the buttons on the first page; the options the user chooses
		JButton learn = new JButton("Learn");
		learn.setFont(new Font("Times New Roman", Font.BOLD, 20));
		ButtonHandler bh = new ButtonHandler();
		learn.addActionListener(bh);
		JButton quiz = new JButton("Quizzes/Tests");
		quiz.setFont(new Font("Times New Roman", Font.BOLD, 20));
		quiz.addActionListener(bh);
		JButton leader = new JButton("View Leaderboard");
		leader.setFont(new Font("Times", Font.BOLD, 14));
		leader.addActionListener(bh);
		JButton guess = new JButton("Guess the Organ");
		guess.setFont(new Font("Times", Font.BOLD, 17));
		guess.addActionListener(bh);
		learn.setBounds(450, 270, 200, 50);
		quiz.setBounds(450, 370, 200, 50);
		leader.setBounds(880, 25, 180, 50);
		guess.setBounds(450, 470, 200, 50);
		add(learn);
		add(quiz);
		add(leader);
		add(guess);
		
		//instruction on the first page
		JLabel intro = new JLabel("Press one of the buttons to play! To exit click 'Quit.'");
		intro.setFont(new Font("Times", Font.BOLD, 30));
		intro.setBounds(230, 600, 900, 30);
		add(intro);
		
		//quit button
		JButton quit = new JButton("Quit");
		quit.setFont(new Font("Times", Font.BOLD, 20));
		quit.addActionListener(bh);
		quit.setBounds(40, 25, 100, 50);
		add(quit);	
		
		drawImage(); //method called for drawing the image on the welcome page panel
			
	}
	
	
	public void drawImage()
	{
		//drawing the image
		pictFile = new File("ImageB.jpg");
		try
		{
			picture = ImageIO.read(pictFile); //reads the file
		}
		catch(IOException e)
		{
			System.err.println("\n\n" + pictFile + " can't be found. \n\n");
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g)
	{
			super.paintComponent(g);
			g.drawImage(picture, 0, 0, 1100, 700, this); //draws the image on the first panel
	}
	
	//used to call the correct class for each panel
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("Learn"))
			{
				card.show(panel, "Learn"); //goes to the Learn page 
			}
			else if(command.equals("Quizzes/Tests"))
			{
				card.show(panel, "Quiz"); //goes to the Quiz page
			}
			else if(command.equals("Guess the Organ"))
			{
				card.show(panel, "Organ"); //goes to the Organ page
			}
			else if(command.equals("Quit"))
			{
				System.exit(1); //exits the game and stops the program from running further
			}
			else if(command.equals("View Leaderboard"))
			{
				card.show(panel, "Leaderboard");
			}
		}
	}
}

class Leaderboard extends JPanel
{
	//both are a field variable because they need to be accessed in this class and the handler class as well
	private BiopediaHolder pa; //instance of BiopediaHolder and Instance of cardLayout
	private CardLayout la; 
	
	public Leaderboard(BiopediaHolder hp, CardLayout cl)
	{
		pa = hp;
		la = cl;
		
		
		Font font = new Font("Serif", Font.BOLD, 18);
		setBackground(Color.ORANGE);
		setLayout(new FlowLayout());
		
		//all the navigation buttons: Home, Quit, Back to Quizzes/Tests
		JButton home = new JButton("Home");
		JButton quitG = new JButton("Quit Game");
		JButton back = new JButton("Back to Quizzes/Tests");
		LeaderHandler lh = new LeaderHandler();
		home.addActionListener(lh);
		quitG.addActionListener(lh);
		back.addActionListener(lh);
		home.setFont(font);
		quitG.setFont(font);
		back.setFont(font);
		add(quitG);
		add(back);
		add(home);
		
		setInformation(); //this method is called to set information in the textArea which is read in from highScore
		
	}
	
	public void setInformation()
	{
		//code taken from GameModuleFiles for reading in from Highscores.txt
		String result = "";
		String fileName = "Highscore.txt";
		Scanner inFile = null;
		File inputFile = new File(fileName);
		try 
		{
			inFile = new Scanner(inputFile);
		} 
		catch(FileNotFoundException e) 
		{
			System.err.printf("ERROR: Cannot open %s\n", fileName);
			System.out.println(e);
			System.exit(1);
		}
		while(inFile.hasNext()) 
		{
			String line = inFile.nextLine(); //this reads each line continuously
			result += line + "\n"; //makes result the line that is put in the text area every time
		}
		
		JTextArea textArea = new JTextArea(result); //textArea with all the users scores on the leaderboard
		textArea.setFont(new Font("Serif", Font.BOLD, 18));
		textArea.setLineWrap(true); 
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setBounds(150, 150, 850, 400);
		add(textArea);
		
	}
	
	//handler class for Leaderboard
	class LeaderHandler implements ActionListener 
	{
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if(command.equals("Home"))
			{
				la.show(pa, "First"); //back to welcome page
			}
			else if(command.equals("Quit Game"))
			{
				System.exit(1); //quits the game
			}
			else if(command.equals("Back to Quizzes/Tests"))
			{
				//in order to create an easier navigation from the quizzes/tests panel to leaderboard and back
				la.show(pa, "Tests"); 
			}
		}
	}
}
class LearnPanel extends JPanel 
{
	private BiopediaHolder mn; //instance of biopedia passed in as well as cardLayout
	private CardLayout ct; //used as fv's because they need to be accessed in both classes
	
	public LearnPanel(BiopediaHolder bhp, CardLayout cla)
	{
		mn = bhp;
		ct = cla;
		
		setBackground(Color.PINK);
		setLayout(null);
		
		HomeHandler1 hh = new HomeHandler1();
		//components of the panel added through nullLayout for this specific panel
		
		//Button
		JButton home = new JButton("Home");
		home.setFont(new Font("Serif", Font.BOLD, 18));
		home.setBounds(450, 50, 200, 50);
		home.addActionListener(hh); 
		add(home);
		
		//TextArea
		JTextArea intro = new JTextArea("Welcome to learn mode! In this game mode, "
			+ "you will have a variety of organs to choose from and learn information about. On the next panel, "
			+ "you will see buttons, select one of them to learn more about. If you wish to continue press the 'continue' button "
			+ "below.");
		intro.setFont(new Font("Times", Font.BOLD, 30));
		intro.setBounds(130, 200, 900, 250);
		intro.setBackground(Color.PINK);
		intro.setLineWrap(true); 
		intro.setWrapStyleWord(true);
		intro.setEditable(false);
		add(intro);
		
		//Continue Button
		JButton cont = new JButton("Continue");
		cont.setFont(new Font("Serif", Font.BOLD, 18));
		cont.setBounds(450, 540, 200, 50);
		cont.addActionListener(hh);
		add(cont);
		
		
	}
	
	//handler class for buttons
	class HomeHandler1 implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String cm = evt.getActionCommand();	
			if (cm.equals("Home"))
			{
				ct.show(mn, "First"); //goes back to the WelcomePage
			}
			else if(cm.equals("Continue"))
			{
				ct.show(mn, "OrgansSh"); //Continues onto the next Panel
			}
		}
	}		

}

class Organs extends JPanel
{
	private CardLayout cds; //same pass in of variables; instance of BiopediaHolder and cardLayout
	private BiopediaHolder hold;
	//this string is passed into the overloaded constructor of learnhold to identify which organ has been chosen
	private String organ2;
	
	
	public Organs(BiopediaHolder boh, CardLayout theCard)
	{
		cds = theCard;
		hold = boh;
		Color color = new Color(51, 204, 255);
		Font font = new Font("Serif", Font.PLAIN, 16);
		
		setLayout(new FlowLayout(150, 80, 100));
		setBackground(color);
		
		//new panel created to center the home and quit button
		JPanel bp = new JPanel(new FlowLayout(0, 225, 20));
		bp.setBackground(color);
		add(bp, FlowLayout.LEFT);
		
		JButton homeB = new JButton("Home"); //Home button
		homeB.setFont(new Font("Serif", Font.BOLD, 18));
		OrganHandler bohn = new OrganHandler();
		homeB.addActionListener(bohn);
		bp.add(homeB, FlowLayout.LEFT);
		
		JButton qB = new JButton("Quit Game"); // quits the game
		qB.setFont(new Font("Serif", Font.BOLD, 18));
		qB.addActionListener(bohn);
		bp.add(qB, FlowLayout.LEFT);
		
		//new panel created to organize all ten of the radio buttons
		JPanel rp = new JPanel(new GridLayout(2, 5, 130, 100));
		rp.setBackground(color);
		add(rp);
		JLabel radioB = new JLabel("Click one of the buttons to learn about an organ, or click 'Home' / 'Quit' to discontinue.");
		radioB.setFont(new Font("Serif", Font.PLAIN, 26));
		add(radioB, FlowLayout.LEFT);
		ButtonGroup bg = new ButtonGroup();
		
		/* these radioButtons are used in the learnMode to learn about each organ
		 * and contain another panel which will later be created which 
		 * has and image and information
		*/
		JRadioButton brain = new JRadioButton("Brain");
		JRadioButton lung = new JRadioButton("Lung");
		JRadioButton heart = new JRadioButton("Heart");
		JRadioButton liver = new JRadioButton("Liver");
		JRadioButton stomach = new JRadioButton("Stomach");
		JRadioButton spleen = new JRadioButton("Spleen");
		JRadioButton kidney = new JRadioButton("Kidney");
		JRadioButton pan = new JRadioButton("Pancreas");
		JRadioButton intes = new JRadioButton("Intestine");
		JRadioButton blad = new JRadioButton("Bladder");
		
		RadioHandler rh = new RadioHandler();
		
		brain.setBackground(color);
		lung.setBackground(color);
		heart.setBackground(color);
		liver.setBackground(color);
		stomach.setBackground(color);
		spleen.setBackground(color);
		kidney.setBackground(color);
		pan.setBackground(color);
		intes.setBackground(color);
		blad.setBackground(color);
		brain.setFont(font);
		lung.setFont(font);
		heart.setFont(font);
		liver.setFont(font);
		stomach.setFont(font);
		spleen.setFont(font);
		kidney.setFont(font);
		pan.setFont(font);
		intes.setFont(font);
		blad.setFont(font);
		//all added to the actionlistener
		brain.addActionListener(rh);
		liver.addActionListener(rh);
		heart.addActionListener(rh);
		lung.addActionListener(rh);
		stomach.addActionListener(rh);
		spleen.addActionListener(rh);
		kidney.addActionListener(rh);
		pan.addActionListener(rh);
		intes.addActionListener(rh);
		blad.addActionListener(rh);
		bg.add(brain);
		bg.add(lung);
		bg.add(heart);
		bg.add(liver);
		bg.add(stomach);
		bg.add(spleen);
		bg.add(kidney);
		bg.add(pan);
		bg.add(intes);
		bg.add(blad);
		rp.add(brain);
		rp.add(lung);
		rp.add(heart);
		rp.add(liver);
		rp.add(stomach);
		rp.add(spleen);
		rp.add(kidney);
		rp.add(pan);
		rp.add(intes);
		rp.add(blad);
		
	}
	
	class OrganHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String cmd = evt.getActionCommand();	
			if(cmd.equals("Home"))
			{
				cds.show(hold, "First"); //back to WelcomePage
			}
			else if(cmd.equals("Quit Game"))
			{
				System.exit(1);
			}
		}
	}
	
	class RadioHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String cd = evt.getActionCommand(); 
			String organ2 = new String("");
			if(cd.equals("Brain"))
			{
				organ2 = "Brain";
			}
			else if(cd.equals("Lung"))
			{
				organ2 = "Lung";
			}
			else if(cd.equals("Heart"))
			{
				organ2 = "Heart";
			}
			else if(cd.equals("Liver"))
			{
				organ2 = "Liver";
			}
			else if(cd.equals("Stomach"))
			{
				organ2 = "Stomach";
			}
			else if(cd.equals("Spleen"))
			{
				organ2 = "Spleen";
			}
			else if(cd.equals("Kidney"))
			{
				organ2 = "Kidney";
			}
			else if(cd.equals("Pancreas"))
			{
				organ2 = "Pancreas";
			}
			else if(cd.equals("Intestine"))
			{
				organ2 = "Intestine";
			}
			else if(cd.equals("Bladder"))
			{
				organ2 = "Bladder";
			}
			
			//this creates instance of Learnhold which is called here and organ2 is passed in to identify which organ the user selected
			LearnHold lh = new LearnHold(hold, cds, organ2); 
			hold.add(lh, "Panel"); //added to biopediaholder
			cds.show(hold, "Panel"); //cardLayout shows it
			
		}
	}
}

class LearnHold extends JPanel
{
	private BiopediaHolder bo; //biopedia holder instance
	private CardLayout cad; //cardLayout instance
	private String organName; //string set to the organIn passed in
	private File pictFile; //used to get file for each organ
	private Image picture; //used in paintComponent and drawImage to get the image and draw it
	//name is used to store the image name
	private String name; //String used to store each line of text as it goes through the file and checks
	//these strings are used to be printed in the textArea
	private String firstLine; //first line of information read in 
	private String secondLine;//second line of information read in and  so on
	private String thirdLine;
	private String fourthLine;
	private String fifthLine;
	
	public LearnHold(BiopediaHolder bio, CardLayout ca, String organIn)
	{
		organName = organIn;
		bo = bio;
		cad = ca;
		Font font = new Font("Times", Font.BOLD, 18);
		Color color = new Color(64,224,208);
		
		setBackground(color);
		setLayout(new FlowLayout(0, 208, 30));
		
		LearnHoldHandler lhh = new LearnHoldHandler();
		JButton homeButton = new JButton("Home");
		add(homeButton);
		homeButton.setFont(font);
		homeButton.addActionListener(lhh);
		JButton quit = new JButton("Quit");
		add(quit);
		quit.setFont(font);
		quit.addActionListener(lhh);
		JButton back = new JButton("Back");
		add(back);
		back.setFont(font);
		back.addActionListener(lhh);
		
		JPanel setPanel = new JPanel(new FlowLayout(0,300,30));
		add(setPanel);
		setPanel.setBackground(color);
		JLabel label = new JLabel(organName);
		label.setFont(new Font("Times", Font.BOLD, 25));
		setPanel.add(label);
		
		Scanner inFile = null;
		String fileName = "Learn.txt";
		File inputFile = new File(fileName);
		try
		{
			inFile = new Scanner(inputFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot open %s\n", fileName);
			System.out.println(e);
		}
		
		int i = 0;
		while(inFile.hasNextLine())
		{
			i++;
			name = inFile.nextLine(); //stores each line until organName is found
			if(name.equals(organName))
			{
				name = inFile.nextLine(); //name is the image name for the organ
				drawImage(); //draws the image
				//all the information read it
				firstLine = inFile.nextLine();
				secondLine = inFile.nextLine();
				thirdLine = inFile.nextLine();
				fourthLine = inFile.nextLine();
				fifthLine = inFile.nextLine();
			}
		}
		
		//each string is added to a new textArea which is added to the panel
		Font myFont = new Font("Times", Font.BOLD, 18);
		JPanel jpan = new JPanel();
		add(jpan);
		jpan.setPreferredSize(new Dimension(300, 800));
		jpan.setBackground(color);
		jpan.setLayout(new FlowLayout());
		JTextArea jta = new JTextArea(firstLine);
		jta.setFont(myFont);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		jta.setBounds(250, 270, 300, 50);
		jta.setEditable(false);
		jpan.add(jta);
		JTextArea jta2 = new JTextArea(secondLine);
		jta2.setFont(myFont);
		jta2.setLineWrap(true);
		jta2.setWrapStyleWord(true);
		jta2.setEditable(false);
		jta2.setBounds(100, 330, 300, 100);
		jpan.add(jta2);
		JTextArea jta3 = new JTextArea(thirdLine);
		jta3.setFont(myFont);
		jta3.setLineWrap(true);
		jta3.setWrapStyleWord(true);
		jta3.setEditable(false);
		jta3.setBounds(100, 330, 300, 100);
		jpan.add(jta3);
		JTextArea jta4 = new JTextArea(fourthLine);
		jta4.setFont(myFont);
		jta4.setLineWrap(true);
		jta4.setWrapStyleWord(true);
		jta4.setEditable(false);
		jta4.setBounds(100, 440, 300, 100);
		jpan.add(jta4);
		JTextArea jta5 = new JTextArea(fifthLine);
		jta5.setFont(myFont);
		jta5.setLineWrap(true);
		jta5.setWrapStyleWord(true);
		jta5.setEditable(false);
		jta5.setBounds(100, 440, 300, 100);
		jpan.add(jta5);
		
		
	}
	public void drawImage()
	{
		//drawing the image each time
		pictFile = new File(name);
		try
		{
			picture = ImageIO.read(pictFile); //reads the file
		}
		catch(IOException e)
		{
			System.err.println("\n\n" + pictFile + " can't be found. \n\n");
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g)
	{
			super.paintComponent(g);
			g.drawImage(picture, 575, 200, 425, 400, this); //draws the image on the first panel
	}
	
	//hanlder for buttons
	class LearnHoldHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String cmd = evt.getActionCommand();
			if(cmd.equals("Home"))
			{
				cad.show(bo, "First");
			}
			else if(cmd.equals("Back"))
			{
				cad.show(bo, "OrgansSh");
			}
			else if(cmd.equals("Quit"))
			{
				System.exit(1);
			}
		}
	}
		
}
class QuizPanel extends JPanel 
{
	private BiopediaHolder bo; //instance of biopediaholder and cardlayout
	private CardLayout cad;
	
	public QuizPanel(BiopediaHolder bio, CardLayout ca)
	{
		bo = bio;
		cad = ca;
		
		//components added through FlowLayout
		
		Font font = new Font("Times", Font.BOLD, 18);
		setBackground(Color.YELLOW);
		setLayout(new FlowLayout(550, 100, 100));
		
		//Continue button
		JButton contButton = new JButton("Continue");
		contButton.setFont(font);
		HomeHandler hh = new HomeHandler();
		contButton.addActionListener(hh);
		add(contButton, FlowLayout.LEFT);
		
		//TextArea for instructions
		JTextArea area = new JTextArea("Welcome to Quizzes and Tests! This mode "
			+ "helps you test your knowledge on how much you know about the organs discussed "
			+ "in the Learn mode. To go back to the home page click the 'Home' button "
			+ "or to continue, click the 'Continue' Button.");
		area.setFont(new Font("Times", Font.BOLD, 35));
		area.setBounds(100, 200, 900, 250);
		area.setBackground(Color.YELLOW);
		area.setLineWrap(true); 
		area.setWrapStyleWord(true);
		area.setEditable(false);
		add(area, FlowLayout.LEFT);
		
		//HomeButton
		JButton homeButton = new JButton("Home");
		homeButton.setFont(font);
		homeButton.addActionListener(hh);
		add(homeButton, FlowLayout.LEFT);
		
		
	}
	
	//listener to go back to home or continue
	class HomeHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String mand = evt.getActionCommand();
			
			if(mand.equals("Home"))
			{
				cad.show(bo, "First");
			}	
			else if(mand.equals("Continue"))
			{
				cad.show(bo, "Tests"); //continues onto next panel
			}
		}
		
	}

}

class Tests extends JPanel 
{
	private BiopediaHolder bop; //biopedia holder and cardlayout fv's that are accessed in this class and actionPerformed
	private CardLayout clt; //cardLayout is passed in
	private Question qi; //Question instance passed in to set the name
	private JTextField userName; //textfield will be used to store user's name and print to the leaderboard if necessary
	private String name; //name that the user enters in the textField
	
	public Tests(BiopediaHolder bhIn, CardLayout clIn, Question qt)
	{
		bop = bhIn;
		clt = clIn;
		qi = qt;
		
		Color color = new Color(255, 255, 153);
		setLayout(new FlowLayout(550, 100, 60));
		Font font = new Font("Tahoma", Font.PLAIN, 16);
		setBackground(color);
		
		JPanel userP = new JPanel(new FlowLayout(550, 300, 0));
		userP.setBackground(color);
		userName = new JTextField("Please enter your name here."); //will be later used to store the user's name for the leaderboard
		userName.setFont(new Font("Serif", Font.PLAIN, 25));
		add(userP, FlowLayout.LEFT);
		userP.add(userName);
		RadioH rh = new RadioH();
		TextFieldHandler tfh = new TextFieldHandler();
		userName.addActionListener(rh);
		userName.addActionListener(tfh);
		
		//this is a new panel which contains the radiobuttons for the kind of test needed
		//	and if the user wants to see the leaderboard
		JPanel radioG = new JPanel(new GridLayout(1, 3, 140, 40));
		TestHandler bo = new TestHandler();
		radioG.setBackground(color);
		add(radioG, FlowLayout.LEFT);
		ButtonGroup bgp = new ButtonGroup();
		JRadioButton choice1 = new JRadioButton("Written Test");
		choice1.setBackground(color);
		choice1.setFont(font);
		JRadioButton choice2 = new JRadioButton("Multiple Choice Test");
		choice2.setBackground(color);
		choice2.setFont(font);
		JRadioButton choice3 = new JRadioButton("Click to see leaderboard");
		choice3.setBackground(color);
		choice3.setFont(font);
		choice1.addActionListener(rh);
		choice2.addActionListener(rh);
		choice3.addActionListener(rh);
		bgp.add(choice1);
		bgp.add(choice2);
		bgp.add(choice3);
		radioG.add(choice1);
		radioG.add(choice2);
		radioG.add(choice3);
		
		//a new panel created and added with the textarea that has the instructions
		JPanel bp = new JPanel(new FlowLayout(0, 225, 10));
		JTextArea qInt = new JTextArea("Below, there are two options to choose from." 
			+ " A written test or a multiple choice test. Written tests will use one word responses"
			+ " Multiple choice tests will have with four options to choose from."
			+ " To select a type of press one of the buttons. To discontinue press Quit/Home."
			+ " Or click 'Leaderboard' button to view the Leaderboard.");
		qInt.setBounds(100, 600, 900, 250);
		qInt.setFont(new Font("Serif", Font.BOLD, 35));
		qInt.setBackground(color);
		qInt.setLineWrap(true); 
		qInt.setWrapStyleWord(true);
		qInt.setEditable(false);
		add(qInt, FlowLayout.LEFT);
		add(bp, FlowLayout.LEFT);
		bp.setBackground(color);
		
		//home and quit button created and added
		JButton hom = new JButton("Home");
		hom.setFont(new Font("Serif", Font.BOLD, 18));
		hom.addActionListener(bo);
		bp.add(hom, FlowLayout.LEFT);
		JButton quit = new JButton("Quit Game");
		quit.setFont(new Font("Serif", Font.BOLD, 18));
		quit.addActionListener(bo);
		bp.add(quit, FlowLayout.LEFT);
		
		
		
	}
	
	class TextFieldHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String cd = evt.getActionCommand();
			
			if(userName.getText() != null)
			{
				name = userName.getText();
				qi.setData(name);
			}
		}
	}	
	class TestHandler implements ActionListener 
	{
		//handler class for home and quit buttons
		public void actionPerformed(ActionEvent evt)
		{
			String cd = evt.getActionCommand();
			
			if(cd.equals("Home"))
			{
				clt.show(bop, "First");
				userName.setText("Please enter your name here.");
			}
			else if(cd.equals("Quit Game"))
			{
				System.exit(1);
			}
		}
	}
	
	class RadioH implements ActionListener 
	{
		//handler class for the radiobutton
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			String text = userName.getText();
			
			if(command.equals("Click to see leaderboard"))
			{
				clt.show(bop, "Leaderboard");
				userName.setText("Please enter your name here.");
			}
			else if(command.equals("Written Test") && !userName.getText().equals("Please enter your name here."))
			{
				clt.show(bop, "Written");
				userName.setText("Please enter your name here.");
			}
			else if(command.equals("Multiple Choice Test") && !userName.getText().equals("Please enter your name here."))
			{
				clt.show(bop, "Multiple");
				userName.setText("Please enter your name here.");
			}
		}
	}
		
}

class Written extends JPanel
{
	private BiopediaHolder ph; //both instances are passed in order to be accessed in both classes
	private CardLayout cp;
	
	public Written(BiopediaHolder bp, CardLayout ct)
	{
		ph = bp;
		cp = ct;
		Font myFont = new Font("Serif", Font.PLAIN, 19);
		Color color = new Color(51, 204, 255);
		
		JPanel mc = new JPanel();
		setLayout(new FlowLayout(550, 30, 30));
		setBackground(color);
		
		//new Panel created for the home, back, and quitgame button which are located on the top
		JPanel gl = new JPanel(new FlowLayout(550, 195, 50));
		add(gl);
		gl.setBackground(color);
		JButton home = new JButton("Home");
		home.setFont(myFont);
		gl.add(home, FlowLayout.LEFT);
		WrittenHandler wp = new WrittenHandler();
		home.addActionListener(wp);
		JButton back = new JButton("Back");
		back.setFont(myFont);
		gl.add(back, FlowLayout.LEFT);
		back.addActionListener(wp);
		JButton quit = new JButton("Quit Game");
		quit.setFont(myFont);
		gl.add(quit, FlowLayout.RIGHT);
		quit.addActionListener(wp);
		
		//mew panel for the textarea of instructions
		JPanel jp = new JPanel(new FlowLayout(0, 80, 50));
		add(jp);
		jp.setBackground(color);
		JTextArea intro = new JTextArea("In the written test section, for each question there will be a ONE WORD answer you must respond within 45 seconds in order to continue." 
			+ " On the next panel, there will be a start button. To continue the test, press it to start the timer and make a question appear. Good luck!");
		intro.setFont(new Font("Times", Font.BOLD, 35));
		intro.setBounds(130, 200, 900, 250);
		intro.setLineWrap(true); 
		intro.setWrapStyleWord(true);
		intro.setEditable(false);
		intro.setBackground(color);
		jp.add(intro);
		
		//and finally the last panel that contains the continue button in the center of the bottom of the panel
		JPanel np = new JPanel(new FlowLayout(550, 455, 0));
		add(np);
		np.setBackground(color);
		JButton continueOn = new JButton("Continue");
		continueOn.setFont(myFont);
		continueOn.addActionListener(wp);
		np.add(continueOn);
	}
		
	 
	class WrittenHandler implements ActionListener 
	{
		//handler class for all the buttons
		public void actionPerformed(ActionEvent evt)
		{
			String action = evt.getActionCommand();
			if(action.equals("Home"))
			{
				cp.show(ph, "First");
			}
			else if(action.equals("Quit Game"))
			{
				System.exit(1);
			}
			else if(action.equals("Back"))
			{
				cp.show(ph, "Tests");
			}
			else if(action.equals("Continue"))
			{
				cp.show(ph, "Worksheet");
			}
		}
	}
		
}
class MultipleChoice extends JPanel 
{
	private BiopediaHolder bl; //instance of biopedia holder and cardlayout passed in
	private CardLayout cl; // both are fv's because they need to be accessed in both classes
	
	public MultipleChoice(BiopediaHolder bp, CardLayout cy)
	{
		bl = bp;
		cl = cy;
		Font font = new Font("Serif", Font.PLAIN, 18);
		JPanel wc = new JPanel();
		Color color = new Color(255, 102, 102);
		setLayout(new FlowLayout(550, 30, 30));
		setBackground(color);
		
		//new panel created for the home, back, and quit button
		JPanel pan = new JPanel(new FlowLayout(550, 195, 50));
		add(pan);
		pan.setBackground(color);
		JButton home = new JButton("Home");
		home.setFont(font);
		pan.add(home, FlowLayout.LEFT);
		ChoiceHandler cg = new ChoiceHandler(); //instance of handler class
		home.addActionListener(cg);
		JButton back = new JButton("Back");
		back.setFont(font);
		pan.add(back, FlowLayout.LEFT);
		back.addActionListener(cg);
		JButton quit = new JButton("Quit Game");
		quit.setFont(font);
		pan.add(quit, FlowLayout.RIGHT);
		quit.addActionListener(cg);
		
		//panel created that holds the textarea of instructioins
		JPanel jp = new JPanel(new FlowLayout(0, 80, 50));
		add(jp);
		jp.setBackground(color);
		JTextArea ist = new JTextArea("In the multiple choice section, each question will have four options for you to choose from." 
			+ " On the next panel, click 'start' to start  the time and make a question appear for the test to continue." 
			+ " You will be given 45 seconds for each question. Good luck!");
		ist.setFont(new Font("Times", Font.BOLD, 35));
		ist.setBounds(130, 200, 900, 250);
		ist.setLineWrap(true); 
		ist.setWrapStyleWord(true);
		ist.setEditable(false);
		ist.setBackground(color);
		jp.add(ist);
		
		//panel created which holds the continue button at the bottom of the frame
		JPanel np = new JPanel(new FlowLayout(550, 455, 0));
		add(np);
		JButton continueGame = new JButton("Continue");
		continueGame.setFont(font);
		continueGame.addActionListener(cg);
		np.setBackground(color);
		np.add(continueGame);
	}
	
	class ChoiceHandler implements ActionListener 
	{
		//handler class for the buttons
		public void actionPerformed(ActionEvent evt)
		{
			String actionCmd = evt.getActionCommand();
			if(actionCmd.equals("Home"))
			{
				cl.show(bl, "First");
			}
			else if(actionCmd.equals("Quit Game"))
			{
				System.exit(1);
			}
			else if(actionCmd.equals("Back"))
			{
				cl.show(bl, "Tests");
			}
			else if(actionCmd.equals("Continue"))
			{
				cl.show(bl, "Choice");
			}
		}
	}
		
}

class WrittenTest extends JPanel implements ActionListener
{
	private BiopediaHolder pa; //biopedia holder and cardlayout instances passed in
	private CardLayout lay; // both are field variables because they are used in two classes 
	// Timer is accessed in three of the classes and is kept a field variables
	private Multiple mp; //instance of Multiple is passed in so that ScorePanel can check if it was a multiple or written test
	private Question qn; //question instance passed in to access methods for storing variable or getting a variable
	private Timer gameTime; //timer used 
	private int time; // amount of time the timer should run for
	private int tenthSec; // how much should be subtracted from the time
	private int secondsDisplay; // the time displayed as a graphic 
	private int amountCorrect; //amount of answers user got correct 
	private int timerCount; //how many times Start Timer was pressed
	private int count; //count used to count how many times a question was asked
	//booleans to check if answer was write or wrong
	private boolean right;
	private boolean wrong;
	private boolean runTime; // boolean for if the timer is running or not
	private Color color; //color 
	private String cmd2; //answer variable which stores the answer return from Question
	private JTextField tf; // both textfields contain instructions which are changed and kept field variables
	private JTextField jft;
	
	public WrittenTest(BiopediaHolder bp, CardLayout out, Question ask, Multiple ml)
	{
		//initialize fv's
		pa = bp;
		lay = out;
		qn = ask;
		mp = ml;
		
		timerCount = 0;
		count = 0;
		Font font = new Font("Serif", Font.PLAIN, 18);
		setLayout(null);
		color = new Color(221,160,221);
		setBackground(color);
		
		//JButtons
		WrittenTestHandler wth = new WrittenTestHandler();
		JButton quit = new JButton("Quit");
		quit.setFont(font);
		quit.addActionListener(wth);
		quit.setBounds(470, 20, 80, 35);
		add(quit);
		JButton home = new JButton("Home");
		home.setFont(font);
		home.addActionListener(wth);
		home.setBounds(550, 20, 80, 35);
		add(home);
		
		//question textfield
		tf = new JTextField(" Please press the start button below to make the question appear here.");
		tf.setFont(new Font("Serif", Font.BOLD, 30));
		tf.setEditable(false);
		tf.setBounds(80, 125, 935, 70);
		add(tf);
		
		
		jft = new JTextField("");
		jft.setFont(new Font("Serif", Font.PLAIN, 25));
		jft.setEditable(true);
		jft.setBounds(240, 460, 300, 50);
		add(jft);
		JButton submit = new JButton("Submit");
		submit.setFont(new Font("Serif", Font.PLAIN, 26));
		submit.addActionListener(wth);
		submit.setBounds(640, 460, 110, 50);
		add(submit);
		
		//startTimer button
		JButton start = new JButton("Start Timer");
		start.setFont(font);
		start.addActionListener(this);
		start.setBounds(510, 325, 110, 35);
		add(start);
		
		//this is the timer created for 45 seconds along with the second subtracted from it 
		gameTime = new Timer(1000, this); 
		time = 45;
		tenthSec = 0;
	
		
	}
	
	//paintcomponent used for the timer
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//similar code used from TimerExample.java
		
		secondsDisplay = (int)time - tenthSec % 60; //subtracts from 45 constantly and is added to in actionPerformed	
		g.setFont(new Font("Serif", Font.BOLD, 40));
		g.setColor(Color.BLACK);
		 //draws the timer onto the screen and is called in actionPerformed constantly till secondDisplay != 0
		if(secondsDisplay == 0)
		{
			g.drawString("0:00", 525, 300);
		}
		else if(secondsDisplay <= 9 && secondsDisplay > 0)
		{
			g.drawString("0:0" + secondsDisplay, 525, 300);
		}
		else
			g.drawString("0:" + secondsDisplay, 525, 300);
		
		//based on whether or not the user answered right or wrong, a message is printed
		//		from the class WrittenTestHandler by calling repaint()
		if(right == true)
		{
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("GREAT JOB! Your answer was correct. Keep it up!!!", 200, 600);
			timerCount = 0; //sets timerCount to 0 for the next question
		}
		
		if(wrong == true)
		{
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("The previous answer was wrong it was " + cmd2 + ". Have no fear keep on going!", 50, 600);
			timerCount = 0;
		}
	}
	
	//actionPerformed controls the action of the timer and regulates it based on the user's actions
	public void actionPerformed(ActionEvent evt)
	{
		String action = evt.getActionCommand();
		if(action != null)
		{
			if(action.equals("Start Timer") && timerCount == 0) //start starts the timer and changes the text which will later be a question printed to the screen
			{
				timerCount = 1;
				runTime = true;
				gameTime.start();
				qn.getQuestionFile();
				tf.setText(qn.getQuestion()); //PRINTS THE QUESTION SELECTED FROM THE FILE
			}
		}
		if (secondsDisplay == 0) //when the timer is over later there will be a new panel shown up and the panel is reset to it's original state
		{
			gameTime.stop(); //stops timer
			runTime = false;
			tf.setText(" Please press the start button below to make the question appear here."); //resets texField
			//sets everything back to the way it was
			jft.setText("");
			WrittenTestHandler wth = new WrittenTestHandler();
			jft.addActionListener(wth);
			lay.show(pa, "Worksheet");
			right = false;
			wrong = false;
		}
		if(runTime == true) //keeps on calling paintComponent to redraw the timer for the panel
			tenthSec++;
		this.repaint();
			
	}
	
	//handler class for the home and quit button
	class WrittenTestHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String cmd = evt.getActionCommand();
			String userAnswer = jft.getText();
			userAnswer = userAnswer.trim();
			count = qn.giveValue();
			cmd2 = qn.getAnswer();
			
			
			if(cmd.equals("Home"))
			{
				//since clicking home doesn't reset the timer or text, that is done here as well 
				lay.show(pa, "First"); //shows the welcomepage
				//timer counts set to 0 or 45
				time = 45;
				tenthSec = 0;
				count = 0;
				//everything here is reset to it's original value
				runTime = false;
				amountCorrect = 0;
				timerCount = 0;
				tf.setText(" Please press the start button below to make the question appear here.");
				jft.setText("");
				qn.resetEverything();
				qn.resetCount();
				right = false;
				wrong = false;
			}
			else if(cmd.equals("Quit"))
			{
				System.exit(1); //exits the game
			}
			else if(cmd.equals("Submit") && runTime)
			{
				if(cmd.equals("Submit") && runTime)
				{
					//resetEverything() is called to reset answer and question to a new one
					qn.resetEverything();
					count = qn.giveValue(); //checks the question number
					runTime = false; //timer is not running
					time = 45;
					tenthSec = 0;
					tf.setText(" Please press the start button below to make the question appear here.");
					jft.setText("");
					if(userAnswer.equals(cmd2))
					{
						amountCorrect++; //userScore is added on
						if(wrong == true)
						{
							wrong = false; //this is used to remove the previous message if necessary
						}
						right = true;
						repaint();
					}
					else
					{
						if(right == true)
						{
							right = false; //this is used to remove the previous message if necessary
						}
						wrong = true;
						repaint();
					}
				}
				if(count > 4)
				{
					//resets everything
					runTime = false;
					right = false;
					wrong = false;
					time = 45;
					tenthSec = 0;
					count = 0;
					timerCount = 0;
					//this gives in the correct value
					qn.enterValue(amountCorrect);
					amountCorrect = 0;
					//resets question, count, and answer
					qn.resetEverything();
					qn.resetCount();
					String type = "Written";
					//instance of ScorePanel is created to pass in a String which tells what kind of test was taken
					ScorePanel sp = new ScorePanel(pa, lay, qn, mp, type); 
					pa.add(sp, "Score");
					lay.show(pa, "Score");
	
				}
				else if(count < 5)
				{
					lay.show(pa, "Worksheet"); //keeps on showing this panel
				}
			}
		}
	}
}

class MultipleTest extends JPanel implements ActionListener
{
	private BiopediaHolder bh; //biopedia holder and cardlayout instances passed in
	private CardLayout cp; // both are field variables because they are used in two classes 
	// Timer is accessed in three of the classes and is kept a field variables
	private Multiple ml; //this is used to call methods to return or send in values
	private Question qt; //used to send in when creating instance of Scorepanel
	private Timer timer; //timer used for 45 seconds
	private JTextField tf; // both textfields contain instructions which are changed and kept field variables
	private JTextField fd; // both are accessed in all classes
	//all the JRadioButton
	private JRadioButton option1; 
	private JRadioButton option2;
	private JRadioButton option3;
	private JRadioButton option4;
	//booleans used to see if answer was right or wrong
	private boolean wrong;
	private boolean right;
	private boolean running; // boolean for if the timer is running or not
	private String cmd2; //methods called from Multiple and used to get the correct answer stored in this string
	private int count; //Multiple method called to find how many questions have been asked
	private int amountCorrect; //amount of questions answered correctly
	private int timeRan; // amount of time the timer should run for
	private int tenthSec; // how much should be subtracted from the time
	private int secondsDisplay; // the time displayed as a graphic 
	private int timerCount; //amount of timer Start Timer has been pressed
	   
	public MultipleTest(BiopediaHolder bp, CardLayout lay, Question askIn, Multiple mul)
	{
		//set fv's
		bh = bp;
		cp = lay;
		ml = mul;
		qt = askIn;
		
		timerCount = 0;
		count = 0;
		
		Font font = new Font("Serif", Font.PLAIN, 18);
		setLayout(null);
		Color color = new Color(175,238,238);
		setBackground(color);
		
		//JButtons set
		MultipleTestHandler wth = new MultipleTestHandler();
		JButton quit = new JButton("Quit");
		quit.setFont(font);
		quit.setBounds(470, 20, 80, 35);
		add(quit);
		JButton home = new JButton("Home");
		home.setFont(font);
		home.setBounds(550, 20, 80, 35);
		add(home);
		quit.addActionListener(wth);
		home.addActionListener(wth);
		
		//textfield set for the question to appear
		tf = new JTextField(" Please press the start button below to make the question appear here.");
		tf.setFont(new Font("Serif", Font.BOLD, 30));
		tf.setEditable(false);
		tf.setBounds(80, 125, 935, 70);
		add(tf);
		
		//all the jradiobuttons set and added onto panel
		Font myFont = new Font("Serif", Font.PLAIN, 25);
		ButtonGroup bg = new ButtonGroup();
		option1 = new JRadioButton("Option 1");
		option2 = new JRadioButton("Option 2");
		option3 = new JRadioButton("Option 3");
		option4 = new JRadioButton("Option 4");
		option1.setFont(myFont);
		option2.setFont(myFont);
		option3.setFont(myFont);
		option4.setFont(myFont);
		bg.add(option1);
		bg.add(option2);
		bg.add(option3);
		bg.add(option4);
		option1.setBackground(color);
		option2.setBackground(color);
		option3.setBackground(color);
		option4.setBackground(color);
		option1.setBounds(325, 400, 250, 50);
		option2.setBounds(675, 400, 250, 50);
		option3.setBounds(325, 500, 250, 50);
		option4.setBounds(675, 500, 250, 50);
		add(option1);
		add(option2);
		add(option3);
		add(option4);
		option1.addActionListener(wth);
		option2.addActionListener(wth);
		option3.addActionListener(wth);
		option4.addActionListener(wth);
		
		//timer created
		JButton start = new JButton("Start Timer");
		start.setFont(font);
		start.addActionListener(this);
		start.setBounds(510, 325, 110, 35);
		add(start);
		
		//this is the timer created for 45 seconds along with the second subtracted from it 
		timer = new Timer(1000, this); 
		timeRan= 45;
		tenthSec = 0;
	}
	
	//paintcomponent used for the timer
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	
		secondsDisplay = (int)timeRan - tenthSec % 60; //subtracts from 45 constantly and is added to in actionPerformed
		g.setFont(new Font("Serif", Font.BOLD, 40));
		g.setColor(Color.BLACK);
		//draws a timer which shows the amount of seconds left before the game keeps on going
		if(secondsDisplay == 0)
		{
			g.drawString("0:00", 525, 300);
		}
		else if(secondsDisplay <= 9 && secondsDisplay > 0)
		{
			g.drawString("0:0" + secondsDisplay, 525, 300);
		}
		else
			g.drawString("0:" + secondsDisplay, 525, 300);
		if(right == true)
		{
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("GREAT JOB! Your answer was correct. Keep it up!!!", 200, 630);
			timerCount = 0; //sets Timercount to 0 for the timer to start again
		}
		
		if(wrong == true)
		{
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("The previous answer was wrong it was " + cmd2 + ". Have no fear keep on going!", 50, 630);
			timerCount = 0; //sets Timercount to 0 for the timer to start again
		}
		
		
		
	}
		
	//actionPerformed which controls the timers
	public void actionPerformed(ActionEvent evt)
	{
		String action = evt.getActionCommand();
		if(action != null)
		{
			if(action.equals("Start Timer") && timerCount == 0)
			{
				timerCount = 1;
				// when the user clicks start the timer will start and the question will show up
				running = true;
				timer.start();
				ml.getQuestionFile();
				tf.setText(ml.getQuestion()); //used to get question then set it to the textfield
				//sets all the radiobuttons to have a value
				option1.setText(ml.choice1());
				option2.setText(ml.choice2());
				option3.setText(ml.choice3());
				option4.setText(ml.choice4());
			}
		}
		if (secondsDisplay == 0) //when the timer is over later there will be a new panel shown up and the panel is reset to it's original state
		{
			//resets everything to its original value
			timer.stop();
			running = false;
			tf.setText(" Please press the start button below to make the question appear here.");
			cp.show(bh, "Option");
			option1.setText("Option 1");
			option2.setText("Option 2");
			option3.setText("Option 3");
			option4.setText("Option 4");
			right = false;
			wrong = false;
			timeRan = 45;
			tenthSec = 0;
		}
		if(running == true)
			tenthSec++;
		this.repaint(); //keeps on calling paintComponent to redraw the timer for the panel
	}
	
	
	
	class MultipleTestHandler implements ActionListener //handler class fo the buttons
	{
		public void actionPerformed(ActionEvent evt)
		{
			String cmd = evt.getActionCommand();
			count = ml.giveValue(); //used to find how many questions were asked
			cmd2 = ml.getAnswer(); //used to get answer 
			
			if(cmd.equals("Home"))
			{
				//since clicking home doesn't reset the timer or text, that is done here as well
				// resets everything to its original value 
				cp.show(bh, "First");
				running = false;
				wrong = false;
				right = false;
				timer.stop();
				timeRan = 45;
				tenthSec = 0;
				tf.setText(" Please press the start button below to make the question appear here.");
				option1.setText("Option 1");
				option2.setText("Option 2");
				option3.setText("Option 3");
				option4.setText("Option 4");
				//these two methods are called to also set answer, question, and questionCount to "" and 0 along with userAnswer
				ml.resetEverything();
				ml.resetCount();
				amountCorrect = 0;
				
			}
			if(cmd.equals("Quit"))
			{
				System.exit(1);
			}
			
			if(cmd.equals(cmd2) && running)
			{
				ml.resetEverything(); //reset question and answer for nextpanel along with userAnswer
				count = ml.giveValue(); //checks count
				running = false;
				timeRan = 45;
				tenthSec = 0;
				tf.setText(" Please press the start button below to make the question appear here.");
				option1.setText("Option 1");
				option2.setText("Option 2");
				option3.setText("Option 3");
				option4.setText("Option 4");
				amountCorrect++;
				if(wrong == true)
				{
					wrong = false; //used to make the previous message clear
				}
				right = true;
				repaint(); //used to draw message to screen
				
				if(count > 4)
				{
					running = false;
					right = false;
					wrong = false;
					timeRan = 45;
					tenthSec = 0;
					count = 0;
					ml.resetEverything();
					ml.resetCount();
					//sends in score to be used in ScorePanel and called when necessary
					ml.enterValue(amountCorrect);
					amountCorrect = 0;
					String name = "Multiple";
					//instance of ScorePanel made to call with string about test type passed in
					ScorePanel sp = new ScorePanel(bh, cp, qt, ml, name); 
					bh.add(sp, "Score");
					cp.show(bh, "Score");
				}
				else if(count <= 5)
				{

					cp.show(bh, "Choice");
				}
			
			}
			else if(cmd != cmd2 && running ) 
			{
				ml.resetEverything(); //reset question and answer for nextpanel along with userAnswer
				count = ml.giveValue(); //checks count
				running = false;
				timeRan = 45;
				tenthSec = 0;
				tf.setText(" Please press the start button below to make the question appear here.");
				option1.setText("Option1");
				option2.setText("Option2");
				option3.setText("Option3");
				option4.setText("Option4");
				if(right == true)
				{
					right = false; //used to make the previous message clear
				}
				wrong = true;
				repaint(); //used to draw message to screen
				
				if(count > 4)
				{
					running = false;
					right = false;
					wrong = false;
					timeRan = 45;
					tenthSec = 0;
					count = 0;
					ml.resetEverything();
					ml.resetCount();
					//sends in score to be used in ScorePanel and called when necessary
					ml.enterValue(amountCorrect);
					amountCorrect = 0;
					String name = "Multiple";
					//instance of ScorePanel made to call with string about test type passed in
					ScorePanel sp = new ScorePanel(bh, cp, qt, ml, name); 
					bh.add(sp, "Score");
					cp.show(bh, "Score");
		
				}
				else if(count < 5)
				{
					cp.show(bh, "Choice"); //keeps on showing this panel
				}	
			}
		}
	}
}

class OrganPanel extends JPanel
{
	private BiopediaHolder pa; //instances passed in to be accessed by both classes
	private CardLayout co;
	
	public OrganPanel(BiopediaHolder bp, CardLayout out)
	{
		pa = bp;
		co = out;
		
		//components added using FlowLayout
		Font font = new Font("Serif", Font.BOLD, 18);    
		setBackground(Color.ORANGE);
		setLayout(new FlowLayout(550, 100, 100));
		HomeHandler3 hht = new HomeHandler3();
		
		//Continue Button
		JButton contAt = new JButton("Continue");
		contAt.setFont(font);
		contAt.addActionListener(hht);
		add(contAt, FlowLayout.LEFT);
		
		//Instructions TextArea
		JTextArea inst = new JTextArea("Welcome to Guess the Organ! On the next panel, "
			+ "there will be a picture or information about the organ and you have to identify " 
			+ "which organ it is. To continue click the 'Continue' button to go back to the Home page "
			+ "click 'Home.'");
		inst.setFont(new Font("Times", Font.BOLD, 35));
		inst.setBounds(100, 200, 900, 250);
		inst.setBackground(Color.ORANGE);
		inst.setLineWrap(true); 
		inst.setWrapStyleWord(true);
		inst.setEditable(false);
		add(inst, FlowLayout.LEFT);
		//HomeButton
		JButton atHome = new JButton("Home");
		atHome.setFont(font);
		atHome.addActionListener(hht);
		add(atHome, FlowLayout.LEFT);
	}
	//listener to go back to home or continue
	class HomeHandler3 implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String pressed = evt.getActionCommand();
			if(pressed.equals("Home"))
			{
				co.show(pa, "First");
				
			}	
			else if(pressed.equals("Continue"))
			{
				co.show(pa, "OptionOrg"); //onto the next panel
			}
		}
	}
}

class OptionOrg extends JPanel 
{
	private BiopediaHolder bol; //instances passed in to be accessed by both classes
	private CardLayout cya;
	
	public OptionOrg(BiopediaHolder bsh, CardLayout csh)
	{
		bol = bsh;
		cya = csh;
		Font font = new Font("Serif", Font.BOLD, 18);
		
		
		//component added through flowLayout
		setLayout(new FlowLayout(550, 100, 100));
		setBackground(Color.PINK);
		//this panel will later just go directly into the test with a question on this panel
		JButton homB = new JButton("Home");
		homB.setFont(font);
		OrgHandler org = new OrgHandler();
		homB.addActionListener(org);
		add(homB);
		
		JButton quit = new JButton("Quit");
		quit.setFont(font);
		quit.addActionListener(org);
		add(quit);
		
		//textarea created with instructions and added
		JTextArea txt = new JTextArea("'Guess the Organ' is a rapid round where you will be given 15 seconds to type in a one word answer, which will be the name of an organ "
			+ "based of a picture or short information given to it. On the next panel will be a Start button, click it for the timer to start and to make the "
			+ "question appear so the game can continue. Good luck!");
		txt.setFont(new Font("Serif", Font.BOLD, 30));
		txt.setBounds(100, 200, 900, 250);
		txt.setEditable(false);
		txt.setBackground(Color.PINK);
		txt.setLineWrap(true); 
		txt.setWrapStyleWord(true);
		add(txt);
		
		JButton continueLevel = new JButton("Continue");
		continueLevel.setFont(font);
		continueLevel.addActionListener(org);
		add(continueLevel);
		
		
	}
	//listener to go back to home or continue
	class OrgHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String action = evt.getActionCommand();
			if(action.equals("Home"))
			{
				cya.show(bol, "First"); //go back to WelcomePage
			}
			else if(action.equals("Quit"))
			{
				System.exit(1);
			}
			else if(action.equals("Continue"))
			{
				cya.show(bol, "Guess");
			}
		}
	}
}

class GuessPanel extends JPanel implements ActionListener
{
	private BiopediaHolder bh; //biopedia holder and cardlayout instances passed in
	private CardLayout cp; // both are field variables because they are used in two classes 
	// Timer is accessed in three of the classes and is kept a field variables
	private GuessIt gt; //instance of gt used to get methods and send in values
	private Timer timer; //timer used for 45 seconds
	private boolean running; // boolean for if the timer is running or not
	private int timeRan; // amount of time the timer should run for
	private int tenthSec; // how much should be subtracted from the time
	private int secondsDisplay; // the time displayed as a graphic 
	private JTextField tf; // both textfields contain instructions which are changed and kept field variables
	private JTextField jft; // both are accessed in all classes 
	//checks if answers were right or wrong
	private boolean right;
	private boolean wrong;
	private int amountCorrect; //amount of answers that are correct
	private int count; //count of questions asked
	private int timerCount; //how many timers setTimer has been called
	private String cmd2; //answer set to string returned from GuessIt
	private File pictFile; //pictFile and picture used as a field variable to be accessed in paintComponent
	private Image picture; //picture that is drawn to the screen from paintcomponent
	
	public GuessPanel(BiopediaHolder pa, CardLayout cards, GuessIt ge)
	{
		bh = pa;
		cp = cards;
		gt = ge;
		Font font = new Font("Serif", Font.BOLD, 18);
		timerCount = 0;
		setLayout(null);
		Color color = new Color(175,238,238);
		setBackground(color);
		
		//panel for quit and home button
		GuessPanelHandler gph = new GuessPanelHandler();
		JButton quit = new JButton("Quit");
		quit.setFont(font);
		quit.addActionListener(gph);
		quit.setBounds(470, 15, 80, 35);
		add(quit);
		JButton home = new JButton("Home");
		home.setFont(font);
		home.addActionListener(gph);
		home.setBounds(550, 15, 80, 35);
		add(home);
		
		//textFields for the question
		tf = new JTextField(" Please press the start button below to make the question and image appear over here");
		tf.setFont(new Font("Serif", Font.BOLD, 25));
		tf.setEditable(false);
		tf.setBounds(80, 60, 935, 50);
		add(tf);
		
		//textField for the answer
		jft = new JTextField("");
		jft.setFont(new Font("Serif", Font.PLAIN, 25));
		jft.setEditable(true);
		jft.setBounds(400, 520, 300, 50);
		add(jft);
		JButton submit = new JButton("Submit");
		submit.setFont(new Font("Serif", Font.PLAIN, 26));
		submit.addActionListener(gph);
		submit.setBounds(750, 520, 110, 50);
		add(submit);
		
		//jbutton to start timer
		JButton start = new JButton("Start Timer");
		start.setFont(new Font("Serif", Font.PLAIN, 26));
		start.addActionListener(this);
		start.setBounds(200, 520, 150, 50);
		add(start);
		
		//this is the timer created for 45 seconds along with the second subtracted from it 
		timer = new Timer(1000, this); 
		timeRan = 15;
		tenthSec = 0;
		//jpanel created for the textfield which gives motivating messages along with if the user answered the question right or wrong
	}
	
	//paintcomponent used for the timer
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		secondsDisplay = (int)timeRan - tenthSec % 60; //subtracts from 45 constantly and is added to in actionPerformed	
		g.setFont(new Font("Serif", Font.BOLD, 45));
		g.setColor(Color.BLACK);
		 //draws the timer onto the screen and is called in actionPerformed constantly till secondDisplay != 0
		if(secondsDisplay == 0)
		{
			g.drawString("0:00", 230, 300);
			g.drawImage(picture, 450, 115, 450, 390, this);
		}
		else if(secondsDisplay <= 9 && secondsDisplay > 0)
		{
			g.drawString("0:0" + secondsDisplay, 230, 300);
			g.drawImage(picture, 450, 115, 450, 390, this);
		}
		else
			g.drawString("0:" + secondsDisplay, 230, 300);
			g.drawImage(picture, 450, 115, 450, 390, this);
			
		if(right == true)
		{
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("GREAT JOB! Your answer was correct. Keep it up!!!", 200, 630);
			timerCount = 0; //to be able to press StartTimer again 
		}
		
		if(wrong == true)
		{
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("The previous answer was wrong it was " + cmd2 + ". Have no fear keep on going!", 30, 630);
			timerCount = 0; //to be able to press StartTimer again
		}
	}
		
	//actionPerformed which controls the timers
	public void actionPerformed(ActionEvent evt)
	{
		String action = evt.getActionCommand();
		if(action != null)
		{
			if(action.equals("Start Timer") && timerCount == 0) //start starts the timer and changes the text which will later be a question printed to the screen
			{
				timerCount = 1;
				running = true;
				timer.start();
				gt.getQuestionFile();
				tf.setText(gt.getQuestion()); //PRINTS THE QUESTION SELECTED FROM THE FILE
				drawImage(); //draws image from GuessIt
			}
		}
		if (secondsDisplay == 0) //when the timer is over later there will be a new panel shown up and the panel is reset to it's original state
		{
			//this resets EVERYTHING to its original value
			timer.stop();
			running = false;
			tf.setText(" Please press the start button below to make the question appear here.");
			jft.setText("");
			cp.show(bh, "Organs");
			right = false;
			wrong = false;
			timeRan = 15;
			tenthSec = 0;
			timerCount = 0;
			count++;
		}
		if(running == true) //keeps on calling paintComponent to redraw the timer for the panel
			tenthSec++;
		this.repaint();
			
	}
	
	public void drawImage()
	{
		String imageName = gt.returnName();
		//drawing the image
		pictFile = new File(imageName);
		try
		{
			picture = ImageIO.read(pictFile); //reads the file
		}
		catch(IOException e)
		{
			System.err.println("\n\n" + pictFile + " can't be found. \n\n");
			e.printStackTrace();
		}
	}
	
	class GuessPanelHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String cmd = evt.getActionCommand();
			String userAnswer = jft.getText(); //gets  the user's answer
			userAnswer = userAnswer.trim(); //trims the answer
			count = gt.giveValue(); //gets the question count
			cmd2 = gt.getAnswer(); //gets the answer from GuessIt
			boolean inGame = false; //if the user wants to go back to Home page inGame is false
			
				if(cmd.equals("Home"))
				{
					//everything is reset to its original value
					cp.show(bh, "First");
					timeRan = 15;
					tenthSec = 0;
					count = 0;
					inGame = false;
					running = false;
					amountCorrect = 0;
					timerCount = 0;
					tf.setText(" Please press the start button below to make the question appear here.");
					jft.setText("");
					//resets answer, question, user's answer, and count
					gt.resetEverything();
					gt.resetCount();
					right = false;
					wrong = false;
				}
				else if(cmd.equals("Quit"))
				{
					System.exit(1); //exits the game
				}
				
				if(cmd.equals("Submit") && running)
				{
					//sets boolean to true that the user wishes to continue playing
					inGame = true;
					//calls methods to reset everything
					gt.resetEverything();
					count = gt.giveValue();
					//reset everything
					running = false;
					timeRan = 15;
					tenthSec = 0;
					tf.setText(" Please press the start button below to make the question appear here.");
					jft.setText("");
					if(userAnswer.equals(cmd2))
					{
						amountCorrect++;
						System.out.println("Score: " + amountCorrect); 
						if(wrong == true)
						{
							wrong = false; //used to clear previous message
						}
						right = true;
						repaint();
					}
					else
					{
						if(right == true)
						{
							right = false; //used to clear previous message
						}
						wrong = true;
						repaint();
					}
				}
				if(count == 5 && inGame)
				{
					//resets everything
					running = false;
					right = false;
					wrong = false;
					timeRan = 15;
					tenthSec = 0;
					count = 0;
					gt.resetEverything();
					gt.resetCount();
					tf.setText(" Please press the start button below to make the question appear here.");
					jft.setText("");
					//sends in amount correct
					GuessScore sp = new GuessScore(bh, cp, gt, amountCorrect); 
					bh.add(sp, "GuessIt");
					cp.show(bh, "GuessIt");
					amountCorrect = 0;					
					
				}
				else if(count < 5 && inGame)
				{
					cp.show(bh, "Guess"); //keeps on showing this panel
				}
			}
		}
	}



class ScorePanel extends JPanel
{
	private BiopediaHolder bh; //biopedia holder and cardlayout instances passed in
	private CardLayout cp; // both are field variables because they are used in two classes
	private Multiple mp; //used to get score from Multiple if user did multiple choice test
	private Question qt; //used to get score from Question if user did written test
	private JTextArea jtf;
	private String name; //user's name which is read in from Question
	private int score; //score of user
	private String type; //type of test printed: written / multiple
	
	public ScorePanel(BiopediaHolder bhIn, CardLayout card, Question qn, Multiple ml, String nameIn)
	{
		bh = bhIn;
		cp = card;
		qt = qn;
		mp = ml;
		type = nameIn;
		
		setBackground(Color.YELLOW);
		Font font = new Font("Serif", Font.BOLD, 18);
		setLayout(new FlowLayout(300, 250, 100));
		
		//sets Buttons
		ButtonHand ba = new ButtonHand();
		JButton playAgain = new JButton("Play Again");
		playAgain.setFont(font);
		add(playAgain, FlowLayout.LEFT);
		playAgain.addActionListener(ba);
		JButton leader = new JButton("See Leaderboard");
		leader.setFont(font);
		add(leader, FlowLayout.LEFT);
		leader.addActionListener(ba);
		
		if(type.equals("Written"))
		{
			score = qt.returnValue();
		}
		
		else if(type.equals("Multiple"))
		{
			score = mp.returnValue();
		}
		name = qt.returnName();
		
		//based on the user score it informs about whether their name is on the leaderboard or not
		if(score > 3)
		{
			jtf = new JTextArea("Good job " + name + "! You did great! Your name will be on the Leaderboard!" 
				+ " If you want to see leaderboard click 'Leaderboard' below. To exit, click 'Home'. "
				+ " And to play again, click 'Play again'.");
		}
		else if(score <= 3)
		{
			jtf = new JTextArea("Good job " + name + "! You need to get above a score of 3 to be on the Leaderboard. " 
				+ "If you want to see leaderboard click 'Leaderboard' below. To exit, click 'Home'. "
				+ " And to play again, click 'Play again'.");
		}
		
		//continue adding jtextarea 
		jtf.setFont(new Font("Serif", Font.BOLD, 25));
		jtf.setLineWrap(true); 
		jtf.setWrapStyleWord(true);
		jtf.setEditable(false);
		jtf.setBounds(100, 200, 600, 300);
		add(jtf, FlowLayout.LEFT);
		
		//tells user their score
		JLabel scoreNow = new JLabel("Total Score: " + score + "/5");
		scoreNow.setFont(new Font("Serif", Font.BOLD, 40));
		add(scoreNow, FlowLayout.LEFT);
		 
		JButton home = new JButton("Home");
		home.setFont(font);
		add(home, FlowLayout.LEFT);
		home.addActionListener(ba);
		
		qt.resetCount();
		qt.resetEverything();
		saveToHighScores(); //calls this to write to file
		
	}
	
	public void saveToHighScores ( )
	{
		if(score > 3)
		{
			//code used from GameModuleFiles.java
			String result = ""; //used to give user info
			boolean hasBeenAdded = false; 
			String fileName = "Highscore.txt";
			Scanner inFile = null;
			File inputFile = new File(fileName);
			try 
			{
				inFile = new Scanner(inputFile);
			} 
			catch(FileNotFoundException e) 
			{
				System.err.printf("ERROR: Cannot open %s\n", fileName);
				System.out.println(e);
				System.exit(1);
			}
			while(inFile.hasNext()) 
			{
				String line = inFile.nextLine();
				//checks to make sure that user's information is in order based on score
				if(!hasBeenAdded && Integer.parseInt("" + line.charAt(line.indexOf("/") - 1)) <= score) 
				{
					result += "Type: " + type + ", Name: " + name + ", Total Score: " + score + "/5\n"; //line added to the file
					hasBeenAdded = true;
				}
				result += line + "\n";
			}
			if(!hasBeenAdded) 
			{
				result += "Type: " + type + ", Name: " + name + ", Total Score: " + score + "/5\n";
			}
			inFile.close();
			
			//this write the result string to the file and then closes it 
			File ioFile = new File("Highscore.txt");
			PrintWriter outFile = null;
			try
			{
				outFile = new PrintWriter(ioFile);
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
			outFile.print(result);
			outFile.close();
		}
	}
	
	
	class ButtonHand implements ActionListener
	{
		//handler class for buttons
		public void actionPerformed(ActionEvent evt)
		{
			String cmd = evt.getActionCommand();
			if(cmd.equals("Home"))
			{
				cp.show(bh, "First");
			}
			else if(cmd.equals("See Leaderboard"))
			{
				cp.show(bh, "Leaderboard");
			}
			else if(cmd.equals("Play Again"))
			{
				cp.show(bh, "Tests");
			}
		}
	}
	
	
}	
	
class GuessScore extends JPanel
{
	private BiopediaHolder bh; //biopedia holder and cardlayout instances passed in
	private CardLayout cp; // both are field variables because they are used in two classes
	private GuessIt gt; //instance of gt passed in
	
	public GuessScore(BiopediaHolder bo, CardLayout ca, GuessIt gi, int score)
	{
		setBackground(Color.YELLOW);
		Font font = new Font("Serif", Font.BOLD, 18);
		setLayout(new FlowLayout(100, 200, 100));
		
		bh = bo;
		cp = ca;
		gi = gt;
		int userscore = score;
		
		GuessHandler ba = new GuessHandler();
		//jbutton play again
		JButton playAgain = new JButton("Play Again");
		playAgain.setFont(font);
		add(playAgain, FlowLayout.LEFT);
		playAgain.addActionListener(ba);
		
		//jtf used to tell user their score 
		JTextArea jtf = new JTextArea("Good job! Your score was " + userscore + "/5." 
			+ " If you want to play again, click 'Play Again' and to go back home to explore more, "
			+ "click the 'Home' button");
		jtf.setFont(new Font("Serif", Font.BOLD, 30));
		jtf.setLineWrap(true); 
		jtf.setWrapStyleWord(true);
		jtf.setEditable(false);
		jtf.setBounds(100, 200, 600, 300);
		add(jtf, FlowLayout.LEFT);
		
		//tells the user their score in bold
		JLabel scoreNow = new JLabel("Total Score: " + userscore + "/5");
		scoreNow.setFont(new Font("Serif", Font.BOLD, 40));
		add(scoreNow, FlowLayout.LEFT);
		
		//jbutton home
		JButton home = new JButton("Home");
		home.setFont(font);
		add(home, FlowLayout.LEFT);
		home.addActionListener(ba);
		
	}
	
	class GuessHandler implements ActionListener
	{
		//handler class for buttons
		public void actionPerformed(ActionEvent evt)
		{
			String cmd = evt.getActionCommand();
			if(cmd.equals("Home"))
			{
				cp.show(bh, "First");
			}
			else if(cmd.equals("Play Again"))
			{
				cp.show(bh, "OptionOrg");
			}
		}
	}
}

class Question 
{
	private String question; //question returned back to quiz panels
	private String answer; //answer that will later be used to store the answer from the textfiles
	private int countCorrect; //amount of questions that the user answers correctly
	private int questionNumber; //random number generated to pick a random question from Written.txt in the file
	private int questionCount; //same number of questions
	private String username; //name user uses passed in
	private int[] array; //check if question has already been asked based on it's number and the number randomly generated
	private int userScore; //user's score passed in
	
	public Question()
	{
		//intialized fv's
		question = "";
		answer = "";
		countCorrect = 0;
		questionCount = 0;
		array = new int[6];
	}
	
	public void getQuestionFile()
	{
		Scanner inFile = null;
		String fileName = "Written.txt";
		File inputFile = new File(fileName);
		//this try-catch is used to read from written.txt that has all the questions
		try
		{
			inFile = new Scanner(inputFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot open %s\n", fileName);
			System.out.println(e);
		}
		
		
		questionNumber = (int)(Math.random() *20); //random number selected and used to read in from file
		for(int i = 0; i < 6; i++)
		{
			if(array[i] == questionNumber)
			{
				//checks array to see if this number generated has been added if it is, new number is generated
				questionNumber = (int)(Math.random() *20); 
			}
		}
		
		boolean keepGoing = true;
		while(inFile.hasNext() && keepGoing && questionCount < 5)
		{
			question = inFile.nextLine();
			if(question.equals("" + questionNumber))
			{
				array[questionCount] = questionNumber;
				answer = inFile.nextLine(); //goes to next line which contains the answer
				question = inFile.nextLine(); //gets the question on the next line
				keepGoing = false; //stops the loop since answer and question has been found
			}
		}
		questionCount++;
	}

	public String getQuestion ( )
	{
		return question; //used to call from other classes to get a question
	}
	
	public String getAnswer()
	{
		return answer;
	}
	public void resetEverything()
	{
		//when needed to reset all the values this method is called
		userScore = 0;
		question = "";
		answer = ""; 
	}		
	
	public int giveValue()
	{
		return questionCount; //returns count for question
	}
	
	public void resetCount()
	{
		questionCount = 0; //resets question count
	}
	public void setData(String name)
	{
		username = name; //gets user name passed in
	}
	public String returnName()
	{
		return username; //returns user's name
	}
	
	public void enterValue(int score)
	{
		userScore = score; //score of user passed in 
	}
	
	public int returnValue()
	{
		return userScore; //return's user score
	}
		
}

class Multiple
{
	private String question; //question returned back to quiz panels
	private String answer; //answer that will later be used to store the answer from the textfiles
	private int countCorrect; //amount of questions that the user answers correctly
	private int questionNumber; //random number generated to pick a random question from Written.txt in the file
	private int questionCount; //same number of questions
	private int userScore; //user's score passed in
	private int[] array; //array to store question numbers and check with randomly generated numbers
	//names read in and returned for the radiobuttons
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	
	public Multiple()
	{
		//intialized fv's
		question = "";
		answer = "";
		countCorrect = 0; 
		questionCount = 0;
		array = new int[6];
	}
	
	public void getQuestionFile()
	{
		Scanner inFile = null;
		String fileName = "Multiple.txt";
		File inputFile = new File(fileName);
		//this try-catch is used to read from written.txt that has all the questions
		try
		{
			inFile = new Scanner(inputFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot open %s\n", fileName);
			System.out.println(e);
		}
		
		
		questionNumber = (int)(Math.random() *15); //random number selected and used to read in from file
		for(int i = 0; i < 6; i++)
		{
			if(array[i] == questionNumber)
			{
				//checks array to see if this number generated has been added if it is, new number is generated
				questionNumber = (int)(Math.random() *15);
			}
		}
		boolean keepGoing = true;
		while(inFile.hasNext() && keepGoing && questionCount < 5)
		{
			question = inFile.nextLine();
			if(question.equals("" + questionNumber))
			{
				array[questionCount] = questionNumber;
				question = inFile.nextLine(); //goes to next line which contains the question
				//gets all the name of radiobuttons
				option1 = inFile.nextLine();
				option2 = inFile.nextLine();
				option3 = inFile.nextLine();
				option4 = inFile.nextLine();
				//gets answer for the question
				answer = inFile.nextLine();
				keepGoing = false; //fix if questions are same
			}
		}
		questionCount++;
	}

	public String getQuestion()
	{
		return question; //used to call from other classes to get a question
	}
	
	public String getAnswer()
	{
		return answer; //returns answer
	}
	public void resetEverything()
	{
		//when needed to reset all the values this method is called
		question = "";
		answer = ""; 
		option1 = "";
		option2 = "";
		option3 = "";
		option4 = "";
	}		
	
	public int giveValue()
	{
		return questionCount; //returns amount of questions asked
	}
	
	public void resetCount()
	{
		questionCount = 0; //resets question count
	}
	
	public void enterValue(int score)
	{
		userScore = score; //userScore passed in
	}
	
	public int returnValue()
	{
		return userScore; //returns user socre
	}
	
	//returns all the strings for the radiobuttons
	public String choice1()
	{
		return option1;
	}
	
	public String choice2()
	{
		return option2;
	}
	
	public String choice3()
	{
		return option3;
	}
	
	public String choice4()
	{
		return option4;
	}
}

class GuessIt 
{
	private String question; //question returned back to quiz panels
	private String answer; //answer that will later be used to store the answer from the textfiles
	private int countCorrect; //amount of questions that the user answers correctly
	private int questionNumber; //random number generated to pick a random question from Written.txt in the file
	private int questionCount; //same number of questions
	private String username;
	private int totalScore;
	private String imgName;
	private int[] array;
	
	public GuessIt()
	{
		//intialized fv's
		question = "";
		answer = "";
		countCorrect = 0;
		questionCount = 0;
		array = new int[6];
		totalScore = 0;
	}
	
	public void getQuestionFile()
	{
		Scanner inFile = null;
		String fileName = "Guess.txt";
		File inputFile = new File(fileName);
		//this try-catch is used to read from written.txt that has all the questions
		try
		{
			inFile = new Scanner(inputFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot open %s\n", fileName);
			System.out.println(e);
		}
		
		
		questionNumber = (int)(Math.random() *14); //random number selected and used to read in from file
		for(int i = 0; i < 6; i++)
		{
			if(array[i] == questionNumber)
			{
				//checks array to see if this number generated has been added if it is, new number is generated
				questionNumber = (int)(Math.random() *14);
			}
		}
		boolean keepGoing = true;
		while(inFile.hasNext() && keepGoing && questionCount < 5)
		{
			question = inFile.nextLine();
			if(question.equals("" + questionNumber))
			{
				array[questionCount] = questionNumber; //adds question number to array
				question = inFile.nextLine(); //gets question
				imgName = inFile.nextLine(); //image name
				answer = inFile.nextLine(); //answer for question
				keepGoing = false;  //stops loop
			}
		}
		questionCount++;
	}

	public String getQuestion ( )
	{
		return question; //used to call from other classes to get a question
	}
	
	public String getAnswer()
	{
		return answer; //returns answer
	}
	public void resetEverything()
	{
		//resets question and answer
		question = "";
		answer = ""; 
	}		
	
	public int giveValue()
	{
		return questionCount; //returns the amount of questions that have been asked
		
	}
	
	public void resetCount()
	{
		questionCount = 0; //resets questionCount
	}
		
	public String returnName()
	{
		return imgName; //returns image name
	}
}
