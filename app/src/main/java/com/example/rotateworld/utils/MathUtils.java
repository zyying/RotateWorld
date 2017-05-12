package com.example.rotateworld.utils;

/**
 * Created by zhouying on 2017/3/30.
 */

public class MathUtils {
    public static float sin(int num) {
        return (float) Math.sin(num * Math.PI / 180);
    }

    public static float cos(int num) {
        return (float) Math.cos(num * Math.PI / 180);
    }
}
