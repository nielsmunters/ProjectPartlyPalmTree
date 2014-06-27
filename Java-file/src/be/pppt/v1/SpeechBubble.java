package be.pppt.v1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class SpeechBubble {

	private ArrayList<Speech> speechs = new ArrayList<>();
	
	public void addSpeech(String toSay, int timeToLive) {
		speechs.add(new Speech(toSay, timeToLive));
	}
	
	public void update(int timePast) {
		for(int i = 0; i < speechs.size(); i++) {
			if(speechs.get(i).update(timePast)) {
				speechs.remove(i);
			}
		}
	}
	
	public void paint(Graphics g2d, int bottomX, int bottomY, Color textColor, Font textFont) {
		g2d.setFont(textFont);
		g2d.setColor(textColor);
		
		int x = bottomX;
		int y = bottomY;
		
		for (Speech aSpeech : speechs) {
			g2d.drawString(aSpeech.getSpeech(), x - aSpeech.getSpeech().length()/4*textFont.getSize(), y);
			
			y -= textFont.getSize();
		}
		
	}
}
