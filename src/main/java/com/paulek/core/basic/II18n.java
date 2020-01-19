package com.paulek.core.basic;

import java.util.Locale;

public interface II18n {

    /**
     * Get the current plugin locale settings
     *
     * @return the current locale, if not set it will return the default locale
     */
    default Locale getCurrentLocale() {
        return Locale.getDefault();
    }

}
