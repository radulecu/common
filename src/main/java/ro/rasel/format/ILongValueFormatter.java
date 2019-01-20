package ro.rasel.format;

public interface ILongValueFormatter {
    String getPrefix();

    String getSuffix();

    String getPluralSuffix();

    String format(long value);

    Long parse(String value);
}
