
package network;

public class TestClient {

    public static void main(String[] args) {

        DRSClient client = new DRSClient("localhost", 5000);

        try {
            System.out.println("Testing server connection...");

            String ping = client.sendRequest("PING");
            System.out.println("PING Response: " + ping);

            String report = client.sendRequest(
                "REPORT_DISASTER|Fire|Sydney|4|15|Severe|Test fire"
            );
            System.out.println("Report Response: " + report);

            String reports = client.sendRequest("GET_REPORTS");
            System.out.println("GET_REPORTS Response: " + reports);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
