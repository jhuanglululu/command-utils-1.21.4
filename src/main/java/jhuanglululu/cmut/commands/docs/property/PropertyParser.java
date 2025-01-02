package jhuanglululu.cmut.commands.docs.property;

import jhuanglululu.cmut.commands.docs.tag.TagInterface;

import java.util.*;

import static jhuanglululu.cmut.CommandUtils.LOGGER;

public class PropertyParser {

    private static final Comparator<PropertyInterface> PROPERTY_COMPARATOR = ((o1, o2) -> Integer.compare(o1.getPriority(), o2.getPriority()));

    public static void registerProperties() {
        LOGGER.info("Registering property for docs command");

        Registry.register("abstract", Abstract.class);
        Registry.register("deprecated", Deprecated.class);
        Registry.register("demo", Demo.class);
    }

    public static List<PropertyInterface> parse(String input) {
        List<PropertyInterface> result = new ArrayList<>();

        boolean argClose = true;
        boolean propertyOpen = false;
        int breakPoint = 0;
        
        if (input == null) {
            return result;
        }

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '<') {
                argClose = false;
            } else if (c == '>') {
                argClose = true;
            } else if (argClose && c == ' ' && propertyOpen) {
                separateProperty(input.substring(breakPoint, i), result);
                breakPoint = i + 1;
                propertyOpen = false;
            } else {
                propertyOpen = true;
            }
        }

        if (propertyOpen) {
            separateProperty(input.substring(breakPoint), result);
        }

        sort(result);

        return result;
    }

    public static void sort(List<PropertyInterface> tags) {
        tags.sort(PROPERTY_COMPARATOR);
    }

    private static void separateProperty(String input, List<PropertyInterface> result) {
        String[] inputList = input.split("<");

        String key = inputList[0];
        String value = null;
        if (inputList.length > 1) {
            value = inputList[1].substring(0 , inputList[1].length() - 1);
        }

        result.add(Registry.handlePropertyInput(key, value));
    }

    public static class Registry {
        private static final Map<String, Class<? extends PropertyInterface>> registry = new HashMap<>();

        public static void register(String name, Class<? extends PropertyInterface> property) {
            registry.put(name.toLowerCase(), property);
        }

        public static PropertyInterface handlePropertyInput(String name, String args) {

            Class<? extends PropertyInterface> propertyClass = registry.get(name.toLowerCase());

            if (propertyClass != null) {
                try {
                    return propertyClass.getDeclaredConstructor(String.class).newInstance(args);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            }

            LOGGER.info("Unrecognized property {}.", name);

            return new CustomProperty(name, args);
        }
    }
}