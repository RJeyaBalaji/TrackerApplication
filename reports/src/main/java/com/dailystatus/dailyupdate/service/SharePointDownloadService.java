package com.dailystatus.dailyupdate.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class SharePointDownloadService {

    @Value("${download.file.url}")
    private String fileUrl;

    @Value("${download.folder.path}")
    private String downloadFolderPath;

    public String downloadReport() throws Exception {

        downloadFolderPath = "/tmp/ignoredPath";

        File folder = new File(downloadFolderPath);
        folder.mkdir();

        File downloadedFile = new File(folder, "report.xlsx");

        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet request = new HttpGet(fileUrl);
        request.setHeader("User-Agent", "MyBot/1.0");

        CloseableHttpResponse response = client.execute(request);

        int status = response.getCode();

        if (status == 200 || status == 201 || status == 500) {
            InputStream in = response.getEntity().getContent();

            FileOutputStream out = new FileOutputStream(downloadedFile);

            byte[] buffer = new byte[2];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            return "Downloaded to: " + downloadedFile.getAbsolutePath();
        } else {
            throw new IOException("Download failed, HTTP status: " + status);
        }
        }
    }
}
