/*
 * Copyright 2015 Year4000.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package net.year4000.utilities.locale;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import net.year4000.utilities.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This abstract class is here for another class to implement
 * you will need to create a singleton pattern to use this
 * correctly.
 */
@SuppressWarnings("unused")
public abstract class AbstractLocaleManager {
    public static final Locale DEFAULT_LOCALE = new Locale(Locale.US.toString().toLowerCase());
    protected static final String EXTENSION = ".properties";
    private final Map<Locale, Properties> locales = Maps.newConcurrentMap();
    protected String path;
    protected String[] codes;
    protected LogUtil log;

    /**
     * Init the LocaleManager with default LogUtil logger and
     * set the path to get the locales and the codes that this
     * manager will manager.
     */
    public AbstractLocaleManager(String path, String... codes) {
        this(path, codes, new LogUtil());
    }

    /**
     * Fully init the locale manager and then load all the default locale codes.
     *
     * @param path  The paths that stores the locale files.
     * @param codes The locale codes that we try to grab by default.
     * @param log   The log util that this class will use.
     */
    public AbstractLocaleManager(String path, String[] codes, LogUtil log) {
        this.path = checkNotNull(path);
        this.codes = checkNotNull(codes);
        this.log = checkNotNull(log);

        loadLocales();
    }

    /** The method that will handle the loading mechanics of the locales */
    protected abstract void loadLocales();

    /**
     * Load a resource to be used, in the locale system.
     *
     * @param key The name of the.
     */
    protected void loadLocale(String key, InputStream locale) {
        Properties file = new Properties();

        try (InputStreamReader reader = new InputStreamReader(locale, Charsets.UTF_8)) {
            file.load(reader);
            locales.put(new Locale(key.toLowerCase()), file);
            log.debug("LocaleManager Added: " + key);
        }
        catch (IOException e) {
            log.log(e, false);
        }
    }

    /**
     * Check is the requested locale is loaded.
     *
     * @param locale The language code in string form.
     * @return true When the language is loaded.
     */
    public boolean isLocale(Locale locale) {
        locale = new Locale(locale.toString().toLowerCase());
        return locales.containsKey(locale) || locales.containsKey(locale.stripExtensions());
    }

    /**
     * Get the properties file for the current locale.
     *
     * @param locale The locale code.
     * @return The properties for the locale.
     */
    public Properties getLocale(Locale locale) {
        Locale backup = DEFAULT_LOCALE;

        for (Locale localeValue : locales.keySet()) {
            // Language matches
            if (localeValue.equals(locale.stripExtensions())) {
                backup = localeValue;
                break;
            }
        }

        return locales.getOrDefault(locale, locales.getOrDefault(backup, new Properties()));
    }

    public String getPath() {
        return this.path;
    }

    public String[] getCodes() {
        return this.codes;
    }

    public LogUtil getLog() {
        return this.log;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCodes(String[] codes) {
        this.codes = codes;
    }

    public void setLog(LogUtil log) {
        this.log = log;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof AbstractLocaleManager)) return false;
        final AbstractLocaleManager other = (AbstractLocaleManager) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$locales = this.locales;
        final Object other$locales = other.locales;
        if (this$locales == null ? other$locales != null : !this$locales.equals(other$locales)) return false;
        final Object this$path = this.path;
        final Object other$path = other.path;
        if (this$path == null ? other$path != null : !this$path.equals(other$path)) return false;
        if (!java.util.Arrays.deepEquals(this.codes, other.codes)) return false;
        final Object this$log = this.log;
        final Object other$log = other.log;
        if (this$log == null ? other$log != null : !this$log.equals(other$log)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $locales = this.locales;
        result = result * PRIME + ($locales == null ? 0 : $locales.hashCode());
        final Object $path = this.path;
        result = result * PRIME + ($path == null ? 0 : $path.hashCode());
        result = result * PRIME + java.util.Arrays.deepHashCode(this.codes);
        final Object $log = this.log;
        result = result * PRIME + ($log == null ? 0 : $log.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof AbstractLocaleManager;
    }

    public String toString() {
        return "net.year4000.utilities.locale.AbstractLocaleManager(locales=" + this.locales + ", path=" + this.path + ", codes=" + java.util.Arrays.deepToString(this.codes) + ", log=" + this.log + ")";
    }

    public Map<Locale, Properties> getLocales() {
        return this.locales;
    }
}
