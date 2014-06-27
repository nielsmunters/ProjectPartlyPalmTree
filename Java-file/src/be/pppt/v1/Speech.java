package be.pppt.v1;

public class Speech {
	String speech = "";
	int ttl = 0;
	
	
	public Speech(String speech, int ttl) {
		super();
		this.speech = speech;
		this.ttl = ttl;
	}


	public boolean update(int timePast) {
		ttl -= timePast;
		
		if (ttl <= 0) {
			return true;
		}
		
		return false;
	}


	public String getSpeech() {
		return speech;
	}
}
