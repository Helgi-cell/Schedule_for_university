package com.epam.brest.kafkaweb.consumer;

import com.epam.brest.Lector;
import com.epam.brest.LectorsSchedule;
import com.epam.brest.StudentsSchedule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
@EnableJpaRepositories(basePackages = "com.epam.brest")
public class ScheduleConsumerServiceWeb {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleConsumerServiceWeb.class);

    @Autowired
    private final ConsumerFactory<String, String> stringKafkaTemplate;
    @Autowired
    private  final  ConsumerFactory<String, List<StudentsSchedule>> groupScheduleKafkaTemplate;
    @Autowired
    private  final  ConsumerFactory<String, List<List<StudentsSchedule>>> allGroupsScheduleKafkaTemplate;
    @Autowired
    private  final  ConsumerFactory<String, List<LectorsSchedule>> lectorScheduleKafkaTemplate;
    @Autowired
    private  final  ConsumerFactory<String, List<List<LectorsSchedule>>> allLectorsScheduleKafkaTemplate;

    public Boolean isListLectorScheduleChanged = false;
    public Boolean isAllLectorsScheduleChanged = false;
    public Boolean isListStudentScheduleChanged = false;
    public Boolean isAllStudentsScheduleChanged = false;

    public List<StudentsSchedule> groupSchedule = new ArrayList<>();
    public List<LectorsSchedule> lectorSchedule = new ArrayList<>();
    public List<List<StudentsSchedule>> allGroupSchedule = new ArrayList<>();
    public List<List<LectorsSchedule>> allLectorsSchedule = new ArrayList<>();

    public ScheduleConsumerServiceWeb(ConsumerFactory<String, String> stringKafkaTemplate
            , ConsumerFactory<String, List<StudentsSchedule>> groupScheduleKafkaTemplate
            , ConsumerFactory<String, List<List<StudentsSchedule>>> allGroupsScheduleKafkaTemplate
            , ConsumerFactory<String, List<LectorsSchedule>> lectorScheduleKafkaTemplate
            , ConsumerFactory<String, List<List<LectorsSchedule>>> allLectorsScheduleKafkaTemplate) {

        this.stringKafkaTemplate = stringKafkaTemplate;
        this.groupScheduleKafkaTemplate = groupScheduleKafkaTemplate;
        this.allGroupsScheduleKafkaTemplate = allGroupsScheduleKafkaTemplate;
        this.lectorScheduleKafkaTemplate = lectorScheduleKafkaTemplate;
        this.allLectorsScheduleKafkaTemplate = allLectorsScheduleKafkaTemplate;
    }


    @KafkaListener(topics = "sendscheduleforallstudents", groupId = "listschedule")
    public void getAllGroupsSchedule(String groupsScheduleALL) {
        logger.info("Received Message from sendscheduleforallstudents : " + groupsScheduleALL);
        try {
            ObjectMapper mapper = new ObjectMapper();
            allGroupSchedule = mapper.readValue(groupsScheduleALL, new TypeReference<List<List<StudentsSchedule>>>() {
            });
            isAllStudentsScheduleChanged  = true;
            logger.info("Parsed message from sendscheduleforallstudents: " + allGroupSchedule.toString());
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "sendscheduleforgroup", groupId = "listschedule")
    public void getGroupSchedule(String groupsSchedule) {
        logger.info("Received Message from sendscheduleforgroup : " + groupsSchedule);
        try {
            ObjectMapper mapper = new ObjectMapper();
            groupSchedule = mapper.readValue(groupsSchedule, new TypeReference <List<StudentsSchedule>>() {
            });
            isListStudentScheduleChanged = true;
            logger.info("Parsed message from sendscheduleforgroup: " + groupSchedule.toString());
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "sendscheduleforalllectors", groupId = "listschedule")
    public void getAllLectorsSchedule(String lectorsScheduleALL) {
        logger.info("Received Message from sendscheduleforalllectors : " + lectorsScheduleALL);
        try {
            ObjectMapper mapper = new ObjectMapper();
            allLectorsSchedule = mapper.readValue(lectorsScheduleALL, new TypeReference<List<List<LectorsSchedule>>>() {
            });
            isAllLectorsScheduleChanged  = true;
            logger.info("Parsed message from sendscheduleforalllectors: " + allLectorsSchedule.toString());
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

    @KafkaListener(topics = "sendscheduleforlector", groupId = "listschedule")
    public void getLectorSchedule(String lectorsSchedule) {
        logger.info("Received Message from sendscheduleforlector : " + lectorsSchedule);
        try {
            ObjectMapper mapper = new ObjectMapper();
            lectorSchedule = mapper.readValue(lectorsSchedule, new TypeReference<List<LectorsSchedule>>() {
            });
            isListLectorScheduleChanged  = true;
            logger.info("Parsed message from sendscheduleforlector: " + lectorSchedule.toString());
        } catch (Exception ex) {
            throw new SerializationException(ex);
        }
    }

}
