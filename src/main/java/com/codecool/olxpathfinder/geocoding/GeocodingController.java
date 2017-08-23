package com.codecool.olxpathfinder.geocoding;

import com.codecool.olxpathfinder.model.Offer;
import com.google.maps.errors.ApiException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GeocodingController {

    @Autowired
    GeocodingFilter geocodingFilter;

    @Autowired
    public GeocodingController() {
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ModelAndView searchPath(ModelAndView modelAndView) {
        modelAndView.setViewName("map");
        return modelAndView;
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ModelAndView findPath(ModelAndView modelAndView)
        throws InterruptedException, ApiException, IOException {
        modelAndView.setViewName("map");
        return modelAndView;
    }

    @RequestMapping(value = "/getAds", method = RequestMethod.POST)
    public ModelAndView filterCriteria(ModelAndView modelAndView, @RequestBody String streetsJson,
        HttpServletRequest request
    ) throws InterruptedException, ApiException, IOException, JSONException {
        Map<String, String[]> parameters = request.getParameterMap();
        List<Offer> offers = geocodingFilter.filtering(streetsJson, parameters);
        modelAndView.addObject("offers", offers);
        modelAndView.setViewName("map");
        return modelAndView;
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView streetsFiltering(ModelAndView modelAndView, @RequestBody String streetsJson)
        throws InterruptedException, ApiException, JSONException, IOException {
        List<Offer> offersInSearchedArea = geocodingFilter.filtering(streetsJson);
        modelAndView.addObject("offers", offersInSearchedArea);
        modelAndView.setViewName("map");
        return modelAndView;
    }

}
