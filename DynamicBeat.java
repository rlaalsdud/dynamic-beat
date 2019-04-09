package dynamic_beat_2;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import dynamic_beat_1.Main;

public class DynamicBeat extends JFrame{

	private Image screenImage;
	private Graphics screenGraphic;
	private Image background;

	private ImageIcon exitButtonEnteredImage = new ImageIcon((Main.class.getResource("../images/exitButtonEntered.png")));
	private ImageIcon exitButtonBasicImage = new ImageIcon((Main.class.getResource("../images/exitButtonBasic.png")));
	private ImageIcon startButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/startButtonEntered.png"));
	private ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("../images/startButtonBasic.png"));
	private ImageIcon quitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/quitButtonEntered.png"));
	private ImageIcon quitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/quitButtonBasic.png"));
	private ImageIcon leftButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/leftButtonEntered.png"));
	private ImageIcon leftButtonBasicImage = new ImageIcon(Main.class.getResource("../images/leftButtonBasic.png"));
	private ImageIcon rightButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/rightButtonEntered.png"));
	private ImageIcon rightButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rightButtonBasic.png"));
	private ImageIcon easyButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/easyButtonEntered.png"));
	private ImageIcon easyButtonBasicImage = new ImageIcon(Main.class.getResource("../images/easyButtonBasic.png"));
	private ImageIcon hardButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/hardButtonEntered.png"));
	private ImageIcon hardButtonBasicImage = new ImageIcon(Main.class.getResource("../images/hardButtonBasic.png"));
	private ImageIcon backButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/backButtonBasic.png"));
	private ImageIcon backButtonBasicImage = new ImageIcon(Main.class.getResource("../images/backButtonBasic.png"));


	private JButton exitButton = new JButton(exitButtonBasicImage);
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton quitButton = new JButton(quitButtonBasicImage);
	private JButton leftButton = new JButton(leftButtonBasicImage);
	private JButton rightButton = new JButton(rightButtonBasicImage);
	private JButton easyButton = new JButton(easyButtonBasicImage);
	private JButton hardButton = new JButton(hardButtonBasicImage);

	private JButton backButton = new JButton(backButtonBasicImage);
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));

	private int mouseX, mouseY;

	private boolean isMainScreen = false; //main로 이동하면 true로
	private boolean isGameScreen = false;

	ArrayList<Track> trackList = new ArrayList<Track>();

	private Image titleImage;
	private Image selectedImage;
	private Music selectedMusic;
	private int nowSelected = 0; //현재 상태의 것의 인덱스

	Music introMusic = new Music("introMusic.mp3", true);
	public static Game game; //프로젝트 전체에서 사용되는 변수

	public DynamicBeat() {
		trackList.add(new Track("Everything Title Image.jpg", "Everything Start image.jpg", "Everything Game image.jpg",
				"EVERYTHING Selected.mp3", "EVERYTHING - IKON.mp3", "EVERYTHING - IKON")); //index 0
		trackList.add(new Track("Love Title Image.jpg", "Love Start image.jpg", "Love Game image.jpg",
				"LOVE AND FALL selected.mp3", "LOVE AND FALL - BOBBY.mp3", "LOVE AND FALL - BOBBY")); //index 1
		trackList.add(new Track("Rubber Title Image.jpg", "Rubber Start image.jpg", "RubberGame image.jpg",
				"RUBBER BAND selected.mp3", "RUBBER BAND - BOBBY.mp3", "RUBBER BAND - BOBBY")); //index 2
		//로딩이 길어지면 오류가 발생할 수 있으므로 trackList.add는 제일 앞에 두는 것이 오류 방지
		
		setUndecorated(true);
		setTitle("Dynamic Beat");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); //전체 창의 길이
		setResizable(false); //만들어진 크기 마음대로 바꿀 수 없다.
		setLocationRelativeTo(null); //중앙에 화면이 나옴
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //게임 창 종료했을 때 프로그램 전체가 종료, 이것을 쓰지 않으면 종료해도 계속 게임이 돌아감
		setVisible(true); //게임이 정상적으로 되면 우리에게 보여줌
		setBackground(new Color(0, 0, 0, 0)); //배경이 흰색
		setLayout(null); 

		addKeyListener(new KeyListener());

		background = new ImageIcon((Main.class.getResource("../images/introBackground(Title).jpg"))).getImage();

		introMusic.start();
		
		exitButton.setBounds(1245, 0, 30, 30);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); //커서가 손모양
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); //커서가 원래대로 -> 화살표
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				try {
					Thread.sleep(1000);
				} catch(InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0); //게임 종료 
			}
		});
		add(exitButton);


		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		}); //마우스로 막 드래그할 수 있다.(화면이동가능)
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		}); //마우스로 막 드래그할 수 있다.(화면이동가능)
		add(menuBar);



		startButton.setBounds(40, 200, 400, 100);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startButtonEnteredImage);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				introMusic.close();
				enterMain();
			}
		});
		add(startButton);

		quitButton.setBounds(40, 330, 400, 100);
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setIcon(quitButtonEnteredImage);
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setIcon(quitButtonBasicImage);
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(quitButton);

		leftButton.setVisible(false);
		leftButton.setBounds(140, 310, 60, 60);
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				leftButton.setIcon(leftButtonEnteredImage);
				leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				leftButton.setIcon(leftButtonBasicImage);
				leftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				//왼쪽 버튼 이벤트
				selectLeft();
			}
		});
		add(leftButton);

		rightButton.setVisible(false);
		rightButton.setBounds(1080, 310, 60, 60);
		rightButton.setBorderPainted(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setFocusPainted(false);
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rightButton.setIcon(rightButtonEnteredImage);
				rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				rightButton.setIcon(rightButtonBasicImage);
				rightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				//오른쪽 버튼 이벤트
				selectRight();
			}
		});
		add(rightButton);


		easyButton.setVisible(false);
		easyButton.setBounds(375, 580, 250, 67);
		easyButton.setBorderPainted(false);
		easyButton.setContentAreaFilled(false);
		easyButton.setFocusPainted(false);
		easyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				easyButton.setIcon(easyButtonEnteredImage);
				easyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				easyButton.setIcon(easyButtonBasicImage);
				easyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				//난이도 쉬운 이벤트
				gameStart(nowSelected, "Easy");
			}
		});
		add(easyButton);

		hardButton.setVisible(false);
		hardButton.setBounds(655, 580, 250, 67);
		hardButton.setBorderPainted(false);
		hardButton.setContentAreaFilled(false);
		hardButton.setFocusPainted(false);
		hardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hardButton.setIcon(hardButtonEnteredImage);
				hardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				hardButton.setIcon(hardButtonBasicImage);
				hardButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				//난이도 어려운 이벤트
				gameStart(nowSelected, "Hard");

			}
		});
		add(hardButton);

		backButton.setVisible(false);
		backButton.setBounds(20, 50, 60, 60);
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setFocusPainted(false);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				backButton.setIcon(backButtonEnteredImage);
				backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				backButton.setIcon(backButtonBasicImage);
				backButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonEnteredMusic = new Music("buttonPressedMusic.mp3", false);
				buttonEnteredMusic.start();
				//메인 화면으로 돌아가는 이벤트
				backMain();
			}
		});
		add(backButton);

	}
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D)screenGraphic); //Graphics2D는 글자 깨짐방지
		g.drawImage(screenImage, 0, 0, null); //screenImage를 0, 0위치에 둠
	}
	public void screenDraw(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		if(isMainScreen) {//add가 아닌 단순히 이미지를 줄때
			g.drawImage(selectedImage, 340, 100, null);
			g.drawImage(titleImage, 340, 70, null);
		}
		if(isGameScreen) { 
			game.screenDraw(g);
		}
		paintComponents(g); //main에 추가된 요소를 보여줌 ex)add
		try {
			Thread.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.repaint();
	}
	public void selectTrack(int nowSelected) {
		if(selectedMusic != null) {
			selectedMusic.close();
			titleImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getTitleImage())).getImage();
			selectedImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getStartImage())).getImage();
			selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);
			selectedMusic.start();
		}
		else {
			titleImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(0).getTitleImage())).getImage();
			selectedImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(0).getStartImage())).getImage();
			selectedMusic = new Music(trackList.get(0).getStartMusic(), true);
			selectedMusic.start();
		}
	}
	public void selectLeft(){
		if(nowSelected == 0)
			nowSelected = trackList.size() - 1;
		else
			nowSelected--;
		selectTrack(nowSelected);
	}
	public void selectRight(){
		if(nowSelected == trackList.size() - 1)
			nowSelected = 0;
		else
			nowSelected++;
		selectTrack(nowSelected);
	}

	public void gameStart(int nowSelected, String difficulty) {
		if(selectedMusic != null)
			selectedMusic.close();
		isMainScreen = false;
		leftButton.setVisible(false); //main이 아니라서
		rightButton.setVisible(false); 
		easyButton.setVisible(false); 
		hardButton.setVisible(false); 
		background = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getGameImage())).getImage();
		backButton.setVisible(true);
		isGameScreen = true;
		game = new Game(trackList.get(nowSelected).getTitleName(), difficulty, trackList.get(nowSelected).getGameMusic());
		game.start(); //자동으로 Game의 run이 실행됨
		setFocusable(true);
	} 

	public void backMain() {
		isMainScreen = true; //다시 main함수로 돌아왔기 때문에 true
		leftButton.setVisible(true);
		rightButton.setVisible(true); 
		easyButton.setVisible(true); 
		hardButton.setVisible(true);
		background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
		backButton.setVisible(false);
		selectTrack(nowSelected);
		isGameScreen = false;
		game.close();
	}
	public void enterMain(){
		startButton.setVisible(false);
		quitButton.setVisible(false);
		isMainScreen = true;
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		easyButton.setVisible(true);
		hardButton.setVisible(true);
		background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
		introMusic.close();
		selectTrack(0); //처음에는 첫 번째 곡 실행
	}
}
