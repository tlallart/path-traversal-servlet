package com.madamadasune.pathtraversal.util;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Tests for DocumentUtil
 */
public class DocumentUtilTest {

    private final String documentRoot = "src/test/resources";

    @Test
    public void getFileFaif_shouldReturn_faifFile() throws IOException {
        File f = DocumentUtil.getFile(documentRoot + File.separator + "faif-2.0.pdf");
        assertThat(f).isNotNull();
        assertThat(f.getName()).isEqualTo("faif-2.0.pdf");
    }

    @Test(expected = IOException.class)
    public void getFileNotExists_shouldThrowsException() throws IOException {
        DocumentUtil.getFile("dposjgdpsjgpdgùfdf");
    }

    /**
     * Take time to write this kind of test
     * Think like a hacker and like a developper
     * Should the behavior of getFile() is to get any file?
     * Do I have a test for that?
     * If you write it before code, you could undertand better the excepted behavior
     * Here we can see that we could probably expect an exception if we try to
     * get /etc/passwd, but getFile runs well and return the file (to the end user)
     */
    @Test(expected = SecurityException.class)
    public void getFilePasswd_shouldThrowsException() throws IOException {
        DocumentUtil.getFile("/etc/passwd");
    }

    @Test
    public void getFileSecureFaif_shouldReturn_faifFile() throws IOException {
        File f = DocumentUtil.getFileSecure(documentRoot, "faif-2.0.pdf");
        assertThat(f).isNotNull();
        assertThat(f.getName()).isEqualTo("faif-2.0.pdf");
    }

    @Test(expected = SecurityException.class)
    public void getFileSecurePasswd_shouldThrowsException() throws IOException {
        DocumentUtil.getFileSecure(documentRoot, "../../../../etc/passwd");
    }

    @Test
    public void getFileMoreSecureFaif_shouldReturn_faifFile() {
        File f = DocumentUtil.getFileMoreSecure(documentRoot, 1);
        assertThat(f).isNotNull();
        assertThat(f.getName()).isEqualTo("faif-2.0.pdf");
    }

    @Test(expected = SecurityException.class)
    public void getFileMoreSecureAnyOtherFile_shouldThrowsException() {
        DocumentUtil.getFileMoreSecure(documentRoot, -8);
    }

}
