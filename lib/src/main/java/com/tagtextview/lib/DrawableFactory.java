package com.tagtextview.lib;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DrawableFactory {
    private final static int UPPER_LEFT_X = 0;
    private final static int UPPER_LEFT_Y = 0;

    /**
     * convert view to drawable
     * @param view tagView
     * @return drawable
     */
    public static Drawable convertViewToDrawable(View view){

        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        view.layout(UPPER_LEFT_X, UPPER_LEFT_Y, view.getMeasuredWidth(), view.getMeasuredHeight());

        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate(-view.getScrollX(), -view.getScrollY());
        TextView textView = (TextView) ((LinearLayout)view).getChildAt(0);
        textView.draw(c);
        textView.setDrawingCacheEnabled(true);
        textView.getResources();
        Bitmap cacheBmp = textView.getDrawingCache();
        Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
        textView.destroyDrawingCache();
        BitmapDrawable bitmapDrawable = new BitmapDrawable(view.getResources(),viewBmp);
        return bitmapDrawable;
    }
}
