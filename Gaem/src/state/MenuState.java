package state;

import java.awt.*;
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
import stuff.Account;
import stuff.Entry;
import util.GraphicsTools;
import util.ScrollWindow;
import util.TextBox;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MenuState extends State{
	
	InputManager im;
	
	TextBox tb1;
	TextBox tb2;
	TextBox tb3;

	AccountsScrollWindow sw;

	static ArrayList<Account> accountList = new ArrayList<Account>();

	static int accountViewHeight = 60;

	public MenuState(StateManager gsm) {
		super(gsm);
		
		im = new InputManager();

		//"Accounts" text
		Font font1 = new Font("Dialogue", Font.BOLD, 24);
		Font font2 = new Font("Dialogue", Font.PLAIN, 13);
		int textWidth = GraphicsTools.calculateTextWidth("Accounts", font1);
		tb1 = new TextBox(MainPanel.WIDTH/2-textWidth/2, 0, 400, 400, "Accounts", font1);

		im.addInput(new Button(650, 528, 120, 50, "Add", "btn_add"));
		tb2 = new TextBox(32, 58, 10, 0, "Name", font2);
		tb3 = new TextBox(600, 58, 10, 0, "Balance", font2);
		sw = new AccountsScrollWindow(30, 90, 740, 420, 480);
		
	}

	public void addAccount(String name){
		accountList.add(new Account(name));
		sw.im.addInput(new Button(630, (accountList.size()-1)*accountViewHeight, 100, accountViewHeight, "View", name));
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
		tb1.draw(g);
		tb2.draw(g);
		tb3.draw(g);
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
			case "btn_add":
				gsm.states.push(new AccountPromptState(gsm));
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

	public AccountsScrollWindow(int x, int y, int width, int height, int realHeight) {
		super(x, y, width, height, realHeight);
	}

	@Override
	public void repaint(Graphics g, BufferedImage b) {
		im.draw(g);
		for(int i = 0; i<MenuState.accountList.size();i++){
			int y = i*MenuState.accountViewHeight;
			int x = 0;
			g.drawRect(x, y, this.width-10, MenuState.accountViewHeight);
			g.drawString(MenuState.accountList.get(i).name, x+5, y+20);
			DecimalFormat df = new DecimalFormat("#.##");
			g.drawString(df.format(MenuState.accountList.get(i).balance), x+568, y+20);
		}

		this.setRealHeight(MenuState.accountList.size()*MenuState.accountViewHeight);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(this.containsPoint(mouse)) {

			String which = im.mouseClicked(convertMouseEvent(arg0));
			
			if(which == null) {
				return;
			}

		}
	}
	
}
