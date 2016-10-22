package com.tagtextview.lib;


import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TagSpanRender implements TagTextView.ViewSpanRender {

    private int mLayoutId;
    private int width = 0 ;

    public TagSpanRender(int id){
        this.mLayoutId = id;
    }

    @Override
    public View getView(String text, Context context, float width) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(mLayoutId,null);
        TextView tv = null;
        for (int i = 0; i < layout.getChildCount(); i++) {
            if (layout.getChildAt(i) instanceof  TextView){
                tv = (TextView) layout.getChildAt(i);
                break;
            }
        }
        tv.setText(text.substring(1));
        tv.setMaxLines(1);
        tv.setTextColor(context.getResources().getColor(R.color.color_c1c2c7));
        tv.setMaxWidth((int) width);
        TextPaint tvPaint = tv.getPaint();
        if (tvPaint.measureText(text) > width){
            width = width;
        }else {
            width = tvPaint.measureText(text);
        }
        return layout;
    }

    @Override
    public int getWidth() {
        return width;
    }
}
