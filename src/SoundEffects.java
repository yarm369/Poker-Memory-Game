import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffects {
	
	public static void runMusic(int option) throws Throwable{//throws Throwable to allow the usage of the player
		String fileName="";
		switch(option) {
		case 1:
			fileName="sound effects/shuffle.wav";
			break;
		case 2:
			fileName="sound effects/cardturned.wav";
			break;
		case 3:
			fileName="sound effects/crowd-cheer.wav";
			break;
		default:
			fileName="";
		}
		
		File sound=new File(fileName);
		Clip clip=AudioSystem.getClip();
		AudioInputStream inputStream=AudioSystem.getAudioInputStream(sound);
		clip.open(inputStream);
		clip.start();
		
	}
	
}
