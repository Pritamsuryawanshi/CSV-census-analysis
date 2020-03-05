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
    List<IndiaCensusDTO> indiaCensusDTOList = null;
    Map<String, IndiaCensusDTO> censusStateMap = null;

    public CensusAnalyser() {
        this.censusStateMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvIterator = csvBuilder.getCSVIterator(reader, IndiaCensusCSV.class);
            while (csvIterator.hasNext()) {
                IndiaCensusCSV indiaCensusCSV = csvIterator.next();
                censusStateMap.put(indiaCensusCSV.state, new IndiaCensusDTO(indiaCensusCSV));
            }
            indiaCensusDTOList = censusStateMap.values().stream().collect(Collectors.toList());
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    public int loadIndianStateCode(String stateCSVIterator) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(stateCSVIterator))) {
            Iterator<IndiaStateCodeCSV> CSVStateIterator = getCSVIterator(reader, IndiaStateCodeCSV.class);

            while (CSVStateIterator.hasNext()) {
                IndiaStateCodeCSV indiaCensusStateCodeCSV = CSVStateIterator.next();
                IndiaCensusDTO indiaCensusDTO = censusStateMap.get(indiaCensusStateCodeCSV.stateName);
                if (indiaCensusDTO == null) {
                    continue;
                }
            }
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    private <E> Iterator<E> getCSVIterator(Reader reader, Class csvClass) {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        return csvToBean.iterator();
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEnteries;
    }

    public String getStateWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        if (indiaCensusDTOList == null || indiaCensusDTOList.size() == 0) {
            throw new CensusAnalyserException("no census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDTO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensus = new Gson().toJson(indiaCensusDTOList);
        return sortedStateCensus;
    }


    private void sort(Comparator<IndiaCensusDTO> censusComparator) {
        for (int i = 0; i < indiaCensusDTOList.size() - 1; i++) {
            for (int j = 0; j < indiaCensusDTOList.size() - i - 1; j++) {
                IndiaCensusDTO census1 = indiaCensusDTOList.get(j);
                IndiaCensusDTO census2 = indiaCensusDTOList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    indiaCensusDTOList.set(j, census2);
                    indiaCensusDTOList.set(j + 1, census1);
                }
            }
        }
    }
}