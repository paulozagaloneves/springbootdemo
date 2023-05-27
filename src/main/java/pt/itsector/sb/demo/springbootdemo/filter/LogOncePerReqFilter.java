package pt.itsector.sb.demo.springbootdemo.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import pt.itsector.sb.demo.springbootdemo.entity.LogReqRes;
import pt.itsector.sb.demo.springbootdemo.service.LoggingService;

@Component
@Slf4j
public class LogOncePerReqFilter extends OncePerRequestFilter{

    @Autowired
    LoggingService loggingService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);

        String requestBody = getValueAsString(cachingRequestWrapper.getContentAsByteArray(), "UTF-8");//cachingRequestWrapper.getCharacterEncoding());
        String responseBody = getValueAsString(cachingResponseWrapper.getContentAsByteArray(), "UTF-8");//cachingResponseWrapper.getCharacterEncoding());
        String clientIp = RequestUtils.getClientIp(request);
        HttpHeaders requestHeaders = Collections.list(cachingRequestWrapper.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        h -> Collections.list(cachingRequestWrapper.getHeaders(h)),
                        (oldValue, newValue) -> newValue,
                        HttpHeaders::new
                ));

        HttpHeaders responseHeaders = cachingResponseWrapper.getHeaderNames()
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        h -> new ArrayList(cachingResponseWrapper.getHeaders(h)),
                        (oldValue, newValue) -> newValue,
                        HttpHeaders::new
                ));


        logReqRes(clientIp, requestBody, responseBody,cachingRequestWrapper.getRequestURI(),cachingRequestWrapper.getMethod(), requestHeaders.toString(), responseHeaders.toString());

        cachingResponseWrapper.copyBodyToResponse();
    }


    private String getValueAsString(byte[] contentAsByteArray, String characterEncoding) {
        String dataAsString = "";
        try {
            dataAsString = new String(contentAsByteArray, characterEncoding);
        }catch (Exception e) {
            log.error("Exception occurred while converting byte into an array: {}",e.getMessage());
            e.printStackTrace();
        }
        return dataAsString;
    }

    protected void logReqRes(String clientIp, String request,String response,String uri, String httpMethod, String requestHeaders, String responseHeaders) {
        LogReqRes logReqRes = new LogReqRes();
        logReqRes.setRequest(request);
        logReqRes.setResponse(response);
        logReqRes.setUri(uri);
        logReqRes.setHttpMethod(httpMethod);
        logReqRes.setClientIp(clientIp);
        logReqRes.setRequestHeaders(requestHeaders);
        logReqRes.setResponseHeaders(responseHeaders);

        loggingService.logReqRes(logReqRes);
    }

}
