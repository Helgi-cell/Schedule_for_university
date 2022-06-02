package com.epam.brest.dto;

import com.epam.brest.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ComponentScan ("com.epam.brest")
public class Schedule {

    public List<DaySchedule> dayScheduleForAll;
    public List<LectorsSchedule> lectorsScheduleForAll;
    public List<StudentsSchedule> studentsScheduleForAll;

    private int[][] daysForSchedule = {
            {1, 3, 5, 2, 4, 6},
            {2, 4, 6, 1, 3, 5},
            {1, 3, 5, 2, 4, 6}};

    private int[][] pairsForSchedule = {
            {1, 2, 3, 4, 5},
            {2, 3, 4, 5, 1},
            {3, 4, 5, 1, 2},
            {4, 5, 1, 2, 3},
            {5, 1, 2, 3, 4}};

    public Schedule() {
    this.dayScheduleForAll = new ArrayList<>();
    this.lectorsScheduleForAll = new ArrayList<>();
    this.studentsScheduleForAll = new ArrayList<>();
    }


    /*
    create Schedule
    */
    public List<DaySchedule> createSchedule(
            List<String> groups
            , List<String> lectors
            , List<RequestsForGroupe> requestsForGroupes) {

        dayScheduleForAll = new ArrayList<>();
        List<List<RequestsForGroupe>> listOfSortedGroupesRequests = new ArrayList<>();

        boolean ifConsistInRequest = false;

        for (String groupe : groups) {
            List<RequestsForGroupe> sortedRequests = new ArrayList<>();
            for (String lector : lectors) {
                ifConsistInRequest = false;
                for (RequestsForGroupe requestsGroupe : requestsForGroupes) {
                    if ((groupe == requestsGroupe.getGroupe()) && (lector == requestsGroupe.getLector())) {
                        sortedRequests.add(requestsGroupe);
                        ifConsistInRequest = true;
                    }
                }
                if (!ifConsistInRequest) {
                    sortedRequests.add(new RequestsForGroupe(lector, groupe, "    ", 0));
                }
                ifConsistInRequest = false;

            }
            listOfSortedGroupesRequests.add(sortedRequests);
        }

        if (listOfSortedGroupesRequests.size() > 0) {
            if (listOfSortedGroupesRequests.get(0).size() < pairsForSchedule[0].length) {
                return dayScheduleForAll;
            }
        }
        List<List<RequestsForGroupe>> blockOfSortedGroupesRequests = new ArrayList<>();
        int i = 0;
        for (List<RequestsForGroupe> forGroupeList : listOfSortedGroupesRequests) {
            if (blockOfSortedGroupesRequests.size() < pairsForSchedule[0].length) {
                blockOfSortedGroupesRequests.add(forGroupeList);
            }
            if (blockOfSortedGroupesRequests.size() == pairsForSchedule[0].length) {
                blockOfSortedGroupesRequests = deleteAllZeroColumns(blockOfSortedGroupesRequests);
                createDaySchedule(blockOfSortedGroupesRequests, daysForSchedule[i], pairsForSchedule);
                blockOfSortedGroupesRequests = new ArrayList<>();
                i++;
            }
        }

        if (blockOfSortedGroupesRequests.size() > 0) {
            blockOfSortedGroupesRequests = deleteAllZeroColumns(blockOfSortedGroupesRequests);
            createDaySchedule(blockOfSortedGroupesRequests, daysForSchedule[i], pairsForSchedule);
        }

        return dayScheduleForAll;
    }


    // Create DaySchedule -> return DaySchedule list
    private void createDaySchedule(List<List<RequestsForGroupe>> blockOfSortedGroupesRequests, int[] days
            , int[][] pairsForSchedule) {

        for (int day = 0; day < days.length; day++) {
            if(blockOfSortedGroupesRequests != null){
                blockOfSortedGroupesRequests = normalizeGroupeRequests(blockOfSortedGroupesRequests);
            }
            if (blockOfSortedGroupesRequests != null) {
                for (int i = 0; i < blockOfSortedGroupesRequests.size(); i++) {
                    List<RequestsForGroupe> requestsForGroupes = blockOfSortedGroupesRequests.get(i);
                    for (int pair = 0; pair < pairsForSchedule[i].length; pair++) {
                        RequestsForGroupe requests = requestsForGroupes.get(pair);
                        if (requests.getPairs() > 0) {
                            dayScheduleForAll.add(new DaySchedule(requests.getLector()
                                    , requests.getGroupe()
                                    , pairsForSchedule[i][pair]
                                    , requests.getSubject()
                                    , days[day]));
                            requests.setPairs(requests.getPairs() - 1);
                            requestsForGroupes.set(pair, requests);
                        }
                    }
                    blockOfSortedGroupesRequests.set(i, requestsForGroupes);
                }
            }
        }
    }

    //==================================================================================================================
    private List<List<RequestsForGroupe>> normalizeGroupeRequests(List<List<RequestsForGroupe>> blockOfSortedGroupesRequests) {

        if (blockOfSortedGroupesRequests.get(0).size() > pairsForSchedule[0].length) {
            blockOfSortedGroupesRequests = deleteAllZeroColumns(blockOfSortedGroupesRequests);
        } else{
            if (blockOfSortedGroupesRequests.get(0).size() == pairsForSchedule[0].length) {
                blockOfSortedGroupesRequests = ifNullColumns(blockOfSortedGroupesRequests);
            }
        }

        return blockOfSortedGroupesRequests;
    }

    //==================================================================================================================
    private List<List<RequestsForGroupe>> deleteNullColumns(List<List<RequestsForGroupe>> blockOfSortedGroupesRequests) {
        int isZero = 0;
        for (List<RequestsForGroupe> requestsForGroupes : blockOfSortedGroupesRequests) {
            isZero += requestsForGroupes.get(0).getPairs();
        }
        if (isZero == 0) {
            for (int i = 0; i < blockOfSortedGroupesRequests.size(); i++) {
                List<RequestsForGroupe> requestsForGroupes = blockOfSortedGroupesRequests.get(i);
                requestsForGroupes.remove(0);
                blockOfSortedGroupesRequests.set(i, requestsForGroupes);
            }
        }

        return blockOfSortedGroupesRequests;
    }

    //===================================================================================================================
    private List<List<RequestsForGroupe>> ifNullColumns(List<List<RequestsForGroupe>> blockOfSortedGroupesRequests) {
        int countZero = 0;
        for (List<RequestsForGroupe> requestsForGroupes : blockOfSortedGroupesRequests) {
            for (RequestsForGroupe requests : requestsForGroupes) {
                countZero += requests.getPairs();
            }
        }
        if (countZero > 0) {
            return blockOfSortedGroupesRequests;
        } else {
            return null;
        }
    }

    private List<List<RequestsForGroupe>> deleteAllZeroColumns(List<List<RequestsForGroupe>> blockOfSortedGroupesRequests){
        for (int i = 0; i < blockOfSortedGroupesRequests.get(0).size(); i++){
            int count = 0;
            for (List<RequestsForGroupe> requestsForGroupes : blockOfSortedGroupesRequests){
                count += requestsForGroupes.get(i).getPairs();
            }

            if (count == 0){
                for (int j = 0; j < blockOfSortedGroupesRequests.size(); j++){
                    List<RequestsForGroupe> requestsForGroupes = blockOfSortedGroupesRequests.get(j);
                    if (requestsForGroupes.size() > pairsForSchedule[0].length){
                        requestsForGroupes.remove(i);
                    }
                    blockOfSortedGroupesRequests.set(j, requestsForGroupes);
                }
                if (blockOfSortedGroupesRequests.get(0).size() == pairsForSchedule[0].length){
                    return blockOfSortedGroupesRequests;
                }
                i = 0;
            }
        }


        return blockOfSortedGroupesRequests;
    }


//======================================================================================================================
/*
End create
*/


    public List<String> getLectorsNamesList(List<Lector> lectorList) {
        List<String> namesLectors = new ArrayList<>();
        for (Lector lector : lectorList) {
            namesLectors.add(lector.getNameLector());
        }
        return namesLectors;
    }

    // Create schedule for lectors
    public List<LectorsSchedule> createScheduleForLectors(List<String> lectors) {
        lectorsScheduleForAll = new ArrayList<>();

        for (String lector : lectors) {
            for (int para = 1; para < 6; para++) {
                LectorsSchedule lectorsSchedule = new LectorsSchedule();
                lectorsSchedule.setLector(lector);
                lectorsSchedule.setPair(para);
                for (int dayWeek = 1; dayWeek < 7; dayWeek++) {

                    for (DaySchedule daySchedule : dayScheduleForAll) {
                        if ((lector == daySchedule.getLectorName()) && (para == daySchedule.getNumberPair())
                                && (dayWeek == daySchedule.getDay())) {
                            if (dayWeek == 1) {
                                lectorsSchedule.setMonday(daySchedule.getGroupeName() + " - " + daySchedule.getSubject());
                            } else {
                                if (dayWeek == 2) {
                                    lectorsSchedule.setTuesday(daySchedule.getGroupeName() + " - " + daySchedule.getSubject());
                                } else {
                                    if (dayWeek == 3) {
                                        lectorsSchedule.setWednesday(daySchedule.getGroupeName() + " - " + daySchedule.getSubject());
                                    } else {
                                        if (dayWeek == 4) {
                                            lectorsSchedule.setThursday(daySchedule.getGroupeName() + " - " + daySchedule.getSubject());
                                        } else {
                                            if (dayWeek == 5) {
                                                lectorsSchedule.setFriday(daySchedule.getGroupeName() + " - " + daySchedule.getSubject());
                                            } else {
                                                if (dayWeek == 6) {
                                                    lectorsSchedule.setSaturday(daySchedule.getGroupeName() + " - " + daySchedule.getSubject());
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                lectorsScheduleForAll.add(lectorsSchedule);
            }
        }

        return lectorsScheduleForAll;
    }

    // Create schedule for students
    public List<StudentsSchedule> createScheduleForGroupe(List<String> groups) {
        studentsScheduleForAll = new ArrayList<>();
        for (String gruppa : groups) {
            for (int para = 1; para < 6; para++) {
                StudentsSchedule studentsSchedule = new StudentsSchedule();
                studentsSchedule.setGroupe(gruppa);
                studentsSchedule.setPair(para);
                for (int dayWeek = 1; dayWeek < 7; dayWeek++) {

                    for (DaySchedule dayschedule : dayScheduleForAll) {
                        if ((gruppa == dayschedule.getGroupeName()) && (para == dayschedule.getNumberPair())
                                && (dayWeek == dayschedule.getDay())) {
                            if (dayWeek == 1) {
                                studentsSchedule.setMonday(dayschedule.getSubject() + " - " + dayschedule.getLectorName());
                            } else {
                                if (dayWeek == 2) {
                                    studentsSchedule.setTuesday(dayschedule.getSubject() + " - " + dayschedule.getLectorName());
                                } else {
                                    if (dayWeek == 3) {
                                        studentsSchedule.setWednesday(dayschedule.getSubject() + " - " + dayschedule.getLectorName());
                                    } else {
                                        if (dayWeek == 4) {
                                            studentsSchedule.setThursday(dayschedule.getSubject() + " - " + dayschedule.getLectorName());
                                        } else {
                                            if (dayWeek == 5) {
                                                studentsSchedule.setFriday(dayschedule.getSubject() + " - " + dayschedule.getLectorName());
                                            } else {
                                                if (dayWeek == 6) {
                                                    studentsSchedule.setSaturday(dayschedule.getSubject() + " - " + dayschedule.getLectorName());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                studentsScheduleForAll.add(studentsSchedule);
            }
        }

        return studentsScheduleForAll;
    }

} // END CLASS Schedule
