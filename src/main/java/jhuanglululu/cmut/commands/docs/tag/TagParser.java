package jhuanglululu.cmut.commands.docs.tag;

import java.util.*;

import static jhuanglululu.cmut.CommandUtils.LOGGER;

public class TagParser {

    private static final Comparator<TagInterface> TAG_COMPARATOR = ((o1, o2) -> Integer.compare(o1.getPriority(), o2.getPriority()));

    public static void registerTags() {

        LOGGER.info("Registering tags for docs command");

        Registry.register("author", Author.class);
        Registry.register("comment", Comment.class);
        Registry.register("date", Date.class);
        Registry.register("docs", Docs.class);
        Registry.register("ensure", Ensure.class);
        Registry.register("example", Example.class);
        Registry.register("kpop", Kpop.class);
        Registry.register("reference", Reference.class);
        Registry.register("require", Require.class);
        Registry.register("return", Return.class);
        Registry.register("seealso", SeeAlso.class);
        Registry.register("update", Update.class);
        Registry.register("variable", Variable.class);
    }

    // Parse the block of tag from a list of string into a tag object
    public static TagInterface parse(ArrayList<String> input) {

        String[] declarationLine = input.removeFirst().split(" ", 2);
        String tag = declarationLine[0].substring(1);
        String properties = null;

        if (declarationLine.length > 1) {
            properties = declarationLine[1];
        }

        return Registry.handleTagInput(tag, properties, input);
    }

    public static void sort(List<TagInterface> tags) {
        tags.sort(TAG_COMPARATOR);
    }

    public static class Registry {
        private static final Map<String, Class<? extends TagInterface>> registry = new HashMap<>();

        public static void register(String name, Class<? extends TagInterface> tag) {
            registry.put(name.toLowerCase(), tag);
        }

        public static TagInterface handleTagInput(String name,  String property, List<String> content) {

            Class<? extends TagInterface> tagClass = registry.get(name.toLowerCase());

            if (tagClass != null) {
                try {
                    return tagClass.getDeclaredConstructor(String.class, List.class).newInstance(property, content);
                } catch (Exception e) { LOGGER.error(e.getMessage()); }
            }

            LOGGER.info("Unrecognized tag {}.", name);

            return new CustomTag(name, property, content);
        }
    }
}
