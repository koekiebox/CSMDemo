package com.backbase.csmdemo.model;

import org.json.JSONObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The POJO for Geo Location.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
@XmlRootElement(name = "geoLocation")
public class GeoLocation extends ABaseModel{

    private double latitude;
    private double longitude;

    /**
     * The JSON possible mappings for {@code this} class.
     */
    private static class JSONMapping
    {
        public static final String LAT = "lat";
        public static final String LNG = "lng";
    }
    
    /**
     * Default.
     */
    public GeoLocation() {
        super();
    }

    /**
     * @param latitudeParam Latitude
     * @param longitudeParam Longitude
     */
    public GeoLocation(double latitudeParam, double longitudeParam) {
        this.setLatitude(latitudeParam);
        this.setLongitude(longitudeParam);
    }

    /**
     * Populate {@code this} using {@code jsonObjectParam}.
     *
     * @see GeoLocation#populateByJSON(JSONObject) Performs the populating.
     *
     * @param jsonObjectParam The JSON object use to populate.
     */
    public GeoLocation(JSONObject jsonObjectParam) {
        super();

        this.populateByJSON(jsonObjectParam);
    }

    /**
     * @return Latitude
     */
    @XmlElement(name = "latitude")
    public double getLatitude() {
        return this.latitude;
    }

    /**
     *
     * @return Longitude
     */
    @XmlElement(name = "longitude")
    public double getLongitude() {
        return this.longitude;
    }

    /**
     * @param latitudeParam Latitude
     */
    public void setLatitude(double latitudeParam) {
        this.latitude = latitudeParam;
    }

    /**
     * If we are unable to convert {@code latitudeAsStringParam} to a
     * double, the value will be set to 0.
     *
     * @param latitudeAsStringParam Latitude
     */
    public void setLatitude(String latitudeAsStringParam) {

        if(latitudeAsStringParam == null || latitudeAsStringParam.trim().isEmpty())
        {
            this.latitude = 0;
            return;
        }

        try
        {
            this.latitude = Double.parseDouble(latitudeAsStringParam.trim());
        }
        catch (NumberFormatException nfe)
        {
            this.latitude = 0;
        }
    }

    /**
     * @param longitudeParam  Longitude
     */
    public void setLongitude(double longitudeParam) {
        this.longitude = longitudeParam;
    }

    /**
     * @param longitudeAsStringParam Latitude
     */
    public void setLongitude(String longitudeAsStringParam) {

        if(longitudeAsStringParam == null || longitudeAsStringParam.trim().isEmpty())
        {
            this.longitude = 0;
            return;
        }

        try
        {
            this.longitude = Double.parseDouble(longitudeAsStringParam.trim());
        }
        catch (NumberFormatException nfe)
        {
            this.longitude = 0;
        }
    }

    /**
     * @param jsonObjectParam The json object used to populate with.
     */
    @Override
    public void populateByJSON(JSONObject jsonObjectParam) {

        if(jsonObjectParam == null)
        {
            //No purpose here.
            return;
        }

        //Latitude...
        this.setLatitude(0);
        String lclLatString;
        double lclLatDbl;
        if((lclLatString = jsonObjectParam.optString(JSONMapping.LAT)) != null &&
                !lclLatString.isEmpty())
        {
            this.setLatitude(lclLatString);
        }
        else if((lclLatDbl = jsonObjectParam.optDouble(JSONMapping.LAT,0)) != 0)
        {
            this.setLatitude(lclLatDbl);
        }

        //Longitude...
        this.setLongitude(0);
        String lclLongString;
        double lclLongDbl;
        if((lclLongString = jsonObjectParam.optString(JSONMapping.LNG)) != null &&
                !lclLongString.isEmpty())
        {
            this.setLongitude(lclLongString);
        }
        else if((lclLongDbl = jsonObjectParam.optDouble(JSONMapping.LNG,0)) != 0)
        {
            this.setLongitude(lclLongDbl);
        }
    }

    /**
     * Compares by latitude and longitude.
     *
     * @param toCompareToParam The object to compare to.
     * @return Whether the {@code toCompareToParam} is equal to {@code this}.
     */
    @Override
    public boolean equals(Object toCompareToParam) {

        if(!(toCompareToParam instanceof GeoLocation))
        {
            return false;
        }

        GeoLocation paramCasted = (GeoLocation)toCompareToParam;

        return ((paramCasted.getLatitude() == this.getLatitude()) &&
                (paramCasted.getLongitude() == this.getLongitude()));
    }
}
