package com.csp.cases;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.err.println(rename("aaa"));
        System.err.println(rename("aaa.jpg"));
        System.err.println(rename(""));
    }

    private String rename(String fileName) {
        if (fileName == null)
            fileName = "";

        String suffix = "";
        int index = fileName.indexOf(".");
        if (index > -1) {
            suffix = fileName.substring(index);
            fileName = fileName.substring(0, index);
        }
        return fileName + "_" +  System.currentTimeMillis() + suffix;
    }
}