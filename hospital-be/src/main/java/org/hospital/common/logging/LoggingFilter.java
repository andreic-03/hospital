package org.hospital.common.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;


import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        chain.doFilter(wrappedRequest, wrappedResponse);

        logRequest(wrappedRequest);
        logResponse(wrappedResponse);

        wrappedResponse.copyBodyToResponse();
    }

    private void logRequest(ContentCachingRequestWrapper wrappedRequest) throws IOException {
        String logRequestMessage = "Request URL: " + wrappedRequest.getRequestURL() + (wrappedRequest.getQueryString() != null ? "?" + wrappedRequest.getQueryString() : "") +
                " Method: " + wrappedRequest.getMethod() + " Headers: " + getHeaders(wrappedRequest) + " Body: " + getRequestData(wrappedRequest);

        LOG.info(logRequestMessage);
    }

    private void logResponse(ContentCachingResponseWrapper wrappedResponse) throws IOException {
        String logResponseMessage = "Response Status: " + wrappedResponse.getStatus() + " Headers: " +
                getHeaders(wrappedResponse) + " Body: " + getResponseData(wrappedResponse);

        if (wrappedResponse.getStatus() >= 400 && wrappedResponse.getStatus() < 600) {
            LOG.error(logResponseMessage);
        } else {
            LOG.info(logResponseMessage);
        }
    }

    private String getRequestData(ContentCachingRequestWrapper wrappedRequest) throws IOException {
        String requestBody = getBody(wrappedRequest.getContentAsByteArray(), wrappedRequest.getInputStream(),
                wrappedRequest.getCharacterEncoding());

        return removeWhiteLines(requestBody);
    }

    private String getResponseData(ContentCachingResponseWrapper wrappedResponse) throws IOException {
        String responseBody = getBody(wrappedResponse.getContentAsByteArray(), wrappedResponse.getContentInputStream(),
                wrappedResponse.getCharacterEncoding());

        return removeWhiteLines(responseBody);
    }

    private String getHeaders(ContentCachingRequestWrapper wrappedRequest) {
        StringBuilder stringBuilder = new StringBuilder();
        Enumeration<String> headerNames = wrappedRequest.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = wrappedRequest.getHeader(headerName);

            appendHeaderNameAndValue(stringBuilder, headerName, headerValue);
        }

        return stringBuilder.toString();
    }

    private String getHeaders(ContentCachingResponseWrapper wrappedResponse) {
        StringBuilder stringBuilder = new StringBuilder();
        Collection<String> headerNames = wrappedResponse.getHeaderNames();

        for (String headerName : headerNames) {
            String headerValue = wrappedResponse.getHeader(headerName);

            appendHeaderNameAndValue(stringBuilder, headerName, headerValue);
        }

        return stringBuilder.toString();
    }

    private void appendHeaderNameAndValue(StringBuilder stringBuilder, String headerName, String headerValue) {
        stringBuilder.append("(Name: ");
        stringBuilder.append(headerName);
        stringBuilder.append(", Value: ");
        stringBuilder.append(removeWhiteLines(headerValue));
        stringBuilder.append(") ");
    }

    private String removeWhiteLines(String text) {
        return text.replaceAll("\\r|\\n", "");
    }

    private String getBody(byte[] buf, InputStream inputStream, String characterEncoding) throws IOException {
        if (buf != null && buf.length > 0) {
            return new String(buf, 0, buf.length, characterEncoding);
        } else {
            return IOUtils.toString(inputStream, characterEncoding);
        }
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }

}