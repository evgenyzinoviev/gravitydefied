package org.happysanta.gd.Menu;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.happysanta.gd.Global;
import org.happysanta.gd.R;
import org.happysanta.gd.Storage.Level;

import java.util.ArrayList;

import static org.happysanta.gd.Helpers.getString;

public class LevelsAdapter extends ArrayAdapter<Level> {

	private ArrayList<Level> levels;

	public LevelsAdapter(Context context, ArrayList<Level> levels) {
		super(context, R.layout.levels_list_item, levels);
		this.levels = levels;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.levels_list_item, null);
		}

		Level level = levels.get(position);
		if (level != null) {
			TextView name = (TextView) v.findViewById(R.id.level_name);
			TextView count = (TextView) v.findViewById(R.id.level_count);

			name.setTypeface(Global.robotoCondensedTypeface);
			count.setTypeface(Global.robotoCondensedTypeface);

			name.setText(level.getName());
			count.setText(Html.fromHtml(String.format(getString(R.string.levels_count_tpl),
					level.getCountEasy() + " - " + level.getCountMedium() + " - " + level.getCountHard(), level.getShortAddedDate())));
		}

		return v;
	}

}


