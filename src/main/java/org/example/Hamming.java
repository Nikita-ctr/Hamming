package org.example;

/**
 * Convert message into binary representation - done
 * Define our binary representation into 16-bits words (play -> "pl" and "ay") - done
 * encrypt each part of message separately -done
 * insert control bits into binary representation in positions with numbers equal to powers of two -done
 * calculate and insert control bits - done
 * decrypt message and check errors
 */

public class Hamming {


    private static final int CONTROL_AMOUNT = 5;

    private static final int[] POSITIONS = {1, 2, 4, 8, 16};

    public String[] encrypt(String message) {
        String[] converted = HammingUtils.convertStringToBinary(message);
        return insertControl(converted);

    }


    public String decrypt(String[] encrypted) {
        StringBuilder decryptedBuilder = new StringBuilder();
        for (String binary : encrypted) {
            String binaryWithoutControl = removeControl(binary);
            String[] eightBitsArray = HammingUtils.splitIntoEightBits(binaryWithoutControl);
            for (String eightBit : eightBitsArray) {
                int asciiValue = Integer.parseInt(eightBit, 2);
                char character = (char) asciiValue;
                decryptedBuilder.append(character);
            }
        }
        return decryptedBuilder.toString();
    }


    private String removeControl(String binary) {
        StringBuilder binaryBuilder = new StringBuilder(binary);
        for (int i = POSITIONS.length - 1; i >= 0; i--) {
            int pos = POSITIONS[i];
            binaryBuilder.deleteCharAt(pos - 1);
        }
        return binaryBuilder.toString();
    }

    //010011100100011000101
    public String[] changeBit(String[] encrypted, int bitIndex) {

        String[] newStr = new String[encrypted.length];

        for (int i = 0; i < encrypted.length; i++) {
            // flip the bit at the specified index
            newStr[i] = flipBit(encrypted[i], bitIndex);
            // recalculate the control bits for the modified string
            newStr[i] = calculateControl(newStr[i]);
        }

        return newStr;
    }

    private String flipBit(String binary, int bitIndex) {
        StringBuilder binaryBuilder = new StringBuilder(binary);
        char bit = binaryBuilder.charAt(bitIndex);
        binaryBuilder.setCharAt(bitIndex, bit == '0' ? '1' : '0');
        return binaryBuilder.toString();
    }


    private String[] insertControl(String[] binary) {

        StringBuilder[] stringBuilders = new StringBuilder[binary.length + CONTROL_AMOUNT];

        for (int i = 0; i < binary.length; i++) {
            stringBuilders[i] = new StringBuilder(binary[i]);

            for (int j = 0; j < CONTROL_AMOUNT; j++) {
                int pos = POSITIONS[j];
                stringBuilders[i].insert(pos - 1, '0');
            }
        }

        String[] result = new String[binary.length];
        for (int i = 0; i < binary.length; i++) {
            result[i] = calculateControl(stringBuilders[i].toString());
        }

        return result;
    }

    private String calculateControl(String binary) {

        int result;

        char[] arr = binary.toCharArray();

        for (int i = 0; i < POSITIONS.length; i++) {
            int pos = POSITIONS[i];
            result = 0;
            for (int j = pos - 1; j < arr.length; j += pos) {
                result += Character.getNumericValue(arr[j]);
            }

            if (result % 2 == 0) {
                arr[pos - 1] = '0';
            } else {
                arr[pos - 1] = '1';
            }
        }

        return new String(arr);
    }


    public static int[] getControlBits(String binaryString) {
        int[] controlBits = new int[CONTROL_AMOUNT];
        controlBits[0] = binaryString.charAt(0) - '0';
        controlBits[1] = binaryString.charAt(1) - '0';
        controlBits[2] = binaryString.charAt(3) - '0';
        controlBits[3] = binaryString.charAt(7) - '0';
        controlBits[4] = binaryString.charAt(15) - '0';
        return controlBits;
    }

}

