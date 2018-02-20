import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class JustATeamsRankTrioLevel extends JustATeamsEqualPairLevel{
	
	
	public JustATeamsRankTrioLevel(TurnsTakenCounterLabel validTurnTime, JFrame mainFrame, ScoreTracker scoreLabel) {
		super(validTurnTime, mainFrame, scoreLabel);
		this.setCardsToTurnUp(3);
		this.setCardsPerRow(10);
		this.setRowsPerGrid(5);
	}
	
	@Override
	protected void makeDeck() {
		// In Trio level the grid consists of distinct cards, no repetitions

		//back card
		ImageIcon backIcon = this.getCardIcons()[this.getTotalCardsPerDeck()];

		int cardsToAdd[] = new int[getRowsPerGrid() * getCardsPerRow()];
		for(int i = 0; i < (getRowsPerGrid() * getCardsPerRow()); i++)
		{
			cardsToAdd[i] = i;
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
			this.getGrid().add( new JustATeamsCard(this, this.getCardIcons()[num], backIcon, num, rank, suit));
		}
	}
	
	@Override
	protected boolean turnUp(Card card) {
		// the card may be turned
		if(this.getTurnedCardsBuffer().size() < getCardsToTurnUp()) 
		{
			// add the card to the list
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
	
	@Override
	protected void cardsMatch() {
		
			// We are uncovering the last card in this turn
			this.getTurnsTakenCounter().increment();//Record the players turn
			//Get the Cards to see if they match
			JustATeamsCard otherCard1 = (JustATeamsCard) this.getTurnedCardsBuffer().get(0);
			JustATeamsCard otherCard2 = (JustATeamsCard) this.getTurnedCardsBuffer().get(1);
			JustATeamsCard otherCard3=(JustATeamsCard) this.getTurnedCardsBuffer().get(2);
			if((otherCard1.getRank().equals(otherCard2.getRank())) && (otherCard1.getRank().equals(otherCard3.getRank()))) {
				// Three cards match, so remove them from the list (they will remain face up) and add 100 points to the score
				this.getTurnedCardsBuffer().clear();
				this.getScoreLabel().award(100);
				this.getScoreLabel().rankBonus(otherCard1.getRank());
			
				
			}
			else
			{
				// The cards do not match, so start the timer to turn them down and penalty the player's Score
				this.getTurnDownTimer().start();
				this.getScoreLabel().penalty();
				
			}
			
			//After Cards are turned down or removed check if there are any combinations left
			if(!possibleTrioCombinations()&&!isGameOver()) 
				noMovesKeepPlaying();
			
			
			
		}
	
	//Method to check if the game can still continue with possible hands
		protected boolean possibleTrioCombinations() {
			
			for(int i=0;i<this.getGrid().size();i++) {
				if(!this.getGrid().get(i).isFaceUp()) {
					this.getCardsLeft().add(this.getGrid().get(i));
				}
			}
			for(int i=0;i<this.getTurnedCardsBuffer().size();i++) {//If card buffer isn't clear add to list to compare
				this.getCardsLeft().add(this.getTurnedCardsBuffer().get(i));
			}
			
			this.setCardsLeft(sortCardsLeftByRank());
			
			for(int i=0;i<this.getCardsLeft().size()-this.getCardsToTurnUp()+1;i++) {
				if(this.getCardsLeft().get(i).getRank().equals(this.getCardsLeft().get(i+1).getRank())&&
						this.getCardsLeft().get(i).getRank().equals(this.getCardsLeft().get(i+2).getRank())){
					this.getCardsLeft().clear();
					return true;
				}
			}
			
			return false;
		}
		
	
	@Override
	public String getMode() {
		return "ranktrio";
	}
	

}
