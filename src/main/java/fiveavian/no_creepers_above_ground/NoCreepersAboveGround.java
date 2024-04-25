package fiveavian.no_creepers_above_ground;

import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashSet;

public class NoCreepersAboveGround implements ModInitializer {
    public static final String MOD_ID = "no_creepers_above_ground";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final File CONFIG_FILE = new File("config", "no_creepers_above_ground.json");

    public static final HashSet<Identifier> entities = new HashSet<>();
    private static String[] config = {"minecraft:creeper"};

    @Override
    public void onInitialize() {
        try {
            loadConfig();
        } catch (IOException ex) {
            LOGGER.error("Could not load config.", ex);
        }
    }

    private static void loadConfig() throws IOException {
        var gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        CONFIG_FILE.getParentFile().mkdirs();
        if (CONFIG_FILE.createNewFile()) {
            try (var stream = new FileOutputStream(CONFIG_FILE); var writer = new OutputStreamWriter(stream)) {
                gson.toJson(config, writer);
            }
        } else {
            try (var stream = new FileInputStream(CONFIG_FILE); var reader = new InputStreamReader(stream)) {
                config = gson.fromJson(reader, String[].class);
            }
        }
        for (String entityId : config)
            entities.add(new Identifier(entityId));
    }
}
