package com.tagtextview.lib;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static android.R.attr.start;

public class TagTextView extends TextView {
    private static final String DEFAULT_CHAR            = "@";
    private static final int DEFAULT_TAG_LAYOUT         = R.layout.tag_layout;
    private final static int DEFAULT_RENDER_APPLY_MODE  = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
    private final static int UPPER_LEFT_X               = 0;
    private final static int UPPER_LEFT_Y               = 0;
    private Context             mContext;

    private String              mFlagChar;
    private int                 mTagLayout;
    private ViewSpanRender      mViewSpanRender = null;

    /** tag 开始结束的位置 **/
    private int                 mStart;
    private int                 mEnd;


    public TagTextView(Context context) {
        this(context,null);
    }

    public TagTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        readAttribute(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TagTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        readAttribute(attrs);
    }

    private void readAttribute(AttributeSet attrs){
        if (attrs != null){
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs,R.styleable.TagTextView);
            mFlagChar = typedArray.getString(R.styleable.TagTextView_flag_char) == null ? DEFAULT_CHAR
                    : typedArray.getString(R.styleable.TagTextView_flag_char) ;
            mTagLayout = typedArray.getInt(R.styleable.TagTextView_tag_layout,DEFAULT_TAG_LAYOUT);
            typedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    /**
     * 开始渲染
     */
    public void render(){
        TextPaint textPaint = getPaint();
        float hasWidth = getMeasuredWidth() * getMaxLines();

        if (getMaxLines() == Integer.MAX_VALUE || getMaxLines() == -1){
            hasWidth = Float.MAX_VALUE;
        }

        // 计算Tag开始的位置
        String text = getText().toString();
        mStart = text.lastIndexOf(mFlagChar);
        mEnd = text.length();
        if (mStart < 0){
            return;
        }
        String tagText = text.substring(mStart,mEnd);
        String normalText = text.substring(0,mStart);

        if (mViewSpanRender == null){
            mViewSpanRender = new TagSpanRender(mTagLayout);
        }
        View tagView = mViewSpanRender.getView(tagText, mContext,getMeasuredWidth());
        float tagViewWidth = mViewSpanRender.getWidth();
        float needWidth = textPaint.measureText(normalText) + tagView.getWidth();
        if (needWidth > hasWidth ){
            // 进行截取截取
            float oneWidth = textPaint.measureText("测");
            int count = (int) ((hasWidth - tagViewWidth - oneWidth) / oneWidth); // 需要减去的数目
            setText(normalText.substring(0,count-1) + "..." + tagText);
            int distance = mEnd - mStart;
            mStart = count+2;
            mStart = mStart + distance;
        }
        applyRenderers(tagView);
    }

    private void applyRenderers(View tagView){
        if (mViewSpanRender != null) {
            Spannable spannableString = new SpannableString(getText());
            if (getText().toString().contains(mFlagChar)) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) DrawableFactory.convertViewToDrawable(tagView);
                bitmapDrawable.setBounds(UPPER_LEFT_X, UPPER_LEFT_Y, bitmapDrawable.getIntrinsicWidth(), bitmapDrawable.getIntrinsicHeight());
                spannableString.setSpan(new TagImageSpan(bitmapDrawable), mStart, mStart+2, DEFAULT_RENDER_APPLY_MODE);
            }
            setText(spannableString.subSequence(0,mStart+2));
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
//        render();
    }

    public interface ViewSpanRender {
        public abstract View getView(final String text, final Context context, float width);
        public abstract int getWidth();
    }

    public ViewSpanRender getViewSpanRender() {
        return mViewSpanRender;
    }

    public void setViewSpanRender(ViewSpanRender mViewSpanRender) {
        this.mViewSpanRender = mViewSpanRender;
        render();
    }
}
