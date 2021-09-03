package com.mobiquity.packer;

import com.mobiquity.domain.Package;
import com.mobiquity.domain.PackageItem;
import com.mobiquity.exception.APIException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.mobiquity.types.CurrencyEnum.EUROS;

public class Packer {

  private static List<Package> packageList = new ArrayList<>();
  private static int counter = 0;

  private Packer() {
  }

  public static String pack(String filePath) throws APIException, IOException {
    System.out.println("PackMethod Body");
//C:\Users\elant\Documents\Personal\example_input.txt
    Path path = FileSystems.getDefault().getPath(filePath);
    BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
    BufferedReader br = new BufferedReader(reader);

    br.lines().forEach(s -> {
      System.out.println("Line found => " + s);


      try {

        String[] split = s.split(":");

        Package aPackage = new Package();
        aPackage.setPackageWeight(Integer.parseInt(split[0].trim()));
        packageList.add(aPackage);

        String[] res  =  split[1].replaceAll("[()]", " ").split(" ");
        List<PackageItem> packageItems = new ArrayList<>();

        for(String value: res){

          if(!value.equalsIgnoreCase("")){
            PackageItem packageItem = new PackageItem();
            String[] ans = value.split(",");
            packageItem.setIndex(Integer.parseInt(ans[0]));
            packageItem.setPackageItemWeight( Double.parseDouble(ans[1]));
            packageItem.setPackageItemCost(Double.parseDouble(ans[2].replaceFirst("\\p{Sc}", "")));
            packageItems.add(packageItem);
          }
        }

        packageList.get(counter).setAllPackedItemList(packageItems);
        counter++;

      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    return null;
  }
}
