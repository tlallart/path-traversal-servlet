package com.madamadasune.pathtraversal.util;

import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created by tlallart on 20/11/14.
 */
public class DocumentUtil {

    private static final Logger LOGGER = Logger.getLogger(DocumentUtil.class);

    public File getFile(String fileName) {
        String filePath = getServletContext().getRealPath("") + File.separator + "documents" + File.separator + fileName;
        LOGGER.info("Start download of " + filePath);
        File downloadFile = new File(filePath);
        return null;
    }

}
