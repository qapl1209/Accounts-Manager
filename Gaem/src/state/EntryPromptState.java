package state;

import input.Button;
import input.InputManager;
import main.MainPanel;
import stuff.Account;
import stuff.Entry;
import util.GraphicsTools;
import util.TextBox;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class EntryPromptState extends State{

    InputManager im;
    TextBox tb1;

    public EntryPromptState(StateManager gsm)
    {
        super(gsm);

        im = new InputManager();

        Font font1 = new Font("Dialogue", Font.BOLD, 24);
        int textWidth = GraphicsTools.calculateTextWidth("New Entry", font1);
        tb1 = new TextBox(MainPanel.WIDTH/2-textWidth/2, 0, 400, 400, "Account Creation", font1);

//        TextField tf = new TextField(300, 300, 200, "Enter Account Name:", "tf_name");

        im.addInput(new input.TextField(200, 300, 200, "Enter Entry Name:", "tf_name"));
        im.addInput(new input.TextField(200, 200, 120, "Enter Date (ex:12/31/2022):", "tf_date"));
        im.addInput(new input.TextField(200, 400, 150, "Enter Value (no commas):", "tf_val"));
        im.addInput(new Button(400, 400, 50, 50, "Enter", "btn_enter"));
        im.addInput(new Button(20, 20, 40, 20, "<", "btn_back"));
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
                double value = Double.parseDouble(im.getText("tf_val"));
                String temp = im.getText("tf_date");
                System.out.println(name +" "+value + " "+temp);
                String[] a = temp.split("/", 3);

                int month = Integer.parseInt(a[0]);
                int day = Integer.parseInt(a[1]);
                int year = Integer.parseInt(a[2]);

                System.out.println(day +" "+month+" "+year);
                if(!(name.equals("") || month>12 || month < 1 || day > 31 || day < 0)){
                    this.gsm.states.pop();
//                    EntryListState e = (EntryListState) this.gsm.states.peek();
                    EntryListState.currentAccount.entryList.add(new Entry(name, value, day, month, year));
                }
                break;
            case "btn_back":
                this.gsm.states.pop();
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
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent arg0) {

    }
}
