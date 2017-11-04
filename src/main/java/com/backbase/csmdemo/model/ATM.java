package com.backbase.csmdemo.model;

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
    private int distance;
    
    private String type;
    private Address address;

    /**
     * Default.
     */
    public ATM() {
        super();
    }

    /**
     * 
     * @param typeParam
     */
    public ATM(String typeParam) {
        super();

        this.setType(typeParam);
    }

    /**
     * 
     * @param typeParam
     * @param addressParam
     */
    public ATM(String typeParam, Address addressParam) {
        super();

        this.setType(typeParam);
        this.setAddress(addressParam);
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "type")
    public String getType() {
        return this.type;
    }

    /**
     * 
     * @param typeParam
     */
    public void setType(String typeParam) {
        this.type = typeParam;
    }

    /**
     *
     * @return
     */
    @XmlElement(name = "address")
    public Address getAddress() {
        return this.address;
    }

    /**
     *
     * @param addressParam
     */
    public void setAddress(Address addressParam) {
        this.address = addressParam;
    }

    /**
     * 
     * @param toCompareToParam
     * @return
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
