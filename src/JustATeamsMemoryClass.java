import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class JustATeamsMemoryClass extends MemoryFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3716429058342969884L;
	private JPanel contentPane,valuesPane;
	private JustATeamsGameLevel difficulty;
	private JButton muteButton;
	private static Clip music;
	private ScoreTracker Score;
	private static int replays=0;
	
	public ScoreTracker getScore() {
		return Score;
		
	}
	public void setScore(ScoreTracker Score) {
		this.Score= Score;
	}
	
	public Clip getMusic() {
		return music;
	}


	public JustATeamsMemoryClass() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1000,850);
		this.setTitle("Just a Team's Game");
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("Memory");
		menuBar.add(mnFile);
		
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dprintln("actionPerformed " + e.getActionCommand());
				try {
					if(e.getActionCommand().equals("Easy Level")) newGame("easy");
					else if(e.getActionCommand().equals("Equal Pair Level")) newGame("equalpair");
					else if(e.getActionCommand().equals("Same Rank Trio Level")) newGame("ranktrio");
					else if(e.getActionCommand().equals("Flush Level")) newGame("flushlevel");
					else if(e.getActionCommand().equals("How To Play")) showInstructions();
					else if(e.getActionCommand().equals("About")) showAbout();
					else if(e.getActionCommand().equals("Straight Level")) newGame("straightlevel");
					else if(e.getActionCommand().equals("Combo Level")) newGame("comboLevel");
					if(e.getSource().equals(muteButton)) runOrStopMusic();
				} catch (IOException e2) {
					e2.printStackTrace(); throw new RuntimeException("IO ERROR");
				}
			}
		};

		JMenuItem easyLevelMenuItem = new JMenuItem("Easy Level");
		easyLevelMenuItem.addActionListener(action);
		mnFile.add(easyLevelMenuItem);

		JMenuItem equalPairMenuItem = new JMenuItem("Equal Pair Level");
		equalPairMenuItem.addActionListener(action);
		mnFile.add(equalPairMenuItem);
		
		JMenuItem sameRankTrioMenuItem = new JMenuItem("Same Rank Trio Level");
		sameRankTrioMenuItem.addActionListener(action);		
		mnFile.add(sameRankTrioMenuItem);
		
		JMenuItem flushMenuItem = new JMenuItem("Flush Level");
		flushMenuItem.addActionListener(action);		
		mnFile.add(flushMenuItem);
		
		JMenuItem straightLevel=new JMenuItem("Straight Level");
		straightLevel.addActionListener(action);
		mnFile.add(straightLevel);
		
		JMenuItem comboLevel=new JMenuItem("Combo Level");
		comboLevel.addActionListener(action);
		mnFile.add(comboLevel);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHowToPlay = new JMenuItem("How To Play");
		mntmHowToPlay.addActionListener(action);
		mnHelp.add(mntmHowToPlay);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(action);
		mnHelp.add(mntmAbout);
		
		this.contentPane=new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(this.contentPane);
		this.contentPane.setLayout(new BorderLayout(5, 5));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		this.contentPane.add(topPanel,BorderLayout.NORTH);
		
		Component emptySpace1=Box.createHorizontalStrut(50);
		topPanel.add(emptySpace1);
		
		JLabel lblPokerMemory = new JLabel("PoKer Memory");
		lblPokerMemory.setForeground(Color.RED);
		lblPokerMemory.setFont(lblPokerMemory.getFont().deriveFont(18.0f));
		topPanel.add(lblPokerMemory);
		
		Component emptySpace2=Box.createHorizontalGlue();
		topPanel.add(emptySpace2);
		
		muteButton=new JButton("Stop Music");
		muteButton.addActionListener(action);
		muteButton.setFont(muteButton.getFont().deriveFont(15.0f));
		muteButton.setForeground(Color.BLUE);
		topPanel.add(muteButton);
		

		this.setCenterGrid(new JPanel());
		this.getCenterGrid().setBorder(new LineBorder(new Color(0, 0, 0), 4));
		this.contentPane.add(this.getCenterGrid(), BorderLayout.CENTER);
		this.getCenterGrid().setLayout(new GridLayout(4, 4, 5, 5));

		Component horizontalStrut = Box.createHorizontalStrut(10);
		this.contentPane.add(horizontalStrut, BorderLayout.WEST);

		Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		this.contentPane.add(horizontalStrut_1, BorderLayout.EAST);

		valuesPane = new JPanel();
		this.contentPane.add(valuesPane, BorderLayout.SOUTH);
		valuesPane.setLayout(new BoxLayout(valuesPane, BoxLayout.X_AXIS));

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		valuesPane.add(horizontalStrut_2);

		JLabel clickLabel = new JLabel("Clicks: ");
		clickLabel.setFont(clickLabel.getFont().deriveFont(15.0f));
		clickLabel.setForeground(Color.RED);
		valuesPane.add(clickLabel);
		
		this.setTurnCounterLabel(new TurnsTakenCounterLabel()) ;
		this.getTurnCounterLabel().setText("");
		this.getTurnCounterLabel().setFont(this.getTurnCounterLabel().getFont().deriveFont(15.0f));
		valuesPane.add(this.getTurnCounterLabel());

		Component horizontalGlue = Box.createHorizontalGlue();
		valuesPane.add(horizontalGlue);
		
		this.setLevelDescriptionLabel(new JLabel(""));
		this.getLevelDescriptionLabel().setFont(this.getLevelDescriptionLabel().getFont().deriveFont(15.0f));
		valuesPane.add(this.getLevelDescriptionLabel());
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		valuesPane.add(horizontalGlue_1);

		JLabel lblNewLabel_3 = new JLabel("Points: ");
		lblNewLabel_3.setFont(lblNewLabel_3.getFont().deriveFont(15.0f));
		lblNewLabel_3.setForeground(Color.BLUE);
		valuesPane.add(lblNewLabel_3);
		
		this.setScore(new ScoreTracker());
		this.getScore().setText("");
		this.getScore().setFont(this.getScore().getFont().deriveFont(15.0f));
		valuesPane.add(this.getScore());
		

		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		valuesPane.add(horizontalStrut_3);
		
		
		if(replays==0) {//Run music only the first time you run the game
		try {
			JustATeamsMemoryClass.music=AudioSystem.getClip();
			AudioInputStream inputStream;
			inputStream=AudioSystem.getAudioInputStream(new File("sound effects/background-music.wav"));
			music.open(inputStream);
			music.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (LineUnavailableException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}
		replays++;
		dprintln("Number of games played: "+replays);
	}
	
		
			public void runOrStopMusic() {
				if(JustATeamsMemoryClass.music.isRunning()) {
					this.muteButton.setText("Start Music");
					JustATeamsMemoryClass.music.stop();
				}
				else {
					this.muteButton.setText("Stop Music");
					JustATeamsMemoryClass.music.loop(Clip.LOOP_CONTINUOUSLY);
				}
			}
			
			//Override to include the new levels to run
			public void newGame(String difficultyMode) throws IOException
			{
				// Reset the turn counter label
				this.getTurnCounterLabel().reset();

				if(difficultyMode.equalsIgnoreCase("easy")) {
					this.difficulty=new JustATeamsEasyLevel(this.getTurnCounterLabel(), this, this.getScore());
					this.getLevelDescriptionLabel().setText("Easy Level");
				}
				else if(difficultyMode.equalsIgnoreCase("equalPair")){
					this.difficulty=new JustATeamsEqualPairLevel(this.getTurnCounterLabel(), this,this.getScore());
					this.getLevelDescriptionLabel().setText("Equal Pair Level");
				}

				else if(difficultyMode.equalsIgnoreCase("rankTrio")){
					this.difficulty=new JustATeamsRankTrioLevel(this.getTurnCounterLabel(), this,this.getScore());
					this.getLevelDescriptionLabel().setText("Same Rank Trio Level");
				}
			
				else if(difficultyMode.equalsIgnoreCase("flushLevel")) {
					this.difficulty=new FlushLevel(this.getTurnCounterLabel(), this,this.getScore());
					this.getLevelDescriptionLabel().setText("Flush Level");
				}
				else if(difficultyMode.equalsIgnoreCase("straightLevel")) {
					this.difficulty=new StraightLevel(this.getTurnCounterLabel(), this, this.getScore());
					this.getLevelDescriptionLabel().setText("Straight Level");
				}
				else if(difficultyMode.equalsIgnoreCase("ComboLevel")) {
					this.difficulty=new ComboLevel(this.getTurnCounterLabel(), this, this.getScore());
					this.getLevelDescriptionLabel().setText("Combo Level");
								
				}

				else {
					throw new RuntimeException("Illegal Game Level Detected");
				}

				// clear out the content pane (removes turn counter label and card field)
				BorderLayout bl  = (BorderLayout) this.getContentPane().getLayout();
				this.contentPane.remove(bl.getLayoutComponent(BorderLayout.CENTER));
				this.contentPane.add(this.showCardDeck(), BorderLayout.CENTER);

				// show the window (in case this is the first game)
				this.setVisible(true);
			}
			
			public JPanel showCardDeck()//Override to fix error when extending class and creating new GameLevel difficulty variable
			{
				// make the panel to hold all of the cards
				JPanel panel = new JPanel(new GridLayout(difficulty.getRowsPerGrid(),difficulty.getCardsPerRow()));

				// this set of cards must have their own manager
				this.difficulty.makeDeck();

				for(int i= 0; i<difficulty.getGrid().size();i++){
					panel.add(difficulty.getGrid().get(i));
				}
				return panel;
			}
			
			//Override to fix bug where game never quits because difficulty is now variable of subclass
			public boolean gameOver() throws FileNotFoundException, InterruptedException{
				return difficulty.isGameOver();
			}
			
			
			//Overrides to change instructions
			private void showInstructions()
			{
				String howToPlayText="";
				dprintln("MemoryGame.showInstructions()");
				if(this.difficulty.getMode().equalsIgnoreCase("easylevel")) {
					howToPlayText="EASY Level\r\n"+
								"There are no rules!! Choose the cards as you like.\r\n"+
								"You'll always win!!!";
				}
				else if(this.difficulty.getMode().equalsIgnoreCase("equalpair")) {
					howToPlayText="EQUAL PAIR Level\r\n"+
							"The game consists of 8 pairs of cards.  At the start of the game,\r\n"+
							"every card is face down.  The object is to find all the pairs and\r\n"+
							"turn them face up.\r\n"+
							"\r\n"+
							"Click on two cards to turn them face up. If the cards are the \r\n"+
							"same, then you have discovered a pair and will be awarded with 50\r\n"+
							"points.  The pair will remain turned up.  If the cards are different,\r\n"+
							"they will flip backover automatically after a short delay and\r\n"+
							"you will be penalized by removing 5 points if the score isn't 0.\r\n"+
							"Continue flipping cards until you have discovered all of the pairs.\r\n"+
							"The game is won when all cards are face up.\r\n"+
							"\r\n";
				}
				else if(this.difficulty.getMode().equalsIgnoreCase("ranktrio")) {
					howToPlayText="SAME RANK TRIO Level\r\n"+
							"The game consists of a grid of distinct cards.  At the start of the game,\r\n"+
							"every card is face down.  The object is to find all the trios \r\n"+
							"of cards with the same rank and turn them face up.\r\n"+
							"\r\n"+
							"Click on three cards to turn them face up. If the cards have the \r\n"+
							"same rank, then you have discovered a trio and will be awarded with\r\n"+
							"100 points plus the sum of the ranks.  The trio will remain\r\n"+
							"turned up.  If the cards are different, they will flip back\r\n"+
							"over automatically after a short delay and you will be penalized by\r\n"+
							"removing 5 points if the score isn't 0.  Continue flipping\r\n"+
							"cards until you have discovered all of the pairs.  The game\r\n"+
							"is won when all cards are face up.\r\n"+
							"\r\n"+
							"Each time you flip three cards up, the turn counter will\r\n"+
							"increase.  Try to win the game in the fewest number of turns!"+"\r\n"+
							"\r\n";
				}
				else if(this.difficulty.getMode().equalsIgnoreCase("flushlevel")) {
					howToPlayText="FLUSH Level\r\n"+
							"The game consists of a grid of 50 cards. At the start of the game,\r\n"+
							"all cards are face down. The objective is to find a group of five \r\n"+
							"cards with the same suit and turn them face up.\r\n"+
							"\r\n"+
							"Click on five cards to turn them face up. If the cards have the \r\n"+
							"same suit, then you have found a group and the group will stay up \r\n"+
							"and you will be awarded 700 points plus the sum of each rank of \r\n"+
							"the cards. If the cards are different, they will flip back \r\n"+
							"automatically after a short delay and you will be penalized \r\n"+
							"by removing 5 points if the score is not 0. Continue flipping \r\n"+
							"cards until you have discovered all the groups of five cards \r\n"
							+"with the same suit. The game is won when all the cards are \r\n"+
							"face up or no possible flush hands are left.\r\n"+
							"\r\n"+
							"Each time you flip five cards up, the turn counter will\r\n"+
							"increase.  Try to win the game in the fewest number of turns!";
				}
				else if(this.difficulty.getMode().equalsIgnoreCase("straightlevel")) {
					howToPlayText="STRAIGHT Level\r\n"+
							"The game consists of a grid of 50 cards. At the start of the game,\r\n"+
							"all cards are face down. The objective is to find a group of five \r\n"+
							"cards with ranks following each other and turn them face up.\r\n"+
							"\r\n"+
							"For example, if you turn the cards 2,3,4,5,6 no matter the suit, then\r\n"+
							" you have a Straight Hand and therefore be awarded with 1000 points \r\n"+
							"plus the highest rank times 10. If the cards are different however, \r\n"+
							"the you will be pnealized by removing 5 points if the score isn't 0.\r\n"+
							"Continue flipping cards until you have discovered all the groups \r\n"
							+"of five cards with the ranks following each other. The game is \r\n"+
							"won when all the cards are face up or no possible straight hands are left.\r\n"+
							"\r\n"+
							"Each time you flip five cards up, the turn counter will\r\n"+
							"increase.  Try to win the game in the fewest number of turns!";
				}
				
				else if(this.difficulty.getMode().equalsIgnoreCase("combolevel")) {
					howToPlayText="COMBO Level\r\n"+
							"This is it! Time to put your knowledge of poker hands to the test!\r\n"+
							"In this level you start with a grid of 50 cards. Each move you may choose \r\n"+
							"5 cards and have up to 3 types of hands: The Flush Hand, the Straight \r\n"+
							"Hand, and the Full House Hand. The Flush Hand consists in 5 cards with the\r\n"+
							"same suit (See Flush Level for more details). The Straight Hand consists of\r\n"+
							"5 cards of following ranks (See Straight Level for more details). The Full \r\n"+
							"House hand consists of a Rank Trio and an Equal Pair both trio and pair being\r\n"+
							"of different ranks.\r\n"+
							"\r\n"+
							"The objective is to get the highest possible points so therefore every time\r\n"+
							"you uncover a hand you have the option to PASS. This means that the cards will\r\n"+
							"turn back down and you will be penalized as if you hadn't found a hand. This\r\n"+
							"PASS option is helpful if you find a hand but not one with many points.\r\n"+
							"\r\n"+
							"The score is 700 plus the sum of the ranks for a Flush Hand, 800 plus the \r\n"+
							"highest rank times 100 for the Full House Hand, and 1000 plus the highest \r\n"+
							"rank times 100 for the Straight Hand. But for every PASS and every wrong hand,\r\n"+
							"you will be penalized by removing 5 points if the score isn't 0.\r\n"+
							"\r\n"+
							"Each time you flip five cards up, the turn counter will increase.\r\n"+
							"Try to win the game in the fewest number of turns and think strategically\r\n"+
							"to get more points!";
				}
				
				else {
				howToPlayText=	"No help updated for game type.";
				}
				JOptionPane.showMessageDialog(this, howToPlayText
						, "How To Play", JOptionPane.PLAIN_MESSAGE);
			}

			/**
			 * Shows an dialog box with information about the program
			 */
			//Override to change the text
			private void showAbout()
			{
				dprintln("MemoryGame.showAbout()");
				final String ABOUTTEXT = "Game Customized at UPRM by Isaac Rivera and \r\n "
						+ "Yacenia Rios. Originally written by Mike Leonhard";

				JOptionPane.showMessageDialog(this, ABOUTTEXT
						, "About Memory Game", JOptionPane.PLAIN_MESSAGE);
			}
	
	
	
}
