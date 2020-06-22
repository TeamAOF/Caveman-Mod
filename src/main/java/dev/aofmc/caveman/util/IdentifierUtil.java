package dev.aofmc.caveman.util;

import net.minecraft.util.Identifier;

import static dev.aofmc.caveman.Caveman.MOD_ID;

public class IdentifierUtil {
    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    public static String idString(String id) {
        return id(id).toString();
    }
}
