package com.sparta.springpersonalproject.repository;

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
        String sql = "INSERT INTO todo (todo, admin_name, password, create_at, update_at) values (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, entity.getTodo());
            ps.setObject(2, entity.getAdminName());
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
        List<Todo> result = jdbcTemplate.query("SELECT * FROM todo WHERE id = ?", rowMapper(), id);
        return result.stream().findFirst();
    }

    public RowMapper<Todo> rowMapper() {
        return (rs, rowNum) -> {
            return new Todo(
                    rs.getLong("id"),
                    rs.getString("todo"),
                    rs.getString("admin_name"),
                    rs.getString("password"),
                    rs.getTimestamp("create_at").toLocalDateTime(),
                    rs.getTimestamp("update_at").toLocalDateTime()
            );
        };
    }

    public List<Todo> search(String date, String adminName){
        StringBuilder sql = new StringBuilder("SELECT * FROM todo WHERE 1=1");
        List<String> params = new ArrayList<>();

        if(date != null) {
            LocalDate searchDate = LocalDate.parse(date);
            LocalDateTime startOfDay = searchDate.atTime(LocalTime.MIN);
            LocalDateTime endOfDay = searchDate.atTime(LocalTime.MAX);

            sql.append(" AND update_at BETWEEN ? AND ?");
            params.add(startOfDay.toString());
            params.add(endOfDay.toString());
        }

        if(adminName != null) {
            sql.append(" AND admin_name = ?");
            params.add(adminName);
        }

        sql.append(" ORDER BY update_at DESC");

        return jdbcTemplate.query(sql.toString(), rowMapper(), params.toArray());
    }

    public void update(Todo entity) {
        String sql = "UPDATE todo SET todo = ?, admin_name = ?, update_at = NOW() WHERE id = ?";

        List<String> params = new ArrayList<>();
        params.add(entity.getTodo());
        params.add(entity.getAdminName());
        params.add(String.valueOf(entity.getId()));

        jdbcTemplate.update(sql, params.toArray());
    }

    public void delete(long todoId) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql, todoId);
    }

}