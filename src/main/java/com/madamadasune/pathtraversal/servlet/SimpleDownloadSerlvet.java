package com.madamadasune.pathtraversal.servlet;

import com.madamadasune.pathtraversal.util.DocumentUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Simple servlet to download a file
 * Unsecured
 * Use of DocumentUtil.getFile to get file
 */
@WebServlet("/download")
public class SimpleDownloadSerlvet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SimpleDownloadSerlvet.class);

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        // reads input file from an absolute path
        String fileName = request.getParameter("filename");
        if ((fileName == null) || ("".equals(fileName))) {
            throw new IOException("filename cannot be null or empty");
        }
        File downloadFile = DocumentUtil.getFile(getServletContext().getRealPath("") + File.separator + "documents" + File.separator + fileName);
        FileInputStream inStream = new FileInputStream(downloadFile);

        // gets MIME type of the file
        String mimeType = URLConnection.guessContentTypeFromName(downloadFile.getName());
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        LOGGER.info("Set MIME type to " + mimeType);

        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
    }

}
