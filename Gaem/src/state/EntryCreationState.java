package state;

import input.Button;
import input.InputManager;
import main.MainPanel;
import classes.Entry;
import util.GraphicsTools;
import util.TextBox;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class EntryCreationState extends State{

    InputManager im;
    TextBox tb1;
    TextBox tb2;
    TextBox tb3;

    boolean togDate = false;
    boolean togName = false;
    boolean togNull = false;

    public EntryCreationState(StateManager gsm)
    {
        super(gsm);

        im = new InputManager();

        Font font1 = new Font("Dialogue", Font.BOLD, 24);
        Font font3 = new Font("Dialogue", Font.PLAIN, 18);

        int textWidth = GraphicsTools.calculateTextWidth("New Entry", font1);
        tb1 = new TextBox(MainPanel.WIDTH/2-textWidth/2, 0, 400, 400, "Account Creation", font1);

        tb2 = new TextBox(MainPanel.WIDTH/2-textWidth/2, 500, 400, 400, "Please enter a valid date!", font3);
        tb3 = new TextBox(MainPanel.WIDTH/2-textWidth/2, 500, 400, 400, "Double-check entry name!", font3);

        im.addInput(new input.TextField(200, 250, 200, "Enter Entry Name:", "tf_name"));
        im.addInput(new input.TextField(200, 200, 120, "Enter Date XX/XX/XXXX:", "tf_date"));
        im.addInput(new input.TextField(200, 300, 150, "Enter Value (no commas):", "tf_val"));
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
        Font font1 = new Font("Dialogue", Font.BOLD, 24);
        Font font3 = new Font("Dialogue", Font.PLAIN, 18);

        g.setFont(font1);
        int textWidth = GraphicsTools.calculateTextWidth("Entry Creation", font1);
        g.drawString("Entry Creation", MainPanel.WIDTH/2-textWidth/2, 24);
        im.draw(g);

        g.setFont(font3);
        textWidth = GraphicsTools.calculateTextWidth("Please enter a valid date!", font3);
        if(togDate) g.drawString("Please enter a valid date!", MainPanel.WIDTH/2-textWidth/2, 500);

        textWidth = GraphicsTools.calculateTextWidth("Double-check entry name!", font3);
        if(togName) g.drawString("Double-check entry name!", MainPanel.WIDTH/2-textWidth/2, 500);

        textWidth = GraphicsTools.calculateTextWidth("Fill in all boxes properly!", font3);
        if(togNull) g.drawString("Fill in all boxes properly!", MainPanel.WIDTH/2-textWidth/2, 500);

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
        togDate=togName=togNull=false;

        switch(which){
            case "btn_enter":
                //error handling
                try {
                    String name = im.getText("tf_name");
                    double value = Double.parseDouble(im.getText("tf_val"));
                    String temp = im.getText("tf_date");
                    String[] a = temp.split("/", 3);

                    int month = Integer.parseInt(a[0]);
                    int day = Integer.parseInt(a[1]);
                    int year = Integer.parseInt(a[2]);
                    System.out.println(year);
                    boolean validDate = !(month > 12 || month < 1 || day > 31 || day < 0);
                    boolean validName = !(name.equals("") || containsName(name));

                    if (!validDate) togDate = true;
                    else if (!validName) togName = true;
                    else {
                        this.gsm.states.pop();
                        EntryListState e = (EntryListState) this.gsm.states.peek();
                        e.addEntry(name, value, day, month, year);
                    }
                }
                catch(Exception e) {
                    togNull = true;
                }

                break;
            case "btn_back":
                this.gsm.states.pop();
                break;
        }
    }

    public boolean containsName(String name){
        for(Entry e: EntryListState.currentAccount.entryList){
            if(e.name.equals(name)){
                return true;
            }
        }
        return false;
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
