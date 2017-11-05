package com.backbase.csmdemo.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Base class for all application shared POJO's.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public abstract class ABaseModel implements Serializable{

    /**
     * Populate the local object from {@code jsonObjectParam}.
     *
     * @param jsonObjectParam The json object used to populate with.
     */
    public abstract void populateByJSON(JSONObject jsonObjectParam);
}
