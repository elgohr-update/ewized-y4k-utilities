package net.year4000.utilities.sdk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.year4000.utilities.URLBuilder;
import org.eclipse.jetty.io.RuntimeIOException;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The connection used to use along side HttpFetcher.
 */
@Data
public class HttpConnection {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    private static final int TIMEOUT = 5000;
    private static final String USER_AGENT = "Year4000 Utilities API Interface";
    private final URLBuilder urlBuilder;
    // Connections
    private Map<String, String> headers = new LinkedHashMap<>();
    private String userAgent = null;
    private int timeout = -1;
    // Connection
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private HttpURLConnection urlConnection;

    public HttpConnection(String url) {
        urlBuilder = URLBuilder.fromURL(url);
    }

    /** Get or create new connection that only throws RuntimeIOException */
    public <T extends HttpURLConnection> T getSuppressedConnection() {
        try {
            return getConnection();
        }
        catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    /** Get or create new connection */
    public <T extends HttpURLConnection> T getConnection() throws IOException {
        if (urlConnection == null) {
            urlConnection = this.<T>newConnection();
        }

        return (T) urlConnection;
    }

    /** Create a new HTTPURLConnection from this class */
    private <T extends HttpURLConnection> T newConnection() throws IOException {
        T connection = (T) (new URL(urlBuilder.build())).openConnection();

        // Add headers
        headers.forEach(connection::setRequestProperty);

        // Other connection settings
        connection.setRequestProperty("User-Agent", userAgent == null ? USER_AGENT : userAgent);
        connection.setConnectTimeout(timeout < 0 ? TIMEOUT : timeout);
        connection.setReadTimeout(timeout < 0 ? TIMEOUT : timeout);

        return connection;
    }

    /** Response with http */
    public static Reader responseHttp(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();

        // Get Response
        if (responseCode >= HttpURLConnection.HTTP_OK && responseCode <= HttpURLConnection.HTTP_PARTIAL) {
            return new InputStreamReader(connection.getInputStream());
        }
        else {
            throw new IOException(responseCode + " " + connection.getResponseMessage());
        }
    }

    /** Response with https */
    public static Reader responseHttps(HttpsURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();

        // Get Response
        if (responseCode >= HttpsURLConnection.HTTP_OK && responseCode <= HttpsURLConnection.HTTP_PARTIAL) {
            return new InputStreamReader(connection.getInputStream());
        }
        else {
            throw new IOException(responseCode + " " + connection.getResponseMessage());
        }
    }

    /** Send data to the connection https */
    public static void requestHttps(HttpsURLConnection connection, JsonObject object) throws IOException {
        // Send Data
        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            GSON.toJson(object, writer);
        }
    }

    /** Send data to the connection https */
    public static void requestHttp(HttpURLConnection connection, JsonObject object) throws IOException {
        // Send Data
        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
            GSON.toJson(object, writer);
        }
    }
}
