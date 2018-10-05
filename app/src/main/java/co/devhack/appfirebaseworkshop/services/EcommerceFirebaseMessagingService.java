package co.devhack.appfirebaseworkshop.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class EcommerceFirebaseMessagingService extends FirebaseMessagingService {

    private static final String LOGTAG = "EcommerceMessage";

    @Override
    public void onNewToken(String token) {

        super.onNewToken(token);
        Log.i(LOGTAG, "New Token: " + token);

        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(task -> {

                    if (!task.isSuccessful()) {
                        Log.e(LOGTAG, "No pudo registrarse al topico");
                    }

                    Log.d(LOGTAG, "Registrado correctamente al topico");
                });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(LOGTAG, "From: " + remoteMessage.getFrom());
        String idNew = "";

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(LOGTAG, "Message data payload: " + remoteMessage.getData());
        }

        /*if (remoteMessage.getNotification() != null) {

            String titulo = remoteMessage.getNotification().getTitle();
            String texto = remoteMessage.getNotification().getBody();

            Log.d(LOGTAG, "NOTIFICACION RECIBIDA");
            Log.d(LOGTAG, "Título: " + titulo);
            Log.d(LOGTAG, "Texto: " + texto);

            //Opcional: mostramos la notificación en la barra de estado
            showNotification(titulo, texto, idNew);
        }*/

    }

    private void showNotification(String title, String text, String idNew) {
        // create notification
    }

}