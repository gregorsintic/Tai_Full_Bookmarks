package com.example.rok.terroristinfo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/**
 * Ellipsize the text when the lines of text exceeds the value provided by {@link #makeExpandable} methods.
 * Appends {@link #MORE} or {@link #LESS} as needed.
 * TODO: add animation
 */
public class ExpandableTextView extends TextView {
    private static final String ELLIPSIZE = "... ";
    private static final String MORE      = " More";
    private static final String LESS      = " Less";

    private String mFullText;
    private int    mMaxLines;

    public ExpandableTextView(Context context) {
        super(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void makeExpandable(int maxLines) {
        makeExpandable(getText().toString(), maxLines);
    }

    public void makeExpandable(String fullText, final int maxLines) {
        mFullText = fullText;
        mMaxLines = maxLines;
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
                if (getLineCount() <= maxLines) {
                    setText(mFullText);
                } else {
                    setMovementMethod(LinkMovementMethod.getInstance());
                    showLess();
                }
            }
        });
    }

    /**
     * truncate text and append a clickable {@link #MORE}
     */
    private void showLess() {
        int lineEndIndex = getLayout().getLineEnd(mMaxLines - 1);
        String newText = mFullText.substring(0, lineEndIndex - (ELLIPSIZE.length() + MORE.length() + 1))
                + ELLIPSIZE + ' ' + MORE;
        SpannableStringBuilder builder = new SpannableStringBuilder(newText);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showMore();
            }
        }, newText.length() - MORE.length(), newText.length(), 0);
        setText(builder, BufferType.SPANNABLE);
    }

    /**
     * show full text and append a clickable {@ link #LESS}
     */
    private void showMore() {
        SpannableStringBuilder builder = new SpannableStringBuilder(mFullText + ' ' + LESS);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showLess();
            }
        }, builder.length() - LESS.length(), builder.length(), 0);
        setText(builder, BufferType.SPANNABLE);
    }
}