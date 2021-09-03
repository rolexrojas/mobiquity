package com.mobiquity.component;

import com.mobiquity.domain.Package;
import com.mobiquity.exception.APIException;

public class PackageValidator {

    private static int MAX_PACKAGE_WEIGHT = 100;
    private static int MAX_PACKAGE_ITEM_QUANTITY = 15;
    private static int MAX_PACKAGE_ITEM_COST = 100;

    public static void isValidPackageItem(Package aPackage) throws APIException {
        if(aPackage.getPackageWeight() > MAX_PACKAGE_WEIGHT){
            throw new APIException("Max weight that a package can take is ≤ " + MAX_PACKAGE_WEIGHT);
        }

        if(aPackage.getAllPackedItemList().size() > MAX_PACKAGE_ITEM_QUANTITY){
            throw new APIException("Max package items limite was exceeded, Max is: " + MAX_PACKAGE_ITEM_QUANTITY);
        }

        for(int x =0; x < aPackage.getAllPackedItemList().size(); x++){
            if(aPackage.getAllPackedItemList().get(x).getPackageItemWeight() >  MAX_PACKAGE_WEIGHT){
                throw new APIException("Max weight of a package item is ≤ " + MAX_PACKAGE_WEIGHT);
            }

            if(aPackage.getAllPackedItemList().get(x).getPackageItemCost() > MAX_PACKAGE_ITEM_COST){
                throw new APIException("Max cost of a package item is ≤ " + MAX_PACKAGE_ITEM_COST);
            }
        }
    }
}
