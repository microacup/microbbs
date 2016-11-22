package me.micro.bbs.file;

import me.micro.bbs.client.Result;
import me.micro.bbs.file.storage.StorageFileNotFoundException;
import me.micro.bbs.file.storage.StorageService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * 文件上传
 *
 * Created by microacup on 2016/11/16.
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    private final StorageService storageService;

    @Autowired
    public UploadController(StorageService storageService) {
        this.storageService = storageService;
        this.storageService.init();
    }

    //XXX test @GetMapping
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(UploadController.class, "serveFile", path.getParent().getFileName().toString(), path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));

        return "site/uploadForm";
    }

    /**
     * 获取文件
     *
     * @param path
     * @param filename
     * @return
     */
    @GetMapping("/{path}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("path") String path, @PathVariable String filename) {
        Resource file = storageService.loadAsResource(path + "/" + filename);
        return ResponseEntity
                .ok()
                .body(file);
    }

    /**
     * 上传文件
     *
     * @param part
     * @param file
     * @return
     */
    @PostMapping("/{part}")
    @ResponseBody
    public Result<String> handleFileUpload(@PathVariable("part") String part, @RequestParam("file") MultipartFile file) {
        FilePart filePart = null;
        try {
            filePart = FilePart.valueOf(part);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Result.error(HttpStatus.SC_NOT_ACCEPTABLE, "路径请求错误");
        }

        String prefix = filePart.getPath();
        String fileName = storageService.store(file, prefix);
        return Result.ok(fileName);
    }


    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
