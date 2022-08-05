package ca.fxco.encodedchat.config;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.lang3.SerializationException;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

public class ConfigManager {

    private final Path configPath;
    private final Path playerActionsPath;
    private final Yaml yaml;
    private final Yaml playerActionsYaml;

    public ConfigManager(String modId, boolean testing) {
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        SmartRepresenter smartRepresenter = new SmartRepresenter();
        this.yaml = new Yaml(new Constructor(ECConfig.class), smartRepresenter, options);
        this.playerActionsYaml = new Yaml();
        Path configDir = testing ?
                Path.of(System.getProperty("user.dir")).resolve("run").resolve("config") :
                FabricLoader.getInstance().getConfigDir();
        this.configPath = configDir.resolve(modId + ".yaml");
        this.playerActionsPath = configDir.resolve(modId + "-players.yaml");
    }

    public ECConfig loadConfig() {
        if (!Files.exists(this.configPath)) {
            ECConfig config = new ECConfig().validateOnLoad(); // Default
            saveConfig(config);
            return config;
        }
        try (InputStream stream = Files.newInputStream(this.configPath)) {
            return ((ECConfig)this.yaml.load(stream)).validateOnLoad();
        } catch (IllegalStateException | IOException e) {
            throw new SerializationException(e);
        }
    }

    public void saveConfig(ECConfig config) {
        try {
            Files.createDirectories(this.configPath.getParent());
            Files.writeString(
                    this.configPath,
                    Pattern.compile("^- \\{(.*)}$", Pattern.MULTILINE)
                            .matcher(this.yaml.dump(config))
                            .replaceAll("- $1")
            );
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public PlayerActions loadPlayerActions() {
        if (!Files.exists(this.playerActionsPath)) {
            PlayerActions playerActions = new PlayerActions(); // Default
            savePlayerActions(playerActions);
            return playerActions;
        }
        try (InputStream stream = Files.newInputStream(this.playerActionsPath)) {
            PlayerActions playerActions = new PlayerActions();
            playerActions.loadSaveableActions(this.playerActionsYaml.load(stream));
            return playerActions;
        } catch (IllegalStateException | IOException e) {
            throw new SerializationException(e);
        }
    }

    public void savePlayerActions(PlayerActions playerActions) {
        try {
            Files.createDirectories(this.playerActionsPath.getParent());
            Files.writeString(
                    this.playerActionsPath,
                    this.playerActionsYaml.dump(playerActions.getSaveableActions())
            );
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    private static class SmartRepresenter extends Representer {
        @Override
        protected NodeTuple representJavaBeanProperty(Object javaBean, Property property,
                                                      Object propertyValue, Tag customTag) {
            if (propertyValue == null) return null;
            NodeTuple tuple = super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
            Node valueNode = tuple.getValueNode();
            if (Tag.NULL.equals(valueNode.getTag())) return null; // skip 'null' values again
            if (valueNode instanceof CollectionNode) {
                if (Tag.SEQ.equals(valueNode.getTag())) {
                    SequenceNode seq = (SequenceNode) valueNode;
                    if (seq.getValue().isEmpty()) return null; // skip empty lists
                }
                if (Tag.MAP.equals(valueNode.getTag())) {
                    MappingNode seq = (MappingNode) valueNode;
                    if (seq.getValue().isEmpty()) return null; // skip empty maps
                }
            }
            return tuple;
        }

        @Override
        protected Set<Property> getProperties(Class<?> type) {
            Set<Property> reversed = new TreeSet<>(Collections.reverseOrder());
            reversed.addAll(super.getProperties(type));
            List<Property> result = new ArrayList<>(reversed);
            result.sort((o1, o2) -> {
                Weight weight1 = o1.getAnnotation(Weight.class);
                Weight weight2 = o2.getAnnotation(Weight.class);
                return weight1 == null ?
                        (weight2 == null ? 0 : 1) :
                        (weight2 == null ? -1 : (weight1.value() > weight2.value() ? -1 : 1));
            });
            return new LinkedHashSet<>(result);
        }
    }
}