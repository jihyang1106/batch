package com.example.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

@EnableBatchProcessing
@SpringBootApplication
public class BatchApplication {

	// job을 만들기 위해서 step을 만들어야 함
//	private final JobBuilder jobBuilder; // job을 만들어줄 수 있는 builder
//
//	private final StepBuilder stepBuilder; // step을 만들어줄 수 있는 builder
//
//	public BatchApplication(JobBuilder jobBuilder, StepBuilder stepBuilder) {
//		this.jobBuilder = jobBuilder;
//		this.stepBuilder = stepBuilder;
//	}


	// Step 선언
//	@Bean
//	public Step passStep() {
//		return this.stepBuilder.get("passStep")
//				.tasklet((Tasklet) (contribution, chunkContext) -> {
//					System.out.println("Execute PassStep");
//					return RepeatStatus.FINISHED;
//				}).build();
//	}
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
//		return this.jobBuilder.get("passJob")
//				.start(passStep())
//				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

}
