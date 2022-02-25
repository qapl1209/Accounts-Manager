package util;
import classes.Entry;

import java.util.Comparator;
public class DateComparator implements Comparator<Entry>{
    @Override
    public int compare(Entry a, Entry b){
        //sorts entries by date, comparator
        if(a.year==b.year){
            if(a.month==b.month){
                if(a.day==b.day){
                    int r = b.value>a.value?1:-1;
                    return r;
                }
                return b.day-a.day;
            }
            return b.month-a.month;
        }
        return b.year-a.year;
    }
}