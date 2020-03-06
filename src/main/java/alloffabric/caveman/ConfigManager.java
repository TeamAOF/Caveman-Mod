package alloffabric.caveman;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonGrammar;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author CottonMC
 */
public class ConfigManager {
    public static final String DEFAULT_EXTENSION = ".json5";

    /**
     * Loads a .config file from the config folder and parses it to a POJO.
     *
     * @param clazz      The class of the POJO that will store all our properties
     * @param configName The name of the config file
     * @return A new config Object containing all our options from the config file
     */
    public static <T> T loadConfig(Class<T> clazz, String configName) {
        try {
            File file = FabricLoader.getInstance().getConfigDirectory().toPath().resolve(configName).toFile();
            Jankson jankson = Jankson.builder().build();

            // Generate config file if it doesn't exist
            if (!file.exists()) {
                saveConfig(clazz.newInstance(), configName);
            }

            try {
                JsonObject json = jankson.load(file);
                String cleaned = json.toJson(false, true); // Remove comments

                T result = jankson.fromJson(json, clazz);

                // Check if the config file is outdated. If so overwrite it
                JsonElement jsonElementNew = jankson.toJson(clazz.getDeclaredConstructor().newInstance());
                if (jsonElementNew instanceof JsonObject) {
                    JsonObject jsonNew = (JsonObject) jsonElementNew;
                    if (json.getDelta(jsonNew).size() > 0) {
                        saveConfig(result, configName);
                    }
                }

                return result;
            } catch (IOException e) {
//                LOGGER.warning("Failed to load config File %s: %s", configName, e);
            } catch (NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (SyntaxError syntaxError) {
//            LOGGER.warning("Failed to load config File %s: %s", configName, syntaxError);
        } catch (IllegalAccessException | InstantiationException e) {
//            LOGGER.warning("Failed to create new config file for %s: %s", configName, e);
        }

        // Something obviously went wrong, create placeholder config
//        LOGGER.warning("Creating placeholder config for %s...", configName);
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
//            LOGGER.warning("Failed to create placeholder config for %s: %s", configName, e);
        }

        // This is ... unfortunate
        return null;
    }

    /**
     * Saves a POJO Config object to the disk. This is mostly used to create new configs if they don't already exist
     *
     * @param object     The Config we want to save
     * @param configName The filename of our config.
     */
    public static void saveConfig(Object object, String configName) {
        Jankson jankson = Jankson.builder().build();
        JsonElement json = jankson.toJson(object);
        String result = json.toJson(JsonGrammar.builder()
            .withComments(true)
            .printCommas(false)
            .printUnquotedKeys(true)
            .bareSpecialNumerics(true)
            .bareRootObject(true)
            .build());

        try {
            File file = FabricLoader.getInstance().getConfigDirectory().toPath().resolve(configName).toFile();
            if (!file.exists()) {
                file = FabricLoader.getInstance().getConfigDirectory().toPath().resolve(configName + DEFAULT_EXTENSION).toFile();
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
            }
            FileOutputStream out = new FileOutputStream(file, false);

            out.write(result.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
//            LOGGER.warning("Failed to write to config file %s: %s", configName, e);
        }
    }

}