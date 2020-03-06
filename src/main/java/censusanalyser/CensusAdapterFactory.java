package censusanalyser;

import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusAdapterFactory {

    public Map<String, CensusDTO> getCensusAdaptor(CensusAnalyser.Country country, String... csvFilePath) {
        if (country.equals(CensusAnalyser.Country.INDIA)) {
            return new IndianCensusAdapter().loadCensusData(csvFilePath);
        } else if (country.equals(CensusAnalyser.Country.US)) {
            return  new USCensusAdapter().loadCensusData( csvFilePath);
        } else
            throw new CensusAnalyserException("INVALID COUNTRY", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }

}
