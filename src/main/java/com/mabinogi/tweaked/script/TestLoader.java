package com.mabinogi.tweaked.script;

import com.mabinogi.tweaked.api.test.ITweakedTest;
import com.mabinogi.tweaked.controllers.TweakedAnnotations;
import com.mabinogi.tweaked.controllers.TweakedConfiguration;
import net.minecraftforge.common.config.Configuration;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import static com.mabinogi.tweaked.Tweaked.LOG;
import static com.mabinogi.tweaked.script.ScriptLoader.*;

public class TestLoader
{

    public static void loadScripts()
    {
        //create test directory
        File scriptDir = new File(TweakedConfiguration.tweakedDir + File.separator + "test");
        if (!scriptDir.exists())
        {
            scriptDir.mkdirs();
        }

        //clean directory, testing always starts from scratch
        for (File f : ScriptHelper.listFiles(scriptDir))
        {
            f.delete();
        }

        //generate test files
        for (ITweakedTest test : TweakedAnnotations.TESTS)
        {
	        //write variables
	        for (String s : test.getVariables())
	        {
		        try
		        {
			        Path path = Paths.get(scriptDir.getPath() + File.separator + test.getFilename() + ".tweak");
			        Files.write(path, Arrays.asList(s), Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
		        }
		        catch (IOException e)
		        {
			        LOG.warn("Unable to write test : " + s);
		        }
	        }
        }

	    for (ITweakedTest test : TweakedAnnotations.TESTS)
	    {
            //write actions
            for (String s : test.getActions())
            {
                try
                {
                    Path path = Paths.get(scriptDir.getPath() + File.separator + test.getFilename() + ".tweak");
                    Files.write(path, Arrays.asList(s), Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
                }
                catch (IOException e)
                {
                    LOG.warn("Unable to write test : " + s);
                }
            }
        }

        //parse script files
        for (File f : ScriptHelper.listFiles(scriptDir))
        {
            if (f.getName().endsWith(".tweak"))
            {
                //parse file
                BufferedReader buffer = null;
                Configuration.UnicodeInputStreamReader input = null;

                if (f.canRead())
                {
                    try
                    {
                        input = new Configuration.UnicodeInputStreamReader(new FileInputStream(f), "UTF-8");
                        buffer = new BufferedReader(input);

                        //full string we will now build up
                        String in = "";

                        String line;
                        while ((line = buffer.readLine()) != null)
                        {
                            if (line.startsWith("#") || line.startsWith("//"))
                            {
                                //comment, ignore line
                            }
                            else
                            {
                                in += line;
                            }
                        }

                        //clean script
                        in = ScriptHelper.cleanScript(in);

                        //split into separate scripts
                        String[] scripts = in.split(";");

                        //sanity check
                        if (scripts.length == 0 || (scripts.length == 1 && scripts[0].isEmpty()))
                        {
                            //no point parsing
                        }
                        else
                        {
                            for (String script : scripts)
                            {
                                ScriptLoader.parseLine(script);
                            }

                            scriptCount++;
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        IOUtils.closeQuietly(buffer);
                        IOUtils.closeQuietly(input);
                    }
                }
            }
        }

        //debug
        LOG.debug("--------------------");
        LOG.info("Parsed " + scriptCount + " scripts");
        LOG.info("Parsed " + passCount + "/" + (passCount + failCount) + " tweaks");
        LOG.debug("--------------------");
    }

}
