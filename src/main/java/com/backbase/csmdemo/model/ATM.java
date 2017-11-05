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
@XmlRootElement(name = "atm")
public class ATM extends ABaseModel{

    //we are ignoring distance for now.
    //private int distance;
    
    private String type;
    private Address address;

    /**
     * The JSON possible mappings for {@code this} class.
     */
    private static class JSONMapping
    {
        public static final String ADDRESS = "address";
        public static final String TYPE = "type";
    }

    /**
     * Default.
     */
    public ATM() {
        super();
    }

    /**
     * 
     * @param typeParam Type
     */
    public ATM(String typeParam) {
        super();

        this.setType(typeParam);
    }

    /**
     * @param typeParam The type
     * @param addressParam Address
     */
    public ATM(String typeParam, Address addressParam) {
        super();

        this.setType(typeParam);
        this.setAddress(addressParam);
    }

    /**
     * Populate {@code this} using {@code jsonObjectParam}.
     *
     * @see GeoLocation#populateByJSON(JSONObject) Performs the populating.
     *
     * @param jsonObjectParam The JSON object use to populate.
     */
    public ATM(JSONObject jsonObjectParam) {
        super();

        this.populateByJSON(jsonObjectParam);
    }

    /**
     * @return Type
     */
    @XmlElement(name = "type")
    public String getType() {
        return this.type;
    }

    /**
     * @param typeParam Type
     */
    public void setType(String typeParam) {
        this.type = typeParam;
    }

    /**
     * @return Address
     */
    @XmlElement(name = "address")
    public Address getAddress() {
        return this.address;
    }

    /**
     * @param addressParam Address
     */
    public void setAddress(Address addressParam) {
        this.address = addressParam;
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

        //Type...
        this.setType(null);
        String type;
        if((type = jsonObjectParam.optString(JSONMapping.TYPE)) != null)
        {
            this.setType(type);
        }

        //Address...
        this.setAddress(null);
        JSONObject addressJsonObj;
        if((addressJsonObj =
                jsonObjectParam.optJSONObject(JSONMapping.ADDRESS)) != null)
        {
            this.setAddress(new Address(addressJsonObj));
        }
    }

    /**
     * Compares by Type and Address.
     *
     * @param toCompareToParam The object to compare to.
     * @return Whether the {@code toCompareToParam} is equal to {@code this}.
     */
    @Override
    public boolean equals(Object toCompareToParam) {

        if(!(toCompareToParam instanceof ATM))
        {
            return false;
        }

        ATM paramCasted = (ATM)toCompareToParam;

        if(paramCasted.getType() == null || this.getType() == null)
        {
            return false;
        }

        if(!paramCasted.getType().equals(this.getType()))
        {
            return false;
        }

        if(paramCasted.getAddress() == null || this.getAddress() == null)
        {
            return false;
        }
        
        return paramCasted.getAddress().equals(this.getAddress());
    }
}
