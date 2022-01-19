
/**
 * Example MiniMax agent extending Agent class.
 * Here, for simplicity of understanding min and max functions are written separately. One single function can do the task. 
 * @author Azad
 *
 */
public class Alpha_Beta_Agent extends Agent implements Runnable
{
	
	Mancala_Game mnklGame;
	private final int DEPTH = 12;
	public Alpha_Beta_Agent(String name) {
		super(name);
	}

	@Override
	public boolean makeMove(Game game) {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		mnklGame = (Mancala_Game) game;
		CellValueTuple best_val = MAX_CHOICE(mnklGame.board,-1000,1000,DEPTH);
		if(best_val.move!=-1)
		{
			System.out.println("ROBOT MOVE : "+best_val.move);
			boolean flag = mnklGame.board.Bot_move(best_val.move);
			
			Mancala_GUI.Update_GUI(mnklGame.board);
			return flag;
			
		}else{
			
			System.out.println("ROBOT MOVE : "+best_val.move);
			boolean flag = mnklGame.board.Bot_move(get_Dummy_Move());
			
			Mancala_GUI.Update_GUI(mnklGame.board);
			return flag;
		}
		
	}
	
	@Override
	public void run() {
		while(Game.game_on);
		
	}
	
	
	private int Mancala_heuristics(Board board,int turn){
		
		int h_z = 0, b_z = 0;
		for(int i=0;i<6;i++){
			h_z+=board.Human_pot[i];
			b_z+=board.Bot_pot[i];
		}
		
		if(turn==1){
			return (board.Bot_kala-board.Human_kala)+(b_z-h_z);
		}else{
			return (board.Human_kala-board.Bot_kala)+(h_z-b_z);
		}
	}
	
	private CellValueTuple MAX_CHOICE(Board board,int alpha,int beta,int depth)
	{	
		CellValueTuple maxCVT = new CellValueTuple();
		maxCVT.utility = -1000;
		
		int winner = mnklGame.Check_forWin(board);
		
		if(winner != -1){
			
			if(winner==1) maxCVT.utility = 1000;
			else maxCVT.utility = -1000;
			
			return maxCVT;
		}
		else if (depth == 0)
		{
			maxCVT.utility = Mancala_heuristics(board,1); 
			return maxCVT;  
		}
		
		Board kala_board = mnklGame.get_copy(board);
		for(int i = 0; i < 6; i++){
			
			if(kala_board.Bot_pot[i]!=0){
				if(kala_board.Bot_move(i)){
					int v = MAX_CHOICE(kala_board,alpha,beta,depth-1).utility;
					if(v>maxCVT.utility){
						maxCVT.utility = v;
						maxCVT.move = i;
					}
				}
				else{
					int v = MIN_CHOICE(kala_board,alpha,beta,depth-1).utility;
					if(v>maxCVT.utility){
						maxCVT.utility = v;
						maxCVT.move = i;
					}
				}
				if(maxCVT.utility >= beta)
					return maxCVT;
				alpha = Math.max(alpha,maxCVT.utility);
				kala_board = mnklGame.get_copy(board);
			}
		}
		
		return maxCVT;		
	}
	
	private CellValueTuple  MIN_CHOICE(Board board,int alpha,int beta,int depth)
	{	
		CellValueTuple minCVT = new CellValueTuple();
		minCVT.utility = 1000;
		
		
		//terminal check
		int winner = mnklGame.Check_forWin(board);
		
		if(winner != -1){
			
			if(winner==1) minCVT.utility = 1000;
			else minCVT.utility = -1000;
			
			return minCVT;
		}
		else if (depth == 0)
		{
			minCVT.utility = Mancala_heuristics(board,0); 
			return minCVT;  
		}
		
		Board kala_board = mnklGame.get_copy(board);
		for(int i = 0; i < 6; i++){
			
			if(kala_board.Human_pot[i]!=0){
				
				if(kala_board.Human_move(i)){
					int v = MIN_CHOICE(kala_board,alpha,beta,depth-1).utility;
					if(v<minCVT.utility){
						minCVT.utility = v;
						minCVT.move = i;
					}
				}
				else{
					int v = MAX_CHOICE(kala_board,alpha,beta,depth-1).utility;
					if(v<minCVT.utility){
						minCVT.utility = v;
						minCVT.move = i;
					}
				}
				if(minCVT.utility <= alpha)
					return minCVT;
				beta = Math.max(beta,minCVT.utility);
				kala_board = mnklGame.get_copy(board);
			}
			
		}
		
		return minCVT;	
	}
	
	public int get_Dummy_Move(){
		
		int position = 0;
		int value = 50;
		for(int i=5;i>=0;i--){
			
			if(value>mnklGame.board.Bot_pot[i] && mnklGame.board.Bot_pot[i]!=0){
				value = mnklGame.board.Bot_pot[i];
				position = i;
			}
		}
		
		return position;
		
		
	}
	
	
	class CellValueTuple
	{
		int move, utility;
		public CellValueTuple() {
			// TODO Auto-generated constructor stub
			move =-1;
		}
	}

}
