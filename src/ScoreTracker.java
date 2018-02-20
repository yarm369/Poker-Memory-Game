import javax.swing.JLabel;

public class ScoreTracker extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public  long points;
	protected String ranks[] = { "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k", "a" };
	
	public void award(long award) {
		points +=award;
		update();
		}

	
	public void rankBonus(String Rank) {
		
		
		for (int i =0; i<ranks.length; i++) {
			if (Rank.equals(ranks[i])) {
				points += i+2;	
				update();}
			else if (Rank.equals("a")) { 	
				points += 20;	
				update();}
		}	
	}
	
	public void rankBonus(int rank) {
		int  bonusPoints=0;
		if(rank!=12) {
		bonusPoints = rank+2;
		points+= bonusPoints*100;		}
		
		else points += 20*100;
		update();
		
	}
	
	private void update()	{
		this.setText("" + this.getPoints());
	}
	
	public long getPoints() {
		return points;
	}
	
	public void reset() {
		points=0;
		update();
	}
	public void penalty() {
		if(points!=0) {
		points-=5;}
		update();
	}

}
