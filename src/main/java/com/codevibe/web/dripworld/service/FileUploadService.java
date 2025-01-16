package com.codevibe.web.dripworld.service;

import com.codevibe.web.dripworld.handler.types.ProcessFailedException;
import com.codevibe.web.dripworld.util.Env;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.WebApplicationException;
import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.media.multipart.ContentDisposition;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadService {
    private static final String UPLOAD_DIR = Env.get("archive.path");
    private static final String PORT = Env.get("server.port");
    private final ServletContext context;

    @Inject
    public FileUploadService(ServletContext context) {
        this.context = context;
    }

    public FileItem upload(InputStream is, ContentDisposition fileMetaData) {

        final Path UPLOAD_PATH = Paths.get(context.getRealPath(UPLOAD_DIR));

        final String extension = FilenameUtils.getExtension(fileMetaData.getFileName());
        final String fileName = System.currentTimeMillis() + "." + extension;

        if (write(UPLOAD_PATH, fileName, is)) {

            String app_url = String.format("http://%s:%s%s", "localhost", PORT, context.getContextPath());
            String url = context.getContextPath() + UPLOAD_DIR + "/" + fileName;
            String path = UPLOAD_DIR + "/" + fileName;
            String fullUrl = app_url + UPLOAD_DIR + "/" + fileName;

            return new FileItem(fileName, fileMetaData.getFileName(), path, url, fullUrl);

        } else
            throw new ProcessFailedException("Failed to save files");


    }

    public FileItem upload(String directoryName, InputStream is, ContentDisposition fileMetaData) {

        final Path UPLOAD_PATH = Paths.get(context.getRealPath(UPLOAD_DIR + "/" + directoryName));
        final String extension = FilenameUtils.getExtension(fileMetaData.getFileName());
        final String fileName = System.currentTimeMillis() + "." + extension;

        if (write(UPLOAD_PATH, fileName, is)) {
            String app_url = String.format("http://%s:%s%s", "localhost", PORT, context.getContextPath());
            String url = context.getContextPath() + UPLOAD_DIR + "/" + directoryName + "/" + fileName;
            String path = UPLOAD_DIR + "/" + directoryName + "/" + fileName;
            String fullUrl = app_url + UPLOAD_DIR + "/" + directoryName + "/" + fileName;

            return new FileItem(fileName, fileMetaData.getFileName(), path, url, fullUrl);
        } else
            throw new ProcessFailedException("Save Failed");

    }

    private boolean write(Path uploadPath, String fileName, InputStream is) {
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            int read = 0;
            byte[] bytes = new byte[1024];

            OutputStream out = new FileOutputStream((uploadPath + "/" + fileName));
            while ((read = is.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            throw new WebApplicationException("Upload Error Occcured...!");
        }
    }

    public static class FileItem {
        private String filename;
        private String fileNameOriginal;
        private String path;
        private String url;
        private String fullUrl;

        public FileItem(String filename, String fileNameOriginal, String path, String url, String fullUrl) {
            this.filename = filename;
            this.fileNameOriginal = fileNameOriginal;
            this.path = path;
            this.url = url;
            this.fullUrl = fullUrl;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getFileNameOriginal() {
            return fileNameOriginal;
        }

        public void setFileNameOriginal(String fileNameOriginal) {
            this.fileNameOriginal = fileNameOriginal;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFullUrl() {
            return fullUrl;
        }

        public void setFullUrl(String fullUrl) {
            this.fullUrl = fullUrl;
        }
    }
}
