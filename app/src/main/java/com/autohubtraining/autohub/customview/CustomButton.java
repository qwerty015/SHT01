package com.autohubtraining.autohub.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.autohubtraining.autohub.R;

public class CustomButton extends androidx.appcompat.widget.AppCompatButton {

    String customFont;


    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        style(context, attrs);
    }

    private void style(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomFontTextView);
        int cf = a.getInteger(R.styleable.CustomFontTextView_fontName, 0);
        int fontName = 0;
        switch (cf)
        {
            case 1:
                fontName = R.string.Montserrat_Regular;
                break;
            case 2:
                fontName = R.string.Montserrat_Medium;
                break;
            case 3:
                fontName = R.string.Montserrat_SemiBold;
                break;
            case 4:
                fontName = R.string.Montserrat_Bold;
                break;
            default:
                fontName = R.string.Montserrat_Regular;
                break;
        }

        customFont = getResources().getString(fontName);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "fonts/" + customFont + ".ttf");
        setTypeface(tf);
        a.recycle();
    }


    @Override
    public void setEnabled(boolean enabled) {
        if(enabled){
            setAlpha(1.0f);
        }else{
            setAlpha(0.4f);
        }
        super.setEnabled(enabled);
    }
}

