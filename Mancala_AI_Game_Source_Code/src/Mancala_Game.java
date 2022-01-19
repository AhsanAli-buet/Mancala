import java.awt.Color;

import javax.swing.JOptionPane;

/**
 * Example class extending Game class
 * @author Azad
 *
 */
public class Mancala_Game extends Game implements Runnable
{

	/**
	 * The actual game board
	 * -1 empty, 0 -> O, 1 -> X
	 */
	
	Board board;
	
	/**
	 * First agent starts with O
	 * @param a
	 * @param b
	 */
	public Mancala_Game(Agent a, Agent b) {
		super(a, b);
		// TODO Auto-generated constructor stub
		
		a.setRole(0); 
		b.setRole(1);
		
		name = "Mancala";
		
		board = new Board();
	}
	
	public void run(){
		
		int turn = 0;// Human turn 
		//int turn = 1;// AI turn 
		if(Mancala_GUI.AI_RBTN.isSelected())turn=1;
		else turn = 0;
		
		Mancala_GUI.AI_RBTN.setEnabled(false);
		Mancala_GUI.HU_RBTN.setEnabled(false);
		
		updateMessage("Starting " + name + " between "+ agent[turn].name+ " and "+ agent[1].name+".");
		
		
		//System.out.println(agent[turn].name+ " makes the first move.");
		initialize(false);
		
		while(!isFinished())
		{
			/*if(turn ==1){
				Solver.Enable_button(false);
			}else{
				Solver.Enable_button(true);
			}*/
			
			String name = agent[turn].name;
			
			updateMessage(agent[turn].name+ " Turn");
			update_gui_Message(agent[turn].name);
			if(agent[turn].name.equals("AI")){
				Mancala_GUI.ai_BAR.setVisible(true);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(!agent[turn].makeMove(this)) turn = (turn+1)%2;
			
			if(name.equals("AI")){
				Mancala_GUI.ai_BAR.setVisible(false);
			}
			//Mancala_GUI.player_turn_label.setText("");
			enable_button_state();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			showGameState();
		}
		
		if (winner != null && board.Human_kala!=board.Bot_kala){
			game_on = false;
			updateMessage(winner.name+ " wins!!!");
			JOptionPane.showMessageDialog(Mancala_GUI.mancala_panel, winner.name+" Wins!!!");
		}
			
		else{
			game_on = false;
			JOptionPane.showMessageDialog(Mancala_GUI.mancala_panel, "Game Draw");
			updateMessage("Game drawn!!");
		}
		
		finish_update_gui();
	}

	/**
	 * Called by the play method of Game class. It must update the winner variable. 
	 * In this implementation, it is done inside checkForWin() function.
	 */
	@Override
	boolean isFinished() {
		// TODO Auto-generated method stub
		winner = null;
		int h_z = 0 , b_z = 0;
		for(int i=0;i<6;i++){
			
			h_z = h_z + board.Human_pot[i];
			b_z = b_z + board.Bot_pot[i];	
		}
		
		if(h_z == 0 || b_z == 0){
			
			if((h_z+ board.Human_kala)>(b_z+board.Bot_kala)){
				winner = agent[0];
				board.Human_kala = h_z+ board.Human_kala;
				board.Bot_kala = b_z+board.Bot_kala;
				return true;
			}else{
				winner = agent[1];
				board.Human_kala = h_z+ board.Human_kala;
				board.Bot_kala = b_z+board.Bot_kala;
				return true;
			}
		}
		else {
			return false;
		}
		
	}
	
	
	public int Check_forWin(Board brd) {
		// TODO Auto-generated method stub
		
		int h_z = 0 , b_z = 0;
		for(int i=0;i<6;i++){
			
			h_z = h_z + brd.Human_pot[i];
			b_z = b_z + brd.Bot_pot[i];	
		}
		
		if(h_z == 0 || b_z == 0){
			
			if((h_z+ brd.Human_kala)>(b_z+brd.Bot_kala)){
				return 0;
			}else{
				return 1;
			}
			
		}
		
		else {
			return -1;
		}
		
	}

	@Override
	void initialize(boolean fromFile) {
		
		board.initialize(false);
		showGameState();
	}

	/**
	 * Prints the current board (may be replaced/appended with by GUI)
	 */
	@Override
	void showGameState() {
		// TODO Auto-generated method stub
		 
        System.out.println("-----------------------AI--------------------------------");
		
        for(int i=5;i>=0;i--){
        	
        	System.out.print(" | "+board.Bot_pot[i]+" | ");
        }
        
        System.out.println("\n\n"+board.Bot_kala+"                                        "+board.Human_kala+"\n");
        
        for(int i=0;i<6;i++){
        	
        	System.out.print(" | "+board.Human_pot[i]+" | ");
        }
        
        System.out.println("\n--------------------HUMAN-------------------------------");
    }
	
	Board get_copy(Board brd){
		
		Board board = new Board();
		
		for (int i = 0; i < 6; i++) {
			board.Human_pot[i] = brd.Human_pot[i];
			board.Bot_pot[i] = brd.Bot_pot[i];
		}
		
		board.Human_kala = brd.Human_kala;
		board.Bot_kala = brd.Bot_kala;
		
		return board;
	}
	

	@Override
	void updateMessage(String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
	}
	
	public void update_gui_Message(String str){
		
		Mancala_GUI.player_turn_label.setText(str+" Turn");
		Mancala_GUI.player_turn_label.setForeground(new Color(0,128,128));
		if(str.equals("AI")){
			
			Mancala_GUI.Player1_box_change_State(false);
		}
		
	}
	
	public void enable_button_state(){
		
			for(int i=0;i<6;i++){
				if(!(Mancala_GUI.Player1_box.get(i).getText().toString().equals("0"))){
					
					Mancala_GUI.Player1_box.get(i).setEnabled(true);
				}else{
					System.out.println("0 value got");
					Mancala_GUI.Player1_box.get(i).setEnabled(false);
				}
				
				if(!(Mancala_GUI.Player2_box.get(i).getText()=="0")){
					Mancala_GUI.Player2_box.get(i).setEnabled(true);
				}else{
					Mancala_GUI.Player2_box.get(i).setEnabled(false);
				}
			}
			
	}
	
	public void finish_update_gui(){
		
		Mancala_GUI.Player1_box_change_State(false);
		Mancala_GUI.Player2_box_change_State(false);
		Mancala_GUI.Player1_cala.setEnabled(false);
		Mancala_GUI.Player2_cala.setEnabled(false);
		Mancala_GUI.player_turn_label.setText("");
		for(int i=0;i<6;i++){
			Mancala_GUI.Player1_box.get(i).setText(Integer.toString(0));
			Mancala_GUI.Player2_box.get(i).setText(Integer.toString(0));
		}
		
		Mancala_GUI.Player1_cala.setText(Integer.toString(board.Human_kala));
		Mancala_GUI.Player2_cala.setText(Integer.toString(board.Bot_kala));
		
		Mancala_GUI.AI_RBTN.setEnabled(true);
		Mancala_GUI.HU_RBTN.setEnabled(true);
		Mancala_GUI.new_game.setEnabled(true);
	}
	
}
