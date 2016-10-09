package ru.sberbank.school.task8;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EncryptedClassLoader extends ClassLoader {
    private final String key;
    private final File dir;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Path path = Paths.get(dir.getAbsolutePath() + "/" + name + ".class");
        byte[] encrypted = new byte[0];
        try {
            encrypted = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] decrypted = runDecrypter(encrypted);
        return defineClass(name, decrypted, 0, encrypted.length);
    }

    private byte[] runDecrypter(byte[] bytes) {
        byte key = (byte)(this.key.hashCode());
        byte[] decrypted = new byte[bytes.length];
        for (byte i = 0; i < bytes.length; i++) {
            decrypted[i] = (byte)(bytes[i]^key);
        }

        return bytes;
    }
}
