package formatting;

import java.text.Format;

public class ColumnInfo<T> {
    private final String columnName;
    private final int columnLength;
    private final Format formatter;
    private final FieldValueExtractor<T, ?> fieldValueExtractor;

    public ColumnInfo(String columnName, int columnLength, Format formatter, FieldValueExtractor<T, ?> fieldValueExtractor) {
        this.columnName = columnName;
        this.columnLength = columnLength;
        this.formatter = formatter;
        this.fieldValueExtractor = fieldValueExtractor;
    }

    public String getColumnName() {
        return columnName;
    }

    public int getColumnLength() {
        return columnLength;
    }

    public Format getFormatter() {
        return formatter;
    }

    public FieldValueExtractor<T, ?> getFieldValueExtractor() {
        return fieldValueExtractor;
    }
}
