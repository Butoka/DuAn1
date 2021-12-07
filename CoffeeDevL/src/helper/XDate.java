package helper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class XDate {

    static SimpleDateFormat formater = new SimpleDateFormat();

    public static Date toDate(String date, String pattern){
        formater.applyPattern(pattern);
        try {
            return (Date) formater.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(XDate.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }   
    }

    public static String toString(Date date, String pattern) {
        formater.applyPattern(pattern);
        return formater.format(date);
    }

    public static Date addDays(Date date, long days) {
        date.setTime(date.getTime()+days*24*60*60*1000);
        return date;
    }

    
}
