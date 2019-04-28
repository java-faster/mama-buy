package com.room.common;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间日期工具类
 */
public class DateUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) throws ParseException {
//        System.out.println("2018/03/02".replaceAll("/","-"));
//        System.out.println(System.currentTimeMillis());
//        System.out.println(getToday());
//        System.out.println(compareDate(sdf.parse("2018-03-01")));
//        System.out.println(compareDate("2018-03-01 10:00:00"));
//        System.out.println(getDetailDate(0,"2018-03-13"));
//        System.out.println(getNumber(sdf.parse("2018-03-12"),"2018-03-07"));
//        System.out.println(isWeekend("2018-03-08"));
//        System.out.println(getNextDate("2018-03-08"));
//        System.out.println(getDetailDate("2018-03-08"));
//        String minDate = getToday();
//        System.out.println(minDate);
//        String maxDate = getDetailDate(minDate);
//        System.out.println(maxDate);
//        if ("1".equals("1")){
//            minDate = getDate(maxDate,1);
//            maxDate = getDetailDate(minDate);
//        }
//        System.out.println(minDate);
//        System.out.println(maxDate);

    }

    public int compareDate(Date dt1) throws ParseException {
        Date dt2 = sdf.parse(sdf.format(new Date()));
        return  dt1.compareTo(dt2);
    }

    public int compareDate(String f) throws ParseException {
        Date dt1 = sdf2.parse(f);
        //当前时间
        Date dt2 = sdf2.parse(sdf2.format(new Date()));
        return  dt2.compareTo(dt1);
    }

    public String getToday(){
        Calendar cal = Calendar.getInstance();
        return getDate(sdf.format(cal.getTime()));
    }

    public String getDate(String date, int i) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(date));
        cal.set(Calendar.DATE, cal.get(cal.DATE) + i);
        return getDate(sdf.format(cal.getTime()));
    }


    public static String getNextDate(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(date));
        cal.set(Calendar.DATE, cal.get(cal.DATE) + 1);
        return sdf.format(cal.getTime());
    }

    public static String getDetailDate(int i,String minDate){
        Calendar cal = Calendar.getInstance();
        String temp = sdf.format(cal.getTime());
        if (minDate.equals(temp)) {
            cal.set(Calendar.DATE, cal.get(cal.DATE) + i);
        }else {
            cal.set(Calendar.DATE, cal.get(cal.DATE) + i+6);
        }
        return sdf.format(cal.getTime());
    }

    // 3-10 3-07
    public static int getNumber(Date bookDate,String minDate) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(minDate));
        long time1= cal.getTimeInMillis();

        cal.setTime(bookDate);
        long time2 = cal.getTimeInMillis();

        long between_days=(time2-time1)/(1000*3600*24);
        System.out.println("between_days"+between_days);

        int num = Integer.parseInt(String.valueOf(between_days));
        int c = hasWeek(bookDate,sdf.parse(minDate));
        System.out.println("cc"+c);

        return c==0 ? num : num-1;
    }

    public static boolean isWeekend(String date) throws ParseException{
        Date temp = sdf.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(temp);
        return cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY;
    }

    public static String getDate(String date) {
        try {
            return isWeekend(date) ? getNextDate(date) : date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // 3-9 3-10 3-11 3-12 3-13 3-14
    public String getDetailDate(String minDate) throws ParseException {
        int count = 0;
        boolean flag = false;
        String temp = minDate;
        while (count<5){
            if (!isWeekend(getNextDate(temp))){
                temp = getNextDate(temp);
            }else {
                flag = !flag;
                break;
            }
            count++;
        }
        return flag ? getDate(minDate,6) : getDate(minDate,5);
    }

    // 3-9 3-10 3-11 3-12 3-13 3-14 3-15
    public static int hasWeek(Date bookDate,Date minDate) throws ParseException {
        int count = 0;
        boolean flag =true;

        String temp = sdf.format(bookDate);

        while (flag){
            if (minDate.compareTo(sdf.parse(temp))==0)
                return count;

            if (!isWeekend(getBeforeDate(temp))){
                temp = getBeforeDate(temp);
            }else {
                flag = !flag;
                count=1;
                break;
            }

        }
        return count;
    }

    private static String getBeforeDate(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(date));
        cal.set(Calendar.DATE, cal.get(cal.DATE) -1);
        return sdf.format(cal.getTime());
    }
}
