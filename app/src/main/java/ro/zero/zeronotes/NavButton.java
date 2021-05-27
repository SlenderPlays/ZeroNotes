package ro.zero.zeronotes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * This UI Component creates a view which has two main components: Text and an Icon. Both are centered vertically
 * such that the text is aligned to the bottom and the remaining vertical space is assigned to the Icon. <br>
 * You can use padding to decrease the total size taken up by the active elements (Text, Icon)
 * without nesting or size reduction.
 * <br><br><br>
 * Text Properties:
 * <ul>
 * <li><b>navText</b>: The text that will appear under the icon</li>
 * <li><b>navTextSize</b>: The size of the text.</li>
 * <li><b>navTextColor</b>: The color of the text.</li>
 * </ul>
 * <br>
 * Icon Properties:
 * <ul>
 * <li><b>navIcon</b>: A reference to the icon you want displayed.</li>
 * <li><b>navIconSize</b>: The size of the icon.</li>
 * <li><b>navIconOverscale [Optional]</b>: If set to true, the icon will be scaled up to meet the requested size. If set to
 * false then the icon will scale up to it's native size and no further. Default: false.</li>
 * </ul>
 * <br>
 * Background Properties (All optional):
 * <ul>
 * <li><b>selectedColor</b>: The color of the background if <code>.select()</code> is called on this.</li>
 * <li><b>deselectedColor</b>: The color of the background on startup or if <code>.deselect()</code> is called on this.
 * If no value is given to this property, it will use the value of <code>android:background</code></li>
 * </ul>
 */
public class NavButton extends View {

	// Text XML Props
	private String navDestinationText = "None";
	private int textColor = Color.BLACK;
	private float textSize = 10;
	// Icon XML Props
	private Drawable navIcon;
	private float iconSize = 10;
	private boolean overscale = false;
	// Background XML Props
	private Integer deselectedBackgroundColor = null;
	private Integer selectedBackgroundColor = null;


	// Text support vars
	private TextPaint navTextPaint;
	private float textWidth;
	private float textHeight;
	private final Rect textBounds = new Rect();

	// Icon support vars
	private int iconHeight = 0;
	private int iconWidth = 0;


	// Button & Background support vars
	private boolean selected = false;
	private final int ERROR_COLOR = Color.rgb(255, 0, 255);

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

		// Get text params
		navDestinationText = a.getString(R.styleable.NavButton_navText);
		textSize = a.getDimension(R.styleable.NavButton_navTextSize,textSize);
		textColor = a.getColor(R.styleable.NavButton_navTextColor, textColor);

		// Get Icon params
        if (a.hasValue(R.styleable.NavButton_navIcon)) {
			navIcon = a.getDrawable(R.styleable.NavButton_navIcon);
			navIcon.setCallback(this);

			iconSize = a.getDimension(R.styleable.NavButton_navIconSize,iconSize);
			overscale = a.getBoolean(R.styleable.NavButton_navIconOverscale,overscale);
      	}
        // Get Background params
		if(a.hasValue(R.styleable.NavButton_deselectedColor)) {
			deselectedBackgroundColor = a.getColor(R.styleable.NavButton_deselectedColor,ERROR_COLOR);
		} else {
			if (getBackground() instanceof ColorDrawable)
				deselectedBackgroundColor = ((ColorDrawable) getBackground()).getColor();
		}

		if(a.hasValue(R.styleable.NavButton_selectedColor)) {
			selectedBackgroundColor = a.getColor(R.styleable.NavButton_selectedColor,ERROR_COLOR);
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

		if(selectedBackgroundColor != null && deselectedBackgroundColor != null) {
			if(selected) {
				setBackgroundColor(selectedBackgroundColor);
			} else {
				setBackgroundColor(deselectedBackgroundColor);
			}
		}

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

			textBounds.setEmpty();
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
	}

	public void select() {
		selected = true;
		invalidate();
	}
	public void deselect() {
		selected = false;
		invalidate();
	}
}