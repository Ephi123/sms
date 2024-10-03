package fileManagment.file.service.impl;

import fileManagment.file.domain.EthiopianCalendar;
import fileManagment.file.service.IdGeneratorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class IdGeneratorServiceImpl implements IdGeneratorService {
    private final JdbcTemplate jdbcTemplate;


    private int getLastId() {
        var lastValue = jdbcTemplate.queryForObject("SELECT last_value FROM id_generator WHERE user_id = 'user_id'",Integer.class);

        return lastValue == null? 0:lastValue;
    }


    private void updateLastId(int newValue) {
       jdbcTemplate.update("UPDATE id_generator SET last_value = ? WHERE user_id = 'user_id'",newValue);
    }
       @Override
    public String getUserId(String typeOfUser){
        var counter = new AtomicInteger(getLastId());
         var year = EthiopianCalendar.ethiopianYear();
        var num = counter.incrementAndGet();
              updateLastId(num);
        return "DA-"+typeOfUser+"-"+year+String.format("-%02d",num);

        
    }
}
