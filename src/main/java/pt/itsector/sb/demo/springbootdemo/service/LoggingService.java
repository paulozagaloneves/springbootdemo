package pt.itsector.sb.demo.springbootdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pt.itsector.sb.demo.springbootdemo.dao.LoggingDao;
import pt.itsector.sb.demo.springbootdemo.entity.LogReqRes;

@Service
public class LoggingService {

    @Autowired
    LoggingDao loggingDao;

    @Async
    public void logReqRes(final LogReqRes logReqRes ) {
        loggingDao.save(logReqRes);
    }
}
