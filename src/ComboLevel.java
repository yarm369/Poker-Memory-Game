import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ComboLevel extends StraightLevel {
	

	public ComboLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame, ScoreTracker scoreLabel) {
		super(validTurnTime, mainFrame, scoreLabel);
	}

	protected void cardsMatch() {
		
		this.getTurnsTakenCounter().increment();//Record the players turn

		//Get all the cards that have been turned up
		JustATeamsCard otherCard1 = (JustATeamsCard) this.getTurnedCardsBuffer().get(0);
		JustATeamsCard otherCard2 = (JustATeamsCard) this.getTurnedCardsBuffer().get(1);
		JustATeamsCard otherCard3 = (JustATeamsCard) this.getTurnedCardsBuffer().get(2);
		JustATeamsCard otherCard4 = (JustATeamsCard) this.getTurnedCardsBuffer().get(3);
		JustATeamsCard otherCard5 = (JustATeamsCard) this.getTurnedCardsBuffer().get(4);
														
		JustATeamsCard[] cards= {otherCard1,otherCard2,otherCard3,otherCard4,otherCard5};
		int[] ranksChosen= sortByRank(cards);
		
		//Conditionals to show if cards are straight, full house or flush
		if (StraightHand(ranksChosen)) {
			if(pass("Straight Hand")) {//See if the player wants to pass
				this.getScoreLabel().award(1000);
				this.getScoreLabel().rankBonus(ranksChosen[4]);
				this.getTurnedCardsBuffer().clear();
			}
			else {
				this.getScoreLabel().penalty();
				this.getTurnDownTimer().start();
			}
		}
			
		else if(FullHouse(ranksChosen)) {
			if(pass("Full House")) {
				this.getScoreLabel().award(800);
				this.getScoreLabel().rankBonus(ranksChosen[4]);
				this.getTurnedCardsBuffer().clear();
				
			}
			else {
				this.getScoreLabel().penalty();
				this.getTurnDownTimer().start();
			}
		}
			
		else if(FlushHand()) {
			if(pass("Flush Hand")) {
				this.getScoreLabel().award(700);
				for(int i=0; i<this.getCardsToTurnUp(); i++) {
					this.getScoreLabel().rankBonus(this.getTurnedCardsBuffer().get(i).getRank());
				}
				this.getTurnedCardsBuffer().clear();
			}
			else {
			this.getScoreLabel().penalty();
			this.getTurnDownTimer().start();
			}
			
		}
		else {
			// The cards do not match, so start the timer to turn them down
			this.getTurnDownTimer().start();
			this.getScoreLabel().penalty();
			
		}
		//Check if any combinations are left.
		if(!possibleFlushCombinations()||!possibleStraightCombinations()||!possibleFullhouseCombinations())
			noMovesKeepPlaying();
	
			
		}
	
	//Method to check if hand is full house
	protected boolean FullHouse (int[] ranks) {
		int numRanks=1;
		int arrPosition=0;
		
		for (int i=1; i<ranks.length; i++) {
			if(ranks[arrPosition]!=ranks[i]) {
				numRanks++;
				arrPosition=i;}
		}
		
		if(numRanks==2&&ranks[4]==ranks[3]&&ranks[0]==ranks[1]) 
			return true;
		
	 return false;	}

	//Method to incorporate the PASS option
	protected boolean pass (String handType) {
		int pass = JOptionPane.showConfirmDialog(null, "Do you want to PASS?", handType+"!!!", JOptionPane.YES_NO_OPTION);
		
		if(pass==0)	return false;
		return true;
		}
	
	//Method to check if there are full house hands left in deck
	protected boolean possibleFullhouseCombinations() {
		
		for(int i=0;i<this.getGrid().size();i++) {
			if(!this.getGrid().get(i).isFaceUp()) {
				this.getCardsLeft().add(this.getGrid().get(i));
			}
		}
		
		for(int i=0;i<this.getTurnedCardsBuffer().size();i++) {
			this.getCardsLeft().add(this.getTurnedCardsBuffer().get(i));
		}
		
		this.setCardsLeft(sortCardsLeftByRank());
		
		int trios=this.getNumberOfTrios();
		int pairs=this.getNumberOfPairs();
		
		if(trios>=2||(trios==1&&pairs>=1))
			return true;
		
		return false;
	}
	
	protected int getNumberOfTrios() {//Method to get number of trios to see if full house is possible
		Vector<Card> dummyCards= new Vector<Card>(this.getCardsLeft().size());
		
		for(int i=0;i<this.getCardsLeft().size();i++) {
			dummyCards.add(this.getCardsLeft().get(i));
		}
		int trios=0;
		for(int i=0;i<dummyCards.size()-3+1;i++) {//Minus 3 because you are looking for a trios
			if(dummyCards.get(i).getRank().equals(dummyCards.get(i+1).getRank())&&
					dummyCards.get(i).getRank().equals(dummyCards.get(i+2).getRank())) {
				dummyCards.remove(i+2);
				dummyCards.remove(i+1);
				dummyCards.remove(i);
				trios++;
				i--;
			}
		}
		
		
		return trios;
	}
	
	protected int getNumberOfPairs() {//Method to get number of pairs to see if there is a full house
		int pairs=0;
		
		Vector<Card> dummyCards= new Vector<Card>(this.getCardsLeft().size());
		
		for(int i=0;i<this.getCardsLeft().size();i++) {
			dummyCards.add(this.getCardsLeft().get(i));
		}
		
		for(int i=0;i<dummyCards.size()-2+1;i++) {//Minus 2 because you are looking for pairs
			if(dummyCards.get(i).getRank().equals(dummyCards.get(i+1).getRank())) 
			{
				dummyCards.remove(i+1);
				dummyCards.remove(i);
				pairs++;
				i--;
			}
		}
		return pairs;
	}
	
	@Override
	public String getMode() {
		return "combolevel";
	}


}
