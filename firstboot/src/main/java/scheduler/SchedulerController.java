package scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.messaging.support.AbstractHeaderMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SchedulerController {
	
	@Scheduled(initialDelay = 10000, fixedDelay = 3000)
	@GetMapping("/api/scheduler")
	public void callApi() throws IOException, InterruptedException {
		StringBuilder result = new StringBuilder();
		
		String urlStr = "	https://openapi.its.go.kr:9443/trafficInfo?apiKey=test&type=all&minX=126.800000&maxX=127.890000&minY=34.900000&maxY=35.100000&getType=json";
		
		URL url = new URL(urlStr.toString());
		HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
		
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
		
		String returnLine;
		
		while((returnLine = br.readLine()) != null){
			result.append(returnLine);
		}
		
		urlconnection.disconnect();
		System.out.println(result.toString());
		
	
		Thread.sleep(10000);
	}
}
