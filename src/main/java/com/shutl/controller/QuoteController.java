package com.shutl.controller;
import java.util.*;

import com.shutl.model.Quote;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class QuoteController {
    public static final int VEHICLE_BICYCLE = 10;
    public static final int VEHICLE_MOTORBIKE = 15;
    public static final int VEHICLE_PARCEL_CAR = 20;
    public static final int VEHICLE_SMALL_VAN = 30;
    public static final int VEHICLE_LARGE_VAN = 40;

    private static final Map<String, Integer> vehicleMarkup = new HashMap<String, Integer>();

    static {
        vehicleMarkup.put("bicycle", VEHICLE_BICYCLE);
        vehicleMarkup.put("motorbike", VEHICLE_MOTORBIKE);
        vehicleMarkup.put("parcel_car", VEHICLE_PARCEL_CAR);
        vehicleMarkup.put("small_van", VEHICLE_SMALL_VAN);
        vehicleMarkup.put("large_van", VEHICLE_LARGE_VAN);
    }
    
    @RequestMapping(value = "/quote", method = POST)
    public @ResponseBody Quote quote(@RequestBody Quote quote) {
        Long price = Math.abs((Long.valueOf(quote.getDeliveryPostcode(), 36) - Long.valueOf(quote.getPickupPostcode(), 36))/100000000);
        if (quote.getVehicle() != null) {
            Long markUpPrice = Long.valueOf(Math.round(price + (price * vehicleMarkup.get(quote.getVehicle()) / (double) 100)));
            return new Quote(quote.getPickupPostcode(), quote.getDeliveryPostcode(), quote.getVehicle(), markUpPrice);
        }

        return new Quote(quote.getPickupPostcode(), quote.getDeliveryPostcode(), price);
    }
}
