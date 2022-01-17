package state;

import input.Button;
import input.InputManager;
import main.MainPanel;
import stuff.Account;
import util.GraphicsTools;
import util.ScrollWindow;
import util.TextBox;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class EntryListState extends State{
    InputManager im;

    Account currentAccount;

    TextBox tb1;
    TextBox tb2;
    TextBox tb3;
    TextBox tb4;

    EntriesScrollWindow sw;
    public EntryListState(StateManager gsm, Account currentAccount) {
        super(gsm);
        this.currentAccount=currentAccount;
        im = new InputManager();

        String accountName = currentAccount.name;

        Font font1 = new Font("Dialogue", Font.BOLD, 24);
        Font font2 = new Font("Dialogue", Font.PLAIN, 13);
        int textWidth = GraphicsTools.calculateTextWidth(accountName, font1);
        tb1 = new TextBox(MainPanel.WIDTH/2-textWidth/2, 0, 400, 400, accountName, font1);

        im.addInput(new Button(650, 528, 120, 50, "Add", "btn_add"));
        tb2 = new TextBox(32, 58, 10, 0, "Name", font2);
        tb3 = new TextBox(600, 58, 10, 0, "Value", font2);
        tb4 = new TextBox(700, 58, 10, 0, "Date", font2);

        sw = new EntriesScrollWindow(30, 90, 740, 420, 480);
    }

    @Override
    public void init() {

    }

    @Override
    public void tick(Point mouse2) {
        im.tick(mouse2);
        sw.tick(mouse2);
    }

    @Override
    public void draw(Graphics g) {
        im.draw(g);
        tb1.draw(g);
        tb2.draw(g);
        tb3.draw(g);
        tb4.draw(g);
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

        if(which == null){
            return;
        }

        switch(which){
            case "btn_add":

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

class EntriesScrollWindow extends ScrollWindow {

    public EntriesScrollWindow(int x, int y, int width, int height, int realHeight) {
        super(x, y, width, height, realHeight);
    }

    @Override
    public void repaint(Graphics g, BufferedImage b) {
        im.draw(g);
    }
}