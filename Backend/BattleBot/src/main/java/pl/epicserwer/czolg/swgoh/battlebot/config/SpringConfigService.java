package pl.epicserwer.czolg.swgoh.battlebot.config;

import org.commons.Key;
import org.commons.Url;
import org.springframework.beans.factory.annotation.Value;

class SpringConfigService implements ConfigService {
    @Value("${comlink.secret.key}")
    private String comlinkSecretKey;

    @Value("${comlink.access.key}")
    private String comlinkAccessKey;
    @Value("${comlink.url}")
    private String comlinkUrl;

    @Override
    public Key getComlinkSecretKey() {
        return new Key(this.comlinkSecretKey);
    }

    @Override
    public Key getComlinkAccessKey() {
        return new Key(this.comlinkAccessKey);
    }

    @Override
    public Url getComlinkUrl() {
        return new Url(this.comlinkUrl);
    }
}
