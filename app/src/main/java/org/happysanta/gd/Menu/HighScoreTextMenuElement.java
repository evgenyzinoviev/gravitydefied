package org.happysanta.gd.Menu;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import org.happysanta.gd.Global;
import org.happysanta.gd.Menu.Views.MenuImageView;
import org.happysanta.gd.Menu.Views.MenuLinearLayout;
import org.happysanta.gd.R;

import static org.happysanta.gd.Helpers.*;

public class HighScoreTextMenuElement
		extends TextMenuElement
		implements MenuElement {

	protected static final int TEXT_LEFT_MARGIN = 5;
	protected static final int SUBTITLE_MARGIN_BOTTOM = 8;
	protected static final int SUBTITLE_TEXT_SIZE = 20;
	protected static final int LAYOUT_PADDING = 3;
	protected static int medals[] = new int[]{
			R.drawable.s_medal_gold,
			R.drawable.s_medal_silver,
			R.drawable.s_medal_bronze
	};
	protected static Typeface defaultTypeface = null;

	protected boolean showMedal = false;
	protected MenuLinearLayout layout;
	protected MenuImageView image;

	public HighScoreTextMenuElement(String s) {
		super(s);
		createAllViews();
	}

	public HighScoreTextMenuElement(Spanned text) {
		super(text);
		createAllViews();
	}

	protected void createAllViews() {
		Context context = getGDActivity();

		layout = new MenuLinearLayout(context);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		image = new MenuImageView(context);
		image.setScaleType(ImageView.ScaleType.CENTER);
		image.setVisibility(View.GONE);

		// textView was already created in super constructor
		textView.setLineSpacing(0, 1);

		if (defaultTypeface == null) {
			defaultTypeface = textView.getTypeface();
		}

		LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		// textViewLayoutParams.setMargins(getDp(TEXT_LEFT_MARGIN), 0, 0, 0);
		textView.setLayoutParams(textViewLayoutParams);

		layout.addView(image, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
		layout.addView(textView);
	}

	@Override
	public View getView() {
		return layout;
	}

	public void setMedal(boolean showMedal, int medalIndex) {
		image.setVisibility(showMedal ? View.VISIBLE : View.GONE);
		image.setImageResource(medals[medalIndex]);

		LinearLayout.LayoutParams textViewLayoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
		textViewLayoutParams.setMargins(showMedal ? getDp(TEXT_LEFT_MARGIN) : 0, 0, 0, 0);
		textView.setLayoutParams(textViewLayoutParams);

		this.showMedal = showMedal;
	}

	public void setIsSubtitle(boolean is) {
		textView.setTextSize(is ? SUBTITLE_TEXT_SIZE : TEXT_SIZE);
		textView.setTypeface(is ? Global.robotoCondensedTypeface : defaultTypeface);

		LinearLayout.LayoutParams textViewLayoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
		textViewLayoutParams.setMargins(!is && showMedal ? getDp(TEXT_LEFT_MARGIN) : 0, 0, 0, is ? getDp(SUBTITLE_MARGIN_BOTTOM) : 0);
		textView.setLayoutParams(textViewLayoutParams);
	}

	public void setLayoutPadding(boolean use) {
		layout.setPadding(0, use ? getDp(LAYOUT_PADDING) : 0, 0, use ? getDp(LAYOUT_PADDING) : 0);
	}

}
