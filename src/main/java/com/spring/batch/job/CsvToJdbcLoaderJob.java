package com.spring.batch.job;

import com.spring.batch.config.CustomersJobConfig;
import com.spring.batch.model.StudentCsv;
import com.spring.batch.service.CustomerItemProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class CsvToJdbcLoaderJob {

    private Logger logger = LoggerFactory.getLogger(CsvToJdbcLoaderJob.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private CustomersJobConfig customersJobConfig;

    @Value("${spring.batch.inputfile.dirpath}")
    private String fileFullPath;

    public Job csvToJdbcLoaderJob() {
        return jobBuilderFactory.get("csv2jdbcJob")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .build();
    }

    private Step firstChunkStep() {
        return stepBuilderFactory.get("First Chunk Step")
                .<StudentCsv, StudentCsv>chunk(3)
                .reader(flatFileItemReader())
                .processor(csvToJdbcProcessor())
                .writer(jdbcBatchItemWriter())
                .build();
    }

    public FlatFileItemReader<StudentCsv> flatFileItemReader() {
        FlatFileItemReader<StudentCsv> flatFileItemReader =
                new FlatFileItemReader<StudentCsv>();

        flatFileItemReader.setResource(new FileSystemResource(new File(getFileFullPath())));

        flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("ID", "First Name", "Last Name", "Email");
                        setDelimiter("|");
                    }
                });

                setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>() {
                    {
                        setTargetType(StudentCsv.class);
                    }
                });

            }
        });

        flatFileItemReader.setLinesToSkip(1);

        return flatFileItemReader;
    }

    public ItemProcessor csvToJdbcProcessor(){
        return new ItemProcessor<StudentCsv, StudentCsv>() {
            @Override
            public StudentCsv process(StudentCsv modelObj) throws Exception {
                logger.info("Inside CSV To JDBC Item Processor");
                modelObj.setLastName((modelObj.getLastName() + " Enriched "));
                return modelObj;
            }
        };
    }

    //@Bean
    public JdbcBatchItemWriter<StudentCsv> jdbcBatchItemWriter() {
        JdbcBatchItemWriter<StudentCsv> jdbcBatchItemWriter =
                new JdbcBatchItemWriter<StudentCsv>();

        jdbcBatchItemWriter.setDataSource(customersJobConfig.universitydatasource());
        jdbcBatchItemWriter.setSql(
                "insert into student(id, first_name, last_name, email) "
                        + "values (?,?,?,?)");

        jdbcBatchItemWriter.setItemPreparedStatementSetter(
                new ItemPreparedStatementSetter<StudentCsv>() {

                    @Override
                    public void setValues(StudentCsv item, PreparedStatement ps) throws SQLException {
                        ps.setLong(1, item.getId());
                        ps.setString(2, item.getFirstName());
                        ps.setString(3, item.getLastName());
                        ps.setString(4, item.getEmail());
                    }
                });

        return jdbcBatchItemWriter;
    }

    public String getFileFullPath() {
        return fileFullPath;
    }

    public void setFileFullPath(String fileFullPath) {
        this.fileFullPath = fileFullPath;
    }

}
