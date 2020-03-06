package censusanalyser;

import com.google.gson.Gson;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class USCensusAdapter extends CensusAdapter{

    public Map<String, CensusDTO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDTO> censusMap = super.loadCensusData(USCensusCSV.class, csvFilePath[0]);
        return censusMap;
    }


}
