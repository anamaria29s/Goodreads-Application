package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditEntity {
    private String schema;
    private String table;
    private String actionName;

    private LocalDateTime timestamp;

    public AuditEntity(String schema, String table, String actionName, LocalDateTime timestamp) {
        this.schema = schema;
        this.table = table;
        this.actionName = actionName;
        this.timestamp = timestamp;
    }

    public AuditEntity() {}

    public String getSchema() {
        return schema;
    }

    public String getTable() {
        return table;
    }

    public String getActionName() {
        return actionName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setSchema(String schema) {this.schema = schema;}

    public void setTable(String table) {this.table = table;}


    public void setActionName(String actionName) { this.actionName = actionName;}

    public void setTimestamp(LocalDateTime timestamp){this.timestamp = timestamp;}

}