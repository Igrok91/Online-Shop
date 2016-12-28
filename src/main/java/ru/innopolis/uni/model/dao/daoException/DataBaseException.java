package ru.innopolis.uni.model.dao.daoException;

/**
 * Created by innopolis on 28.12.2016.
 */
public class DataBaseException extends Exception {

    public DataBaseException() {
        super();
    }


    public String message() {
        return "Сообщение пользователю:Произошла ошибка запрос к базе данных";
    }
}
