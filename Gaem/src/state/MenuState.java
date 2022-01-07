package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import input.InputManager;
import input.Button;
import input.SliderButton;
import input.TextField;
import input.ToggleButton;
import main.MainPanel;
import util.GraphicsTools;
import util.ScrollWindow;
import util.TextBox;

public class MenuState extends State{
	
	InputManager im;
	
	TextBox tb;
	
	AccountsScrollWindow sw;

	public MenuState(StateManager gsm) {
		super(gsm);
		
		im = new InputManager();

		//"Accounts" text
		Font font = new Font("Dialogue", Font.BOLD, 24);
		int textWidth = GraphicsTools.calculateTextWidth("Accounts", font);
		tb = new TextBox(MainPanel.WIDTH/2-textWidth/2, 0, 400, 400, "Accounts", font);

		im.addInput(new Button(650, 528, 120, 50, "Add", "btn_add"));

		sw = new AccountsScrollWindow(30, 40, 740, 480, 480);
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(Point mouse) {

		im.tick(mouse);
		sw.tick(mouse);
		
	}

	@Override
	public void draw(Graphics g) {
		
		im.draw(g);
		tb.draw(g);
		sw.draw(g);
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		im.keyPressed(arg0);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		im.keyReleased(arg0);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		im.keyTyped(arg0);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		sw.mouseClicked(arg0);

		String which = im.mouseClicked(arg0);
		
		if(which == null) {
			return;
		}
		
		switch(which) {
		case "button1":
//			im.setVal("slider1", 50);
//			im.setVal("slider2", 75);
			break;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		im.mousePressed(arg0);
		sw.mousePressed(arg0);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		im.mouseReleased(arg0);
		sw.mouseReleased(arg0);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		sw.mouseWheelMoved(arg0);
	}

}

class AccountsScrollWindow extends ScrollWindow{
	
	public int counter = 250;

	public AccountsScrollWindow(int x, int y, int width, int height, int realHeight) {
		super(x, y, width, height, realHeight);
	}

	@Override
	public void repaint(Graphics g, BufferedImage b) {
		im.draw(g);
//		for(int i = 0; i < 7; i++) {
//			g.setColor(new Color(i * 33, i * 33, i * 33));
//			g.fillRect(0, i * 33, 33, 33);
//		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(this.containsPoint(mouse)) {

			String which = im.mouseClicked(convertMouseEvent(arg0));
			
			if(which == null) {
				return;
			}
			
			switch(which) {
				case "button1":
	//				im.addInput(new Button(100, counter, 100, 100, "Click Me", "button1"));
					counter += 150;
					this.setRealHeight(this.getRealHeight() + 150);
					break;
			}


		}
	}
	
}
