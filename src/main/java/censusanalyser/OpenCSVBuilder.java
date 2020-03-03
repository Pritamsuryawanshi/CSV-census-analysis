package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder {
    public <E> Iterator<E> getCSVIterator(Reader reader, Class<E> indiaCensusCSVClass) {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(indiaCensusCSVClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        return csvToBean.iterator();
    }
}
