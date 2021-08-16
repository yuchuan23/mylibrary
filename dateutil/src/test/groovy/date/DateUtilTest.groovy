package mylib.date

import mylib.date.exception.DateExceptions.DateParseException
import mylib.date.exception.DateExceptions.DateFormatException

import java.time.Instant
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.ZoneId

import spock.lang.Specification

class DateUtilTest extends Specification {
    def "stringToUts without pattern"() {
        expect:
            DateUtil.stringToUts(date) == expectedUts

        where:
            date                        | expectedUts
            "20210101"                  | 1609430400
            "2021-01-01T00:00:00+08:00" | 1609430400
            "2020-12-31T16:00:00Z"      | 1609430400
    }

    def "stringToUts with a specific pattern"() {
        expect:
            DateUtil.stringToUts(date, pattern) == expectedUts

        where:
            date             | pattern            | expectedUts
            "2021"           | "yyyy"             | 1609430400
            "20210101 0000"  | "yyyyMMdd HHmm"    | 1609430400
    }

    def "stringToUts parsing exception"() {
        when:
            long uts = DateUtil.stringToUts(date, pattern)

        then:
            thrown DateParseException

        where:
            date             | pattern
            "0000"           | "yyyy"
    }

    def "utsToString without pattern"() {
        expect:
            DateUtil.utsToString(uts) == expectedStr

        where:
            uts         | expectedStr
            1609430400  | "2021-01-01T00:00:00+08:00"
    }

    def "utsToString with a specific pattern"() {
        expect:
            DateUtil.utsToString(uts, pattern) == expectedStr

        where:
            uts         | pattern                   | expectedStr
            1609430400  | "yyyy-MM-dd"              | "2021-01-01"
            1609430400  | "yyyyMMdd"                | "20210101"
            1609430400  | "yyyy-MM-dd'T'HH:mm:ssXX" | "2021-01-01T00:00:00+0800"
    }

    def "stringToZonedDateTime without pattern"() {
        expect:
            DateUtil.stringToZonedDateTime(date).toEpochSecond() == expectedUts

        where:
            date                        | expectedUts
            "2021-01-01T00:00:00+08:00" | 1609430400
            "2020-12-31T16:00:00Z"      | 1609430400
    }

    def "stringToZonedDateTime with a specific pattern"() {
        expect:
            DateUtil.stringToZonedDateTime(date, pattern).toEpochSecond() == expectedUts

        where:
            date             | pattern            | expectedUts
            "2021"           | "yyyy"             | 1609430400
            "20210101 0000"  | "yyyyMMdd HHmm"    | 1609430400
    }

    def "format from ZonedDateTime without pattern"() {
        expect:
            DateUtil.format(ZonedDateTime.ofInstant(Instant.ofEpochSecond(uts), ZoneId.of("UTC+8"))) == expectedStr

        where:
            uts         | expectedStr
            1609430400  | "2021-01-01T00:00:00+08:00"
    }

    def "format from ZonedDateTime with pattern"() {
        expect:
            DateUtil.format(ZonedDateTime.ofInstant(Instant.ofEpochSecond(uts), ZoneId.of("UTC+8")), pattern) == expectedStr

        where:
            uts         | pattern                   | expectedStr
            1609430400  | "yyyy-MM-dd"              | "2021-01-01"
            1609430400  | "yyyyMMdd"                | "20210101"
            1609430400  | "yyyy-MM-dd'T'HH:mm:ssXX" | "2021-01-01T00:00:00+0800"
    }

    def "format from LocalDate without pattern"() {
        expect:
            DateUtil.format(ZonedDateTime.ofInstant(Instant.ofEpochSecond(uts), ZoneId.of("UTC+8")).toLocalDate()) == expectedStr

        where:
            uts         | expectedStr
            1609430400  | "2021-01-01"
    }

    def "format from LocalDate with pattern"() {
        expect:
            DateUtil.format(ZonedDateTime.ofInstant(Instant.ofEpochSecond(uts), ZoneId.of("UTC+8")).toLocalDate(), pattern) == expectedStr

        where:
            uts         | pattern                   | expectedStr
            1609430400  | "yyyyMMdd"                | "20210101"
    }

    def "utsToZonedDateTime"() {
        when:
            ZonedDateTime zdt = DateUtil.utsToZonedDateTime(uts)
            println "getCurrentTimeSecond($uts) => $zdt"

        then:
            noExceptionThrown()

        where:
            uts         | _
            1609430400  | _
    }

    def "getCurrentTimeSecond"() {
        when:
            long uts = DateUtil.getCurrentTimeSecond()
            println "getCurrentTimeSecond() => $uts"

        then:
            noExceptionThrown()
    }

    def "getCurrentTimeString without pattern"() {
        when:
            String dateString = DateUtil.getCurrentTimeString()
            println "getCurrentTimeString() => $dateString"

        then:
            noExceptionThrown()

        where:
            uts         | expectedStr
            1609430400  | "2021-01-01T00:00:00+08:00"
    }

    def "getCurrentTimeString with a specific pattern"() {
        when:
            String dateString = DateUtil.getCurrentTimeString(pattern)
            println "getCurrentTimeString(\"$pattern\") => $dateString"

        then:
            noExceptionThrown()

        where:
            pattern         | _
            "yyyy"          | _
            "yyyy-MM-dd"    | _
    }
}

