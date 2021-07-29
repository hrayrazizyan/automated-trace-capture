import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;

public class AutomatedTraceCapturing {
    public static void main(String[] args) throws IOException{
        try {
            File file = new File(args[0]);
            ObjectMapper objectMapper = new ObjectMapper();
            Config config = objectMapper.readValue(file, Config.class);
            write_to_file(config);
        }
        catch (UnrecognizedPropertyException e){
            e.printStackTrace();
        }
    }

    public static boolean write_to_file(Config config) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        if(config.limit <=0 || config.end - config.start <= 0){
            return false;
        }
        else if(config.limit <= 2000){
            String str = send_request(config);
            writer.write(str);
            writer.close();
        }
        else{
            int div_num = get_div_num(config.limit);
            int new_limit = config.limit / div_num;
            long new_time_span = (config.end - config.start) / div_num;
            long new_start = config.start;
            long new_end = new_start + new_time_span;

            for(int i = 0; i < div_num; i++){
                Config tmp = new Config(config.hostname, new_start, new_end, new_limit, config.token);
                String str = send_request(tmp);
                writer.append(str);
                new_start = new_end + 1;
                new_end += new_time_span;
            }
            writer.close();
        }
        return true;
    }


    public static int get_div_num(int limit) {
        if(limit % 2000 == 0){
            return limit / 2000;
        }
        else {
            return limit / 2000 + 1;
        }
    }

    public static String send_request(Config config) throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(config.hostname + "/chart/streaming/v2?request=%7B%22id%22%3A1626287244787%2C%22" +
                        "start%22%3A" + config.start + "000%2C%22" +
                        "end%22%3A" + config.end + "000%2C%22queries%22%3A%5B%7B%22name%22%3A%22traces%22%2C%22query%22%3A%22" +
                        "limit(" + config.limit + "%2C%20traces(%5C%22application%5C%22%3D%5C%22*%5C%22))%22%7D%5D%7D")
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + config.token)
                .build();
        Response response = client.newCall(request).execute();
        String str = response.body().string();
        return str;
    }
}



