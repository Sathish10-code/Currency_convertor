import org.json.JSONObject;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConvertor {
    private static final HashMap<String, String> currencySymbols = new HashMap<>();
    static {
        currencySymbols.put("INR", "₹");
        currencySymbols.put("USD", "$");
        currencySymbols.put("EUR", "€");
        currencySymbols.put("HKD", "HK$");
        currencySymbols.put("CAD", "C$");
        currencySymbols.put("CHF", "CHF");
        currencySymbols.put("JPY", "¥");
        currencySymbols.put("GBP", "£");
    }
        public static void main(String[] args) throws IOException {
        HashMap<Integer,String> currencyCodes = new HashMap<Integer,String>();
        currencyCodes.put(1,"INR");
        currencyCodes.put(2,"USD");
        currencyCodes.put(3,"EUR");
        currencyCodes.put(4,"HKD");
        currencyCodes.put(5,"CAD");
        currencyCodes.put(6,"CHF");
        currencyCodes.put(7,"JPY");
        currencyCodes.put(8,"GBP");

        String fromCode,toCode;
        double amount;

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Currency Convertor!");


        System.out.print("Currency Converting From?:");
        currencyCodes.forEach((key, value) -> System.out.println(key + ". " + value));
        System.out.println("Please enter your currency code:");
        fromCode = currencyCodes.get(sc.nextInt());


        System.out.print("Currency Converting To?:");
        currencyCodes.forEach((key, value) -> System.out.println(key + ". " + value));
        System.out.println("Please enter your currency code:");
        toCode = currencyCodes.get(sc.nextInt());

        System.out.print("Amount do you want to convert:");
        amount = sc.nextDouble();


        convertCurrency(fromCode,toCode,amount);

        System.out.print("Thank you for using the currency convertor! Have a nice day!");

    }

    private static void convertCurrency(String fromCode,String toCode,double amount) throws IOException {
        String GET_URL =  "https://open.er-api.com/v6/latest/" + fromCode;

        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode =  httpURLConnection.getResponseCode();

        if(responseCode ==HttpURLConnection.HTTP_OK){  //SUCCESS
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine=in.readLine()) !=null){
                response.append(inputLine);
            }in.close();

            JSONObject obj = new JSONObject(response.toString());

            double exchangeRate = obj.getJSONObject("rates").getDouble(toCode);
            double convertedAmount = amount * exchangeRate;
            String fromSymbol = currencySymbols.getOrDefault(fromCode,"");
            String toSymbol = currencySymbols.getOrDefault(toCode,"");
            System.out.println(obj.getJSONObject("rates"));
            System.out.println("--------------------------------------");
            System.out.println("Exchange Rate: 1 " + fromCode + " = " + exchangeRate + " " + toCode);
            System.out.println(amount + " " + fromCode + " (" + fromSymbol + amount + ") = "
                    + convertedAmount + " " + toCode + " (" + toSymbol + convertedAmount + ")");
            System.out.println("--------------------------------------");

        }
        else{
            System.out.println("Error| GET Request Failed!");
        }
    }
}
