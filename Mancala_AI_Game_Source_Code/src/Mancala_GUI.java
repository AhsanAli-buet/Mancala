import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;


public class Mancala_GUI {
	
	public static JFrame mancala_frame;
	public static JPanel mancala_panel;
	public static JLabel player1_label;
	public static JLabel player2_label;
	public static JLabel player_turn_label;
	public static JProgressBar ai_BAR;
	public static ArrayList<JButton> Player1_box = new ArrayList<JButton>();
	public static ArrayList<JButton> Player2_box = new ArrayList<JButton>();
	public static JButton Player1_cala;
	public static JButton Player2_cala;
	public static JButton new_game;
	public static JButton about_game;
	public static JRadioButton AI_RBTN;
	public static JRadioButton HU_RBTN;
	public static Agent Human_agent;
	public static Agent Alpha_Beta_agent;
	public static Thread human_thread;
	public static Thread agent_thread;

	public static JFrame AboutWindow = new JFrame();
	public static JLabel L_About = new JLabel("Mancala Game");
	public static JLabel T_About = new JLabel("",JLabel.CENTER);
	
	public Mancala_GUI() {
	}
			
	public void start_gui(){
		
		Build_Mancala_Window();	
		Build_About_Window();
		set_Button_Action();
	}
	
	public static void Build_Mancala_Window(){
		
		
		mancala_frame = new JFrame("Mancala Game");
		mancala_frame.getContentPane().setBackground(new Color(0,128,128));
		mancala_frame.getContentPane().setLayout(null);
		
		mancala_panel = new RoundedPanel(1100,400,150,Color.MAGENTA);
		mancala_panel.setLocation(40,90);
	    
		int player1_box_loc = 150;
		for(int i=1;i<=6;i++){
			
			player1_box_loc+=10;
			JButton button = new RoundButton(Integer.toString(4),120,120);
		    button.setBackground(new Color(0,162,232));
		    button.setFont(new Font("Arial", Font.PLAIN, 40));
		    button.setForeground(Color.WHITE);
		    button.setFocusPainted(false);
		    button.setLocation(player1_box_loc, 260);
		    player1_box_loc+=120;
			mancala_panel.add(button);
			Player1_box.add(button);
			
		}
		
		int player2_box_loc = 930;
		for(int i=1;i<=6;i++){
			
			player2_box_loc-=120;
			JButton button = new RoundButton(Integer.toString(4),120,120);
		    button.setBackground(new Color(0,162,232));
		    button.setFont(new Font("Arial", Font.PLAIN, 40));
		    button.setForeground(Color.WHITE);
		    button.setFocusPainted(false);
		    button.setLocation(player2_box_loc, 20);
		    player2_box_loc-=10;
			mancala_panel.add(button);
			Player2_box.add(button);
		}
		
		player1_box_loc = 930;
		
		Player1_cala = new RoundButton_cala(Integer.toString(0),126,300);
		Player1_cala.setBackground(new Color(0,162,232));
		Player1_cala.setFont(new Font("Arial", Font.PLAIN, 60));
		Player1_cala.setForeground(Color.WHITE);
		Player1_cala.setFocusPainted(false);
		Player1_cala.setLocation(player1_box_loc+25, 80);
		mancala_panel.add(Player1_cala);
		
		Player2_cala = new RoundButton_cala(Integer.toString(0),126,300);
		Player2_cala.setBackground(new Color(0,162,232));
		Player2_cala.setFont(new Font("Arial", Font.PLAIN, 60));
		Player2_cala.setForeground(Color.WHITE);
		Player2_cala.setFocusPainted(false);
		Player2_cala.setLocation(15, 10);
		mancala_panel.add(Player2_cala);
		
		player_turn_label = new JLabel();
		player_turn_label.setText("");
		player_turn_label.setFont(new Font("Lucida Calligraphy", Font.BOLD, 26));
		player_turn_label.setForeground(new Color(255,255,255));
		player_turn_label.setLocation(480,180);
		player_turn_label.setSize(200,50);
		player_turn_label.setHorizontalAlignment(SwingConstants.CENTER);
		player_turn_label.setText("");
		mancala_panel.add(player_turn_label);
		
		new_game = new JButton("New Game");
        new_game.setBounds(525,570,180,40);
        new_game.setBackground(Color.GRAY);
        new_game.setForeground(new Color(70,200,255));
        new_game.setFont(new Font("Lucida Calligraphy", Font.BOLD|Font.ITALIC, 20));
        //new_game.setHorizontalTextPosition(SwingConstants.CENTER);
        new_game.setBorder(new LineBorder(Color.CYAN,3));
        
        about_game = new JButton("About");
        about_game.setBounds(925,570,140,40);
        about_game.setBackground(Color.GRAY);
        about_game.setForeground(new Color(70,200,255));
        about_game.setFont(new Font("Lucida Calligraphy", Font.BOLD|Font.ITALIC, 20));
        //about_game.setHorizontalTextPosition(SwingConstants.CENTER);
        about_game.setBorder(new LineBorder(Color.CYAN,3));
        
        player1_label = new JLabel();
        player1_label.setText("Human Agent");
        player1_label.setFont(new Font("Lucida Calligraphy", Font.BOLD, 25));
        player1_label.setForeground(new Color(255,255,255));
        player1_label.setBounds(505,500,230,50);
        
        JLabel game_label = new JLabel();
        game_label.setText("Mancal Game");
        game_label.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
        game_label.setForeground(Color.CYAN);
        game_label.setBounds(35,25,250,50);
        
        player2_label = new JLabel();
        player2_label.setText("AI Agent");
        player2_label.setFont(new Font("Lucida Calligraphy", Font.BOLD, 25));
        player2_label.setForeground(new Color(255,255,255));
        player2_label.setBounds(525,25,160,50);
        
        ai_BAR =  new JProgressBar(0,200);
        ai_BAR.setPreferredSize(new Dimension(200, 20));
        ai_BAR.setBorder(BorderFactory.createLineBorder( new Color(255, 255, 255,1)));
        ai_BAR.setUI(new MyProgressUI());
        ai_BAR.setForeground(Color.blue);
        ai_BAR.setBackground(new Color(0,128,128));
        ai_BAR.setIndeterminate(true);
        ai_BAR.setVisible(false);
        ai_BAR.setBounds(720, 45, 200, 20);
        
        AI_RBTN = new JRadioButton("AI First");
        AI_RBTN.setActionCommand("AI First");
        AI_RBTN.setBounds(50, 500,200,50);
        AI_RBTN.setFocusPainted(false);
        AI_RBTN.setBackground(new Color(0,128,128));
        AI_RBTN.setForeground(Color.WHITE);
        AI_RBTN.setFont(new Font("Lucida Calligraphy", Font.BOLD, 17));
        
        HU_RBTN = new JRadioButton("Human First");
        HU_RBTN.setActionCommand("Human First");
        HU_RBTN.setSelected(true);
        HU_RBTN.setFocusPainted(false);
        HU_RBTN.setBounds(50,550,200,50);
        HU_RBTN.setBackground(new Color(0,128,128));
        HU_RBTN.setForeground(Color.WHITE);
        HU_RBTN.setFont(new Font("Lucida Calligraphy", Font.BOLD, 17));
        
        ButtonGroup group = new ButtonGroup();
        
        group.add(AI_RBTN);
        group.add(HU_RBTN);
        
		mancala_frame.getContentPane().add(mancala_panel);
		mancala_frame.getContentPane().add(new_game);
		mancala_frame.getContentPane().add(about_game);
		mancala_frame.getContentPane().add(player1_label);
		mancala_frame.getContentPane().add(player2_label);
		mancala_frame.getContentPane().add(game_label);
		mancala_frame.getContentPane().add(ai_BAR);
		mancala_frame.getContentPane().add(AI_RBTN);
		mancala_frame.getContentPane().add(HU_RBTN);
		
		mancala_frame.setSize(1200, 700);
		mancala_frame.setResizable(false);
		mancala_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mancala_frame.setVisible(true);
		
		
		Player1_box_change_State(false);
		Player2_box_change_State(false);
		Player1_cala.setEnabled(false);
		Player2_cala.setEnabled(false);
		
	}
	
public static void Build_About_Window(){
		
		AboutWindow.setBounds(0, 0, 370, 500);
		AboutWindow.setLocationRelativeTo(mancala_frame);
		AboutWindow.setContentPane(new JLabel(new ImageIcon("src/theme.bmp")));
		AboutWindow.getContentPane().setLayout(null);
		AboutWindow.setTitle("About Mancala Game");
		AboutWindow.setResizable(false);
		
		T_About.setForeground(Color.BLACK);
		T_About.setFont(new Font("Lucida Console", Font.BOLD | Font.ITALIC, 13));
		T_About.setBackground(new Color(0,0,0,0));
		T_About.setEnabled(true);
		AboutWindow.getContentPane().add(T_About);
		T_About.setBounds(20,20,345,380);
		T_About.setText("<html>Mancala Game is an assignment of AI <br>LAB BUET CSE Level-4 Term-1,<br> developed by Ahsan Ali 1105083. <br>This is developed in java jdk7." +
				"<br><br>Mancala Game is a simple strategy <br>game. Rules of this game is <br>available in <br><font color=\"Lime\"><a href=\"\">https://en.wikipedia.org/wiki/Kalah</a></font>"+
				"<br><br>This game is developed for playing <br>with AI agent. AI agent is developed using Alpha Beta prunning with a <br>fixed depth and mancala heuristics."+
				"<br>Mancala is a solved game. <br>Both AI first and Human first <br>options are available."+
				"<br><br>Thank you.<br>Ahsan Ali(1105083),BUET,CSE</html>");
		
		T_About.setHorizontalAlignment(SwingConstants.CENTER);
		T_About.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		T_About.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Kalah"));
                } catch (URISyntaxException | IOException ex) {
                	
                }
            }
        });
		
		
		L_About.setForeground(Color.CYAN);
		L_About.setHorizontalAlignment(SwingConstants.CENTER);
		L_About.setFont(new Font("Lucida Calligraphy", Font.BOLD, 15));
		L_About.setBounds(20,20, 300,25);
		AboutWindow.getContentPane().add(L_About);
		
		AboutWindow.setVisible(false);
	}
	
	public static void Player1_box_change_State(Boolean flag){
		
		for(int i=0;i<6;i++)Player1_box.get(i).setEnabled(flag);
	}
	
	public static void Player2_box_change_State(Boolean flag){
			
		for(int i=0;i<6;i++)Player2_box.get(i).setEnabled(flag);
	}
	
	public static void set_Button_Action(){
		
		new_game.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				
				Player1_box_change_State(true);
				Player2_box_change_State(true);
				Player1_cala.setEnabled(true);
				Player2_cala.setEnabled(true);
				start_update_gui();
				Start_game();

			}
		});
		
		about_game.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				AboutWindow.setVisible(true);
			}
    	});
		
	}
	
	public static void Start_game() 
	{
		Game.game_on = true;
		Human_agent = new Human_Agent("Human");
		human_thread = new Thread((Runnable) Human_agent);
		human_thread.start();
		Alpha_Beta_agent  = new Alpha_Beta_Agent("AI");
		agent_thread = new Thread((Runnable) Alpha_Beta_agent);
		agent_thread.start();
		
		Game game = new Mancala_Game(Human_agent,Alpha_Beta_agent);
		Thread game_thread = new Thread((Runnable) game);
		game_thread.start();
	}
	
	public static void start_update_gui(){
		
		Mancala_GUI.player_turn_label.setText("Human Turn");
		for(int i=0;i<6;i++){
			Mancala_GUI.Player1_box.get(i).setText("4");
			Mancala_GUI.Player2_box.get(i).setText("4");
		}
		
		Mancala_GUI.Player1_cala.setText("0");
		Mancala_GUI.Player2_cala.setText("0");
		
		Mancala_GUI.new_game.setEnabled(false);
	}
	
	public static void Update_GUI(Board board){
		
		Board brd = (Board)board;
		for(int i=0;i<6;i++){
			Player1_box.get(i).setText(Integer.toString(brd.Human_pot[i]));
		}
		for(int i=0;i<6;i++){
			Player2_box.get(i).setText(Integer.toString(brd.Bot_pot[i]));
		}
		Player1_cala.setText(Integer.toString(brd.Human_kala));
		Player2_cala.setText(Integer.toString(brd.Bot_kala));
	}
}


class MyProgressUI extends BasicProgressBarUI {
  Rectangle r = new Rectangle();
  @Override
  protected void paintIndeterminate(Graphics g, JComponent c) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    r = getBox(r);
    g.setColor(progressBar.getForeground());
    g.fillOval(r.x, r.y, r.width, r.height);
  }
}
