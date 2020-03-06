package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {
    List<CensusDTO> censusDTOList = null;
    Map<String, CensusDTO> censusMap = null;

    public enum Country {
        INDIA, US;
    }

    public CensusAnalyser() {
        this.censusMap = new HashMap<>();
    }

    public int loadCensusData(Country country, String... csvFilePath) {
        censusMap = new CensusAdapterFactory().getCensusAdaptor(country, csvFilePath);
        return censusMap.size();
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusDTOList == null || censusDTOList.size() == 0) {
            throw new CensusAnalyserException("no census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator = Comparator.comparing(census -> census.state);
        censusDTOList = censusMap.values().stream().collect(Collectors.toList());
        this.sort(censusComparator);
        String sortedStateCensus = new Gson().toJson(this.censusDTOList);
        return sortedStateCensus;
    }

    private void sort(Comparator<CensusDTO> censusComparator) {
        for (int i = 0; i < censusDTOList.size() - 1; i++) {
            for (int j = 0; j < censusDTOList.size() - i - 1; j++) {
                CensusDTO census1 = censusDTOList.get(j);
                CensusDTO census2 = censusDTOList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusDTOList.set(j, census2);
                    censusDTOList.set(j + 1, census1);
                }
            }
        }
    }
}