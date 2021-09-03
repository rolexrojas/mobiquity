package com.mobiquity.test.domain;



import java.util.List;

public class PackageTest {
    private int packageWeight;
    private List<PackageItemTest> allPackedItemList;

    public int getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(int packageWeight) {
        this.packageWeight = packageWeight;
    }

    public List<PackageItemTest> getAllPackedItemList() {
        return allPackedItemList;
    }

    public void setAllPackedItemList(List<PackageItemTest> allPackedItemList) {
        this.allPackedItemList = allPackedItemList;
    }
}
