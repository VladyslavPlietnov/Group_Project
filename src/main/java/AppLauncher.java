import org.example.FrontEnd.FrontEnd;
import org.example.GameBody.GameBody;
import org.example.Parser.CityParser;

import java.util.Set;

public class AppLauncher {
    public static void start() {
        Set<String> citySet = new CityParser().parseCities();

        GameBody.gameBody().setAllCities(citySet);
        new FrontEnd();
    }

    public static void main(String[] args) {
        start();
    }
}
