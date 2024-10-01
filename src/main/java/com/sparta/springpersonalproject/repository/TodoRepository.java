package com.sparta.springpersonalproject.repository;

import com.sparta.springpersonalproject.entity.Admin;
import com.sparta.springpersonalproject.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    public Todo save(Todo entity) {
        String sql = "INSERT INTO todo (todo, admin_id, password, create_at, update_at) values (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, entity.getTodo());
            ps.setObject(2, entity.getAdmin().getId());
            ps.setObject(3, entity.getPassword());
            ps.setObject(4, entity.getCreateAt());
            ps.setObject(5, entity.getUpdateAt());
            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        entity.changeId(id);

        return entity;
    }

    public Optional<Todo> findById(long id) {
        String sql =
                """
                    SELECT
                        t.id,
                        t.todo,
                        t.password,
                        t.create_at,
                        t.update_at,
                        a.id as admin_id,
                        a.name as admin_name,
                        a.email as admin_email,
                        a.create_at as admin_create_at,
                        a.update_at as admin_update_at,
                        FROM todo t JOIN admin a ON t.admin_id = a.id
                        WHERE t.id = ?
                """;

        List<Todo> result = jdbcTemplate.query(sql, rowMapper(), id);
        return result.stream().findFirst();
    }

    public RowMapper<Todo> rowMapper() {
        return (rs, rowNum) -> {
            Admin admin = new Admin(
                    rs.getLong("admin_id"),
                    rs.getString("admin_name"),
                    rs.getString("admin_email"),
                    rs.getTimestamp("admin_create_at").toLocalDateTime(),
                    rs.getTimestamp("admin_update_at").toLocalDateTime()
            );

            return new Todo(
                    rs.getLong("id"),
                    rs.getString("todo"),
                    admin,
                    rs.getString("password"),
                    rs.getTimestamp("create_at").toLocalDateTime(),
                    rs.getTimestamp("update_at").toLocalDateTime()
            );
        };
    }

    public List<Todo> search(String date, String adminName, int page, int size){
        StringBuilder sql = new StringBuilder(
                """
                        SELECT
                        t.id,
                        t.todo,
                        t.password,
                        t.create_at,
                        t.update_at,
                        a.id as admin_id,
                        a.name as admin_name,
                        a.email as admin_email,
                        a.create_at as admin_create_at,
                        a.update_at as admin_update_at,
                        FROM todo t JOIN admin a ON t.admin_id = a.id
                        WHERE 1 = 1
                """
        );

        List<Object> params = new ArrayList<>();

        if(date != null) {
            LocalDate searchDate = LocalDate.parse(date);
            LocalDateTime startOfDay = searchDate.atTime(LocalTime.MIN);
            LocalDateTime endOfDay = searchDate.atTime(LocalTime.MAX);

            sql.append(" AND t.update_at BETWEEN ? AND ?");
            params.add(startOfDay);
            params.add(endOfDay);
        }

        if(adminName != null) {
            sql.append(" AND a.name = ?");
            params.add(adminName);
        }

        sql.append(" ORDER BY t.update_at DESC");

        int offset = page * size;
        sql.append(" LIMIT ? OFFSET ?");
        params.add(size);
        params.add(offset);

        return jdbcTemplate.query(sql.toString(), rowMapper(), params.toArray());
    }

    public void update(Todo entity) {
        String sql = "UPDATE todo SET todo = ?, admin_id = ? WHERE id = ?";

        List<String> params = new ArrayList<>();
        params.add(entity.getTodo());
        params.add(entity.getAdmin().getId().toString());
        params.add(String.valueOf(entity.getId()));

        jdbcTemplate.update(sql, params.toArray());
    }

    public void delete(long todoId) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql, todoId);
    }

}