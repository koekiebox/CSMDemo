package com.backbase.csmdemo.dao.impl.ing;

import com.backbase.csmdemo.dao.ABaseClientWS;
import com.backbase.csmdemo.dao.IATMDAO;
import com.backbase.csmdemo.dao.exception.HttpClientException;
import com.backbase.csmdemo.model.ATM;
import com.backbase.csmdemo.model.Address;
import com.backbase.csmdemo.model.GeoLocation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access object for retrieving the ATM
 * listing for ING financial institution.
 *
 * @author jasonbruwer on 11/3/17.
 * @since 1.0
 */
public class IngATMDAO implements IATMDAO {

    private static String TYPE = "ING";

    //This needs to be a property...
    private static String ENDPOINT = "https://www.ing.nl";

    /**
     * Consume the https://www.ing.nl/api/locator/atms/ endpoint to
     * get a list of ATMs.
     *
     * @return Listing of ATMs from ING.
     *
     * @throws HttpClientException If there is any problem fetching the ATM's.
     */
    @Override
    public List<ATM> getListOfAllATMs() throws HttpClientException{

        //Get the list of ATMs...
        JSONArray arrayOfDataObjects = null;
        try(IngAtmWebServiceClient ingAtmClient = new IngAtmWebServiceClient())
        {
            arrayOfDataObjects = ingAtmClient.getJsonArray("/api/locator/atms/");
        }

        List<ATM> returnVal = new ArrayList<>();

        if(arrayOfDataObjects == null)
        {
            return returnVal;
        }

        for(int index = 0;index <
                arrayOfDataObjects.length();index++)
        {
            Object objAtIndex = arrayOfDataObjects.get(index);

            if(!(objAtIndex instanceof JSONObject))
            {
                continue;
            }

            JSONObject jsonAtm = (JSONObject)objAtIndex;
            JSONObject jsonAddress = jsonAtm.getJSONObject(JSONMappingOfIngATM.ADDRESS);

            if(jsonAddress == null)
            {
                continue;
            }

            String street = jsonAddress.getString(JSONMappingOfIngATM.STREET);
            String houseNumber = jsonAddress.getString(JSONMappingOfIngATM.HOUSE_NUMBER);
            String postalCode = jsonAddress.getString(JSONMappingOfIngATM.POSTAL_CODE);
            String city = jsonAddress.getString(JSONMappingOfIngATM.CITY);

            JSONObject geoLocObj = jsonAddress.getJSONObject(JSONMappingOfIngATM.GEO_LOCATION);

            GeoLocation geoLocation = null;
            if(geoLocObj != null)
            {
                geoLocation = new GeoLocation();
                
                geoLocation.setLatitude(geoLocObj.getString(JSONMappingOfIngATM.LAT));
                geoLocation.setLongitude(geoLocObj.getString(JSONMappingOfIngATM.LNG));
            }

            ATM atmToAdd = new ATM(TYPE);

            Address address = new Address();

            address.setCity(city);
            address.setHouseNumber(houseNumber);
            address.setStreet(street);
            address.setPostalCode(postalCode);
            address.setGeoLocation(geoLocation);
            
            atmToAdd.setAddress(address);

            returnVal.add(atmToAdd);
        }

        return returnVal;
    }

    /**
     * 
     */
    private static class JSONMappingOfIngATM
    {
        public static final String ADDRESS = "address";
        public static final String STREET = "street";
        public static final String HOUSE_NUMBER = "housenumber";
        public static final String POSTAL_CODE = "postalcode";
        public static final String CITY = "city";

        public static final String GEO_LOCATION = "geoLocation";
        public static final String LAT = "lat";
        public static final String LNG = "lng";

        public static final String DISTANCE = "distance";
        public static final String TYPE = "type";
    }

    /**
     * 
     */
    private static class IngAtmWebServiceClient extends ABaseClientWS implements AutoCloseable
    {
        /**
         * 
         */
        public IngAtmWebServiceClient() {
            super(ENDPOINT);
        }

        /**
         * Close the stream.
         */
        @Override
        public void close() {
            this.closeAndClean();
        }
    }
}
