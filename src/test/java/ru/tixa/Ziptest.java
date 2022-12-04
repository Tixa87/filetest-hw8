package ru.tixa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import ru.tixa.model.Datajson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class Ziptest {
    ClassLoader cl = Ziptest.class.getClassLoader();

    @Test
    void zipParseTest() throws Exception {
        try (
                InputStream is = cl.getResourceAsStream("ArchiveTest.zip");
                ZipInputStream zis = new ZipInputStream(is)
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains(".pdf") && !entry.getName().contains("MACOSX")) {
                    PDF content = new PDF(zis);
                    assertThat(content.text).contains("KNOW BEFORE YOU GO");


                } else if (entry.getName().contains(".xlsx") && !entry.getName().contains("MACOSX")) {
                    XLS content = new XLS(zis);
                    assertThat(content.excel.getSheetAt(0).getRow(4).getCell(1).getStringCellValue()).contains("78846");


                } else if (entry.getName().contains(".csv") && !entry.getName().contains("MACOSX")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = reader.readAll();
                    assertThat(content.get(18)[1]).contains("A");
                }
            }
        }
    }
    @Test
    void jsonParseTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File dataJsonfile = new File("src/test/resources/data.json");
        Datajson data = objectMapper.readValue(dataJsonfile, Datajson.class);


        assertThat(Datajson.events_data.id()).contains("47946124");
        assertThat(Datajson.events_data.client_id()).contains("62526");


    }


}



