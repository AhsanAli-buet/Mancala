
public abstract class Agent 
{
	String name; // Name of the agent
	int role; // Agent Role. 
				
				
	public Agent(String name) 
	{
		// TODO Auto-generated constructor stub
		this.name = name;
		
	}
	
	/**
	 * Sets the role of this agent. Typlically will be called by your extended Game class (The  class which extends the Game Class).
	 * @param role
	 */
	public void setRole(int role) {
		this.role = role;
	}

	/**
	 * Implement this method to select a move, and change the game state according to the chosen move.
	 * @param game
	 */
	public abstract boolean makeMove(Game game);
	

}
