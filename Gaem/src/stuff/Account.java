package stuff;
import java.util.ArrayList;
import java.util.Collections;

public class Account {
    public String name;
    public ArrayList<Entry> entryList;
    public double balance;

    public Account(String name){
        this.name = name;
        entryList = new ArrayList<>();
        balance = 0;
    }

    boolean containsName(String name){
        for (int i = 0;i< entryList.size();i++){
            if (entryList.get(i).name.equals(name))return true;// possibly problematic; while editing, name may already exist.
        }
        return false;
    }

    public void setName(String name){
        this.name = name;
    }

    public void addEntry(String name, double balance, int day, int month, int year){
        entryList.add(new Entry(name, balance, day, month, year));
        Collections.sort(entryList, new DateComparator());
    }

    public void editEntry(){

    }

    public void deleteEntry(String name){
        for(int i = 0 ;i< entryList.size();i++){
            if(entryList.get(i).name.equals(name)){
                entryList.remove(i);
                break;
            }
        }
    }

    public void setBalance(double balance){
        this.balance = balance;
    }
}

