package censusanalyser;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<CensusDTO> censusDTOList = null;
    Map<String, CensusDTO> censusMap = null;

    public CensusAnalyser() {
        this.censusMap = new HashMap<>();
    }

    public int loadUSCensusData(String csvFilePath) {
        return new LoadCensusData().loadCensusData(csvFilePath, USCensusCSV.class).size();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        censusMap = new LoadCensusData().loadCensusData(csvFilePath, IndiaCensusCSV.class);
        return censusMap.size();
    }

    public int loadIndianStateCode(String stateCSVIterator) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(stateCSVIterator))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> CSVStateIterator = csvBuilder.getCSVIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> CSVStateIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusMap.get(csvState.state) != null)
                    .forEach(csvState -> censusMap.get(csvState.state).stateCode = csvState.stateCode);
            return censusMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        if (censusDTOList == null || censusDTOList.size() == 0) {
            throw new CensusAnalyserException("no census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensus = new Gson().toJson(censusDTOList);
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