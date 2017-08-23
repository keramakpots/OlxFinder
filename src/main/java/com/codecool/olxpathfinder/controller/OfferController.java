package com.codecool.olxpathfinder.controller;

import com.codecool.olxpathfinder.geocoding.OfferFilter;
import com.codecool.olxpathfinder.model.Offer;
import com.google.maps.errors.ApiException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OfferController {

    @Autowired
    OfferFilter offerFilter;

    @Autowired
    public OfferController() {
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ModelAndView loadStartView(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ModelAndView loadMap(ModelAndView modelAndView) {
        modelAndView.setViewName("map");
        return modelAndView;
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ModelAndView findPlaces(ModelAndView modelAndView)
        throws InterruptedException, ApiException, IOException {
        modelAndView.setViewName("map");
        return modelAndView;
    }

    @RequestMapping(value = "/getAds/", method = RequestMethod.GET)
    public ModelAndView filterCriteria(ModelAndView modelAndView,
        HttpServletRequest request
    ) throws InterruptedException, ApiException, IOException, JSONException {
        Map<String, String[]> parameters = request.getParameterMap();
        List<Offer> offers = offerFilter.filtering(parameters);
        modelAndView.addObject("offers", offers);
        modelAndView.setViewName("map");
        return modelAndView;
    }
}
