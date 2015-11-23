package info.nich.solsang.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import info.nich.solsang.R;
import info.nich.solsang.activities.MainActivity;

/**
 * Created by nich- on 2015/11/19.
 */
public class NotificationHelper {
    /**
     * Build notification with a given priority
     *
     * @param priority priority from Notification.priority
     * @return a Notification object
     */
    public static Notification buildNotification(Context context, int priority) {
        String title = context.getString(R.string.app_name);
        String text = context.getString(R.string.touch_to_launch);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
        return new NotificationCompat.Builder(context)
                .setContentTitle(title)                     // Title
                .setContentText(text)                       // Text
                .setSmallIcon(R.drawable.ic_action_toggle_star_outline)   // Icon
                .setContentIntent(pIntent)                  // Intent to launch this app
                .setWhen(0)                                 // No time to display
                .setPriority(priority)                      // Given priority
                .build();
    }
}
