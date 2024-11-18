package com.caio.mendes.projetobtmandroid.MyFirebaseNotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.caio.mendes.projetobtmandroid.R;
import com.caio.mendes.projetobtmandroid.model.Notificacao;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage notificacao) {

        if(notificacao.getNotification() != null) {
            String titulo = notificacao.getNotification().getTitle();
            String corpo = notificacao.getNotification().getBody();

            enviarNotificacao(titulo, corpo);

           // Log.i("Notificação", "Titulo" +  titulo + " Corpo" + corpo);
        }

    }

    private void enviarNotificacao(String titulo, String corpo){

        //Configuração para notificação
        String canal = getString(R.string.default_notification_channel_id);
        Uri uriSom = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notificacao nf = new Notificacao(titulo, corpo);
        nf.getBody();

        //Criar a Notificação
        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(this, canal);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo_start);
        notificacao.setLargeIcon(largeIcon)

                .setContentTitle(titulo)
                .setContentText(corpo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.alerta)
                .setLargeIcon(largeIcon)
                .setStyle(new NotificationCompat.BigTextStyle() // mudança
                .bigText(corpo))
                .setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(largeIcon)
                .bigLargeIcon(null))
                .setOnlyAlertOnce(true)
                .setSound(uriSom)
                .setAutoCancel(true);
             //   .setContentIntent(pendingIntent);

        //Recuperar notificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Verifica versão do Android a partir do Oreo para configurar canal de notificação
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(canal, "canal", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);

        }

        //Envia a Notificação
        notificationManager.notify(0, notificacao.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //Salvando Token no firebase



        //token do aparelho google pixel
        //cC72BgC9HcQ:APA91bHa7Y6kL_GkWIMDlhg-YsSuf1IL0jA9EEG8XME6LaV7A91p7ITDAcPPsf6uimVUJo22hHwhVqpaP9k-0UG7NGDmMEPRy66IxB-7uUICbEn-RSwB95hbJ9mzraKc4LY8zSPytuuX
        Log.i("onNewToken", "onNewToken: " + s);
    }

}
