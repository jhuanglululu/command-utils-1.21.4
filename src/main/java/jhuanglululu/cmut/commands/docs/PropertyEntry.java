package jhuanglululu.cmut.commands.docs;

public class PropertyEntry {
    private final String property, arguments;

    public PropertyEntry(String input) {
        if (input.charAt(input.length() - 1) == '>') {
            String[] splitString = input.split("<", 2);
            property = splitString[0];
            arguments = splitString[1].substring(0, splitString[1].length() - 1);
        } else {
            property = input;
            arguments = null;
        }
    }

    public String getProperty() {
        return property;
    }

    public String getArguments() {
        return arguments;
    }

    public boolean hasArguments() {
        return arguments != null;
    }
    @Override
    public String toString() {
        return getProperty();
    }
}