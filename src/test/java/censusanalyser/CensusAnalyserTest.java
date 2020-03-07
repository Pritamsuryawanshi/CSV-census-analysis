package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATE);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecords() {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int USCensusCount = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
        Assert.assertEquals(51, USCensusCount);
    }

    @Test
    public void givenIndianCensusData_WhenSortedByPopulation_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATION);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals(199812341, censusCSV[0].population);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByPopulationDensity_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATION_DENSITY);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals(1102.0, censusCSV[0].densityPerSqKm, 0.0);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByArea_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.TOTAL_AREA);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals(342239.0, censusCSV[0].totalArea, 0.0);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenIndianStateCodeCSV_WhenSortedByStateId_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATE_CODE);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("AP", censusCSV[censusCSV.length - 1].stateCode);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenUSCensusData_WhenSortedByState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATE);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("Alabama", censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
        }
    }


    @Test
    public void givenUSCensusData_WhenSortedByPopulation_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATION);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals(37253956, censusCSV[0].population);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByPopulationDensity_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATION_DENSITY);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals(3805.61, censusCSV[0].densityPerSqKm, 0.0);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedByArea_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.TOTAL_AREA);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals(1723338.01, censusCSV[0].totalArea, 0.0);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSStateCodeCSV_WhenSortedByStateId_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortField.STATE_CODE);
            CensusDTO[] censusCSV = new Gson().fromJson(sortedCensusData, CensusDTO[].class);
            Assert.assertEquals("AK", censusCSV[censusCSV.length - 1].stateCode);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusCSVAndUSCensusCsv_WhenSortedByState_ShouldReturnMostPopulousState() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();

            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CSV_FILE_PATH);
            String sortedCensusDataOfIndia = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATION);
            CensusDTO[] censusCSVIndia = new Gson().fromJson(sortedCensusDataOfIndia, CensusDTO[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSVIndia[0].state);

            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CSV_FILE_PATH);
            String sortedCensusDataOfUS = censusAnalyser.getStateWiseSortedCensusData(SortField.POPULATION);
            CensusDTO[] censusCSVUS = new Gson().fromJson(sortedCensusDataOfUS, CensusDTO[].class);
            Assert.assertEquals("California", censusCSVUS[0].state);

            Assert.assertTrue(Double.compare(censusCSVUS[0].population, censusCSVIndia[0].population) < 0);

        } catch (CensusAnalyserException e) {
        }
    }
}