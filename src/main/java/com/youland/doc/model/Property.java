package com.youland.doc.model;

import com.youland.commons.model.AddressInfo;

public class Property extends AddressInfo {

    /**
     *
     */

    private String propertyType;

    /**
     * Remark: full property information
     * Example: 37 Country Club Drive, Hayward, CA 94542
     * Placeholders: PROPERTY_ADDRESS
     */
    private String propertyAddress;

    private String apn;

    private String county;

    private String legalDescription;

}
