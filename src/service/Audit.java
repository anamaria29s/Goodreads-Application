package service;


import model.AuditEntity;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static service.DatabaseConnection.connection;

public class Audit {
    private static Audit instance;
    private static FileWriter writer;
    private static String path;

    private Audit(){
        try{
            path = "audit.csv";
            writer  = new FileWriter(path);
            writer.append("USER, TABLE, ACTION, TIMESTAMP\n");
            writer.flush();
        }catch (Exception e){
            System.out.println("Couldn`t open file for audit");
        }
    }


    public static Audit getInstance() {
        if (instance == null) {
            instance = new Audit();
        }
        return instance;
    }

    public static void log(AuditEntity audit){
        try {
            writer.append(audit.getSchema());
            writer.append(", ");
            writer.append(audit.getTable());
            writer.append(", ");
            writer.append(audit.getActionName());
            writer.append(", ");
            writer.append(audit.getTimestamp());
            writer.append("\n");

            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void log_multiple(ArrayList<AuditEntity> list) {
        try {
            for (var item: list) {
                Audit.log(item);
            }

            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void afisare() {
        String sql = """
                     SELECT object_schema, object_name, action_name, event_timestamp
                     FROM UNIFIED_AUDIT_TRAIL
                     WHERE current_user = 'java' AND LOWER(UNIFIED_AUDIT_POLICIES) = 'audit_operations'
                     """;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("SCHEMA | TABLE | ACTION | TIMESTAMP");
            System.out.println("------------------------------------");

            while (rs.next()) {
                String schema = rs.getString("object_schema");
                String table = rs.getString("object_name");
                String action = rs.getString("action_name");
                String timestamp = rs.getString("event_timestamp");

                System.out.printf("%s | %s | %s | %s%n", schema, table, action, timestamp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching audit trail data", e);
        }
    }
}