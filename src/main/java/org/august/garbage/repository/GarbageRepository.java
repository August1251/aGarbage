package org.august.garbage.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.august.garbage.aGarbage;
import org.august.garbage.model.GarbageModel;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
    }

    public void removeGarbage(GarbageModel garbageModel) {
        garbages.remove(garbageModel);
    }

    public boolean existsGarbage(GarbageModel garbageModel) {
        for (GarbageModel i : garbages) {
            if (i.equals(garbageModel)) return true;
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