package android.mvvm.mg.com.mvvm_android.core.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.mvvm.mg.com.mvvm_android.R;
import android.mvvm.mg.com.mvvm_android.core.constants.IConstants;
import android.mvvm.mg.com.mvvm_android.core.constants.IUrls;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import dmutils.com.dmutils.general.DMDimensionConverter;

import java.util.Locale;

public class MVVMUtils {

    public static LayerDrawable changeDrawableColor(final Context context, final String startColor, final String endColor) {

        final int corner = (int) (context.getResources().getDimension(R.dimen.corner_radius_1) / context.getResources().getDisplayMetrics().density);

        final GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{Color.parseColor(startColor), Color.parseColor(endColor)});
        gd.setCornerRadius(DMDimensionConverter.dpToPixel(corner, context));

        final GradientDrawable bd = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{ContextCompat.getColor(context, R.color.black_30), ContextCompat.getColor(context, R.color.black_30)});
        bd.setCornerRadius(DMDimensionConverter.dpToPixel(corner, context));

        final Drawable[] layers = {bd, gd};
        final LayerDrawable layerDrawable = new LayerDrawable(layers);


        layerDrawable.setLayerInset(0, 0, 0, 0, 0);
        layerDrawable.setLayerInset(1, (int) DMDimensionConverter.dpToPixel(0, context), 0, (int) DMDimensionConverter.dpToPixel(1, context), (int) DMDimensionConverter.dpToPixel(2, context));

        return layerDrawable;
    }

    public static String getTransactionUrl(final int page) {
        final String url;
        switch (page) {
            case 0:
                url = IUrls.Method.TRANSACTION_1;
                break;
            case 1:
                url = IUrls.Method.TRANSACTION_2;
                break;
            case 2:
                url = IUrls.Method.TRANSACTION_3;
                break;
            default:
                url = IUrls.Method.TRANSACTION;
        }

        return url;
    }

    public static void updateLanguage(final Context context, final String code) {
        final Resources res = context.getResources();
        final DisplayMetrics dm = res.getDisplayMetrics();
        final Configuration conf = res.getConfiguration();
        conf.locale = new Locale(code);
        res.updateConfiguration(conf, dm);
    }

    public static String getLanguageName(final Context context, final String code) {
        switch (code) {
            case IConstants.Language.HY:
                return context.getString(R.string.language_armenian);
            case IConstants.Language.EN:
                return context.getString(R.string.language_english);
            case IConstants.Language.RU:
                return context.getString(R.string.language_russian);
            default:
                return null;
        }
    }
}
