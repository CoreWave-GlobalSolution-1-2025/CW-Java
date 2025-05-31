package com.corewave.repositories;

import com.corewave.infrastructure.DataBaseConfig;
import com.corewave.models.EVENT_TYPE;
import com.corewave.models.Event;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventRepo extends _BaseRepo implements _CrudRepo<Event> {
    @Override
    public void add(Event obj) {
        var query = """
                INSERT INTO EVENTOS
                (name, deleted, event_type, place, description)
                VALUES (?,?,?,?,?)
                """;

        LOGGER.info("Adicionando evento no sistema: {}", obj.getId());

        try (var stmt = DataBaseConfig.getConnection().prepareStatement(query)) {
            stmt.setString(1, obj.getName());
            stmt.setBoolean(2, obj.isDeleted());
            stmt.setString(3, obj.getEventType().toString());
            stmt.setString(4, obj.getPlace());
            stmt.setString(5, obj.getDescription());
            stmt.execute();
        } catch (SQLException e) {
            LOGGER.error("Erro ao adicionar evento no sistema: {}", e);
        }
    }

    @Override
    public List<Event> list() {
        var eventList = new ArrayList<Event>();
        var query = """
                SELECT * FROM EVENTOS WHERE deleted = 0
                """;

        try (
                var stmt = DataBaseConfig.getConnection().prepareStatement(query);
                var rs = stmt.executeQuery()) {

            while (rs.next()) {
                var event = new Event();

                event.setId(rs.getInt("id"));
                event.setName(rs.getString("name"));
                event.setDeleted(rs.getBoolean("deleted"));
                event.setEventType(EVENT_TYPE.valueOf(rs.getString("event_type")));
                event.setPlace(rs.getString("place"));
                event.setDescription(rs.getString("description"));

                eventList.add(event);
            }

        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar eventos no sistema: {}", e);
        }


        return List.of();
    }

    @Override
    public Optional<Event> getById(int id) {
        return Optional.empty();
    }

    @Override
    public void updateById(int id, Event uObj) {

    }

    @Override
    public void deleteById(int id) {

    }
}
