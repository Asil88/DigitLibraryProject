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
        String accessToken = "sl.BcEy40iR_q8KXSR-bTXkMAtE_uIV5alJOY6OkbLPjBSZIskw5huFbVpQDxhxJVOP6w-rWQWQFUfhuexyGsLKGgHnRSYnExALlDxQa43EC7G0DZWYyEQntF0rzWWXOEyktSGkYbL9NKmv";
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



