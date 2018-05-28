package pro.mezentsev.munch.browser.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public final class ImageHelper {
    @Nullable
    public static Bitmap getBitmapFromBase64(@NonNull String base64image) {
        try {
            byte[] decodedString = Base64.decode(base64image, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static String getBase64FromBitmap(@NonNull Bitmap bitmap) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }
}
