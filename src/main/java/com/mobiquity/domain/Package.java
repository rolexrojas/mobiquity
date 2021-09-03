package com.mobiquity.domain;

import java.util.List;

/**
 * Package class
 * Holds max capacity that package can take
 * Holds a list of packageItems that may or may not fit the package
 */
public class Package {

    private int packageWeight;
    private List<PackageItem> allPackedItemList;

    public int getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(int packageWeight) {
        this.packageWeight = packageWeight;
    }

    public List<PackageItem> getAllPackedItemList() {
        return allPackedItemList;
    }

    public void setAllPackedItemList(List<PackageItem> allPackedItemList) {
        this.allPackedItemList = allPackedItemList;
    }
}
