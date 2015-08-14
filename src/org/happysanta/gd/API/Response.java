package org.happysanta.gd.API;

import org.happysanta.gd.API.*;
import org.json.JSONArray;
import org.json.JSONException;

public class Response {

	JSONArray jsonArray;

	public Response(String result) throws JSONException, APIException {
		jsonArray = new JSONArray(result);
		if (!isOK()) {
			throw new APIException(jsonArray.getString(1));
		}
	}

	public boolean isOK() throws JSONException {
		return jsonArray.getString(0).equals("ok");
	}

	public JSONArray getJSON() {
		return jsonArray;
	}

}
