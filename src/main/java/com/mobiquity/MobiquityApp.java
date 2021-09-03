package com.mobiquity;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.Packer;

public class MobiquityApp {
    public static void main(String[] args) throws APIException {
        Packer.pack("C:\\Users\\elant\\Documents\\Personal\\example_input.txt");
    }
}
