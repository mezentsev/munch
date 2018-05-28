package pro.mezentsev.munch.browser.helpers;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateHelper {

    @NonNull
    public static String getDate(long timestamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM", Locale.US);
            Date netDate = (new Date(timestamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }

    @NonNull
    public static String getTime(long timestamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
            Date netDate = (new Date(timestamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }
}
