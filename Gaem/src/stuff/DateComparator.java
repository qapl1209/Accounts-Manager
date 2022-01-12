package stuff;
import java.util.Comparator;
public class DateComparator implements Comparator<Entry>{
    @Override
    public int compare(Entry a, Entry b){
        if(a.year==b.year){
            if(a.month==b.month){
                return a.day-b.day;
            }
            else{
                return a.month-b.month;
            }
        }
        return a.year-b.year;
    }
}