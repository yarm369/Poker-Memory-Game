import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public abstract class JustATeamsGameLevel extends GameLevel{
	private ScoreTracker scoreLabel;
	private Vector<Card> cardsLeft;//Vector to facilitate the possible combinations left
	public JustATeamsGameLevel(TurnsTakenCounterLabel counterLabel, int cardsToGuess, JFrame mainFrame,ScoreTracker scoreLabel) {
	super(counterLabel, cardsToGuess, mainFrame);
	this.setScoreLabel(scoreLabel);
	scoreLabel.reset();
	this.cardsLeft=new Vector<Card>(cardsToGuess);
	this.getCardIcons()[52]=new ImageIcon("images/final.jpg");//Changed Card Background
	try {
		SoundEffects.runMusic(1);
	} catch (Throwable e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	public ScoreTracker getScoreLabel() {
		return scoreLabel;
	}
	
	public Vector<Card> getCardsLeft() {
		return cardsLeft;
	}
	public void setCardsLeft(Vector<Card> cardsLeft) {
		this.cardsLeft = cardsLeft;
	}

	public void setScoreLabel(ScoreTracker scoreLabel) {
		this.scoreLabel = scoreLabel;
	}

	@Override
	public String getMode() {
		// TODO Auto-generated method stub
		return null;
	}

	
	protected abstract void makeDeck() ;
	
	
	protected abstract boolean turnUp(Card card);

	protected abstract void cardsMatch();//Added Method to check if cards match so that
	//turn up will only turn up the cards
	
	protected abstract boolean isGameOver();
	

}
