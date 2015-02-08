package com.madamadasune.pathtraversal.servlet;

import com.madamadasune.pathtraversal.util.DocumentUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;

/**
 * Simple servlet to download a file
 * Secured
 * Use of DocumentUtil.getFileMoreSecure to get file
 */
@WebServlet("/moresecuredownload")
public class MoreSecureDownloadSerlvet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MoreSecureDownloadSerlvet.class);

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        // reads input file from an absolute path
        String fileId = request.getParameter("fileid");
        if ((fileId == null) || ("".equals(fileId))) {
            throw new IOException("fileId cannot be null or empty");
        }
        Integer id = Integer.parseInt(fileId);
        File downloadFile = DocumentUtil.getFileMoreSecure(getServletContext().getRealPath("") + File.separator + "documents", id);
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
