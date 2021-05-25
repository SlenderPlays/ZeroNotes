package ro.zero.zeronotes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class NavButton extends View {
//    private String mExampleString;
//    private int mExampleColor = Color.RED;
//    private float mExampleDimension = 0;
//    private Drawable mExampleDrawable;
//
//    private TextPaint mTextPaint;
//    private float mTextWidth;
//    private float mTextHeight;

	private String navDestinationText = "None";
	private int textColor = Color.BLACK;
	private float textSize = 10;
	private Drawable navIcon;
	private float iconSize = 10;
	private boolean overscale = false;

	private int iconHeight = 0;
	private int iconWidth = 0;

	private TextPaint navTextPaint;
	private float textWidth;
	private float textHeight;

	public NavButton(Context context) {
		super(context);
		init(null, 0);
	}
	public NavButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}
	public NavButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		// Load attributes
		final TypedArray a = getContext().obtainStyledAttributes(
				attrs, R.styleable.NavButton, defStyle, 0);

		navDestinationText = a.getString(R.styleable.NavButton_navText);
		textSize = a.getDimension(R.styleable.NavButton_navTextSize,textSize);
		textColor = a.getColor(R.styleable.NavButton_navTextColor, textColor);

        if (a.hasValue(R.styleable.NavButton_navIcon)) {
			navIcon = a.getDrawable(R.styleable.NavButton_navIcon);
			navIcon.setCallback(this);

			iconSize = a.getDimension(R.styleable.NavButton_navIconSize,iconSize);
			overscale = a.getBoolean(R.styleable.NavButton_navIconOverscale,overscale);
      	}
		a.recycle();

		// Set up a default TextPaint object
		navTextPaint = new TextPaint();
		navTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		navTextPaint.setTextAlign(Paint.Align.LEFT);

		// Update TextPaint and text measurements from attributes
		resetComponents();
	}

	private void resetComponents() {
		resetText();
		resetIcon();
	}

	private void resetText() {
		navTextPaint.setTextSize(textSize);
		navTextPaint.setColor(textColor);

		textWidth = navTextPaint.measureText(navDestinationText);
		textHeight = navTextPaint.getFontMetrics().bottom;
	}

	private void resetIcon() {
		float aspectRatio = (float)navIcon.getIntrinsicWidth() / (float)navIcon.getIntrinsicHeight();
		iconHeight = (int)iconSize;
		iconWidth = (int)(iconSize * aspectRatio);
		if(!overscale) {
			iconHeight = Math.min(iconHeight,navIcon.getIntrinsicHeight());
			iconWidth = Math.min(iconWidth,navIcon.getIntrinsicWidth());
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// TODO: consider storing these as member variables to reduce
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();

		int contentWidth = getWidth() - paddingLeft - paddingRight;
		int contentHeight = getHeight() - paddingTop - paddingBottom;

		if(navIcon == null) {
			canvas.drawText(navDestinationText,
					paddingLeft + (contentWidth - textWidth) / 2,
					paddingTop + (contentHeight + textHeight) / 2,
					navTextPaint);
		} else {
			canvas.drawText(navDestinationText,
					paddingLeft + (contentWidth - textWidth) / 2,
					paddingTop + contentHeight - textHeight,
					navTextPaint);

			Rect textBounds = new Rect();
			navTextPaint.getTextBounds(navDestinationText,0,navDestinationText.length(),textBounds);

			int leftoverHeight = getHeight() - getPaddingTop() - getPaddingBottom() - textBounds.height();

			int finalIconHeight = Math.min(iconHeight,leftoverHeight);
			float aspectRatio = (float)navIcon.getIntrinsicWidth() / (float)navIcon.getIntrinsicHeight();
			int finalIconWidth = Math.min(iconWidth,(int)(finalIconHeight * aspectRatio));


			navIcon.setBounds(
					paddingLeft + (contentWidth - finalIconWidth)/2,
					paddingTop + (leftoverHeight - finalIconHeight)/2,
					paddingLeft + (contentWidth + finalIconWidth)/2,
					paddingTop + (leftoverHeight + finalIconHeight)/2);
			navIcon.draw(canvas);
		}

//		// Draw the text.
//		canvas.drawText(mExampleString,
//				paddingLeft + (contentWidth - mTextWidth) / 2,
//				paddingTop + (contentHeight + mTextHeight) / 2,
//				mTextPaint);
//
//		// Draw the example drawable on top of the text.
//		if (mExampleDrawable != null) {
//			mExampleDrawable.setBounds(paddingLeft, paddingTop,
//					paddingLeft + contentWidth, paddingTop + contentHeight);
//			mExampleDrawable.draw(canvas);
//		}
	}
}