package com.backbase.csmdemo.model;

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
     * Default.
     */
    public GeoLocation() {
        super();
    }

    /**
     * 
     * @param latitudeParam
     * @param longitudeParam
     */
    public GeoLocation(double latitudeParam, double longitudeParam) {
        this.setLatitude(latitudeParam);
        this.setLongitude(longitudeParam);
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "latitude")
    public double getLatitude() {
        return this.latitude;
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "longitude")
    public double getLongitude() {
        return this.longitude;
    }

    /**
     *
     * @param latitudeParam
     */
    public void setLatitude(double latitudeParam) {
        this.latitude = latitudeParam;
    }

    /**
     * 
     * @param latitudeAsStringParam
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
     * 
     * @param longitudeParam
     */
    public void setLongitude(double longitudeParam) {
        this.longitude = longitudeParam;
    }

    /**
     * 
     * @param longitudeAsStringParam
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
     *
     * @param toCompareToParam
     * @return
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
