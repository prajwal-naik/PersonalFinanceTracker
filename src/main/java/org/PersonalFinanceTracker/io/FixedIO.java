package org.PersonalFinanceTracker.io;

import java.util.LinkedList;
import java.util.Queue;

public class FixedIO implements IO {
    private final Queue<String> inputs = new LinkedList<>();
    private final StringBuilder outputs = new StringBuilder();

    public void addInput(String line) { inputs.add(line); }

    @Override
    public String readLine() {
        return inputs.poll();
    }

    @Override
    public void print(String s) { outputs.append(s); }

    public String getOutput() { return outputs.toString(); }
}
