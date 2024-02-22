package com.example.springbatch.job.listener;

import com.example.springbatch.job.constant.ImportCustomerConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

@Slf4j
public class SkipCheckingListener implements StepExecutionListener {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String exitCode = stepExecution.getExitStatus().getExitCode();
        if (!exitCode.equals(ExitStatus.FAILED.getExitCode()) && stepExecution.getSkipCount() > 0) {
            return new ExitStatus(ImportCustomerConstant.COMPLETED_WITH_SKIP_STATUS);
        }
        return stepExecution.getExitStatus();
    }
}
