package com.cloudspend.cloudspendintegration.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ehcache.shadow.org.terracotta.offheapstore.HashingMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RequestJobDescriptionVO {
    private String type="file";
    private Credential credentials;
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Credential{
        private String partnerUserID;
        private String partnerUserSecret;
    }
    private ONReceive onReceive;

    @Getter
    @Setter
    public static class ONReceive{
        List<String> immediateResponse = Arrays.asList("returnRandomFileName");
    }
    private InputSettings inputSettings;
    @Getter
    @Setter
    public static class InputSettings{
        String type="combinedReportData";
        private Map<String,Object> filters=new HashMap<>();
    }
    private Map<String,Object> outputSettings=new HashMap<>();
}
