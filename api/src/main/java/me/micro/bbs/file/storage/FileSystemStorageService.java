package me.micro.bbs.file.storage;

import me.micro.bbs.file.FilePart;
import me.micro.bbs.util.ShortUUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${site.upload.windows}")
    private String location_windows;

    @Value("${site.upload.linux}")
    private String location_linux;

    private Path rootLocation;


    @Override
    public String store(MultipartFile file, String prefix) {
        String originalFilename = file.getOriginalFilename();
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + originalFilename);
            }

            // 生成唯一名字
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = ShortUUID.uuid() + suffix;
            Files.copy(file.getInputStream(), load(prefix + fileName), REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + originalFilename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 2)
                        .filter(path -> !path.equals(this.rootLocation) && !path.toFile().isDirectory())
                        .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            if (this.rootLocation == null) {
                String os = System.getProperty("os.name").toLowerCase();
                if (os.indexOf("windows") >= 0) {
                    this.rootLocation = Paths.get(location_windows);
                } else {
                    this.rootLocation = Paths.get(location_linux);
                }
            }

            if (!rootLocation.toFile().exists()) {
                Files.createDirectories(rootLocation);
            }
            for (FilePart file : FilePart.values()) {
                Path dir = rootLocation.resolve(file.getPath());
                if (!dir.toFile().exists()) {
                    Files.createDirectory(dir);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
