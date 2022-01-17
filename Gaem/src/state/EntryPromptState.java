package state;

import input.Button;
import input.InputManager;
import main.MainPanel;
import util.GraphicsTools;
import util.TextBox;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class EntryPromptState extends State{

    InputManager im;
    TextBox tb1;

    public EntryPromptState(StateManager gsm) {
        super(gsm);

        im = new InputManager();

        Font font1 = new Font("Dialogue", Font.BOLD, 24);
        int textWidth = GraphicsTools.calculateTextWidth("Account Creation", font1);
        tb1 = new TextBox(MainPanel.WIDTH/2-textWidth/2, 0, 400, 400, "Account Creation", font1);

//        TextField tf = new TextField(300, 300, 200, "Enter Account Name:", "tf_name");

        im.addInput(new input.TextField(200, 300, 200, "Enter Entry Name:", "tf_name"));
        im.addInput(new input.TextField(200, 200, 120, "Enter Date (ex:12/31):", "tf_date"));
        im.addInput(new input.TextField(200, 400, 150, "Enter Value (no commas):", "tf_val"));
        im.addInput(new Button(400, 400, 50, 50, "Enter", "btn_enter"));
    }

    @Override
    public void init() {

    }

    @Override
    public void tick(Point mouse2) {
        im.tick(mouse2);
    }

    @Override
    public void draw(Graphics g) {
        tb1.draw(g);
        im.draw(g);
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
        String which  = im.mouseClicked(arg0);

        switch(which){
            case "btn_enter":
                String name = im.getText("tf_name");
                String value = im.getText("tf_value");
                String temp = im.getText("tf_date");
                String[] a = temp.split("/");
                int month = Integer.parseInt(a[0]);
                int day = Integer.parseInt(a[1]);

                if(!(name.equals("") || month>12 || month < 1 || day > 31 || day < 0)){
//                    this.gsm.states.pop();
//                    EntryListState e = (EntryListState) this.gsm.states.peek();
//                    e.addEntry()
                }
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
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent arg0) {

    }
}
