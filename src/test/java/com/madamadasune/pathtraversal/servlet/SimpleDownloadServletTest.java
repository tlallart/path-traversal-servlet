package com.madamadasune.pathtraversal.servlet;

import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.asset.AssetUtil;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

/**
 * Created by tlallart on 07/11/14.
 */
@RunWith(Arquillian.class)
public class SimpleDownloadServletTest {

    private SimpleDownloadSerlvet simpleDownloadServlet;

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive jar =  ShrinkWrap.create(WebArchive.class)
                .addClass(SimpleDownloadSerlvet.class)
                .addAsManifestResource("arquillian.xml")
                .setWebXML("web.xml")
                ;

        System.out.println(jar.toString(true));

        return jar;
    }

    @Before
    public void setup() {
        simpleDownloadServlet = new SimpleDownloadSerlvet();
    }

    @Test(expected = IOException.class)
    public void emptyRequestShouldThrowIOException() throws IOException, ServletException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        simpleDownloadServlet.doPost(request, response);
    }

    @Test
    public void requestWithCorrectFileShouldReturnResponse() throws IOException, ServletException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(request.getParameter("filename")).thenReturn("gpl-3.0.txt");
        Mockito.when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) throws IOException {

            }
        });
        simpleDownloadServlet.doPost(request, response);
    }

}
