import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;

public class StraightLevel extends FlushLevel{
		
	public StraightLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame, ScoreTracker scoreLabel) {
		super(validTurnTime,mainFrame, scoreLabel);
	}
	
	
	@Override
	protected void cardsMatch() {
		
			//Get all the cards that have been turned up
			JustATeamsCard otherCard1 = (JustATeamsCard) this.getTurnedCardsBuffer().get(0);
			JustATeamsCard otherCard2 = (JustATeamsCard) this.getTurnedCardsBuffer().get(1);
			JustATeamsCard otherCard3 = (JustATeamsCard) this.getTurnedCardsBuffer().get(2);
			JustATeamsCard otherCard4 = (JustATeamsCard) this.getTurnedCardsBuffer().get(3);
			JustATeamsCard otherCard5 = (JustATeamsCard) this.getTurnedCardsBuffer().get(4);
					
			JustATeamsCard[] cards= {otherCard1,otherCard2,otherCard3,otherCard4,otherCard5};
			int[] ranks=sortByRank(cards);
			
			this.getTurnsTakenCounter().increment();//Record the players turn
			
			if(StraightHand(ranks)) {
				this.getScoreLabel().award(1000);
				this.getScoreLabel().rankBonus(ranks[4]);
				this.getTurnedCardsBuffer().clear();// If StraightHand, remove all cards from the list (they will remain face up)
				
			}
			
			else{
				// The cards do not match, so start the timer to turn them down
				this.getScoreLabel().penalty();
				this.getTurnDownTimer().start();
			}
		
			if(!possibleStraightCombinations()) noMovesKeepPlaying();
		
	}
	
	//Method to check if the cards picked are a straight hand
	protected boolean StraightHand (int[] ranks) {
		
		int matchingRanks=1;
		for(int i=0;i<ranks.length-1;i++) {
			if(ranks[i]+1!=ranks[i+1]) {
				// The ranks do not match the objective
				break;	}
			else 	matchingRanks++;
		}
		
		if(matchingRanks==this.getCardsToTurnUp()) {
			return true;
		}
		else return false;
		
	}
	
	//Method to check if there are possible hands left
	protected boolean possibleStraightCombinations() {
		
		for(int i=0;i<this.getGrid().size();i++) {
			if(!this.getGrid().get(i).isFaceUp()) {
				this.getCardsLeft().add(this.getGrid().get(i));
			}
		}
		
		for(int i=0;i<this.getTurnedCardsBuffer().size();i++) {
			this.getCardsLeft().add(this.getTurnedCardsBuffer().get(i));
		}
		
		int[] cardsInRanks=sortByRank();
		
		List<Integer> ranksLeft=new ArrayList<Integer>();
		for(int i=0;i<13;i++) {//There are 13 types of ranks in the deck
			for(int j=0;j<cardsInRanks.length;j++) {
				if(i==cardsInRanks[j])//exists card with this rank therefore add to a comparison list
				{
					ranksLeft.add(cardsInRanks[j]);
					break;
				}
			}
		}
		
		for(int i=0;i<ranksLeft.size()-this.getCardsToTurnUp()+1;i++) {
			if(ranksLeft.get(i)+1==ranksLeft.get(i+1)&&
					ranksLeft.get(i+1)+1==ranksLeft.get(i+2)&&
					ranksLeft.get(i+2)+1==ranksLeft.get(i+3)&&
					ranksLeft.get(i+3)+1==ranksLeft.get(i+4)) {
				this.getCardsLeft().clear();
				return true;
			}
		}
		
		return false;
	}
	
	
	//Used to simplify the checking of the cards rank
	protected int[] sortByRank(JustATeamsCard[] cards) {
		int[] ranks=new int[cards.length];
		for(int i=0;i<cards.length;i++) {
			ranks[i]=cards[i].getNum()/4;	}
		Arrays.sort(ranks);
		return ranks;
		}
	
	//Overload to use for checking possible combinations in cards left
	protected int[] sortByRank() {
		int[] ranks=new int[this.getCardsLeft().size()];
		for(int i=0;i<this.getCardsLeft().size();i++) {
			ranks[i]=this.getCardsLeft().get(i).getNum()/4;
		}
		Arrays.sort(ranks);
		return ranks;
	}
	
	@Override
	public String getMode() {
		return "straightlevel";
	}
	
}
