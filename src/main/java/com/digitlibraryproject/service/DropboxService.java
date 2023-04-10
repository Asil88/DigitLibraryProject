package com.digitlibraryproject.service;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class DropboxService {
    private final DbxClientV2 client;

    public DropboxService() {
        String accessToken = "sl.BcSP1GBOuggQKmesG6h1286pOYyS_0-bawQ-_16H4RM9R7R32hVEhKIMM7Hh7IsSsC4ho4Tw06CMa9oxUpaNYRJ43zOYfTXfXhl0tApA00PCygblyQ4IZOQgPjD4OE9lXva10HUD4KzW";
        DbxRequestConfig config = DbxRequestConfig.newBuilder("DigitLibrary").build();
        client = new DbxClientV2(config, accessToken);
    }

    public void uploadFile(MultipartFile file) throws IOException, DbxException {
        String fileName = file.getOriginalFilename();
        try (InputStream in = file.getInputStream()) {
            client.files().uploadBuilder("/" + fileName)
                    .withMode(WriteMode.ADD)
                    .uploadAndFinish(in);
        } catch (UploadErrorException e) {
            throw new RuntimeException(e);
        } catch (DbxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] downloadFile(String fileName) throws IOException, DbxException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            client.files().downloadBuilder("/" + fileName).download(outputStream);
            return outputStream.toByteArray();
        }
    }
}



