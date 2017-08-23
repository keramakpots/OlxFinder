package com.codecool.olxpathfinder.geocoding;

import com.codecool.olxpathfinder.model.ExtendOffer;
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
import org.joda.time.DateTime;

public class GeocodingCalculator {

    private final String apiKey = "AIzaSyAyGpOIccTKlun-pBU8LMFRWwXIP8h_X_4";
    private final GeoApiContext context = new GeoApiContext.Builder()
        .apiKey(apiKey)
        .build();
    private String street;
    private long maxDist;
    private long maxTravel;
    private long maxBic;
    private long maxDrive;

    public GeocodingCalculator(String street, long maxDist, long maxTravel, long maxBic,
        long maxDrive) {
        this.street = street;
        this.maxDist = maxDist;
        this.maxTravel = maxTravel;
        this.maxBic = maxBic;
        this.maxDrive = maxDrive;
    }


    public ExtendOffer DistanceCalculator(Offer offer)
        throws InterruptedException, ApiException, IOException {
        DistanceMatrix matrix = DistanceMatrixApi.newRequest(context)
            .origins(offer.getStreet() + ", Kraków, Polska")
            .destinations(this.street + ", Kraków, Polska")
            .mode(TravelMode.DRIVING)
            .language("pl")
            .avoid(RouteRestriction.TOLLS)
            .units(Unit.METRIC)
            .departureTime(
                new DateTime().plusMinutes(2)) // this is ignored when an API key is used
            .await();
        long duration = 0;
        long distance = 0;
        for (DistanceMatrixRow row : matrix.rows) {
            for (DistanceMatrixElement element : row.elements
                ) {
                duration = element.duration.inSeconds;
                distance = element.distance.inMeters;
            }
        }
        ExtendOffer extendOffer = new ExtendOffer(offer.getUrl(), offer.getStreet(),
            offer.getDescription(), offer.getTitle(), offer.getPublishTime(), offer.getPrice()
            , distance, duration);
        return extendOffer;
    }

}
