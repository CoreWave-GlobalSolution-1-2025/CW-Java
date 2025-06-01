package com.corewave.repositories;

import com.corewave.infrastructure.DataBaseConfig;
import com.corewave.models.EVENT_RISK;
import com.corewave.models.EVENT_TYPE;
import com.corewave.models.Event;
import jakarta.ws.rs.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventRepo extends _BaseRepo implements _CrudRepo<Event> {
    @Override
    public void add(Event obj) {
        var query = """
                INSERT INTO EVENTOS
                (name, deleted, event_type, place, description, event_risk)
                VALUES (?,?,?,?,?,?)
                """;

        LOGGER.info("Adicionando evento no sistema: {}", obj.getId());

        try (var stmt = DataBaseConfig.getConnection().prepareStatement(query)) {
            stmt.setString(1, obj.getName());
            stmt.setBoolean(2, obj.isDeleted());
            stmt.setString(3, obj.getEventType().toString());
            stmt.setString(4, obj.getPlace());
            stmt.setString(5, obj.getDescription());
            stmt.setString(6, obj.getEventRisk().toString());
            stmt.execute();
            LOGGER.info("Evento adicionado no sistema.");
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

        LOGGER.info("Recuperando eventos no sistema.");

        try (
                var stmt = DataBaseConfig.getConnection().prepareStatement(query);
                var rs = stmt.executeQuery()) {

            while (rs.next()) {
                var event = createEventFromResult(rs);

                eventList.add(event);
            }

            LOGGER.info("Eventos recuperados com sucesso: {} eventos", eventList.size());

        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar eventos no sistema: {}", e);
        }
        return eventList;
    }

    @Override
    public Optional<Event> getById(int id) {
        var query = """
                SELECT * FROM EVENTOS WHERE id = ?
                """;

        LOGGER.info("Recuperando evento no sistema pelo id: {}", id);

        try (var stmt = DataBaseConfig.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                var event = createEventFromResult(rs);
                LOGGER.info("Evento recuperado com sucesso.");
                return Optional.of(event);
            } else {
                LOGGER.warn("Evento não encontrado.");
            }

        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar eventos no sistema: {}", e);
        }
        return Optional.empty();
    }

    @Override
    public void updateById(int id, Event uObj) {
        var eventOptional = getById(id);

        LOGGER.info("Buscando evento para atualizar. ID: {}", id);

        if (eventOptional.isEmpty()) {
            LOGGER.warn("Evento não encontrado para atualização.");
            throw new NotFoundException("Evento não encontrado no sistema. Verifique se o ID está correto.");
        }
        LOGGER.info("Atualizando evento encontrado");
        var nEvent = (Event) eventOptional.get()
                .updateAttibutes(uObj);

        var query = """
                UPDATE EVENTOS
                SET
                name = ?,
                deleted = ?,
                event_type = ?,
                event_risk = ?,
                place = ?,
                description = ?
                WHERE id = ?
                """;

        try (var stmt = DataBaseConfig.getConnection().prepareStatement(query)) {
            stmt.setInt(7, id);

            stmt.setString(1, nEvent.getName());
            stmt.setBoolean(2, nEvent.isDeleted());
            stmt.setString(3, nEvent.getEventType().toString());
            stmt.setString(4, nEvent.getEventRisk().toString());
            stmt.setString(5, nEvent.getPlace());
            stmt.setString(6, nEvent.getDescription());
        } catch (SQLException e) {
            LOGGER.error("Erro ao atualizar evento no sistema: {}", e);
        }
    }

    @Override
    public void deleteById(int id) {

        //TODO: Implementar deletação lógica
        var eventOptional = getById(id);

        if (eventOptional.isEmpty()) {
            throw new NotFoundException("Evento não encontrado no sistema. Verifique se o ID está correto.");
        }

        var query = """
                DROP FROM EVENTOS WHERE id = ?
                """;


    }

    private static Event createEventFromResult(ResultSet rs) throws SQLException {
        var event = new Event();

        event.setId(rs.getInt("id"));
        event.setName(rs.getString("name"));
        event.setDeleted(rs.getBoolean("deleted"));
        event.setEventType(EVENT_TYPE.valueOf(rs.getString("event_type")));
        event.setPlace(rs.getString("place"));
        event.setDescription(rs.getString("description"));
        event.setEventRisk(EVENT_RISK.valueOf(rs.getString("event_risk")));
        return event;
    }
}
