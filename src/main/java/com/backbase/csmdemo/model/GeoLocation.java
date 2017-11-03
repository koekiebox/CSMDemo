package com.backbase.csmdemo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The POJO for Geo Location.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
@XmlRootElement(name = "geo_location")
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
     * @param longitudeParam
     */
    public void setLongitude(double longitudeParam) {
        this.longitude = longitudeParam;
    }

    /**
     *
     * @param toCompareToParam
     * @return
     */
    @Override
    public boolean equals(Object toCompareToParam) {

        //TODO compelte....
    }
}
