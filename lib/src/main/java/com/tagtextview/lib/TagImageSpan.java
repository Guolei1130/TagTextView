package com.tagtextview.lib;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

public class TagImageSpan extends ImageSpan {

    public TagImageSpan(BitmapDrawable arg1) {
        super(arg1);
    }
    public int getSize(Paint paint, CharSequence text, int start, int end,
                       Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        if (fm != null) {
            Paint.FontMetricsInt fmPaint=paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight=rect.bottom-rect.top;
            /**
             * 部分手机无法对齐，原因未知
             * 正常情况下应该
             * @{code int top= drHeight/2 - fontHeight/4;
                      int bottom=drHeight/2 + fontHeight/4;}
             */
            int top= drHeight/2 - fontHeight/2;
            int bottom=drHeight/2 + fontHeight/2;
            fm.ascent=-bottom;
            fm.top=-bottom;
            fm.bottom=top;
            fm.descent=top;
        }
        return rect.right;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, Paint paint) {
        Drawable b = getDrawable();
        canvas.save();
        int transY = 0;
        transY = ((bottom-top) - b.getBounds().bottom)/2+top;
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}
