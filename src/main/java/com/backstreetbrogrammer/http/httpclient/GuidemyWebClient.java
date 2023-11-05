package com.backstreetbrogrammer.http.httpclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class GuidemyWebClient {

    private final HttpClient client;

    public GuidemyWebClient() {
        this.client = HttpClient.newBuilder()
                                .version(HttpClient.Version.HTTP_1_1)
                                .build();
    }

    public CompletableFuture<String> sendTask(final String url, final byte[] requestPayload) {
        final HttpRequest request = HttpRequest.newBuilder()
                                               .POST(HttpRequest.BodyPublishers.ofByteArray(requestPayload))
                                               .uri(URI.create(url))
                                               .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                     .thenApply(HttpResponse::body);
    }

}
