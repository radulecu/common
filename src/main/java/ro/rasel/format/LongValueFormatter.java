package ro.rasel.format;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LongValueFormatter implements ILongValueFormatter {
    private final String prefix;
    private final String suffix;
    private final String pluralSuffix;
    private final Pattern pattern;

    public LongValueFormatter(String suffix) {
        this(suffix, suffix);
    }

    public LongValueFormatter(String suffix, String pluralSuffix) {
        this("", suffix, pluralSuffix);
    }

    public LongValueFormatter(String prefix, String suffix, String pluralSuffix) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.pluralSuffix = pluralSuffix;
        pattern = Pattern.compile(
                "\\A *\\Q" + ((prefix + "\\E *(\\d+) *(\\Q" + suffix).trim() + "\\E|" + "\\Q" + pluralSuffix).trim() +
                        "\\E" + ") *\\Z");
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    @Override
    public String getPluralSuffix() {
        return pluralSuffix;
    }

    @Override
    public String format(long value) {
        return prefix + value + (value == 1 ? suffix : pluralSuffix);
    }

    @Override
    public Long parse(String value) {
        Objects.requireNonNull(value, "value argument should not be null");
        pattern.matcher(value);
        try {
            return Optional.of(value)
                    .map(pattern::matcher)
                    .filter(Matcher::matches)
                    .map(matcher -> matcher.replaceAll("$1"))
                    .map(Long::valueOf)
                    .orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
