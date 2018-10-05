package co.devhack.appfirebaseworkshop.util;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class UtilAnalytics {

    public static void logEventSelectProduct(Context context, String id, String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        FirebaseAnalytics.getInstance(context)
                .logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public static void logEventPurchaseProduct(Context context, String id, String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        FirebaseAnalytics.getInstance(context)
                .logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, bundle);
    }

    public static void logEventCreateProduct(Context context, String id, String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        FirebaseAnalytics.getInstance(context)
                .logEvent("create_product", bundle);
    }

    public static void logEventNewUser(Context context, String name, String email) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, email);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        FirebaseAnalytics.getInstance(context)
                .logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
    }

    public static void setUserPropertyFavoriteProduct(Context context, String nameProduct) {
        Bundle bundle = new Bundle();
        FirebaseAnalytics.getInstance(context)
                .setUserProperty("favorite_product", nameProduct);
    }
}
