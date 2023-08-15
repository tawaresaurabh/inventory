package fi.plasmonics.inventory.setup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;


@Component
@Profile("local")
@Slf4j
public class SetUpItemDataLoader {


    @EventListener({ApplicationReadyEvent.class})
    public void setupDevEnvironment() {


    }

    private static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }


    private void postRequest(String apiPath , String contentType, String acceptType,  String body){
        RestTemplate restTemplate = getRestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", contentType);
        headers.add("accept",  acceptType);

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        try{
            restTemplate.postForObject(apiPath, request, String.class);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

}
