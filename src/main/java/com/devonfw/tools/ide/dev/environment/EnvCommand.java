package com.devonfw.tools.ide.dev.environment;

import com.devonfw.tools.ide.dev.command.AbstractCommand;
import picocli.CommandLine;

public abstract class EnvCommand extends AbstractCommand {

    @Override
    public void run() {
        envCommand();
    }

    protected abstract void envCommand();

}
