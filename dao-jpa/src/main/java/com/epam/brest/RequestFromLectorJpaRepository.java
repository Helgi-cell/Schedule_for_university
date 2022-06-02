package com.epam.brest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Repository
public interface RequestFromLectorJpaRepository extends JpaRepository<RequestFromLector, Integer> {

    default List<RequestFromLector> findAllByForeignKey(Integer id) {
        return (List<RequestFromLector>) findAll().stream()
                                                  .filter(req -> id == req.getIdLector())
                                                  .collect(Collectors.toList());
    }



    default RequestFromLector updateRequest(RequestFromLector requestFromLector) {
       return (RequestFromLector) saveAndFlush(requestFromLector);
    }



    default List<RequestFromLector> createRequestsforNewLector(List<String> groups, Integer id) {
        List<RequestFromLector> requestsFromLector = groups
            .stream()
            .flatMap(group -> Stream.of(new RequestFromLector(id, group, "0", "    ", new Date())))
            .collect(Collectors.toList());
        return (List<RequestFromLector>)saveAllAndFlush(requestsFromLector);
    }



    default  List<RequestFromLector> addNewGroupeInAllLectorRequests(List <Integer> idLectors, String group) {
        List<RequestFromLector> requestsFromLector = idLectors
            .stream()
            .flatMap(idLector -> Stream.of(new RequestFromLector(idLector, group,
                    "0", "    ", new Date())))
            .collect(Collectors.toList());
       return (List<RequestFromLector>) saveAllAndFlush((List<RequestFromLector>)requestsFromLector);
    }



    default RequestFromLector deleteRequest(RequestFromLector requestFromLector) {
        deleteById(requestFromLector.getIdRequest());
        return (RequestFromLector) requestFromLector;
    }



    default boolean deleteRequestsWhenDeletedGroup(String nameGroup) {
        List<RequestFromLector> requestsFromLector = (List<RequestFromLector>) findAll().stream()
                                                            .filter(requestFromLector -> requestFromLector.getGroup().equals(nameGroup))
                                                            .collect(Collectors.toList());
        if (!requestsFromLector.isEmpty()) {
            deleteAllInBatch((Iterable<RequestFromLector>) requestsFromLector);
            return true;
        } else {
        return false;
        }
    }



    default boolean updateRequestsWhenChangedNameGroup(String newGroup, String oldGroup){
        List<RequestFromLector> requestsFromLector = (List<RequestFromLector>) findAll().stream()
                .filter(requestFromLector -> requestFromLector.getGroup().equals(oldGroup))
                .peek(requestFromLector -> requestFromLector.setGroup(newGroup))
                .collect(Collectors.toList());

        if (!requestsFromLector.isEmpty()) {
            saveAllAndFlush((Iterable<RequestFromLector>) requestsFromLector);
            return true;
        } else {
            return false;
        }
    }


}
