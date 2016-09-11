package io.sponges.bot.modules.rest;

import io.sponges.bot.api.Logger;
import io.sponges.bot.api.module.ModuleLogger;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

class Authentication {

    private final Set<String> lines;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    Authentication(ModuleLogger logger, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
            logger.log(Logger.Type.INFO, "API keys file created. Please populate with API keys!");
        }
        this.lines = read(file);
    }

    boolean isValid(String key) {
        return lines.contains(key);
    }

    private Set<String> read(File file) throws IOException {
        Set<String> lines = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

}
