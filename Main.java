package dynamic_beat_2;

public class Main {
	
	public static final int SCREEN_WIDTH = 1280; //우리가 만들 게임의 넓이
	public static final int SCREEN_HEIGHT = 720; //우리가 만들 게임의 높이 
	public static final int NOTE_SPEED = 3; //노트가 떨어지는 속도
	public static final int SLEEP_TIME = 10; //노트가 떨어지는 주기시간, 이게 없으면 속도가 낮아도 겁나 빠름
	public static final int REACH_TIME = 2; //노트가 생성되고 나서 판정대까지 도달하는 시간
	public static void main(String[] args) {
		
		new DynamicBeat();

	}

}
