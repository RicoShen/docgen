package com.youland.doc.model;

import com.youland.commons.model.AddressInfo;

import java.time.Instant;

public class Services {

    /**
     * Title Company
     */
    private String CompanyName;

    private AddressInfo mailingAddress;

    private String titleOrderNumber;

    private Instant titleEffectiveDate;

    private String titlePolicyExceptionItems;

    private WhoSign whoSign;

    /**
     * Escrow/Closing
     */

    private Boolean byTitleCompany = Boolean.TRUE;



}
