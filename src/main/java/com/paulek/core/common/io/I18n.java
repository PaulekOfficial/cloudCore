package com.paulek.core.common.io;

import com.paulek.core.Core;
import com.paulek.core.basic.II18n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Pattern;


//From EssentialsX https://github.com/EssentialsX/Essentials/blob/fb779533e6dbff299f45376286ce9aab695e9832/Essentials/src/com/earth2me/essentials/I18n.java
public class I18n implements II18n {

    private Core core;
    private static I18n i18n;
    private Locale currentLocale = Locale.getDefault();
    private transient ResourceBundle customBundle;
    private transient ResourceBundle localeBundle;
    private final transient ResourceBundle defaultBundle;
    private transient Map<String, MessageFormat> messageFormatCache = new HashMap<>();
    private static final Pattern NO_DOUBLE_MARK = Pattern.compile("''");
    private final ResourceBundle NULL_BUNDLE = new ResourceBundle() {
        @Override
        protected Object handleGetObject(String key) {
            return null;
        }

        @Override
        public Enumeration<String> getKeys() {
            return null;
        }
    };

    public I18n(Core core) {
        this.core = Objects.requireNonNull(core, "core");
        this.defaultBundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
        this.localeBundle = defaultBundle;
        this.customBundle = NULL_BUNDLE;
    }

    public void init() {
        i18n = this;
    }

    private String translate(final String string) {
        try {
            try {
                return customBundle.getString(string);
            } catch (MissingResourceException ex) {
                return localeBundle.getString(string);
            }
        } catch (MissingResourceException ex) {
            core.getLogger().log(Level.WARNING, String.format("Missing translation key \"%s\" in translation file %s", ex.getKey(), localeBundle.getLocale().toString()), ex);
            return defaultBundle.getString(string);
        }
    }

    public static String tl(final String string, final Object... objects) {
        if (i18n == null) {
            return "";
        }
        if (objects.length == 0) {
            return NO_DOUBLE_MARK.matcher(i18n.translate(string)).replaceAll("'");
        } else {
            return i18n.format(string, objects);
        }
    }

    public String format(final String string, final Object... objects) {
        String format = translate(string);
        MessageFormat messageFormat = messageFormatCache.get(format);
        if (messageFormat == null) {
            try {
                messageFormat = new MessageFormat(format);
            } catch (IllegalArgumentException e) {
                core.getLogger().log(Level.SEVERE, "Invalid Translation key for '" + string + "': " + e.getMessage());
                format = format.replaceAll("\\{(\\D*?)\\}", "\\[$1\\]");
                messageFormat = new MessageFormat(format);
            }
            messageFormatCache.put(format, messageFormat);
        }
        return messageFormat.format(objects).replace(' ', ' '); // replace nbsp with a space
    }

    @Override
    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void updateLocale(final String loc) {
        if (loc != null && !loc.isEmpty()) {
            final String[] parts = loc.split("[_\\.]");
            if (parts.length == 1) {
                currentLocale = new Locale(parts[0]);
            }
            if (parts.length == 2) {
                currentLocale = new Locale(parts[0], parts[1]);
            }
            if (parts.length == 3) {
                currentLocale = new Locale(parts[0], parts[1], parts[2]);
            }
        }

        ResourceBundle.clearCache();
        messageFormatCache = new HashMap<String, MessageFormat>();
        core.getLogger().log(Level.INFO, String.format("Using locale %s", currentLocale.toString()));

        try {
            localeBundle = ResourceBundle.getBundle("messages", currentLocale);
        } catch (MissingResourceException ex) {
            localeBundle = NULL_BUNDLE;
        }

        try {
            customBundle = ResourceBundle.getBundle("messages", currentLocale, new FileResClassLoader(I18n.class.getClassLoader(), core));
        } catch (MissingResourceException ex) {
            customBundle = NULL_BUNDLE;
        }
    }

    public static String capitalCase(final String input) {
        return input == null || input.length() == 0 ? input : input.toUpperCase(Locale.ENGLISH).charAt(0) + input.toLowerCase(Locale.ENGLISH).substring(1);
    }


    private static class FileResClassLoader extends ClassLoader {
        private final transient File dataFolder;

        FileResClassLoader(final ClassLoader classLoader, final Core core) {
            super(classLoader);
            this.dataFolder = core.getDataFolder();
        }

        @Override
        public URL getResource(final String string) {
            final File file = new File(dataFolder, string);
            if (file.exists()) {
                try {
                    return file.toURI().toURL();
                } catch (MalformedURLException ex) {
                }
            }
            return null;
        }

        @Override
        public InputStream getResourceAsStream(final String string) {
            final File file = new File(dataFolder, string);
            if (file.exists()) {
                try {
                    return new FileInputStream(file);
                } catch (FileNotFoundException ex) {
                }
            }
            return null;
        }
    }
}
