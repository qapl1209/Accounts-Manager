package input;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class InputManager {

	public ArrayList<Input> inputs;
	
	public InputManager() {
		this.inputs = new ArrayList<Input>();
	}
	
	public void addInput(Input i) {
		this.inputs.add(i);
	}
	
	public void tick(java.awt.Point mouse) {
		for(Input i : inputs) {
			i.hovering(mouse);
			i.tick(mouse);
		}
	}
	
	public void draw(Graphics g) {
		for(Input i : inputs) {
			i.draw(g);
		}
	}

	public void removeInput(String name){
		for(int i = 0; i < inputs.size(); i++) {
			if(inputs.get(i).name.equals(name)){
				inputs.remove(i);
			}
		}
	}

	public Input getInput(String name){
		for(int i = 0; i < inputs.size(); i++){
			if(inputs.get(i).name.equals(name)){
				return inputs.get(i);
			}
		}
		return null;
	}
	
	//gets text from text input
	public String getText(String name) {
		for(Input i : inputs) {
			if(i instanceof TextField && i.name.equals(name)) {
				return ((TextField) i).getText();
			}
		}
		return null;
	}
	
	public void mousePressed(MouseEvent arg0) {
		for(Input i : inputs) {
			i.pressed(arg0);
		}
	}
	
	public void mouseReleased(MouseEvent arg0) {
		for(Input i : inputs) {
			i.released(arg0);
		}
	}
	
	public String mouseClicked(MouseEvent arg0) {
		String ans = null;
		for(Input i : inputs) {
			if(i.clicked(arg0)) {
				ans = i.name;
			}
		}
		return ans;
	}
	
	public void keyPressed(KeyEvent arg0) {
		for(Input i : inputs) {
			i.keyPressed(arg0);
		}
	}
	
	public void keyTyped(KeyEvent arg0) {
		for(Input i : inputs) {
			i.keyTyped(arg0);
		}
	}
	
	public void keyReleased(KeyEvent arg0) {
		for(Input i : inputs) {
			i.keyReleased(arg0);
		}
	}
	
}
