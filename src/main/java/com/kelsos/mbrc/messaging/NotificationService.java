package com.kelsos.mbrc.messaging;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.kelsos.mbrc.R;
import com.kelsos.mbrc.controller.RunningActivityAccessor;
import com.kelsos.mbrc.views.MainView;
import com.kelsos.mbrc.views.UpdateView;

@Singleton
public class NotificationService
{
	public static final int PLUGIN_OUT_OF_DATE = 15612;

	public static final int NOW_PLAYING_PLACEHOLDER = 15613;

	public static final String NOTIFICATION_PLAY_PRESSED = "com.kelsos.mbrc.notification.play";

	public static final String NOTIFICATION_NEXT_PRESSED = "com.kelsos.mbrc.notification.next";

	public static final String NOTIFICATION_CLOSE_PRESSED = "com.kelsos.mbrc.notification.close";

	private Context context;
	private RunningActivityAccessor accessor;

	@Inject
	public NotificationService(Context context, RunningActivityAccessor accessor)
	{
		this.context = context;
		this.accessor = accessor;
	}

	/**
	 * Given a message
	 *
	 * @param message the string of a message that will be shown as a toast message.
	 */
	private void showToast(final String message)
	{
		try{
		if (accessor.getRunningActivity() == null) return;
		accessor.getRunningActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			}
		});
		}
		catch (Exception ex)
		{

		}
	}

	/**
	 * Using an id of the string stored in the strings XML this function
	 * displays a toast window.
	 */
	public void showToastMessage(final int id)
	{
		String data = context.getString(id);
		showToast(data);
	}

	/**
	 * Given a message, it displays the message on a toast window.
	 * If the AppNotification manager is not properly initialized
	 * nothing happens.
	 *
	 * @param message the string message that will be shown as a toast message.
	 */
	public void showToastMessage(final String message)
	{
		showToast(message);
	}

 	public void notificationBuilder(String title, String artist, Bitmap cover)
	{
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.notification_control);
		Intent mediaButtonIntent = new Intent(NOTIFICATION_PLAY_PRESSED);
		PendingIntent mediaPendingIntent = PendingIntent.getBroadcast(context, 1,mediaButtonIntent,PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(R.id.notification_play,mediaPendingIntent);
		Intent mediaNextButtonIntent = new Intent(NOTIFICATION_NEXT_PRESSED);
		PendingIntent mediaNextButtonPendingIntent = PendingIntent.getBroadcast(context, 1,mediaNextButtonIntent,PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(R.id.notification_next, mediaNextButtonPendingIntent);
		views.setTextViewText(R.id.notification_artist,artist);
		views.setTextViewText(R.id.notification_title,title);
		views.setImageViewBitmap(R.id.notification_album_art,cover);
		views.setImageViewResource(R.id.notification_play,R.drawable.ic_notification_play);
		views.setImageViewResource(R.id.notification_next,R.drawable.ic_notification_next);
		views.setImageViewResource(R.id.notification_close,R.drawable.ic_notification_close);


		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

		Intent notificationIntent = new Intent(context, MainView.class);
		PendingIntent notificationPendingIntent = PendingIntent.getActivity(context,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(notificationPendingIntent);

		Notification notification = mBuilder.getNotification();
		notification.contentView = views;
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		notification.icon = R.drawable.ic_mbrc_status;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(NOW_PLAYING_PLACEHOLDER, notification);
	}




	public void updateAvailableNotificationBuilder()
	{
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
				.setSmallIcon(R.drawable.ic_mbrc_status)
				.setContentTitle(context.getString(R.string.application_name))
				.setContentText(context.getString(R.string.notification_plugin_out_of_date));

		Intent resultIntent = new Intent(context, UpdateView.class);
		resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

		PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		final Notification notification = mBuilder.getNotification();
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(PLUGIN_OUT_OF_DATE, notification);
	}


}