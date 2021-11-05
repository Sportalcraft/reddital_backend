package com.project.reddital_backend;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Arrays;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
        boolean success = true;

        for (Class testSuite : TestsToRun) {
            //System.out.println(testSuite.getPackageName() + ":");
            success &= runTests(testSuite);
            System.out.println();
        }

        if (success)
            System.out.println("All tests passed!");

    }


    private static final List<Class> TestsToRun = Arrays.asList(
            AllUnitTests.class
    );

    private static boolean runTests(Class testSuite) {
        Result result = JUnitCore.runClasses(testSuite);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        return result.wasSuccessful();
    }
}
