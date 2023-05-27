package pt.itsector.sb.demo.springbootdemo.dao;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.itsector.sb.demo.springbootdemo.entity.LogReqRes;
@Transactional
public interface LoggingDao extends JpaRepository<LogReqRes, Long>{

}
