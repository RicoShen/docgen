package com.youland.doc.model;

import com.youland.commons.model.AddressInfo;
import com.youland.commons.model.PersonalFullInfo;

import java.math.BigDecimal;
import java.time.Instant;

public class Services {

    private TitleCompany titleCompany;
    private EscrowOrClosing escrowOrClosing;
    private EscrowDeposits escrowDeposits;



    public static class TitleCompany{

        /**
         * Example: North American Title Company
         * Remark: Title Company
         * Placeholders: TITLE_COMPANY_NAME
         */
        private String CompanyName;

        /**
         * Address:
         * Example: 689 Portola Drive
         * Remark: address of Title Company Mailing Address
         * Placeholders: TITLE_COMPANY_MAILING_ADDRESS
         *
         * City:
         * Example: San Francisco
         * Remark: city of Title Company Mailing Address
         * Placeholders: TITLE_COMPANY_MAILING_CITY
         *
         * State:
         * Example: CA
         * Remark: state of Title Company Mailing Address
         * Placeholders: TITLE_COMPANY_MAILING_SHORT_SATE
         * Placeholders: TITLE_COMPANY_MAILING_SATE
         *
         * PostCode:
         * Example: 94127
         * Remark: ZIP Code of Title Company Mailing Address
         * Placeholders: TITLE_COMPANY_MAILING_POSTCODE
         *
         * City,State ZIP Code
         * Example: San Francisco, CA 94127
         * Placeholders: TITLE_COMPANY_CITY_STATE_ZIPCODE
         */

        private AddressInfo mailingAddress;

        /**
         * Example: 56606-21-04011
         * Remark: Title Order Number of Title Company
         * Placeholders: TITLE_ORDER_NO
         */
        private String titleOrderNumber;

        /**
         * Example: July 12, 2021
         * Remark: Title Effective Date of Title Company
         * Placeholders: TITLE_EFFECTIVE_NO
         */
        private Instant titleEffectiveDate;

        private String titlePolicyExceptionItems;

        /**
         *
         * fistName:
         *  Example: Connie
         *  Remark: Who is signing the closing instructions on behalf of the title company?
         *  Placeholders: SIGNING_FIRST_NAME
         *
         * lastName:
         *   Example: Choy
         *   Remark: Who is signing the closing instructions on behalf of the title company?
         *   Placeholders: SIGNING_LAST_NAME
         *
         * userName:
         *  Example: Connie Choy
         *  Remark: Who is signing the closing instructions on behalf of the title company?
         *  Placeholders: SIGNING_USER_NAME
         *
         * email:
         *  Example: cmchoy@nat.com
         *  Remark: Who is signing the closing instructions on behalf of the title company?
         *  Placeholders: SIGNING_USER_EMAIL
         * phone:
         *  Example: 4157533003
         *  Remark: Who is signing the closing instructions on behalf of the title company?
         *  Placeholders: SIGNING_USER_PHONE
         */
        private WhoSign whoSign;

    }

    public static class EscrowOrClosing extends PersonalFullInfo {

        private Boolean managingByTitleCompany = Boolean.TRUE;

        private AddressInfo mailingAddress;

        /**
         * Example: 56606-21-04011
         * Remark: Escrow Number
         * Placeholders: ESCROW_NUMBER
         */
        private String escrowNumber;

    }

    public static class EscrowDeposits{

        private BigDecimal yearlyTaxes;
        private BigDecimal monthlyTaxes;
        private BigDecimal yearlyInsurance;
        private BigDecimal monthlyInsurance;
    }


    public static class Servicer{

        /**
         * Enum: FCI Lender Services, Inc. , Fay Servicing LLC , Other
         *
         */
        private String servicerType;

        private String phone;

        private String email;

        private AddressInfo address;
    }

}
