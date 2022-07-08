package com.epam.brest.kafkaweb.producer;

import com.epam.brest.Lector;
import com.epam.brest.LectorsSchedule;
import com.epam.brest.StudentsSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleProducerServiceWeb {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleProducerServiceWeb.class);

    @Autowired
    private KafkaTemplate<String, String> stringKafkaTemplate;

    private String givescheduleforallstudents = "givescheduleforallstudents";
    private String givescheduleforalllectors = "givescheduleforalllectors";
    private String createschedule = "createschedule";
    private String givescheduleforlector = "givescheduleforlector";
    private String givescheduleforgroup = "givescheduleforgroup";

    public void sendCreateSchedule(String message) {
        logger.info("ScheduleProducerServiceWeb send CreateSchedule");
        stringKafkaTemplate.send(createschedule, message);
    }

    public void sendGiveScheduleForAllLectors(String message) {
        logger.info("ScheduleProducerServiceWeb send GiveScheduleForAllLectors");
        stringKafkaTemplate.send(givescheduleforalllectors, message);
    }

    public void sendGiveScheduleForAllStudents(String message) {
        logger.info("ScheduleProducerServiceWeb send GiveScheduleForAllStudents");
        stringKafkaTemplate.send(givescheduleforallstudents, message);
    }

    public void sendGiveScheduleForGroup(Integer groupId) {
        logger.info("ScheduleProducerServiceWeb send GiveScheduleForGroup");
        stringKafkaTemplate.send(givescheduleforgroup, "" + groupId);
    }

    public void sendGiveScheduleForLector(Integer lectorId) {
        logger.info("ScheduleProducerServiceWeb send GiveScheduleForLector");
        stringKafkaTemplate.send(givescheduleforlector, "" + lectorId);
    }

}
