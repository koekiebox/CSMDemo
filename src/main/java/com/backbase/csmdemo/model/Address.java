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
     * @param geoLocationParam
     */
    public Address(GeoLocation geoLocationParam) {
        super();

        this.setGeoLocation(geoLocationParam);
    }

    /**
     *
     * @param geoLocationParam
     * @param streetParam
     * @param houseNumberParam
     * @param postalCodeParam
     * @param cityParam
     */
    public Address(
            GeoLocation geoLocationParam,
            String streetParam,
            String houseNumberParam,
            String postalCodeParam,
            String cityParam) {

        this.setGeoLocation(geoLocationParam);
        this.setStreet(streetParam);
        this.setHouseNumber(houseNumberParam);
        this.setPostalCode(postalCodeParam);
        this.setCity(cityParam);
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
    @XmlElement(name = "houseNumber")
    public String getHouseNumber() {
        return this.houseNumber;
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "postalCode")
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
    @XmlElement(name = "geoLocation")
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

        if(!(toCompareToParam instanceof Address))
        {
            return false;
        }

        //First try and match according to GEO location...
        Address paramCasted = (Address)toCompareToParam;
        if(paramCasted.getGeoLocation() != null && this.getGeoLocation() != null)
        {
            return paramCasted.getGeoLocation().equals(this.getGeoLocation());
        }

        //If geo location is not set, we will fallback to street etc.
        if(paramCasted.getStreet() == null || this.getStreet() == null)
        {
            return false;
        }

        if(paramCasted.getHouseNumber() == null || this.getHouseNumber() == null)
        {
            return false;
        }

        if(paramCasted.getPostalCode() == null || this.getPostalCode() == null)
        {
            return false;
        }

        if(paramCasted.getCity() == null || this.getCity() == null)
        {
            return false;
        }

        //Match Street, House Number, Postal Code and City...
        return ((paramCasted.getStreet().equals(this.getStreet()) &&
                paramCasted.getHouseNumber().equals(this.getHouseNumber())) &&
                (paramCasted.getPostalCode().equals(this.getPostalCode()) &&
                        paramCasted.getCity().equals(this.getCity())));
    }
}
