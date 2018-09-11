package co.devhack.appfirebaseworkshop.util;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import co.devhack.appfirebaseworkshop.R;

public class Util {

    public static MaterialDialog showMaterialProgressDialog(Context context, String msgTitle) {
        return new MaterialDialog.Builder(context)
                .title(msgTitle)
                .content(R.string.wait_please)
                .progress(true, 0)
                .show();
    }

}
