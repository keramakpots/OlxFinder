package com.codecool.olxpathfinder.geocoding;

import com.codecool.olxpathfinder.OfferService.OfferService;
import com.codecool.olxpathfinder.jsonParser.JsonReader;
import com.codecool.olxpathfinder.model.ExtendOffer;
import com.codecool.olxpathfinder.model.Offer;
import com.google.maps.errors.ApiException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferFilter {

    private String streetJson = "{\n"
        + "  \"ads\" :\n"
        + "  [\n"
        + "  {\n"
        + "    \"url\" : \"https://www.olx.pl/oferta/wynajme-nowe-mieszkanie-53-m2-w-centrum-chojnic-CID3-IDo8x6Y.html#f3c4691bd2\" ,\n"
        + "    \"street\" : \"Szafrana\" ,\n"
        + "    \"description\" : \"piekne mieszaknie przy Polna\" ,\n"
        + "    \"publishTime\" : \"15.09.2017\" ,\n"
        + "    \"price\" : 10000000 ,\n"
        + "    \"title\" : \"SUPER MIESZXKANIEEE\"\n"
        + "  } ,\n"
        + "  {\n"
        + "    \"url\" : \"https://www.olx.pl/oferta/mieszkanie-komfortowe-wynajme-CID3-IDo3Uuu.html#c82cfa8117;promoted\" ,\n"
        + "    \"street\" : \"Balicka\" ,\n"
        + "    \"description\" : \"piekne mieszaknie przy Polna\" ,\n"
        + "    \"publishTime\" : \"15.09.2017\" ,\n"
        + "    \"price\" : 1000 ,\n"
        + "    \"title\" : \"SUPER MIESZXKANIEEE\"\n"
        + "  }\n"
        + "]\n"
        + "}";

    private GeocodingCalculator geocodingCalculator;
    @Autowired
    private OfferService offerService;

    public List<Offer> filtering(Map<String, String[]> parameters)
        throws InterruptedException, ApiException, IOException, JSONException {
        JSONObject jsonObject = new JSONObject(streetJson);
        JsonReader jsonReader = new JsonReader(jsonObject);
        List<Offer> offers = jsonReader.parseJsonToOfferObject();
        List<Offer> offersInSearchedArea = new ArrayList<>();
        long minPrice = 0;
        long maxPrice = 0;
        String street = "";
        int rooms = 0;
        long maxDist = 1000;
        long maxTravel = 10000000;
        long maxBic = 10000000;
        long maxDrive = 1144;
        for (String key : parameters.keySet()) {
            String[] vals = parameters.get(key);
            for (String val : vals) {
                if (key.equals("minPrice")) {
                    minPrice = Long.valueOf(val);
                } else if (key.equals("maxPrice")) {
                    maxPrice = Long.valueOf(val);
                } else if (key.equals("street")) {
                    street = key;
                } else if (key.equals("rooms")) {
                    rooms = Integer.valueOf(val);
                } else if (key.equals("maxDist")) {
                    maxDist = Long.valueOf(val);
                } else if (key.equals("maxTravel")) {
                    maxTravel = Long.valueOf(val);
                } else if (key.equals("maxBic")) {
                    maxBic = Long.valueOf(val);
                } else if (key.equals("maxDrive")) {
                    maxDrive = Long.valueOf(val);
                }
            }
        }
        List<Offer> offersFromDB = offerService.findAllByCriteria(minPrice, maxPrice, rooms);
        geocodingCalculator = new GeocodingCalculator(street, maxDist, maxTravel, maxBic, maxDrive);
        for (Offer offer : offers) {
            ExtendOffer extendOffer = geocodingCalculator.DistanceCalculator(offer);
            if (extendOffer.getDistance() <= maxDist) {
                offersInSearchedArea.add(extendOffer);
            } else if (extendOffer.getDuration() <= maxDrive) {
                offersInSearchedArea.add(extendOffer);
            }
        }
        return offersInSearchedArea;
    }
}

