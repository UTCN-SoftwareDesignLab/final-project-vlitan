package main.service;

import main.util.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.util.Optional;

/*
* Had a hard time naming this class
* Basically it tries to perform an operation on a generic Repo and returns a notification
* */
public class AbstractRepoAdapter{
    public static <R extends JpaRepository<O,Integer>, O> Notification<Boolean> save(R repo, O object){
        Notification<Boolean> saveNotification = new Notification<>();
        try {
            repo.save(object);
            saveNotification.setResult(Boolean.TRUE);
        } catch (Exception e) {
            saveNotification.addError("Something went bad while saving");
            saveNotification.setResult(Boolean.FALSE);
        }
        return saveNotification;
    }



    public static <R extends JpaRepository<O,Integer>, O> Optional<O> findById(R repo, Integer id){
        try{
            return Optional.of(repo.getOne(id));
        }
        catch(EntityNotFoundException e){
            return Optional.empty();
        }
    }

    public static <R extends JpaRepository<O, Integer>, O> Notification<Boolean> deleteById(R repo, Integer id){
        Notification<Boolean> deleteNotification = new Notification<>();
        if (id.intValue() > 0){
            try {
                Optional<O> optResult = findById(repo, id);
                if(optResult.isPresent()){
                    repo.delete(optResult.get());
                    deleteNotification.setResult(Boolean.TRUE);
                }
                else{
                    deleteNotification.setResult(Boolean.FALSE);
                    deleteNotification.addError("Nothing with id " + id.toString() + " no found");
                }


            } catch (Exception e) {
                deleteNotification.setResult(Boolean.FALSE);
                deleteNotification.addError("Something went bad while deleting");
            }
        }
        else {
            deleteNotification.setResult(Boolean.FALSE);
            deleteNotification.addError("Id cannot be negative");
        }
        return deleteNotification;
    }

    public static <R extends JpaRepository<O, Integer>, O> Notification<Boolean> delete(R repo, O object){
        Notification<Boolean> deleteNotification = new Notification<>();
        try {
            repo.delete(object);
            deleteNotification.setResult(Boolean.TRUE);
        } catch (Exception e) {
            deleteNotification.setResult(Boolean.FALSE);
            deleteNotification.addError("Something went bad while deleting");
        }
        return deleteNotification;
    }
}
