package com.huawei.ist.utility;

public class Hex {
    public static String encodeHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        byte[] var2 = data;
        int var3 = data.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            sb.append(hexCode[b >> 4 & 15]);
            sb.append(hexCode[b & 15]);
        }

        return sb.toString();
    }
}
