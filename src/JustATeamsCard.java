import javax.swing.Icon;

public class JustATeamsCard extends Card{
	
	private JustATeamsGameLevel turnedManager;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JustATeamsCard(JustATeamsGameLevel turnedManager, Icon face, Icon back, int num, String rank, String suit) {
		super(turnedManager, face, back, num, rank, suit);
		this.turnedManager=turnedManager;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void turnUp() {	
		
		super.turnUp();
		
		boolean cardsMatchRunner=false;
		if(turnedManager.getTurnedCardsBuffer().size()==turnedManager.getCardsToTurnUp())
			cardsMatchRunner=true;
		
		if(cardsMatchRunner&&!turnedManager.getTurnDownTimer().isRunning())
		this.turnedManager.cardsMatch();
		
}
}