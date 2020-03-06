package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndianCensusAdapter extends CensusAdapter {
    Map<String, CensusDTO> censusMap = null;

    @Override
    public Map<String, CensusDTO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        this.loadIndianStateCode(csvFilePath[1]);
        return censusMap;
    }

    private <E> Map loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> CSVStateIterator = csvBuilder.getCSVIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> CSVStateIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> censusMap.get(csvState.state) != null)
                    .forEach(csvState -> censusMap.get(csvState.state).stateCode = csvState.stateCode);
            return censusMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}

