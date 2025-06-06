package com.corewave.repositories;

import com.corewave.infrastructure.DataBaseConfig;
import com.corewave.models.User;
import jakarta.ws.rs.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepo extends _BaseRepo implements _CrudRepo<User> {
    @Override
    public void add(User obj) throws SQLException {
        var query = """
                INSERT INTO USUARIO
                (name, deleted, email, password)
                VALUES (?,?,?,?)
                """;

        LOGGER.info("Adicionando usuário no sistema: {}", obj.getId());

        try (var conn = DataBaseConfig.getConnection();
             var stmt = conn.prepareStatement(query)) {
            stmt.setString(1, obj.getName());
            stmt.setBoolean(2, obj.isDeleted());
            stmt.setString(3, obj.getEmail());
            stmt.setString(4, obj.getPassword());
            stmt.execute();
            LOGGER.info("Usuário adicionado no sistema.");
        } catch (SQLException e) {
            LOGGER.error("Erro ao adicionar usuário no sistema: {}", e);
            throw e;
        }
    }

    @Override
    public List<User> list() throws SQLException {
        var userList = new ArrayList<User>();
        var query = """
                SELECT * FROM USUARIO WHERE deleted = 0
                """;

        LOGGER.info("Recuperando usuários no sistema.");

        try (
                var conn = DataBaseConfig.getConnection();
                var stmt = conn.prepareStatement(query);
                var rs = stmt.executeQuery()) {

            while (rs.next()) {
                var user = createUserFromResult(rs);

                userList.add(user);
            }

            LOGGER.info("Usuários recuperados com sucesso: {} usuários", userList.size());

        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar usuários no sistema: {}", e);
            throw e;
        }
        return userList;
    }

    @Override
    public Optional<User> getById(int id) throws SQLException {
        var query = """
                SELECT * FROM USUARIO WHERE id = ?
                """;

        LOGGER.info("Recuperando usuário no sistema pelo id: {}", id);

        try (var conn = DataBaseConfig.getConnection();
             var stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                var user = createUserFromResult(rs);
                LOGGER.info("Usuário recuperado com sucesso.");
                return Optional.of(user);
            } else {
                LOGGER.warn("Usuário não encontrado.");
            }

        } catch (SQLException e) {
            LOGGER.error("Erro ao recuperar usuário no sistema: {}", e);
            throw e;
        }
        return Optional.empty();
    }

    @Override
    public void updateById(int id, User uObj) throws SQLException, RuntimeException {
        var userOptional = getById(id);

        LOGGER.info("Buscando usuário para atualizar. ID: {}", id);

        if (userOptional.isEmpty()) {
            LOGGER.warn("Usuário não encontrado para atualização.");
            throw new NotFoundException("Usuário não encontrado no sistema. Verifique se o ID está correto.");
        }
        LOGGER.info("Atualizando usuário encontrado");
        var user = userOptional.get();
        user.updateAttributes(uObj);

        var query = """
                UPDATE USUARIO
                SET
                name = ?,
                deleted = ?,
                email = ?,
                password = ?,
                WHERE id = ?
                """;

        try (var conn = DataBaseConfig.getConnection();
             var stmt = conn.prepareStatement(query)) {
            stmt.setInt(5, id);

            stmt.setString(1, user.getName());
            stmt.setBoolean(2, user.isDeleted());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
        } catch (SQLException e) {
            LOGGER.error("Erro ao atualizar usuário no sistema: {}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) throws Exception {
        var userOptional = getById(id);

        if (userOptional.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado no sistema. Verifique se o ID está correto.");
        }
        var user = userOptional.get();
        user.setDeleted(true);
        updateById(id, user);
    }

    private User createUserFromResult(ResultSet rs) throws SQLException {
        var user = new User();

        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setDeleted(rs.getBoolean("deleted"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        return user;
    }
}
