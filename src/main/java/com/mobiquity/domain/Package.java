package com.mobiquity.domain;

import java.util.List;

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
