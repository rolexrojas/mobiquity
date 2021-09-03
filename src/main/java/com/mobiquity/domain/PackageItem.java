package com.mobiquity.domain;

public class PackageItem {

    private int index;
    private Double packageItemWeight;
    private Double packageItemCost;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Double getPackageItemWeight() {
        return packageItemWeight;
    }

    public void setPackageItemWeight(double packageItemWeight) {
        this.packageItemWeight = packageItemWeight;
    }

    public Double getPackageItemCost() {
        return packageItemCost;
    }

    public void setPackageItemCost(double packageItemCost) {
        this.packageItemCost = packageItemCost;
    }
}
