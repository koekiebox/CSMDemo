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
     * Default.
     * 
     * @param typeParam
     */
    public ATM(String typeParam) {
        super();

        this.setType(typeParam);
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

        //TODO compelte....
    }
}
