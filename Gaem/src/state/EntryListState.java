package state;

import input.Button;
import input.InputManager;
import main.MainPanel;
import classes.Account;
import util.DateComparator;
import classes.Entry;
import util.GraphicsTools;
import util.ScrollWindow;
import util.TextBox;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static util.GraphicsTools.calculateCenteredX;
import static util.GraphicsTools.calculateTextWidth;

public class EntryListState extends State{
    InputManager im;

    static Account currentAccount;
    static EntriesScrollWindow sw;

    TextBox tb2;
    TextBox tb3;
    TextBox tb4;

    DecimalFormat df = new DecimalFormat("#.##");

    TextBox tb5;
    public EntryListState(StateManager gsm, Account currentAccount) { //String name, ArrayList<Entry> entryList, double balance
        super(gsm);
        this.currentAccount=currentAccount;
        im = new InputManager();

        Font font2 = new Font("Dialogue", Font.PLAIN, 13);

        im.addInput(new Button(650, 528, 120, 50, "Add", "btn_add"));
        tb2 = new TextBox(32, 58, 10, 0, "Name", font2);
        tb3 = new TextBox(520, 58, 10, 0, "Value", font2);
        tb4 = new TextBox(640, 58, 10, 0, "Date", font2);

        sw = new EntriesScrollWindow(30, 90, 740, 420, 480);
        for(Entry e : currentAccount.entryList){
            sw.im.addInput(new Button(710, 400, 20, 20, "x", e.name+"_del")); //currently initializes at arbitrary position
        }
        currentAccount.entryList.sort(new DateComparator());

        im.addInput(new Button(20, 20, 40, 20, "<", "btn_back"));
    }

    public static void addEntry(String name, double value, int day, int month, int year){
        sw.im.addInput(new Button(710, 400, 20, 20, "x", name+"_del")); //currently initializes at arbitrary position
        currentAccount.entryList.add(new Entry(name, value, day, month, year));
        currentAccount.entryList.sort(new DateComparator());

    }

    public void deleteEntry(String name){
        ArrayList<Entry> eList = EntryListState.currentAccount.entryList;
        for(int i=0;i<eList.size();i++){
            if(eList.get(i).name.equals(name)){
                eList.remove(i);
                break;
            }
        }
        sw.im.removeInput(name+"_del");
    }

    public double sumEntries(){
        double sum = 0;
        for(Entry e: currentAccount.entryList){
            sum+=e.value;
        }
        return sum;
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
        tb2.draw(g);
        tb3.draw(g);
        tb4.draw(g);
        Font font1 = new Font("Dialogue", Font.BOLD, 24);

        String accountName = currentAccount.name;
        int textWidth = calculateTextWidth(accountName, font1);
        g.setFont(font1);
        g.drawString(currentAccount.name, MainPanel.WIDTH/2-textWidth/2, 24);

        Font font3 = new Font("Dialogue", Font.PLAIN, 18);

        currentAccount.balance=this.sumEntries();
        String temp = currentAccount.balance>=0 ? "$"+df.format(currentAccount.balance):"-$"+df.format(Math.abs(currentAccount.balance));
        textWidth = calculateTextWidth(temp, font3);

        g.setFont(font3);
        g.drawString(temp, MainPanel.WIDTH/2-textWidth/2,52);

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

        switch(which) {
            case "btn_add":
                gsm.states.push(new EntryCreationState(gsm));
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
        g.setColor(Color.black);
        for(int i = 0;i< EntryListState.currentAccount.entryList.size();i++){
            ArrayList<Entry> e = EntryListState.currentAccount.entryList;
            int y = i*AccountListState.accountViewHeight;
            int x = 0;
            g.drawRect(x, y, this.width-10, AccountListState.accountViewHeight);
            g.drawString(e.get(i).name, x+5, y+20);

            Font font2 = new Font("Dialogue", Font.PLAIN, 13);

            //draw value, centered
            DecimalFormat df = new DecimalFormat("#.##");
            String s = e.get(i).value>=0 ? "$"+df.format(e.get(i).value):"-$"+df.format(Math.abs(e.get(i).value));
            g.drawString(s, calculateCenteredX(s, 520 - calculateTextWidth("Value", font2)/2, font2), y+20);

            //draw date, centered
            s = e.get(i).month+"/"+e.get(i).day+"/"+e.get(i).year;
            g.drawString(s, calculateCenteredX(s, 640 - calculateTextWidth("Date", font2)/2, font2), y+20);

            //draw delete button
            Button temp = (Button) im.getInput(e.get(i).name+"_del");
            temp.setParameters(710, y);
        }
        this.setRealHeight(EntryListState.currentAccount.entryList.size()*AccountListState.accountViewHeight);
    }

    @Override
    public void mouseClicked(MouseEvent arg0){
        if(this.containsPoint(mouse)){
            String which = im.mouseClicked(convertMouseEvent(arg0));

            if(which == null){
                return;
            }

            for(Entry e: EntryListState.currentAccount.entryList){
                if((e.name+"_del").equals(which)){
                    EntryListState el = (EntryListState) StateManager.states.peek();
                    el.deleteEntry(e.name);
                    break;
                }
            }
        }
    }
}