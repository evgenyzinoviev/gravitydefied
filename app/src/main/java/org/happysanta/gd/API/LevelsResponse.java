package org.happysanta.gd.API;

import org.happysanta.gd.Storage.Level;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class LevelsResponse {

	protected Level levels[] = null;
	protected int totalCount = 0;

	public LevelsResponse(Response response) {
		parse(response);
	}

	protected void parse(Response response) {
		JSONArray json = response.getJSON();
		try {
			JSONObject object = json.getJSONObject(1);
			Vector<Level> levels = new Vector<>();
			totalCount = object.getInt("count");
			JSONArray items = object.getJSONArray("items");

			try {
				JSONObject item;
				JSONArray tracks;
				for (int i = 0; i < items.length(); i++) {
					item = items.getJSONObject(i);
					tracks = item.getJSONArray("tracks");

					levels.addElement(new Level(
							0,
							item.getString("name"),
							item.getJSONObject("author").getString("name"),
							tracks.getInt(0),
							tracks.getInt(1),
							tracks.getInt(2),
							item.getInt("added"),
							item.getInt("size"),
							item.getInt("id")
					));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				this.levels = levels.toArray(new Level[0]);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public Level[] getLevels() {
		return levels;
	}

}
