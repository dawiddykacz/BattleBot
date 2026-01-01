package pl.epicserwer.czolg.swgoh.battlebot.config;

import org.commons.Key;
import org.commons.Url;

public interface ConfigService {
    Key getComlinkSecretKey();
    Key getComlinkAccessKey();
    Url getComlinkUrl();
}
