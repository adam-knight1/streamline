package converter;

import com.amazonaws.services.lambda.runtime.events.S3BatchEvent;
import com.kenzie.capstone.service.model.TaskRecord;
import com.kenzie.capstone.service.model.TaskRequest;
import com.kenzie.capstone.service.model.TaskResponse;

import java.time.ZonedDateTime;

public class TaskConverter {

    public static TaskRecord fromRequestToRecord(TaskRequest task) {
        TaskRecord record = new TaskRecord();
        record.setTaskDescription(task.getTaskDescription());
        record.setTaskId(task.getTaskId());
        record.setTaskName(task.getTaskName());
        record.setUserId(task.getUserId());
        record.setCompleted(task.isCompleted());
        return record;
    }

    public static TaskResponse fromRecordToResponse(TaskRecord record) {
        TaskResponse referral = new TaskResponse();
        referral.setTaskDescription(record.getTaskDescription());
        referral.setTaskId(record.getTaskId());
        referral.setTaskName(record.getTaskName());
        referral.setUserId(record.getUserId());
        referral.setCompleted(record.isCompleted());
        return referral;
    }

}
