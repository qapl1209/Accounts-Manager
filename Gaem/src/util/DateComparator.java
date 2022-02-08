package util;
import classes.Entry;

import java.util.Comparator;
public class DateComparator implements Comparator<Entry>{
    @Override
    public int compare(Entry a, Entry b){
        //sorts entries by date, comparator
        if(a.year==b.year){
            if(a.month==b.month){
                return b.day-a.day;
            }
            else{
                return b.month-a.month;
            }
        }
        return b.year-a.year;
    }
}