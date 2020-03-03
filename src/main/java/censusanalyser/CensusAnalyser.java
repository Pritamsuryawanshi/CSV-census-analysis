package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<IndiaCensusCSV> censusCSVIterator = getCSVIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable = () -> censusCSVIterator;
            int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
            return numOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PASS);
        }
    }

    public int loadIndianStateCode(String indiaStateCsvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaStateCsvFilePath))) {
            Iterator<IndiaStateCodeCSV> censusCSVIterator = getCSVIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> censusCSVIterator;
            int stateCodeCount = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
            return stateCodeCount;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PASS);
        }
    }

    private <E> Iterator<E> getCSVIterator(Reader reader, Class csvClass) {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        return csvToBean.iterator();
    }

}