
package network;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TestServer {

    private static final Map<Integer, String[]> reports =
            new LinkedHashMap<>();

    private static int nextId = 1001;

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(5000)) {

            System.out.println("Test server started on port 5000");

            while (true) {
                try (Socket socket = server.accept();
                     BufferedReader input = new BufferedReader(
                         new InputStreamReader(
                             socket.getInputStream(),
                             StandardCharsets.UTF_8
                         )
                     );
                     PrintWriter output = new PrintWriter(
                         socket.getOutputStream(),
                         true,
                         StandardCharsets.UTF_8
                     )) {

                    String request = input.readLine();

                    System.out.println("Received: " + request);

                    output.println(processRequest(request));

                } catch (IOException e) {
                    System.out.println(
                        "Client error: " + e.getMessage()
                    );
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processRequest(String request) {

        if (request == null) {
            return "ERROR|Empty request";
        }

        if (request.equals("PING")) {
            return "PONG";
        }

        String[] parts = request.split("\\|", -1);

        try {

            switch (parts[0]) {

                case "REPORT_DISASTER":

                    if (parts.length != 7) {
                        return "ERROR|Invalid report data";
                    }

                    int id = nextId++;

                    // Store:
                    // type, location, severity, people,
                    // impact, priority, status, department
                    reports.put(id, new String[]{
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4],
                        parts[5],
                        "Low",
                        "Reported",
                        ""
                    });

                    return "SUCCESS|" + id;

                case "GET_REPORTS":

                    StringBuilder result =
                            new StringBuilder("REPORTS|");

                    for (Map.Entry<Integer, String[]> entry
                            : reports.entrySet()) {

                        String[] r = entry.getValue();

                        if (result.length() > 8) {
                            result.append(";");
                        }

                        result.append(entry.getKey());

                        for (String value : r) {
                            result.append(",").append(value);
                        }
                    }

                    return result.toString();

                case "UPDATE_PRIORITY":

                    if (parts.length != 3) {
                        return "ERROR|Invalid priority request";
                    }

                    int priorityId = Integer.parseInt(parts[1]);

                    if (!reports.containsKey(priorityId)) {
                        return "ERROR|Disaster not found";
                    }

                    reports.get(priorityId)[5] = parts[2];

                    return "SUCCESS|Priority updated";

                case "ASSIGN_DEPARTMENT":

                    if (parts.length != 3) {
                        return "ERROR|Invalid assignment request";
                    }

                    int assignmentId = Integer.parseInt(parts[1]);

                    if (!reports.containsKey(assignmentId)) {
                        return "ERROR|Disaster not found";
                    }

                    reports.get(assignmentId)[7] = parts[2];
                    reports.get(assignmentId)[6] = "Assigned";

                    return "SUCCESS|Department assigned";

                case "UPDATE_STATUS":

                    if (parts.length != 3) {
                        return "ERROR|Invalid status request";
                    }

                    int statusId = Integer.parseInt(parts[1]);

                    if (!reports.containsKey(statusId)) {
                        return "ERROR|Disaster not found";
                    }

                    reports.get(statusId)[6] = parts[2];

                    return "SUCCESS|Status updated";

                default:
                    return "ERROR|Unknown request";
            }

        } catch (NumberFormatException e) {
            return "ERROR|Invalid disaster ID";
        }
    }
}
