package com.youland.doc.model;

import java.math.BigDecimal;
import java.util.List;

public class Fees {

    private BigDecimal DocumentPreparationFee;

    private List<LenderFee> lenderFees;

    private List<BrokerFee> brokerFees;
}
