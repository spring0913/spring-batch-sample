package org.example.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class SpringBatchConfig extends DefaultBatchConfiguration {

    /*
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("/org/springframework/batch/core/schema-h2.sql")
                .generateUniqueName(true).build();
    }
    */

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                   Step step) {
        return new JobBuilder("job", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    System.out.println("Hello, world!");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
