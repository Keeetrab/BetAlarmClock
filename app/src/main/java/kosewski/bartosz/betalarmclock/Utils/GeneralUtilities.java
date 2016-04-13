package kosewski.bartosz.betalarmclock.Utils;

import android.view.Window;
import android.view.WindowManager;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Bartosz on 09.02.2016.
 */
public final class GeneralUtilities {

    private final static String TWO_CHARACTER_SHORT_DAY_PATTERN = "EEEEEE";
    private GeneralUtilities() {}

    public static void setLockingScreenFlags(Window win){
        win.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        win.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }

    public static String[] getShortDayNames() {
        String[] dayNames = new String[7];
        Format formatter = new SimpleDateFormat(TWO_CHARACTER_SHORT_DAY_PATTERN, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        for(int d = Calendar.MONDAY, i = 0; d <= Calendar.SUNDAY; d++, i++) {
            calendar.set(Calendar.DAY_OF_WEEK, d);
            dayNames[i] = formatter.format(calendar.getTime()).toUpperCase(Locale.getDefault());
        }
        return dayNames;
    }

    public static boolean[] daysStringToBoolean(String days) {
        String[] repeatingDays = days.split(",");
        boolean[] daysBoolean = new boolean[7];
        for(int i = 0; i < 7; i++){
            if(repeatingDays[i].equals("True")){
                daysBoolean[i] = true;
            } else {
                daysBoolean[i] = false;
            }
        }
        return daysBoolean;
    }

    public static String daysBooleanToString(boolean[] daysBoolean) {
        String days = "";
        for(Boolean day : daysBoolean){
            if(day){
                days = days + "True,";
            } else {
                days = days + "False,";
            }
        }
        return days.substring(0,days.length()-1);
    }
}
