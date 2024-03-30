package at.banplayerz.jl.utils;

public enum GameManager {

	LOBBY(),
	INGAME(),
	RESTART();
	
	private static GameManager currentState;
	
	public static void setState(GameManager state){
		GameManager.currentState = state;
	}
	
	public static boolean isState(GameManager state){
		return GameManager.currentState == state;
	}
	
	public static GameManager getState(){
		return currentState;
	}
	
	public static String getStatus(){
		String status = "";
		
		if(isState(GameManager.LOBBY)){
			status = "§7» §aLobby §7«";
		} else if(isState(GameManager.INGAME)){
			status = "§7» §9Ingame §7«";
		} else if(isState(GameManager.RESTART)){
			status = "§7» §cRestart §7«";
		} else {
			status = "§7» §4Unbekannt §7«";
		}
		
		return status;
	}
	
}
