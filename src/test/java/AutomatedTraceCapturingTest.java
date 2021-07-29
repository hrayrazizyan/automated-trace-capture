import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AutomatedTraceCapturingTest {


    @Test
    void inputParameterTest() throws IOException {
        assertThrows(UnrecognizedPropertyException.class, ()  ->{
            File file_bad = new File("src/test/resources/config_bad.json");
            ObjectMapper objectMapper = new ObjectMapper();
            Config config_bad = objectMapper.readValue(file_bad, Config.class);
        });
        File file_good = new File("src/test/resources/config_good.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Config config_good = objectMapper.readValue(file_good, Config.class);

        assertEquals(config_good, new Config("https://meru.wavefront.com", 1626279991, 1626279992, 100, "8112b2ca-67e2-452b-ae0f-b226a72ca01c"));

    }

    @Test
    void configParameterTest() throws IOException{
        File file_bad_limit = new File("src/test/resources/config_invalid_limit.json");
        File file_bad_time = new File("src/test/resources/config_invalid_time.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Config config_bad_limit = objectMapper.readValue(file_bad_limit, Config.class);
        Config config_bad_time = objectMapper.readValue(file_bad_time, Config.class);

        assertFalse(AutomatedTraceCapturing.write_to_file(config_bad_limit));
        assertFalse(AutomatedTraceCapturing.write_to_file(config_bad_time));
    }

    @Test
    void send_request() {
        assertEquals(AutomatedTraceCapturing.get_div_num(2500), 2);
        assertEquals(AutomatedTraceCapturing.get_div_num(4000), 2);
        assertEquals(AutomatedTraceCapturing.get_div_num(4100), 3);
    }
}
