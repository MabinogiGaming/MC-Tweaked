package com.mabinogi.tweaked.api.test;

public interface ITweakedTest
{
    /**
     * The name of the file that this test will be generated into
     * @return The name
     */
    String getFilename();

    /**
     * The description used to describe this test in logs
     * @return The description
     */
    String getTestDescription();

    /**
     * An array of varialbe scripts that will be generated
     * @return The variable script
     */
    String[] getVariables();

    /**
     * An array of action scripts that will be generated
     * @return The action script
     */
    String[] getActions();

    /**
     * Runs the test, should log outcome and return whether successful
     * @return Whether the test was successful
     */
    boolean runTest();
}
