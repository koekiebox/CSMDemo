package com.backbase.csmdemo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The POJO for ATM type objects.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
@XmlRootElement(name = "address")
public class Address extends ABaseModel{

    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;

    private GeoLocation geoLocation;

    /**
     * Default.
     */
    public Address() {
        super();
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "street")
    public String getStreet() {
        return this.street;
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "house_number")
    public String getHouseNumber() {
        return this.houseNumber;
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "postal_code")
    public String getPostalCode() {
        return this.postalCode;
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "city")
    public String getCity() {
        return this.city;
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "geo_location")
    public GeoLocation getGeoLocation() {
        return this.geoLocation;
    }

    /**
     *
     * @param streetParam
     */
    public void setStreet(String streetParam) {
        this.street = streetParam;
    }

    /**
     *
     * @param houseNumberParam
     */
    public void setHouseNumber(String houseNumberParam) {
        this.houseNumber = houseNumberParam;
    }

    /**
     *
     * @param postalCodeParam
     */
    public void setPostalCode(String postalCodeParam) {
        this.postalCode = postalCodeParam;
    }

    /**
     *
     * @param cityParam
     */
    public void setCity(String cityParam) {
        this.city = cityParam;
    }

    /**
     * 
     * @param geoLocationParam
     */
    public void setGeoLocation(GeoLocation geoLocationParam) {
        this.geoLocation = geoLocationParam;
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
