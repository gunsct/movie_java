class DVDplayer{

	boolean canRecord = false;
	
	void playRecord(){
		System.out.println("play DVD!");
	}
}

class PlayingDVD{
	public static void main(String[] args){
	
		DVDplayer p = new DVDplayer();
		
		p.canRecord = true;
		
		if(p.canRecord == true){
			p.playRecord();
		}
	}
}