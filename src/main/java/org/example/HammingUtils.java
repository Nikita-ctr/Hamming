package org.example;

public class HammingUtils {

    public static String[] convertStringToBinary(String input) {

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))
                            .replaceAll(" ", "0")
            );
        }

        return result.toString().split("(?<=\\G.{16})");

    }

    public static String[] splitIntoEightBits(String binary) {
        String eightBits = binary.replaceAll("(.{8})", "$1 ");
        return eightBits.split(" ");
    }
}
