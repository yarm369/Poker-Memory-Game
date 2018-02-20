import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class JustATeamsEqualPairLevel extends JustATeamsEasyLevel{

	
	
	public JustATeamsEqualPairLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame,ScoreTracker scoreLabel ) {
		super(validTurnTime, mainFrame, scoreLabel);
	}
	
	@Override
	protected void makeDeck() {
		// Creates a deck to fill the grid.  Each card appears twice in random places.
		ImageIcon backIcon = this.getCardIcons()[this.getTotalCardsPerDeck()];

		// make an array of card numbers: 0, 0, 1, 1, 2, 2, ..., 7, 7
		// duplicate the image in as many cards as the input imageClones
		int totalCardsInGrid = getRowsPerGrid() * getCardsPerRow();
		int totalUniqueCards = totalCardsInGrid/2;

		// Generate one distinct random card number for each unique card	
		int cardsToAdd[] = new int[totalCardsInGrid];
		boolean cardChosen[] = new boolean[getTotalCardsPerDeck()];

		int chosenCount = 0;
		Random rand = new Random();
		while (chosenCount < totalUniqueCards)
		{
			int nextCardNo = rand.nextInt(getTotalCardsPerDeck());
			if (!cardChosen[nextCardNo]) {
				cardChosen[nextCardNo] = true;
				cardsToAdd[2*chosenCount] = nextCardNo;
				cardsToAdd[2*chosenCount + 1] = nextCardNo;
				chosenCount++;
			}
		}

		// randomize the order of the cards
		this.randomizeIntArray(cardsToAdd);

		// make each card object and add it to the game grid
		for(int i = 0; i < cardsToAdd.length; i++)
		{
			// number of the card, randomized
			int num = cardsToAdd[i];
			// make the card object and add it to the panel
			String rank = cardNames[num].substring(0, 1);
			String suit = cardNames[num].substring(1, 2);
			this.getGrid().add( new JustATeamsCard(this, this.getCardIcons()[num], backIcon, num, rank, suit));
		}
	}
	
	@Override
	protected boolean turnUp(Card card) {
		
		// the card may be turned
		if(this.getTurnedCardsBuffer().size() < getCardsToTurnUp()) 
		{
			this.getTurnedCardsBuffer().add(card);
			try {
				SoundEffects.runMusic(2);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	//Used to sort cards left
	protected Vector<Card> sortCardsLeftByRank(){
		
		Vector<Card> sortedCards=new Vector<Card>(this.getCardsLeft().size());
		for(int i=0;i<ranks.length;i++) {
			for(int j=0;j<this.getCardsLeft().size();j++) {
				if(this.getCardsLeft().get(j).getRank().equals(ranks[i])) {
					sortedCards.add(this.getCardsLeft().get(j));
				}
			}
		}
		return sortedCards;
	}
	
	@Override
	protected void cardsMatch() {
		
			this.getTurnsTakenCounter().increment();//Add player turn
			// there are two cards faced up
			// get the other card (which was already turned up)
			JustATeamsCard otherCard1 = (JustATeamsCard) this.getTurnedCardsBuffer().get(0);
			JustATeamsCard otherCard2 = (JustATeamsCard) this.getTurnedCardsBuffer().get(1);
			
			// the cards match, so remove them from the list (they will remain face up) and award 50 points
			if( otherCard1.getNum() == otherCard2.getNum()) {
				this.getTurnedCardsBuffer().clear();
				this.getScoreLabel().award(50);
			
			}
			
			// the cards do not match, so start the timer to turn them down and penalty the player's score
			else{
				this.getTurnDownTimer().start();
				this.getScoreLabel().penalty();
			
				}
				
			if(!possibleEqualPairsCombinations()&&!isGameOver()) {
				noMovesKeepPlaying();
			}
			
		
	}
	
	//Check if there are possible combinations.
	protected boolean possibleEqualPairsCombinations() {
		for(int i=0;i<this.getGrid().size();i++) {
			if(!this.getGrid().get(i).isFaceUp()) {
				this.getCardsLeft().add(this.getGrid().get(i));
			}
		}
		
		this.setCardsLeft(sortCardsLeftByRank());
		
		for(int i=0;i<this.getCardsLeft().size()-this.getCardsToTurnUp()+1;i++) {
			if(this.getCardsLeft().get(i).getRank().equals(this.getCardsLeft().get(i+1).getRank())){
				this.getCardsLeft().clear();
				return true;
			}
		}
		
		
		return false;
	}
	
	//Method that runs if there are no more combinations possible. Runs the JOption accordingly
	protected void noMovesKeepPlaying() {
		
		try {
			SoundEffects.runMusic(3);
		}catch(Throwable e) {
			e.printStackTrace();
		}
		
		int option=JOptionPane.showConfirmDialog(null, "There are no more possible Combinations Left!\n \tKeep Playing?", "Game Over!", JOptionPane.YES_NO_OPTION);
		if(option==1||option==-1) {
			System.exit(0);
		}
		
		else {
			this.getMainFrame().setVisible(false);
			MemoryFrame instance = new JustATeamsMemoryClass();
			try {
				instance.newGame(this.getMode());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String getMode() {
		return "equalPair";
	}
	

}
