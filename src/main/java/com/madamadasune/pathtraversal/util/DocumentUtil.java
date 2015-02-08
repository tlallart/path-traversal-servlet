package com.madamadasune.pathtraversal.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to get file from documents folder on server
 */
public class DocumentUtil {

    /**
     * The most secured way is to build a map with unique ID for each file on server
     * and only getting file from this id (never from a file name or a path).
     * Of course, in this example, we should generate unique ID and give this ID to
     * HTML form and after only accept requests with this ID.
     * For example, I have simplified all this part.
     */
    private static final Map<Integer, String> files = new HashMap<Integer, String>();

    /**
     * LOGGER for class
     */
    private static final Logger LOGGER = Logger.getLogger(DocumentUtil.class);

    /**
     * Get a file to be downloaded (supposed in a public document folder only)
     * @param fileName the fileName to download
     * @return the File found on server
     */
    public static File getFile(String fileName) throws IOException {
        LOGGER.info("Getting file " + fileName);
        File downloadFile = new File(fileName);
        if (downloadFile.exists()) {
            return downloadFile;
        } else {
            throw new IOException("fileName doesn't exist");
        }
    }

    /**
     * Get a file from public document folder
     * @param documentRoot The public document folder
     * @param fileName The file name to download
     * @return the File found on server
     * @throws SecurityException In case of directory traversal attempt
     */
    public static File getFileSecure(String documentRoot, String fileName) throws SecurityException, IOException {
        LOGGER.info("Trying to get file " + documentRoot + File.separator + fileName);
        if (fileName.contains("..")) {
            throw new SecurityException("Directory traversal attempt");
        }
        File downloadFile = new File(documentRoot + File.separator + fileName);
        if (downloadFile.exists()) {
            return downloadFile;
        } else {
            throw new IOException("fileName doesn't exist");
        }
    }

    /**
     * Get a file from public document folder (based on a id map)
     * @param documentRoot The public document folder
     * @param id The file id to get
     * @return the File found on server
     * @throws SecurityException In case of directory traversal attempt
     */
    public static File getFileMoreSecure(String documentRoot, Integer id) throws SecurityException {
        LOGGER.info("Trying to get file with id " + id + " in " + documentRoot);
        initializeMap();
        String fileName = files.get(id);
        if (fileName == null) {
            throw new SecurityException("Unauthorized key");
        }
        return new File(documentRoot + File.separator + fileName);
    }

    /**
     * Initialize the map with unique ID for files.
     * For example, it's really simplified.
     * In reality, we should generate dynamically ID and give them to HTML Form
     * then only accept requests with this ID.
     * Moreover, we could optimize this code to not rebuild map at every request
     * and we have to handle case with new files were created or deleted between requests.
     * A simple increment could not be a good ID.
     * But the principle is just to access file only from an id to avoid every directory
     * traversal attempts.
     */
    private static void initializeMap() {
        files.put(1, "faif-2.0.pdf");
        files.put(2, "GNU_logo.png");
        files.put(3, "gpl-3.0.txt");
    }

}
