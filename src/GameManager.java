/**
 * Main class for Memory game
 *
 * @author Michael Leonhard (Original Author)
 * @author Modified by Bienvenido Vélez (UPRM)
 * @version Sept 2017
 */

import java.io.IOException;

import javax.swing.JOptionPane;


public class GameManager {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		// make an instance of the main game class

		int playMore = 2;
		while (playMore != 1) {
			MemoryFrame instance = new JustATeamsMemoryClass();
			instance.newGame("easy");
			
			while(!instance.gameOver()) {
				Thread.sleep(500);
			}
			playMore = JOptionPane.showConfirmDialog(null, "Play Again?", "GAME OVER!!!", JOptionPane.YES_NO_OPTION);
			instance.setVisible(false);
			System.out.println(playMore+"");
		}
		System.exit(0);
	}
	
	//Method to run a new Game of the same type as the other finshed game
	public static void runNewGame(String gameType) throws IOException, InterruptedException {
		int playMore = 2;
		while (playMore != 1) {
			MemoryFrame instance = new JustATeamsMemoryClass();
			instance.newGame(gameType);
			
			while(!instance.gameOver()) {
				Thread.sleep(500);
			}
			playMore = JOptionPane.showConfirmDialog(null, "Play Again?", "GAME OVER!!!", JOptionPane.YES_NO_OPTION);
			instance.setVisible(false);
			System.out.println(playMore+"");
		}
		System.exit(0);
	}
	
}
