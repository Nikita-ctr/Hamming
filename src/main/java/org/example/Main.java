package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        //after removing bits: "0111010001100101" "0111001101110100" - correct
        Hamming hamming = new Hamming();
        String message = "test";

        String[] encrypted = hamming.encrypt(message);
        System.out.println("Encrypted string: " + Arrays.toString(encrypted));

        String[] error = hamming.changeBit(encrypted, 6);
        System.out.println("Decrypted string with error: " + Arrays.toString(error));

        System.out.println(Arrays.toString(Hamming.getControlBits(encrypted[0])));
        System.out.println(Arrays.toString(Hamming.getControlBits(error[0])));


//        String[] fixed = hamming.fixError(error);
//        System.out.println("Fixed decrypted string: " + Arrays.toString(fixed));
//
//        String dec = hamming.decrypt(fixed);
//        System.out.println("Decrypted string: " + dec);


    }
}