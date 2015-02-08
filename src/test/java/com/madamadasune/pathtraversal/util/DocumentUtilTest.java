package com.madamadasune.pathtraversal.util;

import com.madamadasune.pathtraversal.util.DocumentUtil;
import static org.assertj.core.api.Assertions.*;
import org.junit.Test;

import java.io.File;

/**
 * Created by tlallart on 08/02/15.
 */
public class DocumentUtilTest {

    @Test
    public void getFileFaif_shouldReturn_faifFile() {
        String documentRoot = "src/test/resources";
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
        File f = DocumentUtil.getFile("/etc/passwd");
    }

    @Test
    public void getFileSecureFaif_shouldReturn_faifFile() {
        String documentRoot = "src/test/resources";
        File f = DocumentUtil.getFileSecure(documentRoot, "faif-2.0.pdf");
        assertThat(f).isNotNull();
        assertThat(f.getName()).isEqualTo("faif-2.0.pdf");
    }

    @Test(expected = SecurityException.class)
    public void getFileSecurePasswd_shouldThrowsException() {
        File f = DocumentUtil.getFileSecure("src/test/resources", "../../../../etc/passwd");
    }

    @Test
    public void getFileMoreSecureFaif_shouldReturn_faifFile() {
        String documentRoot = "src/test/resources";
        File f = DocumentUtil.getFileMoreSecure(documentRoot, 1);
        assertThat(f).isNotNull();
        assertThat(f.getName()).isEqualTo("faif-2.0.pdf");
    }

    @Test(expected = SecurityException.class)
    public void getFileMoreSecureAnyOtherFile_shouldThrowsException() {
        File f = DocumentUtil.getFileMoreSecure("src/test/resources", -8);
    }

}
