package com.example.board.dao;

import com.example.board.dto.Board;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertBoard;

    // 생성자 주입, 스프링이 자동으로 HikariCP Bean을 주입한다.
    public BoardDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertBoard = new SimpleJdbcInsert(dataSource)
                .withCatalogName("exampledb")
                .withTableName("board")
                .usingGeneratedKeyColumns("board_id");
    }

    @Transactional
    public void addBoard(int userId, String title, String content) {
        Board board = new Board();
        board.setUserId(userId);
        board.setTitle(title);
        board.setContent(content);
        board.setRegDate(LocalDateTime.now());
        SqlParameterSource params = new BeanPropertySqlParameterSource(board);
        insertBoard.execute(params);
    }

    @Transactional(readOnly = true)
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) as total_count FROM board"; // 무조건 1건의 데이터
        Integer totalCount =  jdbcTemplate.queryForObject(sql, Map.of(), Integer.class);
        return totalCount.intValue();
    }

    @Transactional(readOnly = true)
    public List<Board> getBoards(int page) {
        // start 는 0, 10, 20, 30, 40, 50, 60, 70, 80, 90
        int start = (page - 1) * 10;
        String sql = "select b.user_id, b.board_id, b.title, b.content, b.regdate, b.view_cnt, u.name from board b, user u where b.user_id = u.user_id  order by board_id desc limit :start, 10";
        RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);
        List<Board> list = jdbcTemplate.query(sql, Map.of("start", start), rowMapper);
        return list;
    }


    // 이런 쿼리가 필요하다.
    // select b.user_id, b.board_id, b.title,b.content, b.regdate, b.view_cnt, u.name from board b, user u
    // where b.user_id = u.user_id and board_id= 1;
    @Transactional(readOnly = true)
    public Board getBoard(int boardId) {
        // 0건 또는 1건
        String sql = "select b.user_id, b.board_id, b.title,b.content, b.regdate, b.view_cnt, u.name from board b, user u where b.user_id = u.user_id and board_id= :boardId";

        Board board = jdbcTemplate.queryForObject(sql,Map.of("boardId", boardId), BeanPropertyRowMapper.newInstance(Board.class));
        return board;
    }


    @Transactional
    public void updateViewCnt(int boardId) {
        String sql = "update board set view_cnt = view_cnt + 1 where board_id = :boardId";
        jdbcTemplate.update(sql, Map.of("boardId", boardId));
    }


    @Transactional
    public void deleteBoard(int boardId) {
        String sql = "delete from board where board_id = :boardId";
        jdbcTemplate.update(sql, Map.of("boardId", boardId));
    }
}
