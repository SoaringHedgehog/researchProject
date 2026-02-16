package command;

public interface Command {
    void process();
    String description();
    String getName();
}