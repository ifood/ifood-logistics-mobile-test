package alex.com.ifoodchallenge.controller.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import alex.com.ifoodchallenge.controller.receiver.VerifyNewTweetsReceiver;

public class VerifyNewTweetsScheduler {
    public static final int ALARM_BROADCAST_CODE = 7776;
    public static final int TIME_TO_START = 1000;
    public static final int TIME_BETWEEN = 60000;
    public static PendingIntent currentPendingIntent;

    public static void getNewTweets(Context context) {

        PendingIntent pendingIntent;
        AlarmManager alarmManager;

        Intent intent = new Intent(context, VerifyNewTweetsReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, ALARM_BROADCAST_CODE, intent, 0);

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + TIME_TO_START,
                TIME_BETWEEN,
                pendingIntent);
    }

    public static void removeGetNewTweets(Context context) {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(currentPendingIntent);
    }
}
