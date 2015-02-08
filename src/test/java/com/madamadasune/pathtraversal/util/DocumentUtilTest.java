package com.madamadasune.pathtraversal.util;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;

import java.io.File;

/**
 * Tests for DocumentUtil
 */
public class DocumentUtilTest {

    private final String documentRoot = "src/test/resources";

    @Test
    public void getFileFaif_shouldReturn_faifFile() {
        File f = DocumentUtil.getFile("faif-2.0.pdf");
        assertThat(f).isNotNull();
        assertThat(f.getName()).isEqualTo("faif-2.0.pdf");
    }

    /**
     * Take time to write this kind of test
     * Think like a hacker and like a developper
     * Should the behavior of getFile() is to get any file?
     * Do I have a test for that?
     */
    @Test(expected = Exception.class)
    public void getFilePasswd_shouldThrowsException() {
        DocumentUtil.getFile("/etc/passwd");
    }

    @Test
    public void getFileSecureFaif_shouldReturn_faifFile() {
        File f = DocumentUtil.getFileSecure(documentRoot, "faif-2.0.pdf");
        assertThat(f).isNotNull();
        assertThat(f.getName()).isEqualTo("faif-2.0.pdf");
    }

    @Test(expected = SecurityException.class)
    public void getFileSecurePasswd_shouldThrowsException() {
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
