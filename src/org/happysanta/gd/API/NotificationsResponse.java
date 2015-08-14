package org.happysanta.gd.API;

import org.json.JSONArray;
import org.json.JSONObject;

public class NotificationsResponse {

	private String title;
	private String message;
	private String url;
	private int buttonsCount = 1;
	private String[] buttons = new String[2];
	private boolean empty = true;
	private boolean isURL = false;

	public NotificationsResponse(Response response) {
		parse(response);
	}

	protected void parse(Response response) {
		JSONArray json = response.getJSON();
		try {
			JSONArray jsonResponse = json.getJSONArray(1);
			if (jsonResponse.length() > 0) {
				JSONObject notification = jsonResponse.getJSONObject(0);
				title = notification.getString("title");
				message = notification.getString("message");
				buttons[0] = notification.getString("button_ok");
				if (notification.has("button_cancel")) {
					buttonsCount = 2;
					buttons[1] = notification.getString("button_cancel");
				}
				if (notification.has("open_url")) {
					url = notification.getString("open_url");
					isURL = true;
				}
				empty = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isEmpty() {
		return empty;
	}

	public boolean hasURL() {
		return isURL;
	}

	public String getURL() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

	public boolean hasTwoButtons() {
		return buttonsCount == 2;
	}

	public String getOKButton() {
		return buttons[0];
	}

	public String getCancelButton() {
		return buttons[1];
	}

}
