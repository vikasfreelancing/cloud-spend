package com.cloudspend.cloudspendintegration.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DownloadReportJobDiscriptionVO {
        private String type="download";
        private RequestJobDescriptionVO.Credential credentials;
        private String fileName;
        private String fileSystem ="integrationServer";
}
