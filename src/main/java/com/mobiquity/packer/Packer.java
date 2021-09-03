package com.mobiquity.packer;

import com.mobiquity.domain.Package;
import com.mobiquity.domain.PackageItem;
import com.mobiquity.util.Utility;
import com.mobiquity.exception.APIException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Packer {

    private static List<Package> packageList = new ArrayList<>();
    private static int counter = 0;

    private Packer() {
    }

  /**
   * The Packer class
   * @Param filePath location of a file to be loaded into static pack method
   * @return String displaying the index of items packaged into main pack
   */
    public static String pack(String filePath) throws APIException {

        StringBuilder responseBuilder = new StringBuilder();
        try {
            //setup file path
            Path path = FileSystems.getDefault().getPath(filePath);
            //load file utf8 for Buffer
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(reader);

            //read line by line
            br.lines().forEach(s -> {
                //Split semicolon to isolate package weight property
                String[] split = s.split(":");

                //create and assign new package
                Package aPackage = new Package();
                aPackage.setPackageWeight(Integer.parseInt(split[0].trim()));
                packageList.add(aPackage);

                //remove parenthesis and replace with withespace for a split
                String[] res = split[1].replaceAll("[()]", " ").split(" ");

                //block packageItem container initialization
                List<PackageItem> packageItems = new ArrayList<>();
                //iterate over splitted line to create and assing each packageItem
                for (String value : res) {

                    if (!value.equalsIgnoreCase("")) {
                       //creates single package item
                        PackageItem packageItem = new PackageItem();
                        //split item by comma as a delimiter
                        String[] ans = value.split(",");
                        //assign next 3 splitted elements in fixed order
                        packageItem.setIndex(Integer.parseInt(ans[0]));
                        packageItem.setPackageItemWeight(Double.parseDouble(ans[1]));
                        packageItem.setPackageItemCost(Double.parseDouble(ans[2].replaceFirst("\\p{Sc}", "")));
                        //add package to packageItem List
                        packageItems.add(packageItem);
                    }
                }

                //set packageItemList inside of packed initial object
                packageList.get(counter).setAllPackedItemList(packageItems);
                //increment counter for next iteration
                counter++;
            });

        } catch (Exception e) {
            //catch and rethrow expected exception with cause
            throw new APIException("Error processing file", e);
        }

        //iterates over individual package list and append result to StringBuilder
        for (Package aPackage : packageList) {
            responseBuilder.append(combineDynamicValuesForMostExpensiveCombo(aPackage.getAllPackedItemList(), (double) aPackage.getPackageWeight()));
        }

        return responseBuilder.toString();
    }

  /**
   *
   * @param allItemList contains full list of items that are being evaluated in the matching line iteration
   * @param maxAvailableSpace max space for the main package, also package capacity
   * @return displaying the index of items packaged into main pack
   */
    private static String combineDynamicValuesForMostExpensiveCombo(List<PackageItem> allItemList, double maxAvailableSpace) {

        Double[] weights = new Double[allItemList.size()];
        Double[] values = new Double[allItemList.size()];

        IntStream.range(0, allItemList.size()).forEach(x -> {
            weights[x] = allItemList.get(x).getPackageItemWeight();
            values[x] = allItemList.get(x).getPackageItemCost();
        });

        return Utility.knapSackAlgCalculation(weights, values, values.length, (int) maxAvailableSpace);
    }

}
