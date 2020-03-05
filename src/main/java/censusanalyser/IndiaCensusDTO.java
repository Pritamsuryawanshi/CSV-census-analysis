package censusanalyser;

public class IndiaCensusDTO {

    public long densityPerSqKm;
    public long areaInSqKm;
    public long population;
    public String state;
    public String stateCode;

    public IndiaCensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }
}
