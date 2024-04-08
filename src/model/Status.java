package model;

public record Status(String value) {
    public static final Status READ = new Status("read");
    public static final Status TO_READ = new Status("to read");
    public static final Status READING = new Status("reading");

    public static Status fromString(String value) {
        switch (value.toLowerCase()) {
            case "read":
                return READ;
            case "to read":
                return TO_READ;
            case "reading":
                return READING;
            default:
                throw new IllegalArgumentException("Invalid status: " + value);
        }
    }
}
