
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class FlushLevel extends JustATeamsRankTrioLevel{

	
	protected FlushLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame, ScoreTracker scoreLabel) {
		super(validTurnTime,mainFrame,scoreLabel);
		this.setCardsToTurnUp(5);
		this.setCardsPerRow(10);
		this.setRowsPerGrid(5);
	}
	
	protected void makeDeck() {
		//Flush consists of different cards with 5 same suits
		//Create Back Card
		ImageIcon back= this.getCardIcons()[this.getTotalCardsPerDeck()];
		
		int cardsToAdd[] = new int[getRowsPerGrid() * getCardsPerRow()];
		//Gives a number to fill later the cards but have to order differently to be able to win so establish groups of
		//5 so the player can finish game. This deck will be used Later for other game types.
		int position=0;
		for(int i=0;i<this.getRowsPerGrid()-1;i++) {
			for(int j=0;j<this.getCardsPerRow();j++) {
				cardsToAdd[position]=i+4*j;
				position++;
			}
		}
		
		
		for(int i=0;i<2;i++) {//Two sets of 5 cards of same suit to complete cards
			int rand1=new Random().nextInt(this.getRowsPerGrid()-1);
			int rand2=new Random().nextInt(this.getCardsPerRow()-1);
			for(int j=0;j<this.getCardsToTurnUp();j++) {
				cardsToAdd[position]=rand1+(this.getRowsPerGrid()-1)*(rand2)+4*j;
				position++;
			}
		}
		
		// randomize the order of the deck
		this.randomizeIntArray(cardsToAdd);
		
		// make each card object
		for(int i = 0; i < cardsToAdd.length; i++)
			{
				// number of the card, randomized
				int num = cardsToAdd[i];
				// make the card object and add it to the panel
				String rank = cardNames[num].substring(0, 1);
				String suit = cardNames[num].substring(1, 2);
				this.getGrid().add( new JustATeamsCard(this, this.getCardIcons()[num], back, num, rank, suit));
				}
			}
	
	@Override
	protected void cardsMatch() {
			
			this.getTurnsTakenCounter().increment();//Record the players turn

				if(FlushHand()) {
					this.getScoreLabel().award(700);
					for(int i=0; i<this.getCardsToTurnUp(); i++) {
						this.getScoreLabel().rankBonus(this.getTurnedCardsBuffer().get(i).getRank());
						
					}
					this.getTurnedCardsBuffer().clear();// Five cards match, remove them from the list (they will remain face up)
				}
				
				
				else
				{	// The cards do not match, so start the timer to turn them down
					this.getTurnDownTimer().start();
					this.getScoreLabel().penalty();
					}
				//Check if possible combinations
				if(!possibleFlushCombinations()&&!isGameOver()) 
					noMovesKeepPlaying();
				
			
		}
	
		//Method to check if the cards picked are winners.
		protected boolean FlushHand() {
				
				//Get all the cards that have been turned up
				JustATeamsCard otherCard1 = (JustATeamsCard) this.getTurnedCardsBuffer().get(0);
				JustATeamsCard otherCard2 = (JustATeamsCard) this.getTurnedCardsBuffer().get(1);
				JustATeamsCard otherCard3 = (JustATeamsCard) this.getTurnedCardsBuffer().get(2);
				JustATeamsCard otherCard4 = (JustATeamsCard) this.getTurnedCardsBuffer().get(3);
				JustATeamsCard otherCard5 = (JustATeamsCard) this.getTurnedCardsBuffer().get(4);
						
				//Compare all the cards
				if((otherCard1.getSuit().equals(otherCard2.getSuit())) && (otherCard1.getSuit().equals(otherCard3.getSuit()))
						&& (otherCard1.getSuit().equals(otherCard4.getSuit()))&&(otherCard1.getSuit().equals(otherCard5.getSuit()))) {
					return true;
				}
				
				return false;
			}
	
		//To Check all cards still turned down for possible combinations;
		protected boolean possibleFlushCombinations() {
			for(int i=0;i<this.getGrid().size();i++) {
				if(!this.getGrid().get(i).isFaceUp()) {
					this.getCardsLeft().add(this.getGrid().get(i));
				}
			}
			
			for(int i=0;i<this.getTurnedCardsBuffer().size();i++) {
				this.getCardsLeft().add(this.getTurnedCardsBuffer().get(i));
			}
			
			this.setCardsLeft(sortCardsLeftBySuit());
			
			for(int i=0;i<this.getCardsLeft().size()-this.getCardsToTurnUp()+1;i++) {
				if(this.getCardsLeft().get(i).getSuit().equals(this.getCardsLeft().get(i+1).getSuit()) &&
					this.getCardsLeft().get(i).getSuit().equals(this.getCardsLeft().get(i+2).getSuit()) &&
					this.getCardsLeft().get(i).getSuit().equals(this.getCardsLeft().get(i+3).getSuit()) &&
					this.getCardsLeft().get(i).getSuit().equals(this.getCardsLeft().get(i+4).getSuit())){
					
					this.getCardsLeft().clear();
					return true;
				}
			}
			
			return false;
		}
	
	//Used to check if possible hands are left
		protected Vector<Card> sortCardsLeftBySuit(){
			
			
			Vector<Card> sortedCards=new Vector<Card>(this.getCardsLeft().size());
			for(int i=0;i<suits.length;i++) {
				for(int j=0;j<this.getCardsLeft().size();j++) {
					if(this.getCardsLeft().get(j).getSuit().equals(suits[i])) {
						sortedCards.add(this.getCardsLeft().get(j));
					}
				}
			}
			
			return sortedCards;
		}
	
	@Override
	public String getMode() {
		return "flushlevel";
	}
	
	
}
