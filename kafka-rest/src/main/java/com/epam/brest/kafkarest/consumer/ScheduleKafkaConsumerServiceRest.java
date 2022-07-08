package com.epam.brest.kafkarest.consumer;

import com.epam.brest.LectorsSchedule;
import com.epam.brest.ScheduleDtoServiceApi;
import com.epam.brest.StudentsSchedule;
import com.epam.brest.kafkarest.producer.ScheduleKafkaProducerServiceRest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//@EnableJpaRepositories(basePackages = "com.epam.brest")
public class ScheduleKafkaConsumerServiceRest {

    private final Logger logger = LogManager.getLogger(ScheduleKafkaConsumerServiceRest.class);

    @Autowired
    private final ConsumerFactory<String, String> stringKafkaTemplate;

    @Autowired
    private final ConsumerFactory<String, List<StudentsSchedule>> listStudentsScheduleConsumerFactory;

    @Autowired
    private final ConsumerFactory<String, List<LectorsSchedule>> listLectorsScheduleConsumerFactory;

    @Autowired
    private final ConsumerFactory<String, List<List<StudentsSchedule>>> listAllStudentsScheduleConsumerFactory;

    @Autowired
    private final ConsumerFactory<String, List<List<LectorsSchedule>>> listAllLectorsScheduleConsumerFactory;

    @Autowired
    private final ScheduleDtoServiceApi scheduleDtoService;

    @Autowired
    private final ScheduleKafkaProducerServiceRest scheduleKafkaProducerServiceRest;

    public ScheduleKafkaConsumerServiceRest(ConsumerFactory<String, String> stringKafkaTemplate
                                , ConsumerFactory<String, List<StudentsSchedule>> listStudentsScheduleConsumerFactory
                                , ConsumerFactory<String, List<LectorsSchedule>> listLectorsScheduleConsumerFactory
                                , ConsumerFactory<String, List<List<StudentsSchedule>>> listAllStudentsScheduleConsumerFactory
                                , ConsumerFactory<String, List<List<LectorsSchedule>>> listAllLectorsScheduleConsumerFactory
                                , ScheduleDtoServiceApi scheduleDtoService, ScheduleKafkaProducerServiceRest scheduleKafkaProducerServiceRest) {

        this.stringKafkaTemplate = stringKafkaTemplate;
        this.listStudentsScheduleConsumerFactory = listStudentsScheduleConsumerFactory;
        this.listLectorsScheduleConsumerFactory = listLectorsScheduleConsumerFactory;
        this.listAllStudentsScheduleConsumerFactory = listAllStudentsScheduleConsumerFactory;
        this.listAllLectorsScheduleConsumerFactory = listAllLectorsScheduleConsumerFactory;
        this.scheduleDtoService = scheduleDtoService;
        this.scheduleKafkaProducerServiceRest = scheduleKafkaProducerServiceRest;
    }


    @KafkaListener(topics = "givescheduleforallstudents", groupId = "string")
    public void giveScheduleForAllStudents(String message) {
        logger.info("Received Message in string : " + message);
        List<List<StudentsSchedule>> allStudentsSchedule = scheduleDtoService.getScheduleForAllGroupsService();
        if (allStudentsSchedule.isEmpty()) {
            allStudentsSchedule = new ArrayList<>();
        }
        logger.info("Give schedule for all students in consumer" + allStudentsSchedule.toString());
        scheduleKafkaProducerServiceRest.sendGiveAllScheduleForAllStudents(allStudentsSchedule);
    }

    @KafkaListener(topics = "createschedule", groupId = "string")
    public void createSchedule(String message) {
        logger.info("Received Message in string : " + message);
        logger.info("Create schedule for all students and lectors in consumer" + message);
        scheduleDtoService.createScheduleService();
    }

    @KafkaListener(topics = "givescheduleforalllectors", groupId = "string")
    public void giveScheduleForAllLectors(String message) {
        logger.info("Received Message in string : " + message);
        List<List<LectorsSchedule>> allLectorsSchedule = scheduleDtoService.getScheduleForAllLectorsService();
        if (allLectorsSchedule.isEmpty() ) {
            allLectorsSchedule = new ArrayList<>();
        }
        logger.info("Give schedule for all lectors in consumer" + allLectorsSchedule.toString());
        scheduleKafkaProducerServiceRest.sendGiveAllScheduleForAllLectors(allLectorsSchedule);
    }

    @KafkaListener(topics = "givescheduleforlector", groupId = "string")
    public void giveScheduleForLector(String lectorId) {
        logger.info("Received Message in string : " + lectorId);
        Integer id = Integer.parseInt(lectorId);
        List<LectorsSchedule> lectorsSchedule = scheduleDtoService.getScheduleForLectorService(id);
        if (lectorsSchedule == null) {
            lectorsSchedule = new ArrayList<>();
        }
        logger.info("Give schedule for  lector in consumer" + lectorsSchedule.toString());
        scheduleKafkaProducerServiceRest.sendScheduleForLector(lectorsSchedule);
    }

    @KafkaListener(topics = "givescheduleforgroup", groupId = "string")
    public void giveScheduleForGroup(String groupId) {
        logger.info("Received Message in string : " + groupId);
        Integer id = Integer.parseInt(groupId);
        List<StudentsSchedule> studentsSchedule = scheduleDtoService.getScheduleForGroupService(id);
        if (studentsSchedule == null) {
            studentsSchedule = new ArrayList<>();
        }
        logger.info("Give schedule for  group in consumer" + studentsSchedule.toString());
        scheduleKafkaProducerServiceRest.sendScheduleForGroup(studentsSchedule);
    }

}
