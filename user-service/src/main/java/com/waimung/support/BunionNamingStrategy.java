package com.waimung.support;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.spi.MetadataBuildingContext;

public class BunionNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    @Override
    protected Identifier toIdentifier(String stringForm,MetadataBuildingContext buildingContext) {
        return super.toIdentifier("wm_" + stringForm,buildingContext);
    }
}
