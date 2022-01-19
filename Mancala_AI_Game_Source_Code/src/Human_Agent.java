import java.util.Scanner;


public class Human_Agent extends Agent implements Runnable
{

	public static int Human_move = -1;
	public static boolean clicked = false;
	public Scanner in;
	//static Scanner in = new Scanner(System.in);
	public Human_Agent(String name) {
		super(name);
		//in = new Scanner(System.in);
	
	}

	@Override
	public boolean makeMove(Game game) {

		
		Human_Agent.clicked = false;
		int move;
		boolean flag = false;
		Mancala_Game mnklGame = (Mancala_Game) game;
		
		/*try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		while(!Human_Agent.clicked){
			
			for(int i=0;i<6;i++){
				if(Mancala_GUI.Player1_box.get(i).getModel().isArmed()){
					Human_Agent.clicked = true;
					Human_move = i;
					break;
				}
			}
		}
		
		//Human_move = in.nextInt();
		
		flag = mnklGame.board.Human_move(Human_move);
		
		Mancala_GUI.Update_GUI(mnklGame.board);
		
		return flag;
	
	}
	
	public static void set_move(int move){
		
		Human_Agent.clicked = true;
		Human_move = move;
	}

	@Override
	public void run() {
		
		while(Game.game_on);
		
	}


	

}
