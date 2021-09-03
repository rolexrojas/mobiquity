package com.mobiquity.test;

import com.mobiquity.exception.APIException;
import com.mobiquity.test.component.PackageValidatorTest;
import com.mobiquity.test.domain.PackageItemTest;
import com.mobiquity.test.domain.PackageTest;
import org.apiguardian.api.API;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MobiquityAppTests {

    private PackageTest packageTest = new PackageTest();
    private List<PackageItemTest> packageItemTestList = new ArrayList<>();

    void validatePackage_checkConstraintsAreNotExceeded() throws APIException {
        PackageValidatorTest.isValidPackageItem(packageTest);
    }

    @Test
    void validateItemCost_whenMaxItemCostIsExceeded(){
        maxCapacityWhenItemPackageCostIsExceeded_dataSetUp();
        APIException apiException = assertThrows(APIException.class, this::validatePackage_checkConstraintsAreNotExceeded);
        assertEquals("Max cost of a package item is ≤ " + PackageValidatorTest.MAX_PACKAGE_ITEM_COST ,apiException.getMessage());
    }

    @Test
    void validateItemWeight_whenMaxItemWeightIsExceeded(){
        givenItemWeight_whenMaxItemWeightIsExceeded_dataSetUp();
        APIException apiException = assertThrows(APIException.class, this::validatePackage_checkConstraintsAreNotExceeded);
        assertEquals("Max weight of a package item is ≤ " + PackageValidatorTest.MAX_PACKAGE_WEIGHT ,apiException.getMessage());
    }

    @Test
    void validateItemPackage_whenPackageItemQuantityExceeded() {
        maxCapacityWhenItemQuantityIsExceeded_dataSetUp();
        APIException apiException = assertThrows(APIException.class, this::validatePackage_checkConstraintsAreNotExceeded);
        assertEquals("Max package items limite was exceeded, Max is: " + PackageValidatorTest.MAX_PACKAGE_ITEM_QUANTITY, apiException.getMessage());
    }

    @Test
    void validateMainPackage_whenPackageCapacityExceeded() {
        maxCapacityInMainPackageIsExceeded_dataSetUp();
        APIException apiException = assertThrows(APIException.class, this::validatePackage_checkConstraintsAreNotExceeded);
        assertEquals("Max weight that a package can take is ≤ " + PackageValidatorTest.MAX_PACKAGE_WEIGHT, apiException.getMessage());
    }


    @Test
    void validateItemPackage_whenItemPackageCapacityExceeded() {
        maxCapacityWhenItemPackageIsExceeded_dataSetUp();
        APIException apiException = assertThrows(APIException.class, this::validatePackage_checkConstraintsAreNotExceeded);
        assertEquals("Max weight of a package item is ≤ " + PackageValidatorTest.MAX_PACKAGE_WEIGHT, apiException.getMessage());
    }

    @Test
    void givenAbsoluteFilePath_readUTF8EncodedFile_loadFileWithBufferReader() throws IOException {
        String expectedData = new String("81 : (1,53.38,\u20ac45) (2,88.62,\u20ac98) (3,78.48,\u20ac3) (4,72.30,\u20ac76) (5,30.18,\u20ac9) (6,46.34,\u20ac48)".getBytes(), StandardCharsets.UTF_8);
        String filePath ="src/test/resources/example_input";
        Path path = FileSystems.getDefault().getPath(filePath);
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(reader);

        String currentLine = br.readLine();
        reader.close();

        assertEquals(expectedData, currentLine);
    }

    void readWrongFileFromWrongFilePath() throws APIException {
        String filePath = "src/test/resources_wrong/example_input_wrongFileName";
        Path path = FileSystems.getDefault().getPath(filePath);
        //load file utf8 for Buffer
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new APIException("Error processing file", e);
        }
        BufferedReader br = new BufferedReader(reader);
    }

    @Test
    void testWrongFilePathThrowsApiException(){
        APIException apiException = assertThrows(APIException.class, this::readWrongFileFromWrongFilePath);
        assertEquals("Error processing file", apiException.getMessage());
    }

    @Test
    void givenAbsoluteFilePath_readUTF8EncodedFile_parseFileToExpectedData() throws IOException {
        optimalDataSetUp();
        String filePath ="src/test/resources/example_input";
        Path path = FileSystems.getDefault().getPath(filePath);
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(reader);

        String[] split = br.readLine().split(":");

        List<PackageTest> packageTestList = new ArrayList<>();
        PackageTest filePackage = new PackageTest();
        filePackage.setPackageWeight(Integer.parseInt(split[0].trim()));
        packageTestList.add(filePackage);


        String[] res = split[1].replaceAll("[()]", " ").split(" ");
        List<PackageItemTest> packageItemsList = new ArrayList<>();
        //iterate over splitted line to create and assing each packageItem
        for (String value : res) {

            if (!value.equalsIgnoreCase("")) {
                //creates single package item
                PackageItemTest packageItem = new PackageItemTest();
                //split item by comma as a delimiter
                String[] ans = value.split(",");
                //assign next 3 splitted elements in fixed order
                packageItem.setIndex(Integer.parseInt(ans[0]));
                packageItem.setPackageItemWeight(Double.parseDouble(ans[1]));
                packageItem.setPackageItemCost(Double.parseDouble(ans[2].replaceFirst("\\p{Sc}", "")));
                //add package to packageItem List
                packageItemsList.add(packageItem);
            }
        }

        filePackage.setAllPackedItemList(packageItemsList);


        assertNotNull(packageTest);
        assertEquals(packageTest.getPackageWeight(), filePackage.getPackageWeight());

        for(int x = 0; x < filePackage.getAllPackedItemList().size(); x++) {
            assertEquals(packageTest.getAllPackedItemList().get(x).getIndex(), filePackage.getAllPackedItemList().get(x).getIndex());
            assertEquals(packageTest.getAllPackedItemList().get(x).getPackageItemCost(), filePackage.getAllPackedItemList().get(x).getPackageItemCost());
            assertEquals(packageTest.getAllPackedItemList().get(x).getPackageItemWeight(), filePackage.getAllPackedItemList().get(x).getPackageItemWeight());
        }
    }

    void maxCapacityInMainPackageIsExceeded_dataSetUp(){
        packageTest = new PackageTest();
        packageTest.setPackageWeight(101);

        PackageItemTest packageItemTest1 = new PackageItemTest();
        packageItemTest1.setIndex(1);
        packageItemTest1.setPackageItemCost(45D);
        packageItemTest1.setPackageItemWeight(53.38D);
        packageItemTestList.add(packageItemTest1);

        packageTest.setAllPackedItemList(packageItemTestList);
    }


    void maxCapacityWhenItemPackageIsExceeded_dataSetUp(){
        packageTest = new PackageTest();
        packageTest.setPackageWeight(100);

        PackageItemTest packageItemTest1 = new PackageItemTest();
        packageItemTest1.setIndex(1);
        packageItemTest1.setPackageItemCost(45D);
        packageItemTest1.setPackageItemWeight(103.38D);
        packageItemTestList.add(packageItemTest1);

        packageTest.setAllPackedItemList(packageItemTestList);
    }


    void maxCapacityWhenItemPackageCostIsExceeded_dataSetUp(){
        packageTest = new PackageTest();
        packageTest.setPackageWeight(100);

        PackageItemTest packageItemTest1 = new PackageItemTest();
        packageItemTest1.setIndex(1);
        packageItemTest1.setPackageItemCost(145.56D);
        packageItemTest1.setPackageItemWeight(88.38D);
        packageItemTestList.add(packageItemTest1);

        packageTest.setAllPackedItemList(packageItemTestList);
    }

    void givenItemWeight_whenMaxItemWeightIsExceeded_dataSetUp(){
        packageTest = new PackageTest();
        packageTest.setPackageWeight(88);

        PackageItemTest packageItemTest1 = new PackageItemTest();
        packageItemTest1.setIndex(1);
        packageItemTest1.setPackageItemCost(45D);
        packageItemTest1.setPackageItemWeight(106.38D);
        packageItemTestList.add(packageItemTest1);

        packageTest.setAllPackedItemList(packageItemTestList);
    }


    void maxCapacityWhenItemQuantityIsExceeded_dataSetUp(){
        packageTest = new PackageTest();
        packageTest.setPackageWeight(100);

        for(int x =0; x < PackageValidatorTest.MAX_PACKAGE_ITEM_QUANTITY + 1; x++){
            PackageItemTest packageItemTest1 = new PackageItemTest();
            packageItemTest1.setIndex(x);
            packageItemTest1.setPackageItemCost(x * 45D);
            packageItemTest1.setPackageItemWeight( x * 53.38D);

            packageItemTestList.add(packageItemTest1);
        }


        packageTest.setAllPackedItemList(packageItemTestList);
    }

    void optimalDataSetUp() {
        //Setup Test Main Package
        packageTest = new PackageTest();
        packageTest.setPackageWeight(81);

        PackageItemTest packageItemTest1 = new PackageItemTest();
        packageItemTest1.setIndex(1);
        packageItemTest1.setPackageItemCost(45D);
        packageItemTest1.setPackageItemWeight(53.38D);
        packageItemTestList.add(packageItemTest1);

        PackageItemTest packageItemTest2 = new PackageItemTest();
        packageItemTest2.setIndex(2);
        packageItemTest2.setPackageItemCost(98D);
        packageItemTest2.setPackageItemWeight(88.62D);
        packageItemTestList.add(packageItemTest2);

        PackageItemTest packageItemTest3 = new PackageItemTest();
        packageItemTest3.setIndex(3);
        packageItemTest3.setPackageItemCost(3D);
        packageItemTest3.setPackageItemWeight(78.48D);
        packageItemTestList.add(packageItemTest3);

        PackageItemTest packageItemTest4 = new PackageItemTest();
        packageItemTest4.setIndex(4);
        packageItemTest4.setPackageItemCost(76D);
        packageItemTest4.setPackageItemWeight(72.30D);
        packageItemTestList.add(packageItemTest4);

        PackageItemTest packageItemTest5 = new PackageItemTest();
        packageItemTest5.setIndex(5);
        packageItemTest5.setPackageItemCost(9D);
        packageItemTest5.setPackageItemWeight(30.18D);
        packageItemTestList.add(packageItemTest5);

        PackageItemTest packageItemTest6 = new PackageItemTest();
        packageItemTest6.setIndex(6);
        packageItemTest6.setPackageItemCost(48D);
        packageItemTest6.setPackageItemWeight(46.34D);
        packageItemTestList.add(packageItemTest6);

        packageTest.setAllPackedItemList(packageItemTestList);
    }
}
