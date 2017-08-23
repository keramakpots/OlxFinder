package com.codecool.olxpathfinder.geocoding;

import com.codecool.olxpathfinder.jsonParser.JsonReader;
import com.codecool.olxpathfinder.model.Offer;
import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class GeocodingFilter {

    private final String apiKey = "AIzaSyAyGpOIccTKlun-pBU8LMFRWwXIP8h_X_4";
    private final GeoApiContext context = new GeoApiContext.Builder()
        .apiKey(apiKey)
        .build();

    public List<Offer> filtering(String streetsJson)
        throws InterruptedException, ApiException, IOException, JSONException {
        JSONObject jsonObject = new JSONObject(streetsJson);
        JsonReader jsonReader = new JsonReader(jsonObject);
        List<Offer> offers = jsonReader.parseJsonToOfferObject();
        List<Offer> offersInSearchedArea = new ArrayList<>();
        for (Offer offer : offers) {
            DistanceMatrix matrix = DistanceMatrixApi.newRequest(context)
                .origins(offer.getStreet() + ", Kraków, Polska")
                .destinations("Krowoderska, Polska")
                .mode(TravelMode.DRIVING)
                .language("pl")
                .avoid(RouteRestriction.TOLLS)
                .units(Unit.METRIC)
                .departureTime(
                    new DateTime().plusMinutes(2)) // this is ignored when an API key is used
                .await();
            for (DistanceMatrixRow row : matrix.rows) {
                for (DistanceMatrixElement element : row.elements
                    ) {
                    if (element.duration.inSeconds <= (long) 10000) {
                        offersInSearchedArea.add(offer);
                    }
                }
            }
        }
        return offersInSearchedArea;
    }

    public List<Offer> filtering(String streetsJson, Map<String, String[]> parameters)
        throws InterruptedException, ApiException, IOException, JSONException {
        JSONObject jsonObject = new JSONObject(streetsJson);
        JsonReader jsonReader = new JsonReader(jsonObject);
        List<Offer> offers = jsonReader.parseJsonToOfferObject();
        List<Offer> offersInSearchedArea = new ArrayList<>();
        for (String key : parameters.keySet()) {
            String[] vals = parameters.get(key);
            for (String val : vals) {
            }
            for (Offer offer : offers) {
                DistanceMatrix matrix = DistanceMatrixApi.newRequest(context)
                    .origins(offer.getStreet() + ", Kraków, Polska")
                    .destinations("Krowoderska, Polska")
                    .mode(TravelMode.DRIVING)
                    .language("pl")
                    .avoid(RouteRestriction.TOLLS)
                    .units(Unit.METRIC)
                    .departureTime(
                        new DateTime().plusMinutes(2)) // this is ignored when an API key is used
                    .await();
                for (DistanceMatrixRow row : matrix.rows) {
                    for (DistanceMatrixElement element : row.elements
                        ) {
                        if (element.duration.inSeconds <= (long) 10000) {
                            offersInSearchedArea.add(offer);
                        }
                    }
                }
            }

        }
        return offersInSearchedArea;
    }
}

