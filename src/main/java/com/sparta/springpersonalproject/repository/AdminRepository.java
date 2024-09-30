package com.sparta.springpersonalproject.repository;

import com.sparta.springpersonalproject.entity.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdminRepository {
    private final JdbcTemplate jdbcTemplate;

    public Admin save(Admin entity) {
        String sql = "insert into admin(name, email, create_at, update_at) values(?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, entity.getName());
            ps.setObject(2, entity.getEmail());
            ps.setObject(3, entity.getCreateAt());
            ps.setObject(4, entity.getUpdateAt());
            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        entity.changeId(id);

        return entity;
    }

    public Optional<Admin> findById(long id) {
        List<Admin> result = jdbcTemplate.query("SELECT * FROM admin WHERE id = ?", rowMapper(), id);
        return result.stream().findFirst();
    }

    public RowMapper<Admin> rowMapper() {
        return (rs, rowNum) -> {
            return new Admin(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getTimestamp("create_at").toLocalDateTime(),
                    rs.getTimestamp("update_at").toLocalDateTime()
            );
        };
    }

    public List<Admin> search() {
        String sql = "SELECT * FROM admin";
        return jdbcTemplate.query(sql, rowMapper());
    }

    public void update(Admin entity) {
        String sql = "UPDATE admin SET name = ?, email = ?, update_at = NOW() WHERE id = ?";

        List<String> params = new ArrayList<>();
        params.add(entity.getName());
        params.add(entity.getEmail());
        params.add(String.valueOf(entity.getId()));

        jdbcTemplate.update(sql, params.toArray());
    }

    private void delete(long AdminId){
        String sql = "DELETE FROM admin WHERE id = ?";
        jdbcTemplate.update(sql, AdminId);
    }

}
