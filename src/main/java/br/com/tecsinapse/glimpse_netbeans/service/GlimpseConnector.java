package br.com.tecsinapse.glimpse_netbeans.service;

import br.com.tecsinapse.glimpse.client.http.HttpConnector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class GlimpseConnector implements Comparable<GlimpseConnector> {

    private final String name;
    private final String url;
    private final String username;
    private final String password;
    private HttpConnector httpConnector;
    private ExecutorService executor;

    public GlimpseConnector(String name, String url, String username, String password) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GlimpseConnector other = (GlimpseConnector) obj;
        return !((this.name == null) ? (other.name != null) : !this.name.equals(other.name));
    }

    @Override
    public int compareTo(GlimpseConnector o) {
        return name.compareTo(o.getName());
    }

    HttpConnector getHttpConnector() {
        if (httpConnector == null) {
            httpConnector = new HttpConnector(getUrl(), getUsername(), getPassword());
        }
        return httpConnector;
    }

    ExecutorService getExecutor() {
        if (executor == null) {
            executor = Executors.newFixedThreadPool(3);
        }
        return executor;
    }
}
