public class Config {
    String hostname;
    long start;
    long end;
    int limit;
    String token;

    public Config() {
        hostname = "https://meru.wavefront.com";
        start = 1626279991;
        end = 1626287191;
        limit = 3000;
        token = "8112b2ca-67e2-452b-ae0f-b226a72ca01c";
    }

    public Config(String hostname, long start, long end, int limit, String token) {
        this.hostname = hostname;
        this.start = start;
        this.end = end;
        this.limit = limit;
        this.token = token;
    }

    public String getHostname() {
        return hostname;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public int getLimit() {
        return limit;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object obj) {
        Config config = (Config) obj;
        if(hostname.equals(config.hostname) && start == config.start && end == config.end && limit == config.limit && token.equals(config.token)){
            return true;
        }
        return false;
    }
}
