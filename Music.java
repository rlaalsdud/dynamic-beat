package dynamic_beat_2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import dynamic_beat_1.Main;
import javazoom.jl.player.Player;

public class Music extends Thread{

	private Player player;
	private boolean isLoop;
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;

	public Music(String name, boolean isLoop) {
		try {
			this.isLoop = isLoop;
			file = new File(Main.class.getResource("../music/" + name).toURI());
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	} 
	public int getTime() {
		if(player == null)
			return 0;
		return player.getPosition();
	}
	public void close() { //노래가 틀어져있어도 언제든지 끌 수 있음
		isLoop = false;
		player.close();
		this.interrupt();
	}

	@Override
	public void run() {
		try {
			do {
				player.play();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			}while(isLoop); //true라면 계속 반복
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
