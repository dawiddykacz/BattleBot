package pl.epicserwer.czolg.swgoh.battlebot.stats.comlink;

import lombok.NonNull;
import lombok.ToString;
import org.commons.NameId;

@ToString
abstract class ComlinkRequest {
    protected enum Method{
        POST, GET;

        @Override
        public String toString() {
            return this.name().toUpperCase();
        }
    }

    private final Method method;
    private final NameId endpoint;

    public ComlinkRequest(@NonNull final Method method, @NonNull final String endpoint) {
        this.method = method;
        if(endpoint.startsWith("/")) this.endpoint = new NameId(endpoint);
        else this.endpoint = new NameId("/"+endpoint);
    }

    public String getMethod() {
        return this.method.toString();
    }

    public String getEndpoint() {
        return endpoint.toString();
    }

    public abstract String getJsonBodyAsString();
}
