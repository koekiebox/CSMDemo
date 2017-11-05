package com.backbase.csmdemo.model;

import org.json.JSONObject;

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
     * The JSON possible mappings for {@code this} class.
     */
    private static class JSONMapping
    {
        public static final String STREET = "street";
        public static final String HOUSE_NUMBER = "housenumber";
        public static final String HOUSE_NUMBER_2 = "houseNumber";
        
        public static final String POSTAL_CODE = "postalcode";
        public static final String POSTAL_CODE_2 = "postalCode";
        public static final String CITY = "city";
        public static final String GEO_LOCATION = "geoLocation";
    }

    /**
     * Default.
     */
    public Address() {
        super();
    }

    /**
     * 
     * @param geoLocationParam New instance with geo location.
     */
    public Address(GeoLocation geoLocationParam) {
        super();

        this.setGeoLocation(geoLocationParam);
    }

    /**
     * @param geoLocationParam Location.
     * @param streetParam Street.
     * @param houseNumberParam House Number.
     * @param postalCodeParam Postal Code.
     * @param cityParam City.
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
     * Populate {@code this} using {@code jsonObjectParam}.
     *
     * @see GeoLocation#populateByJSON(JSONObject) Performs the populating.
     *
     * @param jsonObjectParam The JSON object use to populate.
     */
    public Address(JSONObject jsonObjectParam) {
        super();

        this.populateByJSON(jsonObjectParam);
    }

    /**
     * @return Street
     */
    @XmlElement(name = "street")
    public String getStreet() {
        return this.street;
    }

    /**
     *
     * @return House Number
     */
    @XmlElement(name = "houseNumber")
    public String getHouseNumber() {
        return this.houseNumber;
    }

    /**
     *
     * @return Postal Code
     */
    @XmlElement(name = "postalCode")
    public String getPostalCode() {
        return this.postalCode;
    }

    /**
     *
     * @return City
     */
    @XmlElement(name = "city")
    public String getCity() {
        return this.city;
    }

    /**
     *
     * @return Geo Location
     */
    @XmlElement(name = "geoLocation")
    public GeoLocation getGeoLocation() {
        return this.geoLocation;
    }

    /**
     *
     * @param streetParam Street
     */
    public void setStreet(String streetParam) {
        this.street = streetParam;
    }

    /**
     *
     * @param houseNumberParam House Number
     */
    public void setHouseNumber(String houseNumberParam) {
        this.houseNumber = houseNumberParam;
    }

    /**
     *
     * @param postalCodeParam Postal Code
     */
    public void setPostalCode(String postalCodeParam) {
        this.postalCode = postalCodeParam;
    }

    /**
     *
     * @param cityParam City
     */
    public void setCity(String cityParam) {
        this.city = cityParam;
    }

    /**
     * 
     * @param geoLocationParam Geo Location
     */
    public void setGeoLocation(GeoLocation geoLocationParam) {
        this.geoLocation = geoLocationParam;
    }

    /**
     *
     * @param jsonObjectParam The json object used to populate with.
     */
    @Override
    public void populateByJSON(JSONObject jsonObjectParam) {

        if(jsonObjectParam == null)
        {
            //No purpose here.
            return;
        }

        //City...
        this.setCity(null);
        String city = null;
        if((city = jsonObjectParam.optString(JSONMapping.CITY)) != null)
        {
            this.setCity(city);
        }

        //House Number...
        this.setHouseNumber(null);
        String houseNumber;
        if((houseNumber = jsonObjectParam.optString(JSONMapping.HOUSE_NUMBER)) != null &&
                !houseNumber.isEmpty())
        {
            this.setHouseNumber(houseNumber);
        }
        else if((houseNumber = jsonObjectParam.optString(JSONMapping.HOUSE_NUMBER_2)) != null)
        {
            this.setHouseNumber(houseNumber);
        }

        //Geo Location...
        this.setGeoLocation(null);
        JSONObject geoObj = null;
        if((geoObj = jsonObjectParam.optJSONObject(JSONMapping.GEO_LOCATION)) != null)
        {
            this.setGeoLocation(new GeoLocation(geoObj));
        }

        //Postal Code...
        this.setPostalCode(null);
        String postalCode = null;
        if((postalCode = jsonObjectParam.optString(JSONMapping.POSTAL_CODE)) != null &&
                !postalCode.isEmpty())
        {
            this.setPostalCode(postalCode);
        }
        else if((postalCode = jsonObjectParam.optString(JSONMapping.POSTAL_CODE_2)) != null)
        {
            this.setPostalCode(postalCode);
        }

        //Street...
        this.setStreet(null);
        String street = null;
        if((street = jsonObjectParam.optString(JSONMapping.STREET)) != null)
        {
            this.setStreet(street);
        }
    }

    /**
     * Compares by Geo Location, if Geo location is not set on both
     * objects, the street, number etc will be used.
     *
     * @param toCompareToParam The object to compare with.
     * @return Whether the {@code toCompareToParam} is equal to {@code this}.
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
