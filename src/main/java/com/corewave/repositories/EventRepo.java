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
    public void add(Event obj) throws SQLException {
        var query = """
                INSERT INTO EVENTOS
                (name, deleted, eventtype, place, description, eventrisk)
                VALUES (?,?,?,?,?,?)
                """;

        LOGGER.info("Adicionando evento no sistema: {}", obj.getId());

        try (var conn = DataBaseConfig.getConnection();
             var stmt = conn.prepareStatement(query)) {
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
            throw e;
        }
    }

    @Override
    public List<Event> list() throws SQLException {
        var eventList = new ArrayList<Event>();
        var query = """
                SELECT * FROM EVENTOS WHERE deleted = 0
                """;

        LOGGER.info("Recuperando eventos no sistema.");

        try (var conn = DataBaseConfig.getConnection();
             var stmt = conn.prepareStatement(query);
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                var event = createEventFromResult(rs);

                eventList.add(event);
            }

            LOGGER.info("Eventos recuperados com sucesso: {} eventos", eventList.size());

        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar eventos no sistema: {}", e);
            throw e;
        }
        return eventList;
    }

    @Override
    public Optional<Event> getById(int id) throws SQLException {
        var query = """
                SELECT * FROM EVENTOS WHERE id = ?
                """;

        LOGGER.info("Recuperando evento no sistema pelo id: {}", id);

        try (var conn = DataBaseConfig.getConnection();
             var stmt = conn.prepareStatement(query)) {
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
            throw e;
        }
        return Optional.empty();
    }

    @Override
    public void updateById(int id, Event uObj) throws SQLException, RuntimeException {
        var eventOptional = getById(id);

        LOGGER.info("Buscando evento para atualizar. ID: {}", id);

        if (eventOptional.isEmpty()) {
            LOGGER.warn("Evento não encontrado para atualização.");
            throw new NotFoundException();
        }
        LOGGER.info("Atualizando evento encontrado");
        var event = eventOptional.get();
        event.updateAttributes(uObj);

        var query = """
                UPDATE EVENTOS
                SET
                name = ?,
                deleted = ?,
                eventtype = ?,
                eventrisk = ?,
                place = ?,
                description = ?
                WHERE id = ?
                """;

        try (var conn = DataBaseConfig.getConnection();
             var stmt = conn.prepareStatement(query)) {
            stmt.setInt(7, id);

            stmt.setString(1, event.getName());
            stmt.setBoolean(2, event.isDeleted());
            stmt.setString(3, event.getEventType().toString());
            stmt.setString(4, event.getEventRisk().toString());
            stmt.setString(5, event.getPlace());
            stmt.setString(6, event.getDescription());
        } catch (SQLException e) {
            LOGGER.error("Erro ao atualizar evento no sistema: {}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) throws SQLException, RuntimeException {

        var eventOptional = getById(id);

        if (eventOptional.isEmpty()) {
            throw new NotFoundException("Evento não encontrado no sistema. Verifique se o ID está correto.");
        }
        var event = eventOptional.get();
        event.setDeleted(true);
        updateById(id, event);
    }

    private static Event createEventFromResult(ResultSet rs) throws SQLException {
        var event = new Event();

        event.setId(rs.getInt("id"));
        event.setName(rs.getString("name"));
        event.setDeleted(rs.getBoolean("deleted"));
        event.setEventType(EVENT_TYPE.valueOf(rs.getString("eventtype")));
        event.setPlace(rs.getString("place"));
        event.setDescription(rs.getString("description"));
        event.setEventRisk(EVENT_RISK.valueOf(rs.getString("eventrisk")));
        return event;
    }
}
