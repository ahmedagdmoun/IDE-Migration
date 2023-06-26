package com.devonfw.tools.ide.dev.tool;

import com.devonfw.tools.ide.dev.command.ToolCommand;
import com.devonfw.tools.ide.dev.environment.Environment;
import com.devonfw.tools.ide.dev.functions.Functions;
import picocli.CommandLine;

import java.io.*;
import java.nio.file.Path;

@CommandLine.Command(name = "java",
        description = "This is java commandlet"
)
public class Java extends ToolCommand {

    private static final String DEVON_IDE_HOME = Environment.get().get("DEVON_IDE_HOME");
    private static final String DEVON_SOFTWARE_DIR = DEVON_IDE_HOME + "/software/";
    @Override
    protected String getTool() {
        return "java";
    }

    /**
    The additional attributes added are:
     - arity: is set to 0..1 to make the parameter optional.
     - defaultValue: is set to an empty string, indicating that if the parameter is not provided, it will default to an empty string.
     - showDefaultValue: is used to display the default value in the usage help.
     **/
    @CommandLine.Parameters(description = "java cli options", arity = "0..1", defaultValue = "", showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
    private String[] options;
    @Override
    protected void startTool() {
        Path pathToBinFolder = Functions.searchFolder(DEVON_SOFTWARE_DIR+getTool(), "bin");
        if (pathToBinFolder == null) {
            System.out.println(getTool()+" is either not installed or the installation path is broken. please remove any related artifacts and and run the setup again.");
            return;
        }
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (options.length == 1 && options[0].isEmpty()) {
                processBuilder.command(getTool(), "--help");
            }
            else{
                String[] command = new String[options.length + 1];
                command[0] = getTool();
                System.arraycopy(options, 0, command, 1, options.length);
                processBuilder.command(command);
            }

            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();
            int exitValue = process.exitValue();

            if (exitValue == 0) {
                System.out.println("Command " + getTool() + " run successfully.");
            }
            else {
                System.out.println("Failed to run " + getTool() + " command.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
