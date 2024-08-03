import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class CurrencyConverter {

    public static double getExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        String apiUrl = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/" + baseCurrency
                + ".json";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject jsonObject = new JSONObject(inline.toString());
            JSONObject rates = jsonObject.getJSONObject(baseCurrency);
            return rates.getDouble(targetCurrency);
        }
    }

    public static void main(String[] args) {
        System.out.println("------------------------------------------------");
        System.out.println("                CURRENCY CONVERTER              ");
        System.out.println("------------------------------------------------");
        System.out.println();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter base currency (e.g., usd)  : ");
            String baseCurrency = scanner.nextLine().toLowerCase();
            System.out.println();

            System.out.print("Enter target currency (e.g., inr): ");
            String targetCurrency = scanner.nextLine().toLowerCase();
            System.out.println();

            System.out.print("Enter amount to convert: ");
            double amount = scanner.nextDouble();
            System.out.println();
            System.out.println();

            double rate = getExchangeRate(baseCurrency, targetCurrency);
            double convertedAmount = amount * rate;
            System.out.println("------------------------------------------------");
            System.out.printf("%.2f %s is %.2f %s\n", amount, baseCurrency, convertedAmount, targetCurrency);
            System.out.println("------------------------------------------------");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
