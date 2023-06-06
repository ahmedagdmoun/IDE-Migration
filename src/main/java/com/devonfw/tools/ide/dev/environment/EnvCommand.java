package com.devonfw.tools.ide.dev.environment;

import com.devonfw.tools.ide.dev.command.AbstractCommand;
import picocli.CommandLine;

public abstract class EnvCommand extends AbstractCommand {

    @CommandLine.Option(names = {"env", "environment"})
    private boolean env;

    @Override
    public void run() {
        if (env){
            try {
                envCommand();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract void envCommand();

}
