package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {
    List<CensusDTO> censusDTOList;
    Map<String, CensusDTO> censusMap = new HashMap<>();
    Map<SortField, Comparator<CensusDTO>> sortMap;

    public enum Country {
        INDIA, US;
    }


    public CensusAnalyser() {
        this.sortMap = new HashMap<>();
        this.sortMap.put(SortField.STATE, Comparator.comparing(census -> census.state));
        this.sortMap.put(SortField.POPULATION, Comparator.comparing(census -> census.population));
        this.sortMap.put(SortField.POPULATION_DENSITY, Comparator.comparing(census -> census.densityPerSqKm));
        this.sortMap.put(SortField.TOTAL_AREA, Comparator.comparing(census -> census.totalArea));
    }


    public int loadCensusData(Country country, String... csvFilePath) {
        censusMap = new CensusAdapterFactory().getCensusAdaptor(country, csvFilePath);
        return censusMap.size();
    }

    public String getStateWiseSortedCensusData(SortField sortField) throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0) {
            throw new CensusAnalyserException("no census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        censusDTOList = censusMap.values().stream().collect(Collectors.toList());
        this.sort(this.sortMap.get(sortField).reversed());
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