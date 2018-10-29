package com.mabinogi.tweaked.controllers;

import com.mabinogi.tweaked.api.test.ITweakedTest;
import com.mabinogi.tweaked.script.ScriptLoader;

import java.util.ArrayList;
import java.util.List;

import static com.mabinogi.tweaked.Tweaked.LOG;
import static com.mabinogi.tweaked.controllers.TweakedLogging.TAB;

public class TweakedTests
{
    public static int passCount = 0;
    public static int failCount = 0;

    public static List<String> passes = new ArrayList<>();
    public static List<String> failures = new ArrayList<>();

    public static void run()
    {
        //run the tests
        for (ITweakedTest test : TweakedAnnotations.TESTS)
        {
            if (test.runTest())
            {
                passCount++;
                passes.add(test.getTestDescription());
            }
            else
            {
                failCount++;
                failures.add(test.getTestDescription());
            }
        }

        //output results
        boolean passed = ScriptLoader.failCount == 0 && failCount == 0;

        LOG.print("***************************************************************************************************");
        LOG.print("TWEAKED TEST RESULTS : " + (passed ? "PASSED" : "FAILED"));
        LOG.print(" ");
        LOG.print("Tweaks Run : " + ScriptLoader.passCount);
        LOG.print("Tweaks Passed : " + (ScriptLoader.passCount - ScriptLoader.failCount));
        LOG.print(" ");
        LOG.print("Tests Run : " + passCount);
        LOG.print("Tests Passed : " + (passCount - failCount));
        if (!passes.isEmpty())
        {
            LOG.print(" ");
            LOG.print("Passed Tests : ");
            for (String s : passes)
            {
                LOG.print(TAB + s);
            }
        }
        if (!failures.isEmpty())
        {
            LOG.print(" ");
            LOG.print("Failed Tests : ");
            for (String s : failures)
            {
                LOG.print(TAB + s);
            }
        }
        LOG.print("***************************************************************************************************");
    }
}
