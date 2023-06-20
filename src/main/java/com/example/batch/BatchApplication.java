package com.example.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

//@EnableBatchProcessing
@SpringBootApplication
public class BatchApplication {

	@Bean
	public Step passStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
		return new StepBuilder("passStep", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					System.out.println("Execute passStep");
					return RepeatStatus.FINISHED;
				}, platformTransactionManager).build();
	}

	// Job선언
	@Bean
	public Job passJob(JobRepository jobRepository, Step passStep) {
		return new JobBuilder("passJob", jobRepository)
				.start(passStep)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

}
