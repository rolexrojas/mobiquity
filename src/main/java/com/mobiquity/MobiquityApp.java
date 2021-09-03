package com.mobiquity;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.Packer;

import java.io.IOException;

public class MobiquityApp {
    public static void main(String[] args) throws APIException {
        System.out.println(Packer.pack("C:\\Users\\elant\\Documents\\Personal\\example_input.txt"));
    }
}
