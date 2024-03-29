package state;

import input.Button;
import input.InputManager;
import input.TextField;
import main.MainPanel;
import classes.Account;
import util.GraphicsTools;
import util.TextBox;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class AccountEditState extends State{

    InputManager im;

    TextBox tb1;
    TextBox tb2;
    Account curAccount;

    boolean toggled = false;

    public AccountEditState(StateManager gsm, Account curAccount) {
        super(gsm);

        this.curAccount = curAccount;

        im = new InputManager();

        Font font1 = new Font("Dialogue", Font.BOLD, 24);
        Font font3 = new Font("Dialogue", Font.PLAIN, 18);

        int textWidth = GraphicsTools.calculateTextWidth("Edit Account", font1);
        tb1 = new TextBox(MainPanel.WIDTH/2-textWidth/2, 0, 400, 400, "Edit Account", font1);
        tb2 = new TextBox(MainPanel.WIDTH/2-textWidth/2, 500, 400, 400, "Please enter a valid name!", font3);

        im.addInput(new TextField(300, 300, 200, "New Account Name:", "tf_name"));
        im.addInput(new Button(400, 400, 50, 50, "Enter", "btn_enter"));
        im.addInput(new Button(20, 20, 40, 20, "<", "btn_back"));

    }

    @Override
    public void init(){

    }

    @Override
    public void tick(Point mouse2) {
        im.tick(mouse2);
    }

    @Override
    public void draw(Graphics g) {
        im.draw(g);
        tb1.draw(g);
        if(toggled){
            tb2.draw(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {im.keyPressed(arg0);}

    @Override
    public void keyReleased(KeyEvent arg0) {im.keyReleased(arg0);}

    @Override
    public void keyTyped(KeyEvent arg0) {im.keyTyped(arg0);}

    @Override
    public void mouseClicked(MouseEvent arg0) {
        String which = im.mouseClicked(arg0);
        toggled = false;

        switch(which){
            case "btn_enter":
                String in = im.getText("tf_name");
                boolean nameExists = false;
                for(Account a:AccountListState.accountList){
                    if(a.name.equals(in))nameExists=true;
                }
                if(!in.equals("") && !nameExists){
                    this.gsm.states.pop();
                    AccountListState.setComponentName(curAccount.name, in);
                    AccountListState.setComponentName(curAccount.name+"_del", in+"_del");
                    AccountListState.setComponentName(curAccount.name+"_edit", in+"_edit");
                    curAccount.editEntry(in);
                }
                else{
                    toggled = true;
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
        im.mouseReleased(arg0);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent arg0) {

    }
}
