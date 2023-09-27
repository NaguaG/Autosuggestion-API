package com.example.autosuggestion;

import com.google.common.io.Files;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableRedisDocumentRepositories(basePackages = "com.example.autosuggestion.*")
@EnableSwagger2
public class AutosuggestionApplication {
	@Bean
	public CommandLineRunner loadData(AutoSuggestRepository autoSuggestRepository, @Value("${XLSX_FILE_PATH}") Resource dataResource) throws IOException {
		return args -> {
			autoSuggestRepository.deleteAll();

			try (InputStream inputStream = dataResource.getInputStream();
				 Workbook workbook = new XSSFWorkbook(inputStream)) {

				Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

				for (Row row : sheet) {
					if (row.getRowNum() == 0) {
						continue; // Skip the header row
					}
					String name = row.getCell(0).getStringCellValue();
					String fullName = row.getCell(1).getStringCellValue();
					String type = row.getCell(2).getStringCellValue();
					String state = row.getCell(3).getStringCellValue();
					String country = row.getCell(4).getStringCellValue();
					String hierarchyPath = row.getCell(5).getStringCellValue();
					String latitude = row.getCell(6).getStringCellValue();
					String longitude = row.getCell(7).getStringCellValue();

					Location location = Location.of(name, fullName, type, state, country, hierarchyPath, latitude, longitude);
					autoSuggestRepository.save(location);
				}
			}
		};
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}



	public static void main(String[] args) {
		SpringApplication.run(AutosuggestionApplication.class, args);
	}

}
