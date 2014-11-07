package com.madamadasune.pathtraversal.servlet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tlallart on 07/11/14.
 */
public class SimpleDownloadServletTest {

    private SimpleDownloadSerlvet simpleDownloadServlet;

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
        simpleDownloadServlet.doPost(request, response);
    }

}
