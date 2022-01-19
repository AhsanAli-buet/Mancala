
public class Board {
	
	public int[] Human_pot;
	public int[] Bot_pot;
	public int Human_kala;
	public int Bot_kala;
	
	public Board(){
		
		Human_pot = new int[6];
		Bot_pot = new int[6];
	}
	
	
	public void initialize(boolean fromFile) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 6; i++) {
			Human_pot[i] = 4;
			Bot_pot[i] = 4;
		}
		
		Human_kala =0;
		Bot_kala =0;
	}
	
	
	boolean Human_move(int move){
		
		int position = move+1;
		int counter = Human_pot[move];
		Human_pot[move] = 0;
		if(counter!=0){
			while(counter >1){
				
				if(position<=5){
					Human_pot[position]++;
					position++;
					counter--;
				}
				else if(position==6){
					Human_kala++;
					position = 10;
					counter--;
				}
				else if(position>6 && position<16){
					Bot_pot[position-10]++;
					position++;
					counter--;
				}
				else if(position==16){
					position = 0;
				}
			}
			
	
			if(position<=5){
				
				if(Human_pot[position] == 0 && Bot_pot[5-position] != 0){
					 
					Human_kala = Bot_pot[5-position]+Human_kala+1;
					Bot_pot[5-position] = 0;
				}
				else{
					Human_pot[position]++;
				}
				
			}
			else if(position==6){
				Human_kala++;
				return true;
			}
			else if(position>6 && position<16){
				Bot_pot[position-10]++;
			}
			else if(position==16){
				Human_pot[0]++;
			}
		
		}
		
		return false;
	}
	
	
	
	boolean Bot_move(int move){
		
		int position = move+1;
		int counter = Bot_pot[move];
		Bot_pot[move] = 0;
		
		if(counter!=0){
			while(counter >1){
				
				if(position<=5){
					Bot_pot[position]++;
					position++;
					counter--;
				}
				else if(position==6){
					Bot_kala++;
					position = 10;
					counter--;
				}
				else if(position>6 && position<16){
					Human_pot[position-10]++;
					position++;
					counter--;
				}
				else if(position==16){
					position = 0;
				}
			}
			
			if(position<=5){
				
				if(Bot_pot[position] == 0 && Human_pot[5-position] != 0){
					 
					Bot_kala = Human_pot[5-position]+Bot_kala+1;
					Human_pot[5-position] = 0;
				}
				else{
					Bot_pot[position]++;
				}
				
			}
			else if(position==6){
				Bot_kala++;
				return true;
			}
			else if(position>6 && position<16){
				Human_pot[position-10]++;
			}
			else if(position==16){
				Bot_pot[0]++;
			}
		}
		return false;
	}	
}
