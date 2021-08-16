package mylib.date;

import mylib.date.exception.DateExceptions.DateParseException;
import mylib.date.exception.DateExceptions.DateFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String TAIWAN_ZONE = "Asia/Taipei";
    private static final ZoneId TAIWAN_ZONEID = ZoneId.of("UTC+8");
    private static final ZoneOffset TAIWAN_ZONEOFFSET = ZoneOffset.of("+08:00");

    /**
    * Converts date string to uts.
    * date string accepts "yyyyMMdd" and iso 8601 (2021-12-31T16:00:00Z or 2021-01-01T00:00:00+08:00)
    *
    * @param date date string ("yyyyMMdd" or iso 8601 string)
    * @return Unix Time Stamp (in seconds)
    */
    public static long stringToUts(String date) {
        if (date.length() == 8) {
            return stringToUts(date, "yyyyMMdd");
        }
        // iso 8601 to uts
        return stringToUts(date, "yyyy-MM-dd'T'HH:mm:ssXXX");
    }

    /**
    * Converts date string to uts using a specific pattern.
    *
    * @param date date string ("yyyyMMdd" or iso 8601 string)
    * @param pattern a pattern to parse date string, e.g.: "yyyy-MM-dd".
    * @return Unix Time Stamp (in seconds)
    */
    public static long stringToUts(String date, String pattern) {
        try {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(pattern)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
                .parseDefaulting(ChronoField.OFFSET_SECONDS, TAIWAN_ZONEOFFSET.getTotalSeconds())
                .toFormatter();

            ZonedDateTime zdt = ZonedDateTime.parse(date, formatter);
            return zdt.toEpochSecond();
        } catch (Exception e) {
            logger.error("Failed to convert string to Uts, err = {}", e.getMessage());
            throw new DateParseException("cannot convert to uts");
        }
    }

    /**
    * Converts uts to iso 8601 date string.
    *
    * @param uts Unix Time Stamp (in seconds)
    * @return iso 8601 date string
    */
    public static String utsToString(long uts) {
        return utsToString(uts, "yyyy-MM-dd'T'HH:mm:ssXXX");
    }

    /**
    * Converts uts to a formatted date string.
    *
    * @param uts Unix Time Stamp (in seconds)
    * @param pattern a pattern to format, e.g.: "yyyy-MM-dd"
    * @return formatted date string
    */
    public static String utsToString(long uts, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            return ZonedDateTime.ofInstant(Instant.ofEpochSecond(uts), TAIWAN_ZONEID).format(formatter);
        } catch (Exception e) {
            logger.error("Failed to convert uts to date string, err = {}", e.getMessage());
            throw new DateFormatException("cannot convert to date string");
        }
    }

    /**
    * Converts date string to ZonedDateTime instance.
    * date string accepts iso 8601 format string (2021-12-31T16:00:00Z or 2021-01-01T00:00:00+08:00)
    *
    * @param date iso 8601 date string, e.g.: 2021-01-01T00:00:00+08:00
    * @return ZonedDateTime instance
    */
    public static ZonedDateTime stringToZonedDateTime(String date) {
        return stringToZonedDateTime(date, "yyyy-MM-dd'T'HH:mm:ssXXX");
    }

    /**
    * Converts date string to ZonedDateTime instance.
    *
    * @param date date string, e.g.: 2021-01-01
    * @param pattern a pattern to parse date string, e.g.: "yyyy-MM-dd".
    * @return ZonedDateTime instance
    */
    public static ZonedDateTime stringToZonedDateTime(String date, String pattern) {
        try {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(pattern)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
                .parseDefaulting(ChronoField.OFFSET_SECONDS, TAIWAN_ZONEOFFSET.getTotalSeconds())
                .toFormatter();

            return ZonedDateTime.parse(date, formatter);
        } catch (Exception e) {
            logger.error("Failed to convert string to ZonedDateTime, err = {}", e.getMessage());
            throw new DateParseException("cannot convert to ZonedDateTime");
        }
    }

    /**
    * Converts ZonedDateTime instance to a iso 8601 date string.
    *
    * @param zdt ZonedDateTime instance
    * @return iso 8601 date string
    */
    public static String format(ZonedDateTime zdt) {
        return format(zdt, "yyyy-MM-dd'T'HH:mm:ssXXX");
    }

    /**
    * Converts ZonedDateTime instance to a formatted date string.
    *
    * @param zdt ZonedDateTime instance
    * @param pattern a pattern to format, e.g.: "yyyy-MM-dd"
    * @return formatted date string
    */
    public static String format(ZonedDateTime zdt, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            return zdt.format(formatter);
        } catch (Exception e) {
            logger.error("Failed to convert ZonedDateTime to date string, err = {}", e.getMessage());
            throw new DateFormatException("cannot convert to date string");
        }
    }

    /**
    * Converts LocalDate instance to "yyyy-MM-dd" date string.
    *
    * @param local LocalDate instance
    * @return date string "yyyy-MM-dd"
    */
    public static String format(LocalDate local) {
        return format(local, "yyyy-MM-dd");
    }

    /**
    * Converts LocalDate instance to a formatted date string.
    *
    * @param local LocalDate instance
    * @param pattern a pattern to format, e.g.: "yyyyMMdd"
    * @return formatted date string
    */
    public static String format(LocalDate local, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            return local.format(formatter);
        } catch (Exception e) {
            logger.error("Failed to convert LocalDate to date string, err = {}", e.getMessage());
            throw new DateFormatException("cannot convert to date string");
        }
    }

    /**
    * Converts uts to ZonedDateTime instance.
    *
    * @param uts Unix Time Stamp (in seconds)
    * @return ZonedDateTime instance
    */
    public static ZonedDateTime utsToZonedDateTime(long uts) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(uts), TAIWAN_ZONEID);
    }

    /**
    * get current time in seconds.
    *
    * @return uts Unix Time Stamp (in seconds)
    */
    public static long getCurrentTimeSecond() {
        return Instant.now().getEpochSecond();
    }

    /**
    * get current time in iso 8601 format string.
    *
    * @return iso 8601 format string
    */
    public static String getCurrentTimeString() {
        return getCurrentTimeString("yyyy-MM-dd'T'HH:mm:ssXXX");
    }

    /**
    * get current time in a specific date format.
    *
    * @param pattern a pattern to format, e.g.: "yyyy-MM-dd"
    * @return formatted date string
    */
    public static String getCurrentTimeString(String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            return ZonedDateTime.now(TAIWAN_ZONEID).format(formatter);
        } catch (Exception e) {
            logger.error("Failed to getCurrentTimeString, err = {}", e.getMessage());
            throw new DateFormatException(String.format("cannot format current time to %s", pattern));
        }
    }
}
