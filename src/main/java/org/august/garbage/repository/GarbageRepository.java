package org.august.garbage.repository;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.august.garbage.aGarbage;
import org.august.garbage.model.GarbageModel;
import org.august.garbage.repository.deserialize.GarbageDeserialize;
import org.august.garbage.repository.serialize.GarbageSerialize;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GarbageRepository {

    public static class Holder {
        public static final GarbageRepository INSTANCE = new GarbageRepository();
    }

    public static GarbageRepository getInstance() {
        return Holder.INSTANCE;
    }

    private static aGarbage garbage;
    private File file;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    private Set<GarbageModel> garbages = new HashSet<>();

    public void makeFile() {

        file = new File(garbage.getDataFolder(), "garbage.json");

        SimpleModule simpleModule = new SimpleModule("CustomSerialization", new Version(1, 0, 0, null, null, null));
        simpleModule.addSerializer(new GarbageSerialize(GarbageModel.class));
        simpleModule.addDeserializer(GarbageModel.class, new GarbageDeserialize(GarbageModel.class));
        objectMapper.registerModule(simpleModule);

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            boolean fileNotExists = file.createNewFile();
            if (!fileNotExists) return;
            objectWriter.writeValue(file, garbages);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void addGarbage(GarbageModel garbageModel) {
        garbages.add(garbageModel);
        save();
    }

    public void removeGarbage(GarbageModel garbageModel) {
        garbages.remove(garbageModel);
        save();
    }

    public boolean isGarbage(GarbageModel garbageModel) {
        for (GarbageModel i : garbages) {
            if (i.equals(garbageModel)) return true;
        }
        return false;
    }

    public Set<GarbageModel> getGarbage(Player player) {
        Set<GarbageModel> garbages = new HashSet<>();
        for (GarbageModel garbageModel : this.garbages) {
            if (garbageModel.getCreator().equals(player.getName())) garbages.add(garbageModel);
        }
        return garbages;
    }

    public GarbageModel getGarbage(GarbageModel garbageModel) {
        for (GarbageModel i : this.garbages) {
            if (garbageModel.equals(i)) return i;
        }
        return garbageModel;
    }

    public boolean existsGarbage(Player player) {
        for (GarbageModel i : garbages) {
            if (i.getCreator().equals(player.getName())) return true;
        }
        return false;
    }

    public Set<GarbageModel> getGarbages() {
        return garbages;
    }

    public void load() {
        try {
            GarbageModel[] garbages = objectMapper.readValue(file, GarbageModel[].class);
            this.garbages = new HashSet<>(Set.of(garbages));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void save() {
        try {
            objectWriter.writeValue(file, garbages);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setGarbage(aGarbage garbage) {
        GarbageRepository.garbage = garbage;
    }
}