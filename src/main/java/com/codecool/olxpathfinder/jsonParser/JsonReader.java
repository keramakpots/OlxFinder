package com.codecool.olxpathfinder.jsonParser;

import com.codecool.olxpathfinder.model.Offer;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

    private JSONObject jsonObject;

    public JsonReader(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public List<Offer> parseJsonToOfferObject() throws JSONException {
        JSONArray arr = jsonObject.getJSONArray("ads");
        List<Offer> offers = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            Offer offer = new Offer();
            String url = arr.getJSONObject(i).getString("url");
            String street = arr.getJSONObject(i).getString("street");
            String description = arr.getJSONObject(i).getString("description");
            String publishTime = arr.getJSONObject(i).getString("publishTime");
            Long price = arr.getJSONObject(i).getLong("price");
            String title = arr.getJSONObject(i).getString("title");
            offer.setDescription(description);
            offer.setPrice(price);
            offer.setStreet(street);
            offer.setTitle(title);
            offer.setUrl(url);
            offer.setPublishTime(publishTime);
            offers.add(offer);
        }
        return offers;
    }
}
