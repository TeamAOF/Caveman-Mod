package alloffabric.caveman.util;

import net.minecraft.util.Identifier;

import static alloffabric.caveman.Caveman.MOD_ID;

public class IdentifierUtil {
    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    public static String stringId(String id) {
        return id(id).toString();
    }
}
