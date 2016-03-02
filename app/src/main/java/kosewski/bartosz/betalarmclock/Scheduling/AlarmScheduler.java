package kosewski.bartosz.betalarmclock.Scheduling;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kosewski.bartosz.betalarmclock.Alarm;
import kosewski.bartosz.betalarmclock.AlarmReceiver;
import kosewski.bartosz.betalarmclock.Database.AlarmDataSource;

/**
 * Created by Bartosz on 22.02.2016.
 */
public class AlarmScheduler {

    public static final int MONDAY = 1;
    public static final int SUNDAY = 7;


    public static void setAlarms(Context context) {
        AlarmDataSource dataSource = new AlarmDataSource(context);
        List<Alarm> alarms = dataSource.readAlarms();

        for(Alarm alarm : alarms) {
            if(alarm.isEnabled()) {

                Calendar calendarFrom = Calendar.getInstance();

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                PendingIntent pendingIntent = createPendingIntent(context, alarm);
                Log.i(context.getPackageName(), "Setting Alarm: " + alarm.getId() + "Time: " + alarm.getHour() + ":" + alarm.getMinutes());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, getAlarmTime(calendarFrom, alarm), pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, getAlarmTime(calendarFrom, alarm), pendingIntent);
                }
            }
        }
    }



    public static PendingIntent createPendingIntent(Context context, Alarm alarm) {

        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("ID", alarm.getId());

        return PendingIntent.getBroadcast(context, alarm.getId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void cancelAlarms(Context context) {
        AlarmDataSource dataSource = new AlarmDataSource(context);
        List<Alarm> alarms = dataSource.readAlarms();

        if(alarms.size() != 0 || alarms != null) {
            for(Alarm alarm : alarms){
                PendingIntent pendingIntent = createPendingIntent(context, alarm);
                Log.i(context.getPackageName(), "Canceling alarm: " + alarm.getId() + " Time: " + alarm.getHour() + ":" + alarm.getMinutes());

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            }
        }
    }

    public static void deleteAlarm(Context context, Alarm alarm) {
        AlarmScheduler.cancelAlarms(context);
        AlarmDataSource dataSource = new AlarmDataSource(context);
        dataSource.delete(alarm.getId());

        setAlarms(context);
    }

    public static long getAlarmTime(Calendar calendarFrom, Alarm alarm) {
        if (alarm.isOneShot()) {
            return getOneShotAlarmTime(calendarFrom, alarm);
        } else {
            return getRepeatingAlarmTime(calendarFrom, alarm);
        }
    }

    private static long getOneShotAlarmTime(Calendar calendarFrom, Alarm alarm) {
        Calendar calendarAlarm = Calendar.getInstance();
        calendarAlarm.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendarAlarm.set(Calendar.MINUTE, alarm.getMinutes());
        calendarAlarm.set(Calendar.SECOND, 0);
        calendarAlarm.set(Calendar.MILLISECOND, 0);

        final int nowHour = calendarFrom.get(Calendar.HOUR_OF_DAY);
        final int nowMinute = calendarFrom.get(Calendar.MINUTE);

        // if we cannot schedule today then set the alarm for tomorrow
        if ((alarm.getHour() < nowHour) ||
                (alarm.getHour() == nowHour && alarm.getMinutes() <= nowMinute)) {
            calendarAlarm.add(Calendar.DATE, 1);
        }

        return calendarAlarm.getTimeInMillis();
    }

    private static long getRepeatingAlarmTime(Calendar calendarFrom, Alarm alarm) {
        Calendar calendarAlarm = Calendar.getInstance();
        calendarAlarm.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendarAlarm.set(Calendar.MINUTE, alarm.getMinutes());
        calendarAlarm.set(Calendar.SECOND, 0);
        calendarAlarm.set(Calendar.MILLISECOND, 0);
        boolean thisWeek = false;

        int nowDay;
        //Fix to make Monday first in Calendar
        if(calendarFrom.get(Calendar.DAY_OF_WEEK) == 1){
           nowDay = 7;
        } else {
           nowDay = calendarFrom.get(Calendar.DAY_OF_WEEK) - 1;
        }
        final int nowHour = calendarFrom.get(Calendar.HOUR_OF_DAY);
        final int nowMinute = calendarFrom.get(Calendar.MINUTE);

        // First check if it's later today or later in the week

        for (int dayOfWeek = MONDAY; dayOfWeek <= SUNDAY; ++dayOfWeek) {
            if (alarm.getRepeatingDay(dayOfWeek - 1) && dayOfWeek >= nowDay &&
                    !(dayOfWeek == nowDay && alarm.getHour() < nowHour) &&
                    !(dayOfWeek == nowDay && alarm.getHour() == nowHour &&
                            alarm.getMinutes() <= nowMinute)) {
                // Only increment the calendar if the alarm isn't for later today
                if (dayOfWeek > nowDay) {
                    calendarAlarm.add(Calendar.DATE, dayOfWeek - nowDay);
                }
                thisWeek = true;
                break;
            }
        }

        if (!thisWeek) {
            for (int dayOfWeek = MONDAY; dayOfWeek <= SUNDAY; ++dayOfWeek) {
                if (alarm.getRepeatingDay(dayOfWeek) && dayOfWeek <= nowDay) {
                    calendarAlarm.add(Calendar.DATE, (7 - nowDay) + dayOfWeek);
                    break;
                }
            }
        }

        return calendarAlarm.getTimeInMillis();
    }

}
